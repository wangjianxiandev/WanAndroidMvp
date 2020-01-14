package com.wjx.android.wanandroidmvp.presenter.me;

import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.contract.login.Contract;
import com.wjx.android.wanandroidmvp.model.LoginModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/11
 * Time: 22:15
 */
public class LoginPresenter extends BasePresenter<Contract.ILoginView> implements Contract.ILoginPresenter {
    Contract.ILoginModel iLoginModel;
    public LoginPresenter() {
        iLoginModel = new LoginModel();
    }

    @Override
    public void login(String userName, String passWord) {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        iLoginModel.login(userName, passWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(LoginData loginData) {
                        if (isViewAttached()) {
                            getView().onLogin(loginData);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
