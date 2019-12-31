package com.wjx.android.wanandroidmvp.base.utils;

import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.bean.searchwords.SearchWordData;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatClassifyData;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatListData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    /**
     * 获取公众号Tab
     * @return
     */
    @GET("wxarticle/chapters/json")
    Observable<WeChatClassifyData> loadWeChatClassify();

    /**
     * 获取某页公众号列表
     * @param id
     * @param pageNum
     * @return
     */
    @GET("wxarticle/list/{id}/{pageNum}/json")
    Observable<WeChatListData> loadWeChatList(@Path("id") int id, @Path("pageNum") int pageNum);

    /**
     * 刷新公众号列表
     * @return
     */
    @GET("project/list/{0}/json")
    Observable<WeChatListData> refreshWeChatList();


    /**
     * 获取导航数据
     * @return
     */
    @GET("navi/json")
    Observable<NavigationData> loadNavigationData();

    /**
     * 获取搜索热词
     * @return
     */
    @GET("hotkey/json")
    Observable<SearchWordData> loadSearchWordData();


    /**
     * 登陆数据
     * @param userName
     * @param passWord
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<LoginData> loadLoginData(@Field("username") String userName, @Field("password") String passWord);

    /**
     * 注册数据
     * @param userName
     * @param password
     * @param repassword
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<LoginData> loadRegisterData(@Field("username") String userName, @Field("password") String password, @Field("repassword") String repassword);

    @GET("user/logout/json")
    Observable<LoginData> logout();
}
