package com.wjx.android.wanandroidmvp.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ArticleAdapter;
import com.wjx.android.wanandroidmvp.adapter.OnItemClickListener;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.GlideImageLoader;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBeansNew;
import com.wjx.android.wanandroidmvp.contract.home.Contract;
import com.wjx.android.wanandroidmvp.presenter.home.HomePresenter;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;


/**
 * Created with Android Studio.
 * Description: 通过实现View接口，Presenter在初始化时调用发送网络请求返回的数据，直接在Fragment中处理加载
 *
 * @author: 王拣贤
 * @date: 2019/12/14
 * Time: 16:33
 */
public class HomeFragment extends BaseFragment<Contract.IHomeView, HomePresenter> implements Contract.IHomeView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private ArticleAdapter mArticleAdapter;
    private int mCurpage = 0;

    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mPresenter.loadBanner();
        mPresenter.loadArticle(mCurpage);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
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
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    JumpWebUtils.startWebView(getContext(),
                            bannerBean.getData().get(position).getTitle(),
                            bannerBean.getData().get(position).getUrl());
                }
            });
            mBanner.start();
        }
    }

    @Override
    public void loadArticle(List<ArticleBeansNew> articleBean) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mArticleAdapter = new ArticleAdapter(mRecyclerView);
        mArticleAdapter.setBeans(articleBean);
        mRecyclerView.setAdapter(mArticleAdapter);
    }

    @Override
    public void refreshArticle(List<ArticleBeansNew> articleBeans) {
        mArticleAdapter.setBeans(articleBeans);
    }

    /**
     * 加载更多在列表末尾进行刷新
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurpage++;
        mPresenter.loadArticle(mCurpage);
    }

    /**
     * 刷新时在首页进行刷新
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurpage = 0;
        mPresenter.refreshArticle();
    }

    @Override
    public void onError(Throwable e) {
        if (mSmartRefreshLayout.getState() == RefreshState.Loading) {
            mSmartRefreshLayout.finishLoadMore();
            mCurpage--;
        }
        // 完成刷新
        if (mSmartRefreshLayout.getState() == RefreshState.Refreshing) {
            mSmartRefreshLayout.finishRefresh();
        }

    }

    @Override
    public void onComplete() {
        if (mSmartRefreshLayout.getState() == RefreshState.Loading) {
            mSmartRefreshLayout.finishLoadMore();
        }
        // 完成刷新
        if (mSmartRefreshLayout.getState() == RefreshState.Refreshing) {
            mSmartRefreshLayout.finishRefresh();
        }
    }
}