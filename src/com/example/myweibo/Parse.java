package com.example.myweibo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.ViewGroup;

public class Parse {

	public Parse() {
	}

	ViewGroup viewGroup;
	ParseListener parseListener;
	List<WeiboData> list = new ArrayList<WeiboData>();
	int last_postion;
	
	public List<WeiboData> ParseFriendsTimeline(String data,
			ParseListener parseListener) {

		this.parseListener = parseListener;

		JSONArray jsonArray;
		try {
			JSONObject friend_json = new JSONObject(data);
			jsonArray = friend_json.getJSONArray("statuses");
			last_postion = jsonArray.length()-1;
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject sub_object = jsonArray.getJSONObject(i);
				WeiboData data1 = new WeiboData();
				if(i == jsonArray.length()-1){
					Weibo.setMaxID(sub_object.getLong("id"));
					System.out.println("id === " + sub_object.getLong("id"));
				}
				data1.setCreatedAt(handleCreateTime(sub_object
						.getString("created_at")));
				data1.setText(sub_object.getString("text"));
				data1.setRepostsCount("" + sub_object.getInt("reposts_count"));
				data1.setCommentsCount("" + sub_object.getInt("comments_count"));
				data1.setAttitudesCount(""
						+ sub_object.getInt("attitudes_count"));
				// System.out.println("idstr = "
				// +sub_object.getString("idstr")+"\n");

				JSONObject userJsonObject = sub_object.getJSONObject("user");
				String profile_image_url = userJsonObject
						.getString("profile_image_url");
				String screen_name = userJsonObject.getString("screen_name");
				data1.setScreenName(screen_name);

				if (sub_object.has("retweeted_status")) {
					JSONObject retweeted_status_json_object = sub_object
							.getJSONObject("retweeted_status");
					// System.out.println("retweeted_status = " +
					// retweeted_status_json_object.getString("text"));
					data1.setReteetedStatusText(retweeted_status_json_object
							.getString("text"));
				} else {
					data1.setReteetedStatusText("");
				}

				list.add(data1);

				 PorfileImage porfileImage = new PorfileImage();
				 porfileImage.execute(data1, profile_image_url, i);
				 porfileImage.execute();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	private String handleCreateTime(String time) {
		time = time.substring(11, 16);
		return time;
	}

	private String handleAt(String str){
		List<String >result = new ArrayList<String>();
		  int start_index =str.indexOf("@");
		  int end_index = -1;
		  if(start_index != -1){
			  end_index = str.indexOf(":");
			  if(end_index != -1){
				  if(str.substring(start_index, end_index).isEmpty()){
					  result.add(str.substring(start_index, end_index));  
				  }
			  }
		  }
		  
		int count=0;
		int index=str.indexOf("x");
		if(index!=0) count=1;
	
		for(int j=0; j<str.length();j++){
			if(str.indexOf("x", index+1) != -1){
			    count++;
			}
		}
		return "";
	}
	public class PorfileImage extends AsyncTask<Integer, Integer, String> {

		WeiboData weiboData;
		String profile_image_url = null;
		int postion;

		@Override
		protected String doInBackground(Integer... params) {
			URL url = null;
			try {
				url = new URL(profile_image_url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				HttpURLConnection httpConnection = (HttpURLConnection) url
						.openConnection();
				httpConnection.setDoInput(true);
				httpConnection.connect();
				InputStream is = httpConnection.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				weiboData.setProfileImage(bitmap);
				list.get(postion).setProfileImage(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(last_postion == postion){
				System.out.println("Parse.PorfileImage.doInBackground()");
				parseListener.onComplete(list);				
			}
			return "";
		}

		public void execute(WeiboData data1, String url, int postion) {
			weiboData = data1;
			profile_image_url = url;
			this.postion = postion;
		}

	}
	
	public static String parseEmotions(String emotionsString){
		List<Map<String, String>> emotions_list = new ArrayList<Map<String,String>>();
		try {
			JSONArray jsonArray = new JSONArray(emotionsString);
			System.out.println("phrase = " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, String>map = new HashMap<String, String>();
				JSONObject sub_object = jsonArray.getJSONObject(i);
				map.put(sub_object.getString("value"), sub_object.getString("url"));
				emotions_list.add(map);
//				System.out.println("phrase  = " + sub_object.getString("phrase"));
//				System.out.println("value  = " + sub_object.getString("value"));
//				System.out.println("url  = " + sub_object.getString("url"));
//				System.out.println("type  = " + sub_object.getString("type"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("emotions_list = " + emotions_list.toString());
		return "";
	}
}
