package com.x.net;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.x.util.ApplicationClass;
import com.x.util.DensityUtil;

import java.lang.ref.WeakReference;

/**
 * Created by X on 2016/10/2.
 */

public class XActivityindicator {

    private static SVProgressHUD hud;
    private static WeakReference<AlertView> alert;
    private static Toast toast;

    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            if(toast != null)
            {
                toast.cancel();
                toast=null;
            }
        }
    };

    public static void setAlert(AlertView alert) {
        XActivityindicator.alert = new WeakReference<AlertView>(alert);
    }

    public static void showToast(String str)
    {
        if(toast != null )
        {
            toast.setText(str);
        }
        else
        {
            Toast t = Toast.makeText(ApplicationClass.context, str, Toast.LENGTH_SHORT);
            toast = t;
            mHandler.postDelayed(r, 1500);
            t.show();
        }

    }

    public static void hide()
    {
        XNetUtil.APPPrintln("XActivityindicator hide ^^^^^^^^");
        if(alert != null && alert.get() != null)
        {
            alert.get().dismissImmediately();
            alert.clear();
            alert = null;
        }

        if(hud != null)
        {
            hud.dismissImmediately();
            hud = null;
        }
    }

    public static SVProgressHUD getHud() {
        return hud;
    }

    public static SVProgressHUD create()
    {
        return create(null);
    }

    public static SVProgressHUD create(Context context)
    {
        if(alert != null && alert.get() != null)
        {
            alert.get().dismissImmediately();
        }

        if(hud != null)
        {
            hud.setOnDismissListener(null);
            hud.dismissImmediately();
            hud = null;
        }

        hud = new SVProgressHUD(ApplicationClass.context);

        hud.getProgressBar().setRoundWidth(DensityUtil.dip2px(ApplicationClass.context,1));

        return hud;
    }

}
