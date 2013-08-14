package com.example.myweibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;

public class utils {

	public static Bundle parseUrl(String url){
		Bundle bundle = new Bundle();
		try {
			URL u = new URL(url);
			bundle = decodeUrl(u.getRef());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return bundle;
	}
	
	public static Bundle decodeUrl(String url){
		Bundle bundle = new Bundle();
		String str[] = url.split("&");
		for(String param : str){
			String array[] = param.split("=");
			bundle.putString(URLDecoder.decode(array[0]), URLDecoder.decode(array[1]));
		}
		return bundle;
	}
	
	public static String bindUrlParams(List<BasicNameValuePair> params){
		String ret = "";
		for (int i = 0; i < params.size(); i++) {
			ret = ret + params.get(i).getName() + "=" + params.get(i).getValue();
			if(i != params.size()-1){
				ret += "&";
			}
		}
		return ret;
	}
	
	public static String readHttpResponse(HttpResponse response) {
		String ret = "";
		HttpEntity httpEntity = response.getEntity();
		try {
			ByteArrayOutputStream content = new ByteArrayOutputStream();
			InputStream inputStream = httpEntity.getContent();
			byte[] buffer = new byte[512];
			int read_bytes = 0;
			while ((read_bytes = inputStream.read(buffer)) != -1) {
				content.write(buffer, 0, read_bytes);
			}
			ret = new String(content.toByteArray());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
