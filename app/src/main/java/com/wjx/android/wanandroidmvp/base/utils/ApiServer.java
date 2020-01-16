package com.wjx.android.wanandroidmvp.base.utils;

import com.wjx.android.wanandroidmvp.bean.collect.AddCollect;
import com.wjx.android.wanandroidmvp.bean.collect.CollectData;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.bean.home.Banner;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.home.TopArticleBean;
import com.wjx.android.wanandroidmvp.bean.me.IntegralData;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.bean.me.LogoutData;
import com.wjx.android.wanandroidmvp.bean.rank.RankData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.bean.searchwords.SearchWordData;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;
import com.wjx.android.wanandroidmvp.bean.square.SquareData;
import com.wjx.android.wanandroidmvp.bean.square.TreeData;
import com.wjx.android.wanandroidmvp.bean.square.TreeListArticle;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatClassifyData;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatListData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created with Android Studio.
 * Description: 请求APi
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
    @GET(Constant.BANNER_URL)
    Observable<Banner> loadBanner();

    /**
     * 获取首页文章
     * @return
     */
    @GET(Constant.ARTICLE_URL)
    Observable<ArticleBean> loadArticle(@Path("pageNum") int number);

    /**
     * 获取置顶文章
     * @return
     */
    @GET(Constant.TOP_ARTICLE_URL)
    Observable<TopArticleBean> loadTopArticle();

    /**
     * 获取项目种类
     * @return
     */
    @GET(Constant.PROJECT_CATEGORY_URL)
    Observable<ProjectClassifyData> loadProjectClassify();

    /**
     * 获取项目列表
     * @return
     */
    @GET(Constant.PROJECT_LIST_URL)
    Observable<ProjectListData> loadProjectList(@Path("pageNum") int number, @Query("cid") int cid);


    /**
     * 获取公众号Tab
     * @return
     */
    @GET(Constant.WE_CHAT_TAB_URL)
    Observable<WeChatClassifyData> loadWeChatClassify();

    /**
     * 获取某页公众号列表
     * @param id
     * @param pageNum
     * @return
     */
    @GET(Constant.WE_CHAT_LIST_URL)
    Observable<WeChatListData> loadWeChatList(@Path("id") int id, @Path("pageNum") int pageNum);

    /**
     * 获取广场数据
     * @param pageNum
     * @return
     */
    @GET(Constant.HOME_SQUARE_URL)
    Observable<SquareData> loadHomeSquareData(@Path("pageNum") int pageNum);

    /**
     * 获取导航数据
     * @return
     */
    @GET(Constant.NAVI_URL)
    Observable<NavigationData> loadNavigationData();

    /**
     * 获取体系数据
     * @return
     */
    @GET(Constant.TREE_URL)
    Observable<TreeData> loadTreeData();

    /**
     * 获取体系文章列表
     * @param pageNum
     * @param cid
     * @return
     */
    @GET(Constant.TREE_ARTICLE_URL)
    Observable<TreeListArticle> loadTreeArticle(@Path("pageNum") int pageNum, @Query("cid") int cid);

    /**
     * 获取搜索热词
     * @return
     */
    @GET(Constant.HOT_SEARCH_URL)
    Observable<SearchWordData> loadSearchWordData();


    /**
     * 登陆数据
     * @param userName
     * @param passWord
     * @return
     */
    @POST(Constant.URL_LOGIN)
    Observable<LoginData> login(@Query("username") String userName, @Query("password") String passWord);

    /**
     * 注册数据
     * @param userName
     * @param password
     * @param repassword
     * @return
     */
    @POST(Constant.URL_REGISTER)
    Observable<LoginData> register(@Query("username") String userName, @Query("password") String password, @Query("repassword") String repassword);

    /**
     * 登出
     * @return
     */
    @GET(Constant.URL_LOGINOUT)
    Observable<LogoutData> logout();

    /**
     * 点击收藏文章
     * @param id
     * @return
     */
    @POST(Constant.URL_COLLECT)
    Observable<Collect> onCollect(@Path("id") int id);

    /**
     * 点击取消收藏文章
     * @param id
     * @return
     */
    @POST(Constant.URL_UNCOLLECT)
    Observable<Collect> unCollect(@Path("id") int id);

    /**
     * 我的收藏页面取消收藏文章包括自己录入的内容
     * @param id
     * @param originId
     * @return
     */
    @POST(Constant.URL_UNCOLLECT_INCLUDE_ADD)
    Observable<Collect> unCollect(@Path("id") int id, @Query("originId") int originId);

    /**
     * 获取收藏文章列表
     *
     * @param pageNum
     * @return
     */
    @GET(Constant.URL_COLLECT_LIST)
    Observable<CollectData> loadCollect(@Path("pageNum") int pageNum);

    /**
     * 添加站外收藏
     *
     * @param title
     * @param author
     * @param link
     * @return
     */
    @POST(Constant.URL_ADD_COLLECT)
    Observable<AddCollect> addCollect(@Query("title") String title, @Query("author") String author, @Query("link") String link);

    /**
     * 获取个人积分排行
     * @return
     */
    @GET(Constant.URL_INTEGRAL)
    Observable<IntegralData> loadIntegralData();

    /**
     * 获取积分排行列表
     * @return
     */
    @GET(Constant.URL_RANK)
    Observable<RankData> loadRankData(@Path("pageNum") int pageNum);
}
