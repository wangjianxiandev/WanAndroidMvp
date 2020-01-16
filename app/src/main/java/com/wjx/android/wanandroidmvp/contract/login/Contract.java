package com.wjx.android.wanandroidmvp.contract.login;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.bean.me.LogoutData;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/11
 * Time: 22:15
 */
public class Contract {
    public interface ILoginModel {
        /**
         * 登录
         * @param userName
         * @param passWord
         * @return
         */
        Observable<LoginData> login(String userName, String passWord);

    }

    public interface ILoginView extends IBaseView {
        void onLogin (LoginData loginData);

    }

    public interface ILoginPresenter {
        void login (String userName, String passWord);
    }
}
