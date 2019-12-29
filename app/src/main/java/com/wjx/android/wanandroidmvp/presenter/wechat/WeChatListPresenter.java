package com.wjx.android.wanandroidmvp.presenter.wechat;

import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatListData;
import com.wjx.android.wanandroidmvp.contract.wechat.Contract;
import com.wjx.android.wanandroidmvp.model.WeChatListModel;

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
 * Time: 12:59
 */
public class WeChatListPresenter extends BasePresenter<Contract.IWeChatListView> implements Contract.IWeChatListPresenter {

    Contract.IWeChatListModel iWeChatListModel;

    public WeChatListPresenter() {
        iWeChatListModel = new WeChatListModel();
    }
    @Override
    public void loadWeChatList(int cid, int pageNum) {
        iWeChatListModel.loadWeChatList(cid, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatListData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(WeChatListData weChatListData) {
                        if (isViewAttached()) {
                            getView().loadWeChatList(weChatListData);
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
    public void refreshWeChatList() {

    }
}
