package com.example.myweibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpManager {

	public static String doGet(String url){
		
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpUriRequest request = get;
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return utils.readHttpResponse(response);
//		if (response.getStatusLine().getStatusCode() == 200) {
//			return utils.readHttpResponse(response);
//		}
//		return "";
	}
	
	public static String doPost(String url, List<BasicNameValuePair> params) throws IOException{
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		String postPamars = utils.bindUrlParams(params);
		byte[] data = null;
		try {
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			data = postPamars.getBytes("UTF-8");
			ba.write(data);
			data = ba.toByteArray();
			ba.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ByteArrayEntity arrayEntity = new ByteArrayEntity(data);
		httpPost.setEntity(arrayEntity);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200) {
			return utils.readHttpResponse(response);
		}
		return "";
	}
}
