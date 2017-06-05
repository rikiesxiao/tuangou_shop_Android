package com.x.tuangou_shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.robin.lazy.cache.CacheLoaderManager;
import com.x.net.XAPPUtil;
import com.x.util.BaseActivity;
import com.x.util.DataCache;
import com.x.util.FileSizeUtil;
import com.x.util.MyEventBus;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by X on 16/9/2.
 */
public class APPConfig extends BaseActivity {

    private TextView cacheSize;
    private Button btn;

    @Override
    protected void setupUi() {
        setContentView(R.layout.app_config);
        cacheSize = (TextView)findViewById(R.id.app_config_size);
        btn = (Button)findViewById(R.id.app_config_btn);
    }

    @Override
    protected void setupData() {

    }


    @Override
    protected void onResume() {
        super.onResume();

        refreshUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void refreshUI()
    {
        double size = FileSizeUtil.getFileOrFilesSize(ImageLoader.getInstance().getDiskCache().getDirectory().getPath(),3);

        cacheSize.setText(size+"M");

        if(XAPPUtil.APPCheckIsLogin())
        {
            btn.setText("退出登录");
        }
        else
        {
            btn.setText("登录");
        }
    }


    public void cleanCache(View v){
        ImageLoader.getInstance().getDiskCache().clear();
        cacheSize.setText("0.0M");
    }

    public void toFeed(View v){

        Intent intent = new Intent();
        intent.setClass(this,Feedback.class);
        startActivity(intent);

    }

    public void to_about(View v){

        Intent intent = new Intent();
        intent.setClass(this,About.class);
        startActivity(intent);

    }


    public void doLogout(View v)
    {
        if(DataCache.getInstance().user != null)
        {
            AlertView alert = new AlertView("注销登录", "确定要登出账户吗?", null, null,
                    new String[]{"取消", "确定"},
                    this, AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {

                    if(position == 1)
                    {
                        DataCache.getInstance().user = null;
                        CacheLoaderManager.getInstance().delete("User");

                        presentVC(LoginVC.class);
                        finish();
                        EventBus.getDefault().post(new MyEventBus("UserLogouted"));

                    }

                }
            });

            alert.show();
        }

    }


}
