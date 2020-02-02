package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.Custom.loading.LoadingView;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.TreeArticleAdapter;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.contract.square.Contract;
import com.wjx.android.wanandroidmvp.presenter.square.TreeListPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TreeListActivity extends BaseActivity<Contract.ITreeListView, TreeListPresenter> implements Contract.ITreeListView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private Context mContext;

    private TreeArticleAdapter mTreeArticleAdapter;

    private int mCurrentPage = 0;

    private int mCid;

    @BindView(R.id.tree_article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.tree_article_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.tree_article_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tree_title)
    TextView mTitle;

    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    private String title;

    private List<Article> mTreeArticleList = new ArrayList<>();
    @Override
    protected int getContentViewId() {
        return R.layout.activity_tree_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mCid = getIntent().getIntExtra(Constant.KEY_TREE_CID, 0);
        title = getIntent().getStringExtra(Constant.KEY_TITLE);
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initStatusBar();
        initAdapter();
        initToolbar();
        mPresenter.loadTreeList(mCurrentPage, mCid);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initToolbar() {
        mToolbar.setBackgroundColor(Constant.getColor(mContext));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mTitle.setSingleLine(true);
            mTitle.setSelected(true);
            mTitle.setFocusable(true);
            mTitle.setFocusableInTouchMode(true);
            mTitle.setText(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTreeArticleAdapter = new TreeArticleAdapter(mContext, mTreeArticleList);
        mRecyclerView.setAdapter(mTreeArticleAdapter);
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Constant.getColor(mContext));
        }
        if (ColorUtils.calculateLuminance(Constant.getColor(mContext)) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    protected TreeListPresenter createPresenter() {
        return new TreeListPresenter();
    }


    @Override
    public void onLoadTreeList(List<Article> treeListData) {
        mLoadingView.setVisibility(View.GONE);
        mTreeArticleList.addAll(treeListData);
        mTreeArticleAdapter.setArticleList(mTreeArticleList);

    }

    @Override
    public void onRefreshTreeList(List<Article> treeListData) {
        mTreeArticleList.clear();
        mTreeArticleList.addAll(0, treeListData);
        mTreeArticleAdapter.setArticleList(mTreeArticleList);
    }

    @Override
    public void onCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Constant.showSnackMessage(this, "收藏成功");
            } else {
                ToastUtils.showShort("收藏失败");
            }
        }
    }

    @Override
    public void onUnCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Constant.showSnackMessage(this, "取消收藏");
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    @Override
    public void onLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startTranglesAnimation();
    }

    @Override
    public void onLoadFailed() {
        mLoadingView.setVisibility(View.GONE);
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
        mCurrentPage++;
        mPresenter.loadTreeList(mCurrentPage, mCid);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 0;
        mPresenter.refreshTreeList(mCurrentPage, mCid);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_TREE) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mTreeArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mTreeArticleAdapter.notifyDataSetChanged();
                mPresenter.collect(articleId);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mTreeArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mTreeArticleAdapter.notifyDataSetChanged();
                mPresenter.unCollect(articleId);
            } else if (event.type == Event.TYPE_LOGIN) {
                mTreeArticleList.clear();
                mPresenter.refreshTreeList(0, mCid);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mTreeArticleList.clear();
                mPresenter.refreshTreeList(0, mCid);
            } else if (event.type == Event.TYPE_COLLECT_STATE_REFRESH) {
                int articleId = Integer.valueOf(event.data);
                // 刷新的收藏状态一定是和之前的相反
                mTreeArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect =
                        !mTreeArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect;
                mTreeArticleAdapter.notifyDataSetChanged();
            }
        }
    }
}
