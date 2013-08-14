package com.example.myweibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class WriteWeiboActivity extends Activity {

	EditText edit_weibo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_weibo);
		
		((TextView)findViewById(R.id.send_weibo)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("edit_weibo.getText() = " + edit_weibo.getText().toString());
			}
		});
		
		edit_weibo = ((EditText)findViewById(R.id.edit_weibo));
		edit_weibo.setHeight(getWindow().getWindowManager().getDefaultDisplay().getHeight()/3);
		
		Thread emotions = new Thread(emotionsRunnable);
		emotions.start();
	}
	
	
	Runnable emotionsRunnable = new Runnable() {

		@Override
		public void run() {
			
			String url = "https://api.weibo.com/2/emotions.json" + "?" + "access_token=" + Weibo.accessToken();
			String response = HttpManager.doGet(url);
//			System.out.println("response = " + response);
			
			Parse.parseEmotions(response);
		}
	};
}
