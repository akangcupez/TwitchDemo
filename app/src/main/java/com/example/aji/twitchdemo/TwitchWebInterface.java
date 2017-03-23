package com.example.aji.twitchdemo;

import android.app.Activity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * @author Aji Subastian
 */

@SuppressWarnings("unused")
class TwitchWebInterface {

    private Activity mActivity;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    TwitchWebInterface(Activity activity, WebView webView, ProgressBar progressBar) {
        mActivity = activity;
        mWebView = webView;
        mProgressBar = progressBar;
    }

    @JavascriptInterface
    public void startPlaying() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.setVisibility(View.VISIBLE);
                mProgressBar.setIndeterminate(false);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @JavascriptInterface
    public void destroyPlayer() {
        mActivity.finish();
    }

}
