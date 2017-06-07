package com.x.tuangou_shop;

import android.os.Bundle;
import android.view.View;
import com.google.zxing.Result;
import com.x.net.XActivityindicator;
import com.x.util.BaseActivity;
import com.x.util.MyEventBus;

import org.greenrobot.eventbus.EventBus;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class QCScanVC extends BaseActivity {

    private boolean mFlash;
    private ZXingScannerView mScannerView;
    boolean post = false;

    private ZXingScannerView.ResultHandler mResultHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result result) {
            Bundle bundle = new Bundle();

            if(result.getText().indexOf("tuan") != 0)
            {
                XActivityindicator.showToast("此二维码不是团购券二维码，无法验证");
            }
            else
            {
                if(!post)
                {
                    bundle.putString("code",result.getText().replace("tuan",""));
                    pushVC(CheckCouponVC.class,bundle);
                }
                else
                {
                    EventBus.getDefault().post(new MyEventBus("CouponCode",result.getText().replace("tuan","")));
                }

            }

            finish();

        }
    };

    @Override
    protected void setupUi() {
        setContentView(R.layout.qcscan);

        post = getIntent().getBooleanExtra("post",false);

        mScannerView = (ZXingScannerView) findViewById(R.id.scannerView);
        mScannerView.setResultHandler(mResultHandler);

        findViewById(R.id.light).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFlash();
            }
        });

    }

    @Override
    protected void setupData() {

    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(mResultHandler);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    private void toggleFlash() {
        mFlash = !mFlash;
        mScannerView.setFlash(mFlash);
    }
}
