package com.wjx.android.wanandroidmvp.presenter.project;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.contract.project.Contract;
import com.wjx.android.wanandroidmvp.model.ProjectListModel;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/27
 * Time: 17:42
 */
public class ProjectListPresenter extends BasePresenter<Contract.IProjectListView> implements Contract.IProjectListPresenter {
    Contract.IProjectListModel iProjectListModel;

    public ProjectListPresenter() {
        iProjectListModel = new ProjectListModel();
    }
    @Override
    public void loadProjectList(int pageNum, int cid) {
        iProjectListModel.loadProjectList(pageNum, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjectListData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ProjectListData projectListData) {
                        if (isViewAttached()) {
                            getView().loadProjectList(projectListData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void refreshProjectList() {

    }
}
