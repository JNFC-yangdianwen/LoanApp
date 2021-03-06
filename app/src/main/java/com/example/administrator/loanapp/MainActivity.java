package com.example.administrator.loanapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
   private WebView webView;
    private static int type=1;
    private static final String TAG = "MainActivity";
    //http://bk.0531center.com/
    private static  final String WEBURL="http://gy.0531center.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.wv);
        //webview支持js
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings= webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl(WEBURL);
        //在webview中打开一个新的页面
        webView.setWebViewClient(new WebViewClient() {
         public boolean   shouldOverrideUrlLoading(WebView view, String url){
             //判断url是否有tel，截取电话号码。开启打电话的意图
             if(url != null&&"tel".equals(url.substring(0,3))){
                 Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse(url));
                 startActivity(intent);
                 return true;
             }
                return false;
        }});
     webView.setWebChromeClient(new WebChromeClient(){
         //确定
         @Override
         public boolean onJsAlert(WebView view, String url, String message,
                                  JsResult result) {
             // TODO Auto-generated method stub
             Log.e("info", "onJsAlert message:"+message);
             return super.onJsAlert(view, url, message, result);
         }
          //获取到的信息
         @Override
         public boolean onJsConfirm(WebView view, String url,
                                    String message, JsResult result) {

             // TODO Auto-generated method stub
             Log.e("info", "onJsConfirm url"+url);
             return super.onJsConfirm(view, url, "", result);
         }

         @Override
         public boolean onJsPrompt(WebView view, String url, String message,
                                   String defaultValue, JsPromptResult result) {
             // TODO Auto-generated method stub
             Log.e("info", "onJsPrompt message:"+message);
             return super.onJsPrompt(view, url, message, defaultValue, result);
         }
     });
}
    ///重写webview 的onKeyDown方法覆盖系统的onKeyDown方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}