package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.contract.me.Contract;


import io.reactivex.Observable;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/31
 * Time: 14:19
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
