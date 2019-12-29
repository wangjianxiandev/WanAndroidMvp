package com.wjx.android.wanandroidmvp.presenter.square;

import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;
import com.wjx.android.wanandroidmvp.contract.square.Contract;
import com.wjx.android.wanandroidmvp.model.SquareModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 14:57
 */
public class SquarePresenter extends BasePresenter<Contract.ISquareView> implements Contract.ISquarePresenter {
    Contract.ISquareModel iSquareModel;

    public SquarePresenter() {
        iSquareModel = new SquareModel();
    }

    @Override
    public void loadNavigation() {
        iSquareModel.loadNavigation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NavigationData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(NavigationData navigationData) {
                        if (isViewAttached()) {
                            getView().loadNavigation(navigationData);
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
}
