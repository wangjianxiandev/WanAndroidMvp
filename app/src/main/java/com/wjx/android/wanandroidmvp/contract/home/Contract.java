package com.wjx.android.wanandroidmvp.contract.home;

import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description: 契约类
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 15:55
 */
public class Contract {

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
        Observable<ArticleBean> loadArticle(int pageNum);
    }

    public interface IHomeView{
        /**
         * 获取Banner数据进行显示
         * @param bannerBean
         */
        void loadBanner(BannerBean bannerBean);

        /**
         * 获取Article数据进行显示
         * @param articleBean
         */
        void loadArticle(ArticleBean articleBean);
    }

    public interface IHomePresenter{
        /**
         * 首页Banner
         */
        void loadBanner();

        /**
         * 首页文章
         */
        void loadArticle();
    }
}
