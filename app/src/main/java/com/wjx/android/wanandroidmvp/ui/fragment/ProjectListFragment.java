package com.wjx.android.wanandroidmvp.ui.fragment;


import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ProjectListAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListDataNew;
import com.wjx.android.wanandroidmvp.contract.project.Contract;
import com.wjx.android.wanandroidmvp.presenter.project.ProjectListPresenter;

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

    public ProjectListFragment(int cid) {
        mCid = cid;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.project_list_fragment;
    }

    @Override
    protected void init() {
        initAdapter();
        mPresenter.loadProjectList(mCurrentPage, mCid);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mProjectListAdapter = new ProjectListAdapter(mRecyclerView);
    }

    @Override
    protected ProjectListPresenter createPresenter() {
        return new ProjectListPresenter();
    }

    @Override
    public void loadProjectList(List<ProjectListDataNew> projectListData) {
        mProjectListAdapter.setBeans(projectListData);
        mRecyclerView.setAdapter(mProjectListAdapter);
    }

    @Override
    public void refreshProjectList(List<ProjectListDataNew> projectListData) {
        mProjectListAdapter.setBeans(projectListData);
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
        mPresenter.refreshProjectList();
    }

    @Override
    public void onError(Throwable e) {
        if (mSmartRefreshLayout.getState() == RefreshState.Loading) {
            mSmartRefreshLayout.finishLoadMore();
            mCurrentPage--;
        }
        // 完成刷新
        if (mSmartRefreshLayout.getState() == RefreshState.Refreshing) {
            mSmartRefreshLayout.finishRefresh();
        }

    }

    @Override
    public void onComplete() {
        if (mSmartRefreshLayout.getState() == RefreshState.Loading) {
            mSmartRefreshLayout.finishLoadMore();
        }
        // 完成刷新
        if (mSmartRefreshLayout.getState() == RefreshState.Refreshing) {
            mSmartRefreshLayout.finishRefresh();
        }
    }
}
