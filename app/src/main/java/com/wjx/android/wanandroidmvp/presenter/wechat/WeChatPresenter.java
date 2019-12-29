package com.wjx.android.wanandroidmvp.presenter.wechat;

import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatClassifyData;
import com.wjx.android.wanandroidmvp.contract.wechat.Contract;
import com.wjx.android.wanandroidmvp.model.WeChatModel;

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
 * Time: 12:07
 */
public class WeChatPresenter extends BasePresenter<Contract.IWeChatView> implements Contract.IWeChatPresenter {
    Contract.IWeChatModel iWeChatModel;

    public WeChatPresenter () {
        iWeChatModel = new WeChatModel();
    }
    @Override
    public void loadWeChatClassify() {
        iWeChatModel.loadWeChatClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatClassifyData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(WeChatClassifyData weChatClassifyData) {
                        if (isViewAttached()) {
                            getView().loadWeChatClassify(weChatClassifyData);
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
