package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBeansNew;
import com.wjx.android.wanandroidmvp.contract.home.Contract;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;
import com.wjx.android.wanandroidmvp.base.utils.ApiServer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * Description: HomePager的model层，做网络层封装并进行请求处理，创建接口实例
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 15:59
 */
public class HomeModel implements Contract.IHomeModel {
    private static final String BASE_URL = Constant.BASE_URL;

    List<ArticleBeansNew> mArticleBeansNew = new ArrayList<>();

    @Override
    public Observable<BannerBean> loadBanner() {
        return getApiServer().loadBanner();
    }

    @Override
    public Observable<List<ArticleBeansNew>> loadArticle(int pageNum) {
        return getApiServer().loadArticle(pageNum).filter(articleBean -> articleBean.getErrorCode() == 0)
                .map(original -> {
                    original.getData().getDatas().stream().sorted((original1, original2) -> (int) (original2.getPublishTime() - original1.getPublishTime()))
                            .forEach(datas -> {
                                long count = mArticleBeansNew.stream().filter(b -> b.id == datas.getId()).count();
                                if (count <= 0) {
                                    ArticleBeansNew articleBeansNew = new ArticleBeansNew();
                                    articleBeansNew.id = datas.getId();
                                    articleBeansNew.title = datas.getTitle();
                                    articleBeansNew.author = datas.getAuthor();
                                    articleBeansNew.niceDate = datas.getNiceDate();
                                    articleBeansNew.publishTime = datas.getPublishTime();
                                    articleBeansNew.chapterName = datas.getChapterName();
                                    articleBeansNew.superChapterName = datas.getSuperChapterName();
                                    articleBeansNew.collect = datas.isCollect();
                                    articleBeansNew.shareUser = datas.getShareUser();
                                    articleBeansNew.link = datas.getLink();
                                    articleBeansNew.origin = datas.getOrigin();
                                    mArticleBeansNew.add(articleBeansNew);
                                }
                            });
                    return mArticleBeansNew;
                });
    }

    @Override
    public Observable<List<ArticleBeansNew>> refreshArticle() {
        return getApiServer().refreshArticle().filter(articleBean -> articleBean.getErrorCode() == 0)
                .map(original -> {
                    original.getData().getDatas().stream().sorted((o1, o2) -> (int) (o1.getPublishTime() - o2.getPublishTime()))
                            .forEach(datas -> {
                                // 如果是新数据
                                long count = mArticleBeansNew.stream().filter(b -> b.id == datas.getId()).count();
                                if (count <= 0) {
                                    ArticleBeansNew articleBeansNew = new ArticleBeansNew();
                                    articleBeansNew.id = datas.getId();
                                    articleBeansNew.title = datas.getTitle();
                                    articleBeansNew.author = datas.getAuthor();
                                    articleBeansNew.niceDate = datas.getNiceDate();
                                    articleBeansNew.publishTime = datas.getPublishTime();
                                    articleBeansNew.chapterName = datas.getChapterName();
                                    articleBeansNew.superChapterName = datas.getSuperChapterName();
                                    articleBeansNew.collect = datas.isCollect();
                                    articleBeansNew.shareUser = datas.getShareUser();
                                    articleBeansNew.link = datas.getLink();
                                    articleBeansNew.origin = datas.getOrigin();
                                    mArticleBeansNew.add(0, articleBeansNew);
                                }
                            });
                    return mArticleBeansNew;
                });
    }

    /**
     * 获取请求对象
     * @return 当前的请求对象
     */
    private ApiServer getApiServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        return apiServer;
    }
}
