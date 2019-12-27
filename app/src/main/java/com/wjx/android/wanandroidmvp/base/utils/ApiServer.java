package com.wjx.android.wanandroidmvp.base.utils;

import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 15:56
 */
public interface ApiServer {
    /**
     * 获取首页Banner
     * @return
     */
    @GET("banner/json")
    Observable<BannerBean> loadBanner();

    /**
     * 获取首页文章
     * @return
     */
    @GET("article/list/{pageNum}/json")
    Observable<ArticleBean> loadArticle(@Path("pageNum") int number);

    /**
     * 刷新获取首页文章
     * @return
     */
    @GET("article/list/{0}/json")
    Observable<ArticleBean> refreshArticle();

    /**
     * 获取项目种类
     * @return
     */
    @GET("project/tree/json")
    Observable<ProjectClassifyData> loadProjectClassify();

    /**
     * 获取项目列表
     * @return
     */
    @GET("project/list/{pageNum}/json")
    Observable<ProjectListData> loadProjectList(@Path("pageNum") int number, @Query("cid") int cid);

    /**
     * 刷新项目列表
     * @return
     */
    @GET("project/list/{0}/json")
    Observable<ProjectListData> refreshProjectList();
}
