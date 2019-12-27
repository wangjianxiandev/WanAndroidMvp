package com.wjx.android.wanandroidmvp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ArticleAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.GlideImageLoader;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBeansNew;
import com.wjx.android.wanandroidmvp.contract.home.Contract;
import com.wjx.android.wanandroidmvp.presenter.home.HomePresenter;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

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

    private Banner mBanner;

    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.nest_scroll)
    NestedScrollView mNestScrollView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void init() {
        initAdapter();
        mPresenter.loadBanner();
        mPresenter.loadArticle(mCurpage);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mArticleAdapter = new ArticleAdapter(mRecyclerView, mNestScrollView);
        View header = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.home_header_view, null, false);
        mBanner = header.findViewById(R.id.banner);
        mArticleAdapter.setHeaderView(header);
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
        mArticleAdapter.setBeans(articleBean);
        mRecyclerView.setAdapter(mArticleAdapter);
    }

    @Override
    public void refreshArticle(List<ArticleBeansNew> articleBeans) {
        mArticleAdapter.setBeans(articleBeans);
    }

    /**
     * 加载新文章从底部开始
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurpage++;
        mPresenter.loadArticle(mCurpage);
    }

    /**
     * 刷新文章从首页开始
     *
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