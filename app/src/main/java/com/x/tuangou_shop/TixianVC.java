package com.x.tuangou_shop;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bigkoo.svprogresshud.listener.OnDismissListener;
import com.x.model.AccountsModel;
import com.x.model.BankModel;
import com.x.net.XAPPUtil;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.DataCache;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by x on 2017/6/7.
 */

public class TixianVC extends BaseActivity {

    EditText numET,codeET;
    TextView codeBtn,canuseTV,bnameTV,baccountTV;
    TimerTask task;
    private int recLen = 60;
    Timer timer = new Timer();
    BankModel bankModel;
    AccountsModel accountsModel;

    @Override
    protected void setupUi() {

        setContentView(R.layout.tixian);
        numET = (EditText) findViewById(R.id.tixian_num);
        codeET = (EditText) findViewById(R.id.tixian_code);

        codeBtn = (TextView) findViewById(R.id.tixian_codebtn);
        canuseTV = (TextView) findViewById(R.id.tixian_can);
        bnameTV = (TextView) findViewById(R.id.tixian_bname);
        baccountTV = (TextView) findViewById(R.id.tixian_baccount);


    }

    @Override
    protected void setupData() {

        getMoney();

        String sid = DataCache.getInstance().user.getSid();

        XNetUtil.Handle(APPService.bank_info(sid), new XNetUtil.OnHttpResult<BankModel>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(BankModel model) {

                if(model != null)
                {
                    bankModel = model;
                    bnameTV.setText(model.getBank_name());
                    baccountTV.setText(model.getBank_info());
                }

            }
        });

    }

    private void getMoney()
    {
        String sid = DataCache.getInstance().user.getSid();

        XNetUtil.Handle(APPService.accounts_count(sid), new XNetUtil.OnHttpResult<AccountsModel>() {
            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onSuccess(AccountsModel model) {
                if(model != null)
                {
                    accountsModel = model;

                    double v1 = accountsModel.getMoney()*(1.0-accountsModel.getBili());
                    BigDecimal bd = new BigDecimal(v1);
                    bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);

                    canuseTV.setText(bd.doubleValue()+"");
                }

            }
        });
    }

    public void  do_sendmsg(View v)
    {
        XActivityindicator.create().show();

        final String tel = DataCache.getInstance().user.getMobile();

        XNetUtil.Handle(APPService.sms_send_code(tel), "验证码发送成功", null, new XNetUtil.OnHttpResult<Boolean>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(Boolean aBoolean) {

                if(aBoolean)
                {
                    XAPPUtil.saveAPPCache("SMSTIME",((new Date()).getTime()/1000)+"");
                    codeBtn.setEnabled(false);
                    gettime();
                }
            }
        });
    }

    public void  do_submit(View v)
    {
        if(accountsModel == null || bankModel == null)
        {
            XActivityindicator.showToast("余额信息获取失败，请重试");
            return;
        }

        if(bankModel.getBank_info().length() * bankModel.getBank_name().length() * bankModel.getBank_user().length() == 0)
        {
            XActivityindicator.showToast("请先完善银行账户信息");
            return;
        }

        String sid = DataCache.getInstance().user.getSid();
        String money = numET.getText().toString().trim();
        String code = codeET.getText().toString().trim();

        if(money.length() * code.length() == 0)
        {
            XActivityindicator.showToast("请完善信息！");
            return;
        }

        try{
            float f = Float.parseFloat(money);

            if(f<=0 || f>accountsModel.getMoney())
            {
                XActivityindicator.showToast("提现金额有误！");
                return;
            }

        }
        catch (Exception e)
        {
            XActivityindicator.showToast("请输入有效的提现金额！");
            return;
        }

        XActivityindicator.create().show();
        XNetUtil.Handle(APPService.do_tixian_submit(sid,money,code), "提交成功，请等待审核！", null, new XNetUtil.OnHttpResult<Boolean>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(Boolean aBoolean) {

                if(aBoolean)
                {
                    XActivityindicator.getHud().setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(SVProgressHUD hud) {
                            back();
                        }
                    });
                }
            }
        });


    }


    public void gettime() {

        if(task != null)
        {
            return;
        }

        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recLen--;
                        codeBtn.setText(recLen + "秒后再获取");
                        if (recLen < 0) {
                            codeBtn.setText("获取验证码");
                            recLen = 60;
                            codeBtn.setClickable(true);
                            task.cancel();
                            task = null;
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    public void to_history(View v)
    {
        pushVC(TixianListVC.class);
    }

}