package com.example.myweibo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeiboViewAdapter extends BaseAdapter{

	public final class WeiboViewHolder{
		public ImageView friendIcon;
		public TextView screenName;
		public TextView friend_time;
		public TextView weibo_text;
		public TextView weibo_reposts_count;
		public TextView weibo_comments_count;
		public TextView weibo_attitudes_count;
		public TextView retweeted_status_weibo_text;
	}
	
	private LayoutInflater inflater;
	private List<WeiboData> mData;
	
	public WeiboViewAdapter(Context context, List<WeiboData> data){
		this.inflater = LayoutInflater.from(context);
		mData = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		WeiboViewHolder weiboViewHolder = new WeiboViewHolder();
		convertView = inflater.inflate(R.layout.weibo_view, null);
		weiboViewHolder.friendIcon = (ImageView )convertView.findViewById(R.id.friend_icon);
		weiboViewHolder.screenName = (TextView)convertView.findViewById(R.id.user_groud);
		weiboViewHolder.friend_time = (TextView)convertView.findViewById(R.id.created_at);
		weiboViewHolder.weibo_text = (TextView)convertView.findViewById(R.id.weibo_text);
		weiboViewHolder.weibo_reposts_count = (TextView)convertView.findViewById(R.id.weibo_reposts_count);
		weiboViewHolder.weibo_comments_count = (TextView)convertView.findViewById(R.id.weibo_comments_count);
		weiboViewHolder.weibo_attitudes_count = (TextView)convertView.findViewById(R.id.weibo_attitudes_count);
		weiboViewHolder.retweeted_status_weibo_text = (TextView)convertView.findViewById(R.id.retweeted_status_weibo_text);
		
		if(mData.get(position).getProfileImage() != null){
			weiboViewHolder.friendIcon.setImageBitmap(mData.get(position).getProfileImage());			
		}
		weiboViewHolder.screenName.setText(mData.get(position).getScreenName());
		weiboViewHolder.friend_time.setText(mData.get(position).getCreatedAt());
		weiboViewHolder.weibo_text.setText(mData.get(position).getText());
		weiboViewHolder.weibo_reposts_count.setText("杞彂 "+mData.get(position).getRepostsCount());
		weiboViewHolder.weibo_comments_count.setText("璇勮 "+mData.get(position).getCommentsCount());
		weiboViewHolder.weibo_attitudes_count.setText("璧�"+mData.get(position).getAttitudesCount());
		String text = mData.get(position).getReteetedStatusText();
		if(mData.get(position).getReteetedStatusText().isEmpty()){
			weiboViewHolder.retweeted_status_weibo_text.setVisibility(View.INVISIBLE);
		}
		else{
			weiboViewHolder.retweeted_status_weibo_text.setText(mData.get(position).getReteetedStatusText());
		}

		convertView.setTag(weiboViewHolder);
		
//		weiboViewHolder.screenName.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				System.out.println("screenName, getId = " + v.get);
//			}
//		});
		return convertView;
	}
	
}

