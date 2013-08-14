package com.example.myweibo;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeiboTitleBar extends LinearLayout{

	Context context;
	public WeiboTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		TextView user_name = (TextView) findViewById(R.id.user_name);
		user_name.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<String> data = new ArrayList<String>();
				data.add("朋友");
				data.add("家人");
				data.add("同事");
				ListDialog dialog = new ListDialog(context, data);
                dialog.show();
				int[] location = new int[2];
				int height = v.getHeight();
				v.getLocationOnScreen(location);
				WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.x = 0;
                lp.y = location[1] + height;
                lp.width = 120;
                dialog.getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                dialog.getWindow().setAttributes(lp);
			}
		});
		
//		((TextView) findViewById(R.id.user_name)).setText(msg.getData()
//		.getString("screen_name"));
		
		TextView write_weibo = (TextView)findViewById(R.id.write_weibo);
		write_weibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, WriteWeiboActivity.class);
				context.startActivity(intent);
			}
		});
	}
}
