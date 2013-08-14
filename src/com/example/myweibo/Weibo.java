package com.example.myweibo;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;

public class Weibo {

	private static Weibo weiboInstance = null;
	private static String appKey = null;
	public static String redirect_uri = null;
	public static String URL_OAUTH2_ACCESS_AUTHORIZE = "https://open.weibo.cn/oauth2/authorize";
	private static String access_token = null ;
	private static long max_id = 0;

	public synchronized static Weibo getInstance(String appKey, String url) {
		if (weiboInstance == null) {
			weiboInstance = new Weibo();
		}
		Weibo.appKey = appKey;
		Weibo.redirect_uri = url;
		return weiboInstance;
	}

	public void authorize(Context context, BaseListener listener) {
		startAuthDialog(context, listener);
	}

	public void startAuthDialog(Context context, final BaseListener listener) {
		Map<String, String> parameter = new HashMap<String, String>();
//		parameter.put("client_id", appKey);
//		parameter.put("display", "mobile");
//		parameter.put("response_type", "token");
//		parameter.put("redirect_uri", redirect_uri);

		String url = URL_OAUTH2_ACCESS_AUTHORIZE + "?" + encodeUrl(parameter);
		new OAuthDialog(context, url, new BaseListener() {
			
			@Override
			public void onComplete(Bundle bundle) {
				listener.onComplete(bundle);				
			}
		}).show();
	}

	public static String encodeUrl(Map<String, String> parameter) {
		// if (parameter == null) {
		// return "";
		// }

		String para = URLEncoder.encode("client_id")+"=" + URLEncoder.encode(appKey) + "&"
				+ URLEncoder.encode("response_type")+"="+URLEncoder.encode("token")+"&"
				+ URLEncoder.encode("redirect_uri")+"=" + URLEncoder.encode(redirect_uri)+"&"
		+ URLEncoder.encode("display")+"="+URLEncoder.encode("mobile");
		return para;
	}
	
	public static String accessToken(){
		return access_token;
	}
	
	public static void setAccessToken(String access){
		access_token = access;
	}
	
	public static long MaxID(){
		return max_id;
	}
	
	public static void setMaxID(long id){
		max_id = id;
	}
}
