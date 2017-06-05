package com.x.tuangou_shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.x.net.XActivityindicator;
import com.x.util.BaseActivity;
import com.x.util.DataCache;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    TextView title;

    @Override
    protected void setupUi() {
        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.home_title);

        title.setText(DataCache.getInstance().user.getName());

    }

    @Override
    protected void setupData() {

    }

    public void to_orderlist(View v)
    {
        pushVC(OrderListVC.class);
    }

    public void to_dplist(View v)
    {
        pushVC(CommentListVC.class);
    }

    public void to_setup(View v)
    {
        pushVC(APPConfig.class);
    }



    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            XActivityindicator.showToast("再按一次退出程序");

            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {

            finish();
            System.exit(0);
        }
    }

}
