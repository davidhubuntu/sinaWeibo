package com.example.myweibo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class OAuthDialog extends Dialog {

	String mUrl;
	private WebView mWebView;
	private RelativeLayout mContent;
	private BaseListener listener;
	
	public OAuthDialog(Context context, String url, BaseListener listener) {
		super(context);
		mUrl = url;
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContent = new RelativeLayout(getContext());
		setUpWebView();
		addContentView(mContent, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}

	private void setUpWebView() {
		mWebView = new WebView(getContext());
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new OAuthDialog.WeiboWebViewClient());
		mWebView.loadUrl(mUrl);
		mWebView.setLayoutParams(new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		mWebView.setVisibility(View.INVISIBLE);

		mContent.addView(mWebView);
	}

	class WeiboWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (url.startsWith(Weibo.redirect_uri)) {
				Bundle bundle = utils.parseUrl(url);
				listener.onComplete(bundle);
				
				view.stopLoading();
				OAuthDialog.this.dismiss();
				return;
			}
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			mWebView.setVisibility(View.VISIBLE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
		}
	}

}
