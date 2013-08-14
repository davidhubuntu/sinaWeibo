package com.example.myweibo;

import android.R.string;
import android.graphics.Bitmap;

public class WeiboData {

	private String ccreen_name;
	private String text;
	private String source;
	private String created_at;
	private String reposts_count;
	private String comments_count;
	private String attitudes_count;
	private Bitmap profile_image = null;
	private String reteeted_status_text;
	
	public WeiboData(){
	}
	
	public void setWeiboData(String screen_name, String text, String source, String created_at){
		this.ccreen_name = screen_name;
		this.text = text;
		this.source = source;
		this.created_at = created_at;
	}
	
	public void setScreenName(String screen_name){
	    this.ccreen_name = screen_name;
	}
	public String getScreenName(){
		return ccreen_name;
	}
	
	public void setReteetedStatusText(String reteeted_status_text){
	    this.reteeted_status_text = reteeted_status_text;
	}
	public String getReteetedStatusText(){
		return reteeted_status_text;
	}
	
	public void setSource(String source){
	    this.source = source;
	}
	public String getSource(){
		return source;
	}
	
	public void setCreatedAt(String created_at){
	    this.created_at = created_at;
	}
	public String getCreatedAt(){
		return created_at;
	}
	
	public void setRepostsCount(String reposts_count){
	    this.reposts_count = reposts_count;
	}
	public String getRepostsCount(){
		return reposts_count;
	}
	
	public void setCommentsCount(String comments_count){
	    this.comments_count = comments_count;
	}
	public String getCommentsCount(){
		return comments_count;
	}
	
	public void setAttitudesCount(String attitudes_count){
	    this.attitudes_count = attitudes_count;
	}
	public String getAttitudesCount(){
		return attitudes_count;
	}
	
	public void setProfileImage(Bitmap image){
	    this.profile_image = image;
	}
	public Bitmap getProfileImage(){
		return profile_image;
	}
	
	public void setText(String text){
	    this.text = text;
	}
	public String getText(){
		return text;
	}
	
}
