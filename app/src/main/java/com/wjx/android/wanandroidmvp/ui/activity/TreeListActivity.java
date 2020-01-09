package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ArticleAdapter;
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

    private int mCurPage = 0;

    private int mCid;

    @BindView(R.id.tree_article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.tree_article_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.tree_article_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tree_title)
    TextView mTitle;

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
        mPresenter.loadTreeList(mCurPage, mCid);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
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
            getWindow().setStatusBarColor(getColor(R.color.colorPrimary));
        }
        if (ColorUtils.calculateLuminance(getColor(R.color.colorPrimary)) >= 0.5) {
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
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                mTreeArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mTreeArticleAdapter.setArticleList(mTreeArticleList);
            } else {
                ToastUtils.showShort("收藏失败");
            }
        }
    }

    @Override
    public void onUnCollect(Collect collect, int articleId) {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        if (collect != null) {
            if (collect.getErrorCode() == 0) {
                mTreeArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mTreeArticleAdapter.setArticleList(mTreeArticleList);
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
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
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
        mCurPage++;
        mPresenter.loadTreeList(mCurPage, mCid);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurPage = 0;
        mPresenter.refreshTreeList(mCurPage, mCid);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_TREE) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.collect(articleId);
                Event e = new Event();
                e.target = Event.TARGET_MAIN;
                e.type = Event.TYPE_START_ANIMATION;
                EventBus.getDefault().post(e);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mPresenter.unCollect(articleId);
                Event e = new Event();
                e.target = Event.TARGET_MAIN;
                e.type = Event.TYPE_START_ANIMATION;
                EventBus.getDefault().post(e);
            } else if (event.type == Event.TYPE_LOGIN) {
                mTreeArticleList.clear();
                mPresenter.refreshTreeList(0, mCid);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mTreeArticleList.clear();
                mPresenter.refreshTreeList(0, mCid);
            }
        }
    }
}
