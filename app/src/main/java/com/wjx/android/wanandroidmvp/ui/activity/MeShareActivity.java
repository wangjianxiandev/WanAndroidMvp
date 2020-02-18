package com.wjx.android.wanandroidmvp.ui.activity;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.Custom.loading.LoadingView;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.MeShareAdapter;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Share;
import com.wjx.android.wanandroidmvp.bean.share.DeleteShare;
import com.wjx.android.wanandroidmvp.contract.mesharearticle.Contract;
import com.wjx.android.wanandroidmvp.presenter.me.MeSharePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class MeShareActivity extends BaseActivity<Contract.IMeShareView, MeSharePresenter> implements Contract.IMeShareView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private MeShareAdapter mMeShareAdapter;
    private int mCurrentPage = 1;

    private Context mContext;

    private List<Share> mShareList = new ArrayList<>();


    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.meshare_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_me_share;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mPresenter.loadShareArticle(mCurrentPage);
        initAdapter();
        initToolbar();
        initStatusBar();
        initSideSlip();
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    private void initSideSlip() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            // 左滑删除
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipedType = ItemTouchHelper.LEFT;
                return makeMovementFlags(0, swipedType);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // 处理数据上移
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mPresenter.deleteShareArticle(mShareList.get(position).articleId);
                List<Share> tempList = mShareList.stream()
                        .filter(share -> share.articleId != mShareList.get(position).articleId).collect(Collectors.toList());
                mShareList.clear();
                mShareList.addAll(tempList);
                mMeShareAdapter.setShareList(mShareList);
            }

            // 设置高亮
            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    // 滑动状态
                    viewHolder.itemView.getBackground().setColorFilter(Constant.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
                }
            }

            // 上移填补
            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.getBackground().setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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

    private void initToolbar() {
        mToolbar.setBackgroundColor(Constant.getColor(mContext));
        mToolbar.setTitle(R.string.me_share);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMeShareAdapter = new MeShareAdapter(mContext, mShareList);
        mRecyclerView.setAdapter(mMeShareAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected MeSharePresenter createPresenter() {
        return new MeSharePresenter();
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

    @Override
    public void onLoadShareArticle(List<Share> shareList) {
        mLoadingView.setVisibility(View.GONE);
        mShareList.addAll(shareList);
        mMeShareAdapter.setShareList(mShareList);
    }

    @Override
    public void onRefreshShareArticle(List<Share> shareList) {
        mShareList.clear();
        mShareList.addAll(0, shareList);
        mMeShareAdapter.setShareList(mShareList);
    }

    @Override
    public void onDeleteShareArticle(DeleteShare deleteShare, int articleId) {
        List<Share> tempList = mShareList.stream().filter(a -> a.articleId != articleId).collect(Collectors.toList());
        mShareList.clear();
        mShareList.addAll(tempList);
        mMeShareAdapter.setShareList(mShareList);
        Event event = new Event();
        event.target = Event.TARGET_SQUARE;
        event.type = Event.TYPE_DELETE_SHARE;
        event.data = articleId + "";
        EventBus.getDefault().post(event);
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

        // 收藏传递给square
        Event squareEvent = new Event();
        squareEvent.target = Event.TARGET_SQUARE;
        squareEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        squareEvent.data = articleId + "";
        EventBus.getDefault().post(squareEvent);
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
        // 取消收藏传递给square
        Event squareEvent = new Event();
        squareEvent.target = Event.TARGET_SQUARE;
        squareEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        squareEvent.data = articleId + "";
        EventBus.getDefault().post(squareEvent);
    }

    @Override
    public void onLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startTranglesAnimation();
    }

    @Override
    public void onLoadFailed() {
        mLoadingView.setVisibility(View.GONE);
        ToastUtils.showShort("加载失败");
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
        mPresenter.loadShareArticle(mCurrentPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        mPresenter.refreshShareArticle(mCurrentPage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_ME_SHARE) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mShareList.stream().filter(a -> a.articleId == articleId).findFirst().get().isCollect = true;
                mMeShareAdapter.notifyDataSetChanged();
                mPresenter.collect(articleId);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mShareList.stream().filter(a -> a.articleId == articleId).findFirst().get().isCollect = false;
                mMeShareAdapter.notifyDataSetChanged();
                mPresenter.unCollect(articleId);
            } else if (event.type == Event.TYPE_LOGIN) {
                mShareList.clear();
                mPresenter.refreshShareArticle(0);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mShareList.clear();
                mPresenter.refreshShareArticle(0);
            } else if (event.type == Event.TYPE_COLLECT_STATE_REFRESH) {
                int articleId = Integer.valueOf(event.data);
                mShareList.stream().filter(a -> a.articleId == articleId).findFirst().get().isCollect =
                        !mShareList.stream().filter(a -> a.articleId == articleId).findFirst().get().isCollect;
                mMeShareAdapter.notifyDataSetChanged();
            }
        }
    }
}
