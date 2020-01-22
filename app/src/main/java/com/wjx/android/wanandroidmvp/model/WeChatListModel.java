package com.wjx.android.wanandroidmvp.model;


import com.blankj.utilcode.util.NetworkUtils;
import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;

import com.wjx.android.wanandroidmvp.contract.wechat.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 12:56
 */
public class WeChatListModel extends BaseModel implements Contract.IWeChatListModel {
    public WeChatListModel() {
        setCookies(false);
    }

    @Override
    public Observable<List<Article>> loadWeChatList(int cid, int pageNum) {
//        Observable<List<Article>> loadFromLocal = Observable.create(emitter -> {
//            List<Article> weChatArticleList = LitePal.where("type=? and authorId=?",
//                    Article.TYPE_WX + "", cid + "")
//                    .order("time desc")
//                    .offset(pageNum * Constant.PAGE_SIZE)
//                    .limit(Constant.PAGE_SIZE)
//                    .find(Article.class);
//            emitter.onNext(weChatArticleList);
//            emitter.onComplete();
//        });
//        if (NetworkUtils.isConnected()) {
            Observable<List<Article>> loadFromNet = loadWeChatArticleFromNet(cid, pageNum);
//            return Observable.concat(loadFromLocal, loadFromNet);
//        } else {
            return loadFromNet;
//        }
    }

    private Observable<List<Article>> loadWeChatArticleFromNet(int cid, int pageNum) {
        return mApiServer.loadWeChatList(cid, pageNum)
                .filter(weChatListData -> weChatListData.getErrorCode() == Constant.SUCCESS)
                .map(weChatListData -> {
//                    List<Article> allWeChatArticleList = LitePal.where("type=? and authorId=?",
//                            Article.TYPE_WX + "", cid + "")
//                            .find(Article.class);
                    List<Article> weChatArticleList = new ArrayList<>();
                    weChatListData.getData().getDatas().stream().forEach(datasBean -> {
//                        long count = allWeChatArticleList.stream().filter(w -> w.articleId == datasBean.getId()).count();
//                        if (count <= 0) {
                            Article article = new Article();
                            article.type = Article.TYPE_WX;
                            article.title = datasBean.getTitle();
                            article.articleId = datasBean.getId();
                            article.desc = datasBean.getDesc();
                            article.authorId = datasBean.getChapterId();
                            article.author = datasBean.getAuthor();
                            article.chapterName = datasBean.getChapterName();
                            article.superChapterName = datasBean.getSuperChapterName();
                            article.link = datasBean.getLink();
                            article.niceDate = datasBean.getNiceDate();
                            article.time = datasBean.getPublishTime();
                            article.isFresh = datasBean.isFresh();
                            weChatArticleList.add(article);
//                        } else {
//                            allWeChatArticleList.stream().filter(w -> w.articleId == datasBean.getId()).forEach(w -> {
//                                if (w.niceDate != datasBean.getNiceDate() || w.collect != datasBean.isCollect()) {
//                                    w.title = datasBean.getTitle();
//                                    w.desc = datasBean.getDesc();
//                                    w.title = datasBean.getTitle();
//                                    w.authorId = datasBean.getChapterId();
//                                    w.author = datasBean.getAuthor();
//                                    w.chapterName = datasBean.getChapterName();
//                                    w.superChapterName = datasBean.getSuperChapterName();
//                                    w.link = datasBean.getLink();
//                                    w.niceDate = datasBean.getNiceDate();
//                                    w.collect = datasBean.isCollect();
//                                    w.time = datasBean.getPublishTime();
//                                    w.isFresh = datasBean.isFresh();
//                                    if (!w.collect) {
//                                        w.setToDefault("collect");
//                                    }
//                                    w.update(w.id);
//                                }
//                            });
//                        }
                    });
//                    LitePal.saveAll(weChatArticleList);
//                    List<Article> weArticleResult = LitePal.where("type=? and authorId=?", Article.TYPE_WX + "", cid + "")
//                            .order("time desc")
//                            .offset(pageNum * Constant.PAGE_SIZE)
//                            .limit(Constant.PAGE_SIZE)
//                            .find(Article.class);
                    return weChatArticleList;
                });
    }

    @Override
    public Observable<List<Article>> refreshWeChatList(int cid, int pageNum) {
        return loadWeChatArticleFromNet(cid, pageNum);
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
