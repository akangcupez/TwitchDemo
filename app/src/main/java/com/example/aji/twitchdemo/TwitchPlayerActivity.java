package com.example.aji.twitchdemo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * @author Aji Subastian
 */

@SuppressWarnings("unused")
public class TwitchPlayerActivity extends AppCompatActivity {

    public static final String VIDEO_ID = "com.akangcupez.Twitch.VIDEO_ID";
    private FrameLayout mRootLayout;
    private WebView wvTwitchPlayer;
    private ProgressBar pbTwitchPlayer;
    private TwitchWebInterface mWebInterface;
    private String mVideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitch_player);

        initializeViews();
    }

    private void initializeViews() {

        mRootLayout = (FrameLayout) findViewById(R.id.root_player);
        wvTwitchPlayer = (WebView) findViewById(R.id.wv_twitch_player);
        pbTwitchPlayer = (ProgressBar) findViewById(R.id.pb_twitch_player);

        mWebInterface = new TwitchWebInterface(this, wvTwitchPlayer, pbTwitchPlayer);
        showLoading();

        mVideoId = getIntent().getStringExtra(VIDEO_ID);
        if (!TextUtils.isEmpty(mVideoId)) {
            setupTwitchPlayer();
        }
        else {
            finish();
        }
    }

    private void showLoading() {
        pbTwitchPlayer.setVisibility(View.VISIBLE);
        pbTwitchPlayer.setIndeterminate(true);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    private void setupTwitchPlayer() {

        WebSettings mWebSettings = wvTwitchPlayer.getSettings();
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setMediaPlaybackRequiresUserGesture(false);

        wvTwitchPlayer.setWebViewClient(new TwitchWebClient());
        wvTwitchPlayer.setWebChromeClient(new TwitchWebClient.TwitchWebChromeClient(this));
        wvTwitchPlayer.addJavascriptInterface(mWebInterface, "Android");
        wvTwitchPlayer.setFocusableInTouchMode(false);
        wvTwitchPlayer.setFocusable(false);
        wvTwitchPlayer.setClickable(false);

        wvTwitchPlayer.loadData(generateHtml(mVideoId), "text/html", "utf-8");
    }

    private String generateHtml(String videoId) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "html, body {width: 100%;height: 100%;margin: 0;padding: 0;}\n" +
                "#div_tv{position:relative;width:100%;height:100%;}\n" +
                "</style>\n" +
                "</head>\n" +
                //"<body onload=\"window.open('', '_self', '');\">\n" +
                "<body>\n" +
                "<script src= \"http://player.twitch.tv/js/embed/v1.js\"></script>\n" +
                "<div id=\"div_tv\"></div>\n" +
                "<script type=\"text/javascript\">\n" +
                "var w = window.innerWidth;\n" +
                "var h = window.innerHeight;\n" +
                "var options = {\n" +
                "width: w,\n" +
                "height: h,\n" +
                "video: \"" + videoId + "\",\n" +
                "controls: false,\n" +
                "fullscreen: true\n" +
                "};\n" +
                "var player = new Twitch.Player(\"div_tv\", options);\n" +
                "player.addEventListener(Twitch.Player.PLAY, () => { Android.startPlaying(); });\n" +
                //"player.addEventListener(Twitch.Player.ENDED, () => { window.close(); });\n" +
                "player.addEventListener(Twitch.Player.ENDED, () => { Android.destroyPlayer(); });\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
    }

    public void pausePlayer() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            wvTwitchPlayer.evaluateJavascript("if (typeof player != \"undefined\" || player != null) player.pause();", null);
        } else {
            wvTwitchPlayer.loadUrl("javascript:if (typeof player != \"undefined\" || player != null) player.pause();");
        }
    }

    public void resumePlayer() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            wvTwitchPlayer.evaluateJavascript("if (typeof player != \"undefined\" || player != null) player.play();", null);
        } else {
            wvTwitchPlayer.loadUrl("javascript:if (typeof player != \"undefined\" || player != null) player.play();");
        }
    }

    @Override
    protected void onPause() {
        if (wvTwitchPlayer != null) {
            pausePlayer();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wvTwitchPlayer != null) {
            resumePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        if (wvTwitchPlayer != null) {
            wvTwitchPlayer.clearHistory();
            mRootLayout.removeView(wvTwitchPlayer);
            wvTwitchPlayer.removeAllViews();
            wvTwitchPlayer.destroy();
        }

        super.onDestroy();
    }
}
