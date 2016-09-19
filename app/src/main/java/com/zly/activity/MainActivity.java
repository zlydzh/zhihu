package com.zly.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.zly.Util.News;
import com.zly.fragment.NewsAdapter;

import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private LinkedList<News> mDataList;
    private NewsAdapter mAdapter;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        mListView = (ListView)findViewById(R.id.newsList);
    }

    private void initData() {
        mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://news-at.zhihu.com/api/4/news/latest")
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "zly --> onFailure.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String htmlStr =  response.body().string();
                Log.d(TAG, "zly --> onResponse :" + htmlStr);
                JSONObject jsonObj =
            }
        });

        //mAdapter = new NewsAdapter(MainActivity.this, mDataList);
        //mListView.setAdapter(mAdapter);
    }

    private void refreshData() {

    }
}
