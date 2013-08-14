package com.example.myweibo;

import java.util.List;

public interface ParseListener {
	public void onComplete(List<WeiboData> data);
	public void onComplete(WeiboData data);
}
