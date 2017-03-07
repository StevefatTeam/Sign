package com.gisroad.sign.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.gisroad.sign.R;
import com.orhanobut.logger.Logger;
import com.gisroad.sign.mvp.presenter.LoginPresenter;
import com.gisroad.sign.mvp.view.LoginView;
import com.gisroad.sign.utils.DialogUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class LoginActivity extends BaseActivity implements LoginView {
    private LoginPresenter presenter;
    private DialogUtils dialogUtils;


    @Override
    protected void initView() {
        presenter = new LoginPresenter(this);
        dialogUtils = new DialogUtils(this, "数据更新中。。。");
        showProgress();

        WebView webView = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.requestFocus();
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("http://221.13.129.100:9090/kqcx/Default.aspx");


    }

    @Override
    protected int getLayoutView() {
        return R.layout.login_activity;
    }

    @Override
    public void showProgress() {
        dialogUtils.show();
    }

    @Override
    public void hideProgress() {
        dialogUtils.dismiss();
    }

    @Override
    public void toActivity() {
        hideProgress();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setData() {
        toActivity();
    }

    final class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("WebView", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                    + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");

        }
    }


    class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            presenter.init(html);

        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
