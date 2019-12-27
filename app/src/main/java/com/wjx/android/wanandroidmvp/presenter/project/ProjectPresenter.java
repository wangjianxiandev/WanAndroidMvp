package com.wjx.android.wanandroidmvp.presenter.project;

import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.contract.project.Contract;
import com.wjx.android.wanandroidmvp.model.ProjectModel;

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
 * Time: 14:57
 */
public class ProjectPresenter extends BasePresenter<Contract.IProjectView> implements Contract.IProjectPresenter {
    Contract.IProjectModel iProjectModel;

    public ProjectPresenter() {
        iProjectModel = new ProjectModel();
    }
    @Override
    public void loadProjectClassify() {
        iProjectModel.loadProjectClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjectClassifyData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ProjectClassifyData projectClassifyData) {
                        if (isViewAttached()) {
                            getView().loadProjectClassify(projectClassifyData);
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
    public void loadProjectList(int pageNum, int cid) {

    }

    @Override
    public void refreshProjectList() {

    }
}
