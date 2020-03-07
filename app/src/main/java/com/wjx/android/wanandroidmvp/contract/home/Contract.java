package com.wjx.android.wanandroidmvp.contract.home;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.bean.db.Banner;

import com.wjx.android.wanandroidmvp.bean.collect.Collect;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description: HomePager契约类
 *
 * @author: Wangjianxian
 * @date: 2019/12/19
 * Time: 15:55
 */
public class Contract {

    public interface IHomeModel{
        /**
         * 获取banner数据
         * @return banner数据
         */
        Observable<List<Banner>> loadBanner();

        /**
         * 刷新Banner
         * @return
         */
        Observable<List<Banner>> refreshBanner();

        /**
         * 获取文章数据
         * @return 文章数据
         */
        Observable<List<Article>> loadArticle(int pageNum);

        /**
         * 刷新文章列表
         * @return
         */
        Observable<List<Article>> refreshArticle(int pageNum);

        /**
         * 获取置顶文章
         * @return
         */
        Observable<List<Article>> loadTopArticle();

        /**
         * 刷新置顶文章
         * @return
         */
        Observable<List<Article>> refreshTopArticle();

        /**
         * 收藏文章
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);
    }

    public interface IHomeView extends IBaseView {
        /**
         * 获取Banner数据进行显示
         * @param bannerList
         */
        void loadBanner(List<Banner> bannerList);

        void refreshBanner(List<Banner> bannerList);

        /**
         * 获取Article数据进行显示
         * @param articleList
         */
        void loadArticle(List<Article> articleList);


        /**
         * 刷新首页文章
         * @param articleList
         */
        void refreshArticle(List<Article> articleList);

        /**
         * 收藏文章
         *
         * @param collect
         * @param articleId
         */
        void onCollect(Collect collect, int articleId);

        /**
         * 取消收藏文章
         *
         * @param collect
         * @param articleId
         */
        void onUnCollect(Collect collect, int articleId);
    }

    public interface IHomePresenter{
        /**
         * 首页Banner
         */
        void loadBanner();

        void refreshBanner();

        /**
         * 首页文章
         */
        void loadArticle(int pageNum);

        /**
         * 刷新首页文章
         */
        void refreshArticle(int pageNum);

        /**
         * 收藏文章
         *
         * @param articleId
         */
        void collect(int articleId);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollect(int articleId);

    }
}
