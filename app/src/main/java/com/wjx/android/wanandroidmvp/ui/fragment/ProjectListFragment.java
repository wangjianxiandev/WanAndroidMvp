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

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ProjectListAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;

import com.wjx.android.wanandroidmvp.contract.project.Contract;
import com.wjx.android.wanandroidmvp.presenter.project.ProjectListPresenter;

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
 * @date: 2019/12/27
 * Time: 16:17
 */
public class ProjectListFragment extends BaseFragment<Contract.IProjectListView, ProjectListPresenter> implements Contract.IProjectListView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener{

    @BindView(R.id.normal_view)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.project_list_recycler_view)
    RecyclerView mRecyclerView;

    private ProjectListAdapter mProjectListAdapter;
    private int mCurrentPage = 0;
    private int mCid;

    private Context mContext;

    private List<Article> mProjectArticleList = new ArrayList<>();


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

    public ProjectListFragment(int cid) {
        mCid = cid;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.project_list_fragment;
    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        mPresenter.loadProjectList(mCurrentPage, mCid);
        initAdapter();
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mProjectListAdapter = new ProjectListAdapter(mContext, mProjectArticleList);
        mRecyclerView.setAdapter(mProjectListAdapter);
    }

    @Override
    protected ProjectListPresenter createPresenter() {
        return new ProjectListPresenter();
    }


    @Override
    public void onLoadProjectList(List<Article> projectList) {
        mProjectArticleList.addAll(projectList);
        mProjectListAdapter.setProjectList(mProjectArticleList);
    }

    @Override
    public void onRefreshProjectList(List<Article> projectList) {
        mProjectArticleList.clear();
        mProjectArticleList.addAll(0,projectList);
        mProjectListAdapter.setProjectList(mProjectArticleList);
    }

    @Override
    public void onCollect(Collect collect, int articleId) {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        if (collect != null) {
            if (collect.getErrorCode() == 0) {
                mProjectArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mProjectListAdapter.setProjectList(mProjectArticleList);
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
                mProjectArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mProjectListAdapter.setProjectList(mProjectArticleList);
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadProjectList(mCurrentPage, mCid);
    }

    /**
     * 刷新文章从首页开始
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 0;
        mPresenter.refreshProjectList(mCurrentPage, mCid);
    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
        ToastUtils.showShort("加载失败");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_PROJECT) {
            if (event.type == Event.TYPE_COLLECT) {
                String[] data = event.data.split(";");
                if (data.length > 1 && mCid == Integer.valueOf(data[1])) {
                    int articleId = Integer.valueOf(data[0]);
                    mPresenter.collect(articleId);
                    Event e = new Event();
                    e.target = Event.TARGET_MAIN;
                    e.type = Event.TYPE_START_ANIMATION;
                    EventBus.getDefault().post(e);
                }
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                String[] data = event.data.split(";");
                if (data.length > 1 && mCid == Integer.valueOf(data[1])) {
                    int articleId = Integer.valueOf(data[0]);
                    mPresenter.unCollect(articleId);
                    Event e = new Event();
                    e.target = Event.TARGET_MAIN;
                    e.type = Event.TYPE_START_ANIMATION;
                    EventBus.getDefault().post(e);
                }
            } else if (event.type == Event.TYPE_LOGIN) {
                mProjectArticleList.clear();
                mPresenter.refreshProjectList(0, mCid);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mProjectArticleList.clear();
                mPresenter.refreshProjectList(0, mCid);
            } else if (event.type == Event.TYPE_UNCOLLECT_REFRESH) {
                mPresenter.refreshProjectList(mCurrentPage, mCid);
            }
        }
    }
}
