package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.bean.me.LogoutData;
import com.wjx.android.wanandroidmvp.contract.setting.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/16
 * Time: 13:56
 */
public class SettingModel extends BaseModel implements Contract.ILogoutModel {
    public SettingModel() {
        setCookies(true);
    }

    @Override
    public Observable<LogoutData> logout() {
        return mApiServer.logout();
    }
}
