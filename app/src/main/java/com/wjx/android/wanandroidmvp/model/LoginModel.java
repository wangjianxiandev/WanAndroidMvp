package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.contract.login.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/11
 * Time: 22:16
 */
public class LoginModel extends BaseModel implements Contract.ILoginModel {
    public LoginModel() {
        setCookies(true);
    }

    @Override
    public Observable<LoginData> login(String userName, String passWord) {
        return mApiServer.login(userName, passWord);
    }
}
