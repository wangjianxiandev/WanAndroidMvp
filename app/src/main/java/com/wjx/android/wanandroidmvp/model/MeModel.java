package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.bean.me.IntegralData;
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
public class MeModel extends BaseModel implements Contract.IMeModel {
    public MeModel() {
        setCookies(false);
    }

    @Override
    public Observable<IntegralData> loadIntegralData() {
        return mApiServer.loadIntegralData();
    }

    @Override
    public Observable<IntegralData> refreshIntegralData() {
        return mApiServer.loadIntegralData();
    }


}
