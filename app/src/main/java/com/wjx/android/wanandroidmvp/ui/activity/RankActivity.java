package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.RankAdapter;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.db.Rank;
import com.wjx.android.wanandroidmvp.contract.rank.Contract;
import com.wjx.android.wanandroidmvp.presenter.rank.RankPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RankActivity extends BaseActivity<Contract.IRankView, RankPresenter> implements Contract.IRankView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    @BindView(R.id.rank_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.rank_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.integral_merank)
    TextView mMeRank;

    @BindView(R.id.integral_mecount)
    TextView mMeCountCoin;

    @BindView(R.id.integral_mename)
    TextView mMeName;

    private Context mContext;

    private RankAdapter mRankAdapter;

    private List<Rank> mRankList = new ArrayList<>();

    private int mCurPageNum = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.integral_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.help:
                JumpWebUtils.startWebView(this,
                        getString(R.string.integral_rule),
                        Constant.URL_INTEGRAL_HELP);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_rank;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mPresenter.loadRankData(mCurPageNum);
        initExtra();
        initAdapter();
        initStatusBar();
        initToolbar();
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initExtra() {
        Intent intent = getIntent();
        String rank = intent.getStringExtra(Constant.KEY_RANK);
        String countcoin = intent.getStringExtra(Constant.KEY_COUNTCOIN);
        mMeRank.setText(rank);
        mMeCountCoin.setText(countcoin);
        mMeName.setText(LoginUtils.getLoginUser());
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRankAdapter = new RankAdapter(mContext, mRankList);
        mRecyclerView.setAdapter(mRankAdapter);
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

    private void initToolbar() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mToolbar.setTitle(R.string.integral_rank);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    protected RankPresenter createPresenter() {
        return new RankPresenter();
    }

    @Override
    public void onLoadRankData(List<Rank> rankList) {
        mRankList.addAll(rankList);
        mRankAdapter.setRankList(mRankList);
    }

    @Override
    public void onRefreshRankData(List<Rank> rankList) {
        mRankList.clear();
        mRankList.addAll(0, rankList);
        mRankAdapter.setRankList(mRankList);
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
        mCurPageNum++;
        mPresenter.loadRankData(mCurPageNum);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurPageNum = 1;
        mPresenter.refreshRankData(mCurPageNum);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_INTEGRAL_RANK) {
            String[] data = event.data.split(";");
            mMeRank.setText(data[0]);
            mMeCountCoin.setText(data[1]);
        }
    }
}
