package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.bean.me.RegisterData;
import com.wjx.android.wanandroidmvp.contract.register.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/26
 * Time: 15:18
 */
public class RegisterModel extends BaseModel implements Contract.IRegisterModel {
    @Override
    public Observable<RegisterData> register(String userName, String passWord) {
        return mApiServer.register(userName, passWord, passWord);
    }
}
