package com.x.tuangou_shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class Feedback extends Activity {
    EditText num, txt;
    Button sure;
    ImageView back;
    String number, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bussback);
        num = (EditText) findViewById(R.id.feedback_num);
        txt = (EditText) findViewById(R.id.feedback_txt);
        sure = (Button) findViewById(R.id.feedback_sure);
        back = (ImageView) findViewById(R.id.feeback_img);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        sure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                number = num.getText().toString();
                content = txt.getText().toString();
                if (num.getText().toString().trim().equals("")) {
                    Toast.makeText(Feedback.this, "请输入您的联系方式",
                            Toast.LENGTH_SHORT).show();
                } else if (txt.getText().toString().trim().equals("")) {
                    Toast.makeText(Feedback.this, "请输入您的意见",
                            Toast.LENGTH_SHORT).show();
                } else {
                   // getstr();
                }
            }

        });
    }



    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();

    }

}
