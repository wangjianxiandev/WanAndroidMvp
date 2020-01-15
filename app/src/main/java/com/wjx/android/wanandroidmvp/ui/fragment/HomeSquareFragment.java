package com.wjx.android.wanandroidmvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.HomeSquareAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.contract.square.Contract;
import com.wjx.android.wanandroidmvp.presenter.square.HomeSquarePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/09
 * Time: 16:37
 */
public class HomeSquareFragment extends BaseFragment<Contract.IHomeSquareView, HomeSquarePresenter> implements Contract.IHomeSquareView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private Context mContext;

    private HomeSquareAdapter mHomeSquareAdapter;

    private int mCurpage = 0;

    private List<Article> mHomeSquareList = new ArrayList<>();

    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.homesquare_fragment;
    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        initAdapter();
        mPresenter.loadHomeSquareData(mCurpage);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mHomeSquareAdapter = new HomeSquareAdapter(mContext, mHomeSquareList);
        mRecyclerView.setAdapter(mHomeSquareAdapter);
    }

    @Override
    protected HomeSquarePresenter createPresenter() {
        return new HomeSquarePresenter();
    }

    @Override
    public void loadHomeSquareData(List<Article> homeSquareData) {
        mHomeSquareList.addAll(homeSquareData);
        mHomeSquareAdapter.setHomeSquareList(mHomeSquareList);
    }

    @Override
    public void refreshHomeSquareData(List<Article> homeSquareData) {
        mHomeSquareList.clear();
        mHomeSquareList.addAll(0, homeSquareData);
        mHomeSquareAdapter.setHomeSquareList(mHomeSquareList);
    }

    @Override
    public void onCollect(Collect collect, int articleId) {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                mHomeSquareList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mHomeSquareAdapter.setHomeSquareList(mHomeSquareList);
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
                mHomeSquareList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mHomeSquareAdapter.setHomeSquareList(mHomeSquareList);
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
        ToastUtils.showShort("加载失败");
        mSmartRefreshLayout.finishRefresh(false);
        mSmartRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void onLoadSuccess() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurpage++;
        mPresenter.loadHomeSquareData(mCurpage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurpage = 0;
        mPresenter.refreshHomeSquareData(mCurpage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_SQUARE) {
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
                mHomeSquareList.clear();
                mPresenter.refreshHomeSquareData(0);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mHomeSquareList.clear();
                mPresenter.refreshHomeSquareData(0);
            }
        }
    }
}
