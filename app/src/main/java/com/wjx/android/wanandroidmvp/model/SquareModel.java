package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;
import com.wjx.android.wanandroidmvp.contract.square.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/29
 * Time: 14:56
 */
public class SquareModel extends BaseModel implements Contract.ISquareModel {

    public SquareModel() {
        setCookies(false);
    }
    @Override
    public Observable<NavigationData> loadNavigation() {
        return mApiServer.loadNavigationData();
    }
}
