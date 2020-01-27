package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.bean.searchresult.SearchResultData;
import com.wjx.android.wanandroidmvp.contract.searchresult.Contract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/27
 * Time: 18:26
 */
public class SearchResultModel extends BaseModel implements Contract.ISearchResultModel {

    public SearchResultModel() {
        setCookies(false);
    }

    @Override
    public Observable<List<Article>> loadSearchResult(int pageNum, String keyWord) {
        Observable<List<Article>> loadFromNet = loadSearchResultFromNet(pageNum, keyWord);
        return loadFromNet;
    }

    private Observable<List<Article>> loadSearchResultFromNet(int pageNum, String keyWord) {
        return mApiServer.loadSearchResultData(pageNum, keyWord).filter(searchResultData -> searchResultData.getErrorCode() == Constant.SUCCESS)
                .map(searchResultData -> {
                    List<Article> searchResultList = new ArrayList<>();
                    searchResultData.getData().getDatas().stream().forEach(datasBean -> {
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
                        article.isFresh = datasBean.isFresh();
                        searchResultList.add(article);
                    });
                    return searchResultList;
                });
    }

    @Override
    public Observable<List<Article>> refreshSearchResult(int pageNum, String keyWord) {
        return loadSearchResultFromNet(pageNum, keyWord);
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
