package com.wjx.android.wanandroidmvp.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ArticleAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.GlideImageLoader;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.contract.home.Contract;
import com.wjx.android.wanandroidmvp.presenter.home.HomePresenter;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/14
 * Time: 16:33
 */
public class HomeFragment extends BaseFragment<Contract.IHomeView, HomePresenter> implements Contract.IHomeView {

    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mPresenter.loadBanner();
        mPresenter.loadArticle();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void loadBanner(BannerBean bannerBean) {
        mBanner.setImageLoader(new GlideImageLoader());
        if (bannerBean.getErrorCode() == Constant.BANNER_SUCCESS) {
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            List<String> imageUrls = bannerBean.getData()
                    .stream()
                    .map(BannerBean.DataBean::getImagePath)
                    .collect(Collectors.toList());
            mBanner.setImages(imageUrls);
            List<String> bannerTitles = bannerBean.getData()
                    .stream()
                    .map(BannerBean.DataBean::getTitle)
                    .collect(Collectors.toList());
            mBanner.setBannerTitles(bannerTitles);
            mBanner.start();
        }
    }

    @Override
    public void loadArticle(ArticleBean articleBean) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (articleBean.getErrorCode() == Constant.BANNER_SUCCESS) {
            ArticleAdapter articleAdapter = new ArticleAdapter(mRecyclerView);
            articleAdapter.setBeans(articleBean);
            mRecyclerView.setAdapter(articleAdapter);
        }
    }
}
