package com.wjx.android.wanandroidmvp.base.utils;

import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
}
