package com.wjx.android.wanandroidmvp.model;

import com.blankj.utilcode.util.NetworkUtils;
import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.Constant;

import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.contract.project.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/27
 * Time: 17:44
 */
public class ProjectListModel extends BaseModel implements Contract.IProjectListModel {

    public ProjectListModel() {
        setCookies(false);
    }


    @Override
    public Observable<List<Article>> loadProjectList(int pageNum, int cid) {
//        Observable<List<Article>> loadFromLocal = Observable.create(emitter -> {
//            List<Article> projectArticleList = LitePal.where("type=? and projectType=?", Article.TYPE_PROJECT + "", cid + "")
//                    .order("time desc")
//                    .offset(pageNum * Constant.PAGE_SIZE)
//                    .limit(Constant.PAGE_SIZE)
//                    .find(Article.class);
//            emitter.onNext(projectArticleList);
//            emitter.onComplete();
//        });
//        if (NetworkUtils.isConnected()) {
        Observable<List<Article>> loadFromNet = loadProjectArticleListFromNet(pageNum, cid);
//            return Observable.concat(loadFromLocal, loadFromNet);
//        } else {
        return loadFromNet;
//        }
    }

    private Observable<List<Article>> loadProjectArticleListFromNet(int pageNum, int cid) {
        return mApiServer.loadProjectList(pageNum, cid)
                .filter(projectListData -> projectListData.getErrorCode() == Constant.SUCCESS)
                .map(projectListData -> {
//                    List<Article> allProjectArticleList = LitePal.where("type=? and projectType=?"
//                            , Article.TYPE_PROJECT + "", cid + "")
//                            .find(Article.class);
                    List<Article> projectArticleLists = new ArrayList<>();
                    projectListData.getData().getDatas().stream().forEach(datasBean -> {
//                        long count = allProjectArticleList.stream().filter(m -> m.articleId == datasBean.getId()).count();
//                        if (count <= 0) {
                        Article article = new Article();
                        article.type = Article.TYPE_PROJECT;
                        article.articleId = datasBean.getId();
                        article.title = datasBean.getTitle();
                        article.author = datasBean.getAuthor();
                        article.authorId = datasBean.getChapterId();
                        article.niceDate = datasBean.getNiceDate();
                        article.time = datasBean.getPublishTime();
                        article.chapterName = datasBean.getChapterName();
                        article.superChapterName = datasBean.getSuperChapterName();
                        article.projectType = datasBean.getChapterId();
                        article.link = datasBean.getLink();
                        article.desc = datasBean.getDesc();
                        article.envelopePic = datasBean.getEnvelopePic();
                        projectArticleLists.add(article);
//                        } else {
//                            allProjectArticleList.stream().filter(m -> m.articleId == datasBean.getId()).forEach(m -> {
//                                if (m.niceDate != datasBean.getNiceDate() || m.collect != datasBean.isCollect()) {
//                                    m.articleId = datasBean.getId();
//                                    m.title = datasBean.getTitle();
//                                    m.author = datasBean.getAuthor();
//                                    m.authorId = datasBean.getChapterId();
//                                    m.niceDate = datasBean.getNiceDate();
//                                    m.time = datasBean.getPublishTime();
//                                    m.chapterName = datasBean.getChapterName();
//                                    m.superChapterName = datasBean.getSuperChapterName();
//                                    m.projectType = datasBean.getChapterId();
//                                    m.link = datasBean.getLink();
//                                    m.desc = datasBean.getDesc();
//                                    m.envelopePic = datasBean.getEnvelopePic();
//                                    if (!m.collect) {
//                                        m.setToDefault("collect");
//                                    }
//                                    m.update(m.id);
//                                }
//                            });
//                        }
                    });
//                    LitePal.saveAll(projectArticleLists);
//                    List<Article> projectArticleResult = LitePal.where("type=? and projectType=?"
//                            , Article.TYPE_PROJECT + "", cid + "")
//                            .order("time desc")
//                            .offset(pageNum * Constant.PAGE_SIZE)
//                            .limit(Constant.PAGE_SIZE)
//                            .find(Article.class);
                    return projectArticleLists;
                });
    }


    @Override
    public Observable<List<Article>> refreshProjectList(int pageNum, int cid) {
        return loadProjectArticleListFromNet(pageNum, cid);
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
