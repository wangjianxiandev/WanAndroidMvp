package com.wjx.android.wanandroidmvp.ui.fragment;


import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ProjectListAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
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
public class ProjectListFragment extends BaseFragment<Contract.IProjectListView, ProjectListPresenter> implements Contract.IProjectListView {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
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
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void loadProjectList(ProjectListData projectListData) {
        mProjectListAdapter.setBeans(projectListData);
        mRecyclerView.setAdapter(mProjectListAdapter);
    }

    @Override
    public void refreshProjectList(ProjectListData projectListData) {

    }
}
