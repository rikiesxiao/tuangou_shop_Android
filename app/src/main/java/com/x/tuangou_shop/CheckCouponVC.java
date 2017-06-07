package com.x.tuangou_shop;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.robin.lazy.cache.CacheLoaderManager;
import com.x.model.CouponCheckModel;
import com.x.model.ShopsModel;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.MyEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by x on 2017/6/7.
 */

public class CheckCouponVC extends BaseActivity {

    EditText codeET,numET;
    TextView shopsTV,numTV;

    LinearLayout numLayout;

    Button btn;

    AlertView shopsSheet;

    List<ShopsModel> shops;

    ShopsModel choosedShop;

    CouponCheckModel checkModel;

    InputMethodManager imm;

    @Override
    protected void setupUi() {

        setContentView(R.layout.check_coupon);

        EventBus.getDefault().register(this);

        codeET = (EditText) findViewById(R.id.check_coupon_number);
        numET = (EditText) findViewById(R.id.check_coupon_num);

        numTV = (TextView) findViewById(R.id.check_coupon_numtxt);
        shopsTV = (TextView) findViewById(R.id.check_coupon_shops);

        numLayout = (LinearLayout) findViewById(R.id.check_coupon_numlayout);

        btn = (Button) findViewById(R.id.check_coupon_btn);

        String code = getIntent().getStringExtra("code");

        if(code != null)
        {
            codeET.setText(code);
        }

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    protected void setupData() {

        XNetUtil.Handle(APPService.shops_list(), new XNetUtil.OnHttpResult<List<ShopsModel>>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(List<ShopsModel> models) {

                if(models != null)
                {
                    shops = models;
                    initshops();
                }

            }
        });

    }

    private void initshops()
    {
        String[] names = new String[shops.size()];

        int i =0;
        for(ShopsModel m : shops)
        {
            names[i] = m.getName();
            i++;
        }

        shopsSheet = new AlertView("选择门店", null, "取消", null,
                names,
                this, AlertView.Style.ActionSheet, new OnItemClickListener(){
                public void onItemClick(Object o,int p){
                if(p < shops.size() && p >= 0)
                {
                    choosedShop = shops.get(p);
                    shopsTV.setText(choosedShop.getName());
                }
            }
        });

        if(shops != null)
        {
            choosedShop = shops.get(0);
            shopsTV.setText(choosedShop.getName());
        }


    }


    public void choose_shop(View v)
    {
        shopsSheet.show();
    }

    public void do_scan(View v)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("post",true);
        pushVC(QCScanVC.class,bundle);
    }

    public void do_submit(View v)
    {

        imm.hideSoftInputFromWindow(codeET.getWindowToken(),0);

        if(choosedShop == null)
        {
            XActivityindicator.showToast("请先选择门店");
            return;
        }

        String location_id = choosedShop.getId();
        String coupon_pwd = codeET.getText().toString().trim();

        if(coupon_pwd.length() == 0)
        {
            XActivityindicator.showToast("请输入团购券验证码");
            return;
        }

        if(btn.getText().toString().equals("确定消费"))
        {
            String num = numET.getText().toString().trim();

            try{
                int n = Integer.parseInt(num);

                if(n<0 || n > checkModel.getCount())
                {
                    XActivityindicator.showToast("数量输入有误，请检查");
                    return;
                }

            }
            catch (Exception e)
            {
                XActivityindicator.showToast("数量输入无效");
                return;
            }

            do_use_coupon_alert(location_id,coupon_pwd,num);

        }
        else
        {
            XNetUtil.Handle(APPService.check_coupon(location_id, coupon_pwd), new XNetUtil.OnHttpResult<CouponCheckModel>() {
                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onSuccess(CouponCheckModel couponCheckModel) {

                    if(couponCheckModel != null)
                    {
                        checkModel = couponCheckModel;
                        showNext();
                    }

                }
            });
        }

    }

    private void showNext()
    {
        XActivityindicator.hide();

        numTV.setText("使用数量：(剩余"+checkModel.getCount()+"张有效)");
        numLayout.setVisibility(View.VISIBLE);
        btn.setText("确定消费");

        AlertView alert = new AlertView("提醒", checkModel.getInfo()+"\r\n请输入使用张数进行消费", null, null,
                new String[]{"确定"},
                this, AlertView.Style.Alert, null);
        alert.show();
    }


    private void do_use_coupon_alert(final String id, final String pass, final String num)
    {
        XActivityindicator.hide();

        AlertView alert = new AlertView("提醒", checkModel.getInfo()+"\r\n确认使用 【"+num+"】 张?", null, null,
                new String[]{"取消", "确定"},
                this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {

                if(position == 1)
                {
                    do_use_coupon(id,pass,num);
                }

            }
        });
        alert.show();

        XActivityindicator.setAlert(alert);

    }


    private void do_use_coupon(String id,String pass,String num)
    {
        XNetUtil.Handle(APPService.use_coupon(id, pass, num), "消费成功", null, new XNetUtil.OnHttpResult<Boolean>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(Boolean aBoolean) {

                if(aBoolean)
                {
                    checkModel = null;
                    btn.setText("验证");
                    codeET.setText("");
                    numLayout.setVisibility(View.GONE);
                }

            }
        });

    }

    @Subscribe
    public void getEventmsg(MyEventBus myEventBus) {
        if (myEventBus.getMsg().equals("CouponCode")) {
            String str = (String) myEventBus.getInfo();
            codeET.setText(str);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
