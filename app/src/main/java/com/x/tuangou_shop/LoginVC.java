package com.x.tuangou_shop;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.x.model.UserModel;
import com.x.net.ServicesAPI;
import com.x.net.XAPPUtil;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.DataCache;
import com.x.util.MyEventBus;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class LoginVC extends BaseActivity {

    EditText accountET,passET;

    @Override
    protected void setupUi() {
        setContentView(R.layout.login);
        accountET = (EditText) findViewById(R.id.login_account);
        passET = (EditText) findViewById(R.id.login_pass);

        isPush = false;


    }

    @Override
    protected void setupData() {

    }

    public void doLogin(View v)
    {

        if(!XAPPUtil.isNull(accountET) || !XAPPUtil.isNull(passET))
        {
            return;
        }

        String account = accountET.getText().toString().trim();
        String pass = passET.getText().toString().trim();

        if(pass.length() < 6 || pass.length() > 24)
        {
            XActivityindicator.showToast("密码为6-24位");
            return;
        }

        XActivityindicator.create().show();

        XNetUtil.Handle(APPService.dologin(account, pass), new XNetUtil.OnHttpResult<UserModel>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(UserModel userModel) {

                if(userModel != null)
                {
                    XActivityindicator.hide();

                    DataCache.getInstance().user = userModel;
                    XAPPUtil.saveAPPCache("User",userModel);

                    presentVC(MainActivity.class);
                    finish();
                }

            }
        });

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
