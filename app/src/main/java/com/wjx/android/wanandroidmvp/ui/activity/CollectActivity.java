package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.CollectAdaper;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;

import com.wjx.android.wanandroidmvp.bean.collect.AddCollect;
import com.wjx.android.wanandroidmvp.bean.db.Collect;
import com.wjx.android.wanandroidmvp.contract.collect.Contract;
import com.wjx.android.wanandroidmvp.presenter.collect.CollectPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class CollectActivity extends BaseActivity<Contract.ICollectView, CollectPresenter> implements Contract.ICollectView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private CollectAdaper mCollectAdapter;
    private int mCurpage = 0;

    private Context mContext;

    private List<Collect> mCollectList = new ArrayList<>();


    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.collect_toolbar)
    Toolbar mToolbar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mPresenter.loadCollectData(mCurpage);
        initAdapter();
        initToolbar();
        initStatusBar();
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mToolbar.setTitle(R.string.collect_page);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCollectAdapter = new CollectAdaper(mContext, mCollectList);
        mRecyclerView.setAdapter(mCollectAdapter);
    }


    @Override
    protected CollectPresenter createPresenter() {
        return new CollectPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_COLLECT) {
            if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data.split(";")[0]);
                int originId = Integer.valueOf(event.data.split(";")[1]);
                mPresenter.unCollect(articleId, originId);
            }
        }
    }

    @Override
    public void onLoadCollectData(List<Collect> collectList) {
        mCollectList.addAll(collectList);
        mCollectAdapter.setCollectList(mCollectList);
    }

    @Override
    public void onRefreshCollectData(List<Collect> collectList) {
        mCollectList.clear();
        mCollectList.addAll(0, collectList);
        mCollectAdapter.setCollectList(mCollectList);
    }

    @Override
    public void onAddCollect(AddCollect addCollect) {

    }

    @Override
    public void onUnCollect(com.wjx.android.wanandroidmvp.bean.collect.Collect collect, int articleId) {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        if (collect != null) {
            if (collect.getErrorCode() == 0) {
                List<Collect> tempList = mCollectList.stream().filter(a -> a.articleId != articleId).collect(Collectors.toList());
                mCollectList.clear();
                mCollectList.addAll(tempList);
                mCollectAdapter.setCollectList(mCollectList);
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadSuccess() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurpage++;
        mPresenter.loadCollectData(mCurpage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurpage = 0;
        mPresenter.refreshCollectData(mCurpage);
    }
}
