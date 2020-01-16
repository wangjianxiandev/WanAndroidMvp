package com.wjx.android.wanandroidmvp.contract.setting;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.me.LogoutData;

import io.reactivex.Observable;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/16
 * Time: 13:54
 */
public class Contract {
    public interface ILogoutModel {
        /**
         * 退出登录
         * @return
         */
        Observable<LogoutData> logout();
    }

    public interface ILogoutView extends IBaseView {
        void onLogout(LogoutData logoutData);
    }

    public interface ILogoutPresenter {
        void logout();
    }
}
