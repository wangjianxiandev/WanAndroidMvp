package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.ApiServer;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;
import com.wjx.android.wanandroidmvp.contract.square.Contract;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
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
