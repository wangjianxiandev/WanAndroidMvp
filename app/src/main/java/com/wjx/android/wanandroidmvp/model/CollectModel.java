package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.utils.ApiServer;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.collect.CollectBean;
import com.wjx.android.wanandroidmvp.contract.collect.Contract;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/01
 * Time: 22:40
 */
public class CollectModel implements Contract.ICollectModel {
    @Override
    public Observable<CollectBean> loadCollect(int pageNum) {
        return getApiServer().loadCollect(pageNum);
    }

    /**
     * 获取请求对象
     * @return 当前的请求对象
     */
    private ApiServer getApiServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        return apiServer;
    }
}
