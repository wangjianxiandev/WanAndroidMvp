package com.wjx.android.wanandroidmvp.ui.fragment;


import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.contract.project.Contract;
import com.wjx.android.wanandroidmvp.presenter.project.ProjectPresenter;

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
public class ProjectListFragment extends BaseFragment<Contract.IProjectView, ProjectPresenter> implements Contract.IProjectView {
//    @BindView(R.id.tx)
    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected void init() {

    }

    @Override
    protected ProjectPresenter createPresenter() {
        return null;
    }

    @Override
    public void loadProjectClassify(ProjectClassifyData projectClassifyData) {

    }

    @Override
    public void loadProjectList(List<ProjectListData> projectListData) {

    }

    @Override
    public void refreshProjectList(List<ProjectListData> projectListData) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
