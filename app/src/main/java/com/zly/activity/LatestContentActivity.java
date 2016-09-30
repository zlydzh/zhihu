package com.zly.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.gson.Gson;
import com.zly.Util.Content;
import com.zly.Util.Latest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/30.
 */
public class LatestContentActivity extends Activity {
    private WebView webview;
    private OkHttpClient okHttp;
    private Latest.StoriesEntity entity;
    private String content;
    private final int UPDATE_DATA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latest_content_layout);
        initView();
        initData();
    }

    private void initView() {
        webview = (WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setVerticalScrollBarEnabled(false); //垂直不显示
        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        WebSettings webSettings= webview.getSettings(); // webView: 类WebView的实例
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  //就是这句
    }

    private void initData() {
        entity = (Latest.StoriesEntity)getIntent().getSerializableExtra("entity");
        okHttp = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://news-at.zhihu.com/api/4/news/" + entity.getId())
                .build();
        Call call = okHttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zly", "zly --> failure.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                content = response.body().string();
                Log.d("zly", "zly --> content:" + content);
                mHandler.sendEmptyMessage(UPDATE_DATA);
            }
        });
    }

    private void parseJson(String responseString) {
        responseString = responseString.replaceAll("'", "''");
        Gson gson = new Gson();
        Content content = gson.fromJson(responseString, Content.class);
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
//        webview.loadDataWithBaseURL("x-data://base", getNewContent(html), "text/html", "UTF-8", null);
        webview.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case UPDATE_DATA:
                    parseJson(content);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private String getNewContent(String htmltext){
        Document doc= Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
