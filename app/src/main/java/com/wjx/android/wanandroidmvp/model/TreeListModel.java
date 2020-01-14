package com.wjx.android.wanandroidmvp.model;

import androidx.core.widget.NestedScrollView;

import com.airbnb.lottie.L;
import com.blankj.utilcode.util.NetworkUtils;
import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.contract.square.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/09
 * Time: 11:20
 */
public class TreeListModel extends BaseModel implements Contract.ITreeListModel {

    public TreeListModel() {
        setCookies(false);
    }
    @Override
    public Observable<List<Article>> loadTreeList(int pageNum, int cid) {
        Observable<List<Article>> loadFromLocal = Observable.create(emitter -> {
            List<Article> treeList = LitePal.where("type=? and treeType=?"
                    , Article.TYPE_TREE+"", cid +"")
                    .order("time desc")
                    .offset(pageNum * Constant.PAGE_SIZE)
                    .limit(Constant.PAGE_SIZE)
                    .find(Article.class);
            emitter.onNext(treeList);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Article>> loadFromNet = loadTreeListFromNet(pageNum, cid);
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    private Observable<List<Article>> loadTreeListFromNet(int pageNum, int cid) {
        return mApiServer.loadTreeArticle(pageNum,cid)
                .filter(treeListArticle -> treeListArticle.getErrorCode() == Constant.SUCCESS)
                .map(treeListArticle -> {
                    List<Article> allTreeList = LitePal.where("type=? and treeType=?"
                            , Article.TYPE_TREE + "", cid + "")
                            .find(Article.class);
                    List<Article> treeArticleList = new ArrayList<>();
                    treeListArticle.getData().getDatas().stream().forEach(datasBean -> {
                        long count = allTreeList.stream().filter(m -> m.articleId == datasBean.getId()).count();
                        if (count <= 0) {
                            Article article = new Article();
                            article.type = Article.TYPE_TREE;
                            article.articleId = datasBean.getId();
                            article.title = datasBean.getTitle();
                            article.desc = datasBean.getDesc();
                            article.authorId = datasBean.getChapterId();
                            article.author = datasBean.getAuthor();
                            article.chapterName = datasBean.getChapterName();
                            article.superChapterName = datasBean.getSuperChapterName();
                            article.time = datasBean.getPublishTime();
                            article.link = datasBean.getLink();
                            article.niceDate = datasBean.getNiceDate();
                            article.collect = datasBean.isCollect();
                            article.shareUser = datasBean.getShareUser();
                            article.treeType = cid;
                            treeArticleList.add(article);
                        } else {
                            allTreeList.stream().filter(m ->m.articleId == datasBean.getId()).forEach(m ->{
                                if (m.time != datasBean.getPublishTime() || m.collect != datasBean.isCollect()) {
                                    m.title = datasBean.getTitle();
                                    m.desc = datasBean.getDesc();
                                    m.authorId = datasBean.getChapterId();
                                    m.author = datasBean.getAuthor();
                                    m.chapterName = datasBean.getChapterName();
                                    m.superChapterName = datasBean.getSuperChapterName();
                                    m.time = datasBean.getPublishTime();
                                    m.niceDate = datasBean.getNiceDate();
                                    m.link = datasBean.getLink();
                                    m.collect = datasBean.isCollect();
                                    m.shareUser = datasBean.getShareUser();
                                    if (!m.collect) {
                                        m.setToDefault("collect");
                                    }
                                    m.update(m.id);
                                }
                            });

                        }
                    });
                    LitePal.saveAll(treeArticleList);
                    List<Article> resultTreeArticle = LitePal.where("type=? and treeType=?"
                            , Article.TYPE_TREE + "", cid + "")
                            .order("time desc")
                            .offset(pageNum * Constant.PAGE_SIZE)
                            .limit(Constant.PAGE_SIZE)
                            .find(Article.class);
                    return resultTreeArticle;
                });
    }

    @Override
    public Observable<List<Article>> refreshTreeList(int pageNum, int cid) {
        return loadTreeListFromNet(pageNum, cid);
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
