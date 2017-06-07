package com.x.util;

import android.app.Activity;
import android.content.Intent;

import com.robin.lazy.cache.CacheLoaderManager;
import com.x.model.UserModel;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.tuangou_shop.LoginVC;
import com.x.tuangou_shop.R;

import org.greenrobot.eventbus.EventBus;

import static com.x.util.ApplicationClass.APPService;
import static com.x.util.ApplicationClass.context;


/**
 * Created by X on 2016/10/2.
 */

public class DataCache {

    private static volatile DataCache instance=null;

    public static  DataCache getInstance(){
        if(instance==null){
            synchronized(DataCache .class){
                if(instance==null){
                    instance=new DataCache ();
                }
            }
        }
        return instance;
    }

    public int land = 0;
    public boolean msgshow = false;

    public UserModel user;

    public void init()
    {
        XNetUtil.APPPrintln("DataCache is init!!!!!!!!!!!");


    }


    private DataCache()
    {
        user = CacheLoaderManager.getInstance().loadSerializable("User");

        if(user != null)
        {
            String id = user.getId();
            String sid = user.getSid();
            String a = user.getAccount_name();

            XNetUtil.Handle(APPService.user_init(id, sid, a), new XNetUtil.OnHttpResult<String>() {
                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onSuccess(String sess_id) {

                    if(sess_id != null)
                    {
                        user.setSess_id(sess_id);
                    }
                    else
                    {
                        user = null;
                        CacheLoaderManager.getInstance().delete("User");
                        if(context instanceof Activity)
                        {
                            Intent intent = new Intent(context,LoginVC.class);
                            context.startActivity(intent);
                            ((Activity)context).overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                            EventBus.getDefault().post(new MyEventBus("UserLogouted"));
                        }
                    }
                }
            });
        }

    }

}
