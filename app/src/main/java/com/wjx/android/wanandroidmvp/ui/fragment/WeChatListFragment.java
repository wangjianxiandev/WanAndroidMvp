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
import com.wjx.android.wanandroidmvp.adapter.WeChatListAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.Utils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.contract.wechat.Contract;
import com.wjx.android.wanandroidmvp.presenter.wechat.WeChatListPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/29
 * Time: 12:47
 */
public class WeChatListFragment extends BaseFragment<Contract.IWeChatListView, WeChatListPresenter> implements Contract.IWeChatListView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {


    @BindView(R.id.normal_view)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.wechat_list_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private WeChatListAdapter mWeChatListAdapter;
    private int mCurrentPage = 1;
    private int mCid = 0;

    private Context mContext;

    private List<Article> mWechatArticleList = new ArrayList<>();

    public WeChatListFragment(int cid) {
        mCid = cid;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wechatlist_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        mPresenter.loadWeChatList(mCid, mCurrentPage);
        initAdapter();
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mWeChatListAdapter = new WeChatListAdapter(mContext, mWechatArticleList);
        mRecyclerView.setAdapter(mWeChatListAdapter);
    }

    @Override
    protected WeChatListPresenter createPresenter() {
        return new WeChatListPresenter();
    }

    @Override
    public void onLoadWeChatList(List<Article> weChatList) {
        mWechatArticleList.addAll(weChatList);
        mWeChatListAdapter.setWeChatList(mWechatArticleList);
    }

    @Override
    public void onRefreshWeChatList(List<Article> weChatList) {
        mWechatArticleList.clear();
        mWechatArticleList.addAll(0, weChatList);
        mWeChatListAdapter.setWeChatList(mWechatArticleList);
    }

    public void startLoadingView() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_START_ANIMATION;
        EventBus.getDefault().post(e);
    }

    public void stopLoadingView() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
    }

    @Override
    public void onLoading() {
        startLoadingView();
    }

    @Override
    public void onLoadFailed() {
        setNetWorkError(false);
        ToastUtils.showShort("网络未连接请重试");
        stopLoadingView();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadSuccess() {
        stopLoadingView();
        setNetWorkError(true);
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @OnClick(R.id.layout_error)
    public void onReTry() {
        setNetWorkError(true);
        mPresenter.loadWeChatList(mCid, 1);
    }

    private void setNetWorkError(boolean isSuccess) {
        if (isSuccess) {
            mSmartRefreshLayout.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
        } else if (mCurrentPage == 1) {
            mSmartRefreshLayout.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Utils.showSnackMessage(getActivity(), "收藏成功");
            } else {
                ToastUtils.showShort("收藏失败");
            }
        }
    }

    @Override
    public void onUnCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Utils.showSnackMessage(getActivity(), "取消收藏");
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadWeChatList(mCid, mCurrentPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mWechatArticleList.clear();
        mPresenter.refreshWeChatList(mCid, 1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_WX) {
            if (event.type == Event.TYPE_COLLECT) {
                String[] data = event.data.split(";");
                if (data.length > 1 && mCid == Integer.valueOf(data[1])) {
                    int articleId = Integer.valueOf(data[0]);
                    mWechatArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                    mWeChatListAdapter.notifyDataSetChanged();
                    mPresenter.collect(articleId);
                }
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                String[] data = event.data.split(";");
                if (data.length > 1 && mCid == Integer.valueOf(data[1])) {
                    int articleId = Integer.valueOf(data[0]);
                    mWechatArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                    mWeChatListAdapter.notifyDataSetChanged();
                    mPresenter.unCollect(articleId);
                }
            } else if (event.type == Event.TYPE_LOGIN) {
                mWechatArticleList.clear();
                mPresenter.refreshWeChatList(mCid, 1);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mWechatArticleList.clear();
                mPresenter.refreshWeChatList(mCid, 1);
            } else if (event.type == Event.TYPE_COLLECT_STATE_REFRESH) {
                int articleId = Integer.valueOf(event.data);
                // 刷新的收藏状态一定是和之前的相反
                mWechatArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect =
                        !mWechatArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect;
                mWeChatListAdapter.notifyDataSetChanged();
            }
        }
    }
}
