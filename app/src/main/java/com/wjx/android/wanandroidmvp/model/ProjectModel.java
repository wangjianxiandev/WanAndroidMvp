package com.wjx.android.wanandroidmvp.model;

import com.blankj.utilcode.util.NetworkUtils;
import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.ApiServer;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.db.ProjectClassify;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.contract.project.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
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
public class ProjectModel extends BaseModel implements Contract.IProjectModel {

    public ProjectModel() {
        setCookies(false);
    }
    @Override
    public Observable<List<ProjectClassify>> loadProjectClassify() {
        Observable<List<ProjectClassify>> loadFromLocal = Observable.create(emitter -> {
                List<ProjectClassify> projectClassifyList = LitePal.findAll(ProjectClassify.class);
                emitter.onNext(projectClassifyList);
                emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<ProjectClassify>> loadFromNet = loadProjectClassifyFromNet();
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    private Observable<List<ProjectClassify>> loadProjectClassifyFromNet() {
        return mApiServer.loadProjectClassify().filter(p -> p.getErrorCode() == Constant.SUCCESS)
                .map(p -> {
                    List<ProjectClassify> projectClassifyList = new ArrayList<>();
                    p.getData().stream().forEach(dataBean -> {
                        ProjectClassify projectClassifyDB = new ProjectClassify();
                        projectClassifyDB.categoryId = dataBean.getId();
                        projectClassifyDB.name = dataBean.getName();
                        projectClassifyList.add(projectClassifyDB);
                    });
                    if (projectClassifyList.size() > 0) {
                        LitePal.deleteAll(ProjectClassify.class);
                    }
                    LitePal.saveAll(projectClassifyList);
                    return projectClassifyList;
        });
    }

    @Override
    public Observable<List<ProjectClassify>> refreshProjectClassify() {
        return loadProjectClassifyFromNet();
    }
}
