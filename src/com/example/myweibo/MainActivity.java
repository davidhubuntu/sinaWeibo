package com.example.myweibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;

public class MainActivity extends Activity {

	private static final String appKey = "4122757399";
	private static final String redirectUrl = "http://www.sina.com";
	String FRIEND_TIMELINE = "https://api.weibo.com/2/statuses/friends_timeline.json";
	String access_token = "";
	String expires_in = "";
	static WeiboData weiboData;
	ViewGroup viewGroup;
	WeiboHandle weiboHandle;
	List<WeiboData> listWeiboData = new ArrayList<WeiboData>();
	public static final int UPDATE_USER_SHOW = 1;
	public static final int UPDATE_WEIBO = 2;
	boolean is_timeline_running = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_view);
		
		weiboHandle = new WeiboHandle();
		Weibo weibo = new Weibo().getInstance(appKey, redirectUrl);
		weibo.authorize(MainActivity.this, new BaseListener() {
			@Override
			public void onComplete(Bundle bundle) {
				access_token = bundle.getString("access_token");
				Weibo.setAccessToken(access_token);
				new Thread(runnable_timeline).start();
				// new Thread(user_show).start();
			}
		});

	}

	class WeiboHandle extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case UPDATE_WEIBO:
				GridView gridview = (GridView) findViewById(R.id.gridview);
				gridview.setOnScrollListener(new OnScrollListener() {
					
					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
						switch (scrollState) {
						case OnScrollListener.SCROLL_STATE_IDLE:
							if(view.getLastVisiblePosition() == view.getCount() - 1){
								System.out.println("getLastVisiblePosition      " + view.getCount());
								if(!is_timeline_running){
									new Thread(runnable_timeline).start();
									is_timeline_running = true;
							}
							if(0 == view.getFirstVisiblePosition()){
								System.out.println("getFirstVisiblePosition     00");
								}
							}
							break;
						}
					}
					
					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {
						
					}
				});
				
				gridview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long rowid) {
						HashMap<String, Object> item = (HashMap<String, Object>) parent
								.getItemAtPosition(position);
						String screen_name = (String) item.get("screen_name");
						System.out.println("** screen_name = " + screen_name);
					}
				});

				gridview.setColumnWidth(getWindowManager().getDefaultDisplay()
						.getWidth());

				WeiboViewAdapter adapter = new WeiboViewAdapter(
						MainActivity.this, listWeiboData);
				gridview.setAdapter(adapter);
				is_timeline_running = false;
				break;

			default:
				break;
			}
		}
	}

	Runnable updataStatus = new Runnable() {

		@Override
		public void run() {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("status",
					"test Sina Weibo's open API, hahaha"));
			params.add(new BasicNameValuePair("access_token", access_token));
			String url = "https://api.weibo.com/2/statuses/update.json";
			System.out.println("url = " + url);
			try {
				HttpManager.doPost(url, params);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	Runnable runnable_timeline = new Runnable() {

		@Override
		public void run() {
			String url = friendsTimeline(0, Weibo.MaxID(), 5, 1, false, false,
					access_token);
			String response = HttpManager.doGet(url);
			// System.out.println("response = " + response);
			Parse parse = new Parse();
			 List<WeiboData> list = parse.ParseFriendsTimeline(response, new ParseListener() {

					@Override
					public void onComplete(WeiboData data) {
					}

					@Override
					public void onComplete(List<WeiboData> data) {
//							listWeiboData = data;
//							Message msg = weiboHandle.obtainMessage();
//							msg.arg1 = UPDATE_WEIBO;
//							weiboHandle.sendMessage(msg);
//							System.out.println("ParseFriendsTimeline  finished  !!");
					}
				});
			System.out.println("  ** list.size  " + list.size());
			listWeiboData.addAll(list);
			Message msg = weiboHandle.obtainMessage();
			msg.arg1 = UPDATE_WEIBO;
			weiboHandle.sendMessage(msg);
		}
	};

	Runnable user_show = new Runnable() {
		@Override
		public void run() {
			String url = "https://api.weibo.com/2/users/show.json" + "?"
					+ "uid=" + getUid() + "&" + "access_token=" + access_token;
			String response = HttpManager.doGet(url);
			JSONObject friend_json;
			String screen_name = "";
			try {
				friend_json = new JSONObject(response);
				screen_name = friend_json.getString("screen_name");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("screen_name = " + screen_name);
			Message msg = weiboHandle.obtainMessage();
			msg.arg1 = UPDATE_USER_SHOW;
			Bundle b = new Bundle();
			b.putString("screen_name", screen_name);
			msg.setData(b);
			weiboHandle.sendMessage(msg);
		}
	};

	private String getUid() {
		String ret = "";
		String get_uid = "https://api.weibo.com/2/account/get_uid.json";
		String response = HttpManager.doGet(get_uid + "?" + "access_token="
				+ access_token);
		JSONObject friend_json;
		try {
			friend_json = new JSONObject(response);
			ret = friend_json.getString("uid");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private String friendsTimeline(long since_id, long max_id, int count,
			int page, Boolean base_app, Boolean trim_user, String access_token) {

		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("since_id", "" + since_id));
		params.add(new BasicNameValuePair("max_id", "" + max_id));
		params.add(new BasicNameValuePair("count", "" + count));
		params.add(new BasicNameValuePair("page", "" + page));
		params.add(new BasicNameValuePair("base_app", "" + 0));
		params.add(new BasicNameValuePair("feature", "" + 0));
		params.add(new BasicNameValuePair("trim_user", "" + 0));
		params.add(new BasicNameValuePair("access_token", access_token));

		return FRIEND_TIMELINE + "?" + utils.bindUrlParams(params);
	}

}
