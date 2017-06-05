package com.x.util;

import com.robin.lazy.cache.CacheLoaderManager;
import com.x.model.UserModel;
import com.x.net.XNetUtil;

import org.greenrobot.eventbus.EventBus;


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
//        nowCity = CacheLoaderManager.getInstance().loadSerializable("NowCity");
        user = CacheLoaderManager.getInstance().loadSerializable("User");
//        searchKeys = CacheLoaderManager.getInstance().loadSerializable("SearchKeys");
//        if(searchKeys == null){searchKeys = new SearchKeyModel();}
//
//        storesSearchKeys = CacheLoaderManager.getInstance().loadSerializable("StoresSearchKeys");
//        if(storesSearchKeys == null){storesSearchKeys = new SearchKeyModel();}
    }

}
