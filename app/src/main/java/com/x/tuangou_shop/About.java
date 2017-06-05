package com.x.tuangou_shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.x.net.XAPPUtil;


public class About extends Activity {
	TextView versionname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		versionname=(TextView)findViewById(R.id.versionname);
		versionname.setText("软件版本 v"+ XAPPUtil.getAppVersionName(this));

	}

	public void to_hezuo(View v)
	{
		Intent intent = new Intent();
		intent.setClass(this,XHtmlVC.class);
		intent.putExtra("url","http://www.baidu.com");
		startActivity(intent);
	}

	public void to_xieyi(View v)
	{
		Intent intent = new Intent();
		intent.setClass(this,XHtmlVC.class);
		intent.putExtra("url","http://www.baidu.com");
		startActivity(intent);
	}

	public void back(View v)
	{
		finish();
	}

}
