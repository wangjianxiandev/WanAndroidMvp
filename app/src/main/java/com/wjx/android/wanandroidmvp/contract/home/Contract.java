package com.wjx.android.wanandroidmvp.contract.home;

import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBeansNew;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description: HomePager契约类
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 15:55
 */
public class Contract {

    /**
     * 用来指示错误和完成的接口
     */
    public interface IBaseView {
        /**
         * 加载错误回调
         * @param e
         */
        void onError(Throwable e);

        /**
         * 加载完成
         */
        void onComplete();
    }

    public interface IHomeModel{
        /**
         * 获取banner数据
         * @return banner数据
         */
        Observable<BannerBean> loadBanner();

        /**
         * 获取文章数据
         * @return 文章数据
         */
        Observable<List<ArticleBeansNew>> loadArticle(int pageNum);

        /**
         * 刷新文章列表
         * @return
         */
        Observable<List<ArticleBeansNew>> refreshArticle();
    }

    public interface IHomeView extends IBaseView{
        /**
         * 获取Banner数据进行显示
         * @param bannerBean
         */
        void loadBanner(BannerBean bannerBean);

        /**
         * 获取Article数据进行显示
         * @param articleBean
         */
        void loadArticle(List<ArticleBeansNew> articleBean);

        /**
         * 刷新首页文章
         * @param articleBeans
         */
        void refreshArticle(List<ArticleBeansNew> articleBeans);
    }

    public interface IHomePresenter{
        /**
         * 首页Banner
         */
        void loadBanner();

        /**
         * 首页文章
         */
        void loadArticle(int pageNum);

        /**
         * 刷新首页文章
         */
        void refreshArticle();
    }
}
