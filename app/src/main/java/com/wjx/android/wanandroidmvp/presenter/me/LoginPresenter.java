package com.wjx.android.wanandroidmvp.presenter.me;

import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.contract.me.Contract;
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
 * @date: 2019/12/31
 * Time: 14:19
 */
public class LoginPresenter extends BasePresenter<Contract.ILoginView> implements Contract.ILoginPresenter {
    Contract.ILoginModel iLoginModel;

    public LoginPresenter (){
        iLoginModel = new LoginModel();
    }
    @Override
    public void login(String userName, String passWord) {
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
