package com.example.myweibo;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeiboItem extends LinearLayout {

	private TextView friend_name;
	private TextView friend_time;
	private TextView weibo_text;
	private TextView weibo_reposts_count;
	private TextView weibo_comments_count;
	private TextView weibo_attitudes_count;
	private ImageView friend_icon;
	
	public WeiboItem(Context context) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.weibo_view, this);
		
		friend_name = (TextView)findViewById(R.id.user_groud);
		friend_time = (TextView)findViewById(R.id.created_at);
		weibo_text = (TextView)findViewById(R.id.weibo_text);
		weibo_reposts_count = (TextView)findViewById(R.id.weibo_reposts_count);
		weibo_comments_count = (TextView)findViewById(R.id.weibo_comments_count);
		weibo_attitudes_count = (TextView)findViewById(R.id.weibo_attitudes_count);
		friend_icon = (ImageView)findViewById(R.id.friend_icon);
	}
	
	public void setWeibo(String idStr, String time, String source, String text){
		friend_name.setText(idStr);
		friend_time.setText(time);
		weibo_text.setText(text);
	}

	public void setWeiboFriendTime(String time){
		friend_time.setText(time);
	}

	public void setWeiboFriendName(String name){
		friend_name.setText(name);
	}
	
	public void setWeiboText(String text){
		weibo_text.setText(text);
	}
	
	public void setWeiboRespostsCount(String reposts_count){
		weibo_reposts_count.setText(reposts_count);
	}
	
	public void setWeiboCommentsCount(String comments_count){
		weibo_comments_count.setText(comments_count);
	}
	
	public void setWeiboAttitudesCount(String attitudes_count){
		weibo_attitudes_count.setText(attitudes_count);
	}
	
	public void setWeiboProfileImage(Bitmap image){
		friend_icon.setImageBitmap(image);
	}
}
