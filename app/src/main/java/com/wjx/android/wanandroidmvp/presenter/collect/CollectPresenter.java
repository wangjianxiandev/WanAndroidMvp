package com.wjx.android.wanandroidmvp.presenter.collect;

import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.collect.CollectBean;
import com.wjx.android.wanandroidmvp.contract.collect.Contract;
import com.wjx.android.wanandroidmvp.model.CollectModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/01
 * Time: 22:39
 */
public class CollectPresenter extends BasePresenter<Contract.ICollectView> implements Contract.ICollectPresenter {
    Contract.ICollectModel iCollectModel;
    public CollectPresenter() {
        iCollectModel = new CollectModel();
    }
    @Override
    public void loadCollect(int pageNum){
        iCollectModel.loadCollect(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CollectBean collectBean) {
                        if (isViewAttached()) {
                            getView().loadCollect(collectBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
