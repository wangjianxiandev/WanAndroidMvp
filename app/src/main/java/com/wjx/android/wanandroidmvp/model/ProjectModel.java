package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.utils.ApiServer;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.contract.project.Contract;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wjx.android.wanandroidmvp.base.utils.Constant.BASE_URL;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/27
 * Time: 15:20
 */
public class ProjectModel implements Contract.IProjectModel {

    @Override
    public Observable<ProjectClassifyData> loadProjectClassify() {
        return getApiServer().loadProjectClassify();
    }

    @Override
    public Observable<List<ProjectListData>> loadProjectList(int pageNum, int cid) {
        return null;
    }

    @Override
    public Observable<List<ProjectListData>> refreshProjectList() {
        return null;
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
