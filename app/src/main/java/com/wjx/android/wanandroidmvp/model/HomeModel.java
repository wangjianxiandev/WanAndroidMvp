package com.wjx.android.wanandroidmvp.model;

import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.bean.db.Banner;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.contract.home.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description: HomePager的model层，做网络层封装并进行请求处理，创建接口实例, 并将实例缓存在本地增加用户体验
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 15:59
 */
public class HomeModel extends BaseModel implements Contract.IHomeModel {

    public HomeModel() {
        setCookies(false);
    }

    @Override
    public Observable<List<Banner>> loadBanner() {
        Observable<List<Banner>> loadFromLocal = Observable.create(emitter -> {
            List<Banner> bannerList = LitePal.findAll(Banner.class);
            emitter.onNext(bannerList);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Banner>> loadFromNet = loadBannerFromNet();
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    /**
     * 加载并更新本地的Banner
     *
     * @return
     */
    private Observable<List<Banner>> loadBannerFromNet() {
        return mApiServer.loadBanner().filter(banner -> banner.getErrorCode() == Constant.SUCCESS)
                .map(banner -> {
                    List<Banner> bannerList = new ArrayList<>();
                    banner.getData()
                            .stream().forEach(dataBean -> {
                        Banner bannerDB = new Banner();
                        bannerDB.title = dataBean.getTitle();
                        bannerDB.imagePath = dataBean.getImagePath();
                        bannerDB.url = dataBean.getUrl();
                        bannerList.add(bannerDB);
                    });
                    if (bannerList.size() > 0) {
                        LitePal.deleteAll(Banner.class);
                    }
                    LitePal.saveAll(bannerList);
                    return bannerList;
                });
    }

    /**
     * 刷新Banner
     *
     * @return
     */
    @Override
    public Observable<List<Banner>> refreshBanner() {
        return loadBannerFromNet();
    }

    @Override
    public Observable<List<Article>> loadArticle(int pageNum) {
        Observable<List<Article>> loadFromLocal = Observable.create(emitter -> {
            List<Article> articleList = LitePal.where("type=?", Article.TYPE_HOME + "")
                    .order("time desc")
                    .offset(pageNum * Constant.PAGE_SIZE)
                    .limit(Constant.PAGE_SIZE)
                    .find(Article.class);
            emitter.onNext(articleList);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Article>> loadFromNet = loadArticleFromNet(pageNum);
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    @Override
    public Observable<List<Article>> loadTopArticle() {
        Observable<List<Article>> loadFromLocal = Observable.create(emitter -> {
            List<Article> topArticleList = LitePal.where("type=?", Article.TYPE_TOP+"")
                    .order("time desc")
                    .find(Article.class);
            emitter.onNext(topArticleList);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Article>> loadFromNet = loadTopArticleFromNet();
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    private Observable<List<Article>> loadTopArticleFromNet() {
        return mApiServer.loadTopArticle().filter(topArticleBean -> topArticleBean.getErrorCode() == Constant.SUCCESS)
                .map(topArticleBean -> {
                    List<Article> allTopArticleList = LitePal.where("type=?", Article.TYPE_TOP + "")
                            .find(Article.class);
                    List<Article> topArticleList = new ArrayList<>();
                    topArticleBean.getData().stream().forEach(datasBean -> {
                        long count = allTopArticleList.stream().filter(m -> m.articleId == datasBean.getId()).count();
                        if (count <= 0) {
                            Article article = new Article();
                            article.type = Article.TYPE_TOP;
                            article.articleId = datasBean.getId();
                            article.title = datasBean.getTitle();
                            article.author = datasBean.getAuthor();
                            article.chapterName = datasBean.getChapterName();
                            article.superChapterName = datasBean.getSuperChapterName();
                            article.time = datasBean.getPublishTime();
                            article.link = datasBean.getLink();
                            article.collect = datasBean.isCollect();
                            article.niceDate = datasBean.getNiceDate();
                            article.shareUser = datasBean.getShareUser();
                            article.isFresh = datasBean.isFresh();
                            article.isTop = true;
                            if (article.superChapterName.equals("问答")) {
                                article.isQuestion = true;
                            }
                            topArticleList.add(article);
                        } else {
                            allTopArticleList.stream().filter(m -> m.articleId == datasBean.getId()).forEach(m -> {
                                if (m.time != datasBean.getPublishTime() || m.collect != datasBean.isCollect()) {
                                    m.title = datasBean.getTitle();
                                    m.author = datasBean.getAuthor();
                                    m.link = datasBean.getLink();
                                    m.chapterName = datasBean.getChapterName();
                                    m.superChapterName = datasBean.getSuperChapterName();
                                    m.collect = datasBean.isCollect();
                                    m.niceDate = datasBean.getNiceDate();
                                    m.time = datasBean.getPublishTime();
                                    m.shareUser = datasBean.getShareUser();
                                    m.isFresh = datasBean.isFresh();
                                    m.isTop = true;
                                    if (m.superChapterName.equals("问答")) {
                                        m.isQuestion = true;
                                    }
                                    if (!m.collect) {
                                        m.setToDefault("collect");
                                    }
                                    m.update(m.id);
                                }
                            });
                        }
                    });
                    LitePal.saveAll(topArticleList);
                    List<Article> topArticleResult = LitePal.where("type=?", Article.TYPE_TOP + "")
                            .order("time desc")
                            .find(Article.class);
                    return topArticleResult;
                });
    }

    @Override
    public Observable<List<Article>> refreshTopArticle() {
        return loadTopArticleFromNet();
    }


    private Observable<List<Article>> loadArticleFromNet(int pageNum) {
        return mApiServer.loadArticle(pageNum).filter(articleBean ->
                articleBean.getErrorCode() == Constant.SUCCESS)
                .map(articleBean -> {
                    List<Article> allArticles = LitePal.where("type=?", Article.TYPE_HOME + "")
                            .find(Article.class);
                    List<Article> articleList = new ArrayList<>();
                    articleBean.getData().getDatas().stream().forEach(datasBean -> {
                        long count = allArticles.stream().filter(m -> m.articleId == datasBean.getId()).count();
                        if (count <= 0) {
                            Article article = new Article();
                            article.type = Article.TYPE_HOME;
                            article.articleId = datasBean.getId();
                            article.title = datasBean.getTitle();
                            article.author = datasBean.getAuthor();
                            article.chapterName = datasBean.getChapterName();
                            article.superChapterName = datasBean.getSuperChapterName();
                            article.time = datasBean.getPublishTime();
                            article.link = datasBean.getLink();
                            article.collect = datasBean.isCollect();
                            article.niceDate = datasBean.getNiceDate();
                            article.shareUser = datasBean.getShareUser();
                            article.isFresh = datasBean.isFresh();
                            article.isTop = false;
                            articleList.add(article);
                        } else {
                            allArticles.stream().filter(m -> m.articleId == datasBean.getId()).forEach(m -> {
                                if (m.time != datasBean.getPublishTime() || m.collect != datasBean.isCollect()) {
                                    m.title = datasBean.getTitle();
                                    m.author = datasBean.getAuthor();
                                    m.link = datasBean.getLink();
                                    m.chapterName = datasBean.getChapterName();
                                    m.superChapterName = datasBean.getSuperChapterName();
                                    m.collect = datasBean.isCollect();
                                    m.niceDate = datasBean.getNiceDate();
                                    m.time = datasBean.getPublishTime();
                                    m.shareUser = datasBean.getShareUser();
                                    m.isFresh = datasBean.isFresh();
                                    m.isTop = false;
                                    if (!m.collect) {
                                        m.setToDefault("collect");
                                    }
                                    m.update(m.id);
                                }
                            });
                        }
                    });
                    LitePal.saveAll(articleList);
                    List<Article> articleResult = LitePal.where("type=?", Article.TYPE_HOME + "")
                            .order("time desc")
                            .offset(pageNum * Constant.PAGE_SIZE)
                            .limit(Constant.PAGE_SIZE)
                            .find(Article.class);
                    return articleResult;
                });
    }

    @Override
    public Observable<List<Article>> refreshArticle(int pageNum) {
        return loadArticleFromNet(pageNum);
    }

    @Override
    public Observable<Collect> collect(int articleId) {
        return mApiServer.onCollect(articleId);
    }

    @Override
    public Observable<Collect> unCollect(int articleId) {
        return mApiServer.unCollect(articleId);
    }
}
