package com.x.tuangou_shop;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.x.model.BankModel;
import com.x.net.XAPPUtil;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.DataCache;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by x on 2017/6/7.
 */

public class BankInfoVC extends BaseActivity {

    EditText nameET,accountET,pnameET,codeET;
    TextView codeBtn;
    TimerTask task;
    private int recLen = 60;
    Timer timer = new Timer();
    BankModel bankModel;

    @Override
    protected void setupUi() {

        setContentView(R.layout.bank_info);
        nameET = (EditText) findViewById(R.id.bank_info_name);
        accountET = (EditText) findViewById(R.id.bank_info_account);
        pnameET = (EditText) findViewById(R.id.bank_info_pname);
        codeET = (EditText) findViewById(R.id.bank_info_code);
        codeBtn = (TextView) findViewById(R.id.bank_info_codebtn);

    }

    @Override
    protected void setupData() {

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
                    nameET.setText(model.getBank_name());
                    accountET.setText(model.getBank_info());
                    pnameET.setText(model.getBank_user());
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
        String sid = DataCache.getInstance().user.getSid();
        String tel = DataCache.getInstance().user.getMobile();
        String bank_name = nameET.getText().toString().trim();
        String bank_info = accountET.getText().toString().trim();
        String bank_user = pnameET.getText().toString().trim();
        String code = codeET.getText().toString().trim();

        if(bank_name.length() * bank_info.length() * bank_user.length() * code.length() == 0)
        {
            XActivityindicator.showToast("请完善信息！");
            return;
        }

        XNetUtil.Handle(APPService.do_save_bank(sid,bank_name,bank_info,bank_user,tel,code), "信息更新成功", null, new XNetUtil.OnHttpResult<Boolean>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(Boolean aBoolean) {

                if(aBoolean)
                {

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

}
