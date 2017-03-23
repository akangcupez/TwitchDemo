package com.example.aji.twitchdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Aji Subastian
 */

class TwitchWebClient extends WebViewClient {

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return true;
    }

    static class TwitchWebChromeClient extends WebChromeClient {

        private Activity mActivity;

        TwitchWebChromeClient(Activity activity) {
            mActivity = activity;
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
            mActivity.finish();
        }
    }

}
