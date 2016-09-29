package com.zly.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zly.Util.Latest;
import com.zly.Util.News;
import com.zly.fragment.NewsAdapter;
import com.zly.view.VpSwipeRefreshLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int UPDATE_DATA = 0;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private LinkedList<News> mDataList;
    private NewsAdapter mAdapter;
    private OkHttpClient mOkHttpClient;
    private Latest latest;
    private LinearLayout ll_dot;
    private ViewPager vp;
    private List<ImageView> ll_dots;
    private List<View> topPhoto;
    private String htmlStr;
    private List<Latest.TopStoriesEntity> topStoriesEntities;
    private List<Latest.StoriesEntity> mStoriesEntityList;
    private int currentItem = 0;
    private boolean isAutoPlay= true;
    private View kanner;

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
        kanner = (View)getLayoutInflater().inflate(R.layout.list_head_layout, mListView, false);

        mListView.addHeaderView(kanner, null, true);
        vp = (ViewPager)kanner.findViewById(R.id.vp);
        ll_dot = (LinearLayout)kanner.findViewById(R.id.ll_dot);
        mStoriesEntityList = new ArrayList<Latest.StoriesEntity>();
        mAdapter = new NewsAdapter(MainActivity.this, mStoriesEntityList);
        mListView.setAdapter(mAdapter);
    }

    private void initData() {
        topPhoto = new ArrayList<View>();
        ll_dots = new ArrayList<ImageView>();
        topStoriesEntities = new ArrayList<Latest.TopStoriesEntity>();
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
                htmlStr =  response.body().string();
                Log.d(TAG, "zly --> onResponse :" + htmlStr);
                mHandler.sendEmptyMessage(UPDATE_DATA);
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_DATA:
                    parseJson(htmlStr);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void refreshData() {

    }

    private void parseJson(String string) {
        Gson gson = new Gson();
        latest = gson.fromJson(string, Latest.class);
        String data = latest.getDate();
        Log.d(TAG, "zly --> data: " + data);
        topStoriesEntities =  latest.getTop_stories();
        mStoriesEntityList = latest.getStories();

        ImageView imageView = null;
        int topLen = latest.getTop_stories().size();
        Log.d(TAG, "zly --> topLen:" + topLen);
        for (int i = 0; i < topLen; i++) {
            imageView = new ImageView(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            imageView.setImageResource(R.drawable.dot_blur);
            ll_dot.addView(imageView, params);
            ll_dots.add(imageView);
        }

        ImageView iv = null;
        TextView iv_title = null;
        for (int i = 0; i < topLen; i++) {
            View topImageAndText = LayoutInflater.from(MainActivity.this).inflate(R.layout.top_content_layout, null);
            iv = (ImageView)topImageAndText.findViewById(R.id.iv);
            iv_title = (TextView)topImageAndText.findViewById(R.id.iv_title);

            Glide.with(MainActivity.this)
                    .load(latest.getTop_stories().get(i).getImage())
                    .into(iv);
            iv_title.setText(latest.getTop_stories().get(i).getTitle());

            topImageAndText.setOnClickListener(MainActivity.this);
            topPhoto.add(topImageAndText);
        }
        vp.setAdapter(new myPagerAdapter());
        vp.setFocusable(true);
        vp.setCurrentItem(0);
        vp.addOnPageChangeListener(new myOnPageChangeListener());
        startPlay();
        mAdapter.addList(mStoriesEntityList);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "current item is " + vp.getCurrentItem(), Toast.LENGTH_SHORT).show();
    }

    class myPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return topPhoto.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(topPhoto.get(position));
            return topPhoto.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public class myOnPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到
         * 调用。其中三个参数的含义分别为：
         * position :当前页面，及你点击滑动的页面
         * positionOffset:当前页面偏移的百分比
         * positionOffsetPixels:当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 此方法是页面跳转完后得到调用，arg0是你当前选中的页面的Position
         */
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < ll_dots.size(); i++) {
                if (i == position) {
                    ll_dots.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    ll_dots.get(i).setImageResource(R.drawable.dot_blur);
                }
            }
        }

        /**
         * state ==1的时辰默示正在滑动，state==2的时辰默示滑动完毕了，state==0的时辰默示什么都没做。
         * 使用中发现即使是自动切换图片也会调用state==2的状态，只有state == 1没有调用，也就表示state==1时是用户进行切换图片。
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG, "zly --> currentItem:" + vp.getCurrentItem() + " state: " + state);
            switch (state){
                case 0:
                    if (!isAutoPlay) {
                        if (vp.getCurrentItem() == 0) {
                            vp.setCurrentItem(latest.getTop_stories().size(), false);
                        } else if (vp.getCurrentItem() == latest.getTop_stories().size()) {
                            vp.setCurrentItem(0);
                        }
                        currentItem = vp.getCurrentItem();
                    }
                    break;
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
            }
        }
    }

    private void startPlay() {
        isAutoPlay = true;
        mHandler.postDelayed(task, 3000);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                mHandler.postDelayed(task, 3000);
                Log.d(TAG, "zly --> currentItem:" + currentItem);
                if (currentItem < latest.getTop_stories().size()) {
                    currentItem++;
                } else {
                    currentItem = 0;
                }
                vp.setCurrentItem(currentItem);
            } else {
                mHandler.postDelayed(task, 3000);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(task);
    }
}
