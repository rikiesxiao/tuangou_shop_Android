package com.x.tuangou_shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.MyEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;


/**
 * Created by X on 2016/11/27.
 */

public class XHtmlVC extends BaseActivity {

    private WebView web;
    private String url,id;
    protected Handler handlers = new Handler();
    private RelativeLayout navbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xhtmlvc);

        title = (TextView) findViewById(R.id.tv_title_name);
        navbar = (RelativeLayout) findViewById(R.id.xhtml_navbar);

        web = (WebView)findViewById(R.id.web);
        // 设置支持JavaScript等
        WebSettings mWebSettings = web.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(false);
        mWebSettings.setGeolocationEnabled(false);
        mWebSettings.setAppCacheEnabled(false);

        web.setWebViewClient(new WebViewClient(){

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString().toLowerCase();
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                return false;

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String u) {
                String url = u.toLowerCase();
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                return false;
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                XNetUtil.APPPrintln("request000: "+request.getUrl());
                XNetUtil.APPPrintln("errorResponse000: "+errorResponse.toString());
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                XNetUtil.APPPrintln("request111: "+request.getUrl());
                XNetUtil.APPPrintln("errorResponse111: "+error.toString());
            }
        });

        web.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void runAndroidMethod(final String str) {
                handlers.post(new Runnable() {

                    @Override
                    public void run() {
                        //JSONObject obj = JSON.parseObject(str);
                        //HandleJSMsg.handle(obj,XHtmlVC.this);
                    }
                });

            }

        }, "android");


        if(getIntent().getBooleanExtra("hideNavBar",false) == true)
        {
            navbar.setVisibility(View.GONE);
        }

        String t = getIntent().getStringExtra("title");
        t = t == null ? "" : t;

        title.setText(t);

        url = getIntent().getStringExtra("url");
        id = getIntent().getStringExtra("id");

        web.loadUrl(url);

        if(url.contains("ctl=uc_order&act=app_order_info&id="))
        {
            XNetUtil.APPPrintln("url has $$$$$$$$$$$$$$$$$$");
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void setupUi() {

    }

    @Override
    protected void setupData() {

    }

    public void back(View v)
    {
        finish();
    }



    @Subscribe
    public void getEventmsg(MyEventBus myEventBus) {

        if (myEventBus.getMsg().equals("OrderInfoNeedRefresh")) {
            web.reload();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
