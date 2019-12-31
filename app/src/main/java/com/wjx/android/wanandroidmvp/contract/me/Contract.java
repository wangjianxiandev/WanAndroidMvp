package com.wjx.android.wanandroidmvp.contract.me;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/31
 * Time: 14:15
 */
public class Contract {

    public interface ILoginModel {
        Observable<LoginData> login(String userName, String passWord);

    }

    public interface ILoginView extends IBaseView {
        void onLogin (LoginData loginData);
    }

    public interface ILoginPresenter {
        void login (String userName, String passWord);
    }
}
