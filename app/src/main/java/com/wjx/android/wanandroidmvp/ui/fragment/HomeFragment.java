package com.wjx.android.wanandroidmvp.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.wjx.android.wanandroidmvp.Custom.interpolator.ElasticScaleInterpolator;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.ArticleAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.GlideImageLoader;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.contract.home.Contract;
import com.wjx.android.wanandroidmvp.presenter.home.HomePresenter;

import com.wjx.android.wanandroidmvp.ui.activity.SearchWordActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;


/**
 * Created with Android Studio.
 * Description: 通过实现View接口，Presenter在初始化时调用发送网络请求返回的数据，直接在Fragment中处理加载
 *
 * @author: Wangjianxian
 * @date: 2019/12/14
 * Time: 16:33
 */
public class HomeFragment extends BaseFragment<Contract.IHomeView, HomePresenter> implements Contract.IHomeView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private Context mContext;

    private ArticleAdapter mArticleAdapter;

    private int mCurrentPage = 0;

    private Banner mBanner;

    private List<Article> mArticleList = new ArrayList<>();

    private List<Article> mTopArticleList = new ArrayList<>();

    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.nest_scroll)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.second_header)
    TwoLevelHeader mTwoLevelHeader;

    @BindView(R.id.second_floor_content)
    FrameLayout mSecondFloor;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    @BindView(R.id.normal_view)
    ViewGroup mNormalView;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.top_search) {
            Intent intent = new Intent(mContext, SearchWordActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        mPresenter.loadTopArticle();
        mPresenter.loadArticle(mCurrentPage);
        initAdapter();
        initBanner();
        initToolbar();
        initStatusBar();
        initSmartRefreshHeader();
        mPresenter.loadBanner();
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
    }

    private void initSmartRefreshHeader() {
        mSmartRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                if (oldState == RefreshState.TwoLevel) {
                    mSecondFloor.animate().alpha(0).setDuration(100);
                }
            }
        });
        mTwoLevelHeader.setOnTwoLevelListener(refreshLayout -> {
            mSecondFloor.animate().alpha(1).setDuration(1000);
            return true;
        });
    }

    private void initToolbar() {
        int showOrHideToolbarHeight = Constant.dpToPx(mContext, 200)
                - Constant.getStatusBarHeight(mContext)
                - Constant.getActionBarHeight(mContext);
        mNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY && scrollY - showOrHideToolbarHeight >= 0) {
                // 向上滑
                ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
                mToolbar.setBackgroundColor(Constant.getColor(mContext));
                mToolbar.setPadding(0, Constant.getStatusBarHeight(mContext), 0, 0);
                mToolbar.setTitle(R.string.bottomname1);
                mToolbar.setVisibility(View.VISIBLE);
            } else if (scrollY < oldScrollY && scrollY - showOrHideToolbarHeight <= 0) {
                // 向下滑
                ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
                mToolbar.setVisibility(GONE);
            }
        });
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    private void initBanner() {
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setBannerAnimation(Transformer.Tablet);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mArticleAdapter = new ArticleAdapter(mContext, mArticleList);
        View header = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.home_header_view, null, false);
        mBanner = header.findViewById(R.id.banner);
        mArticleAdapter.setHeaderView(header);
        mRecyclerView.setAdapter(mArticleAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        initStatusBar();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void loadBanner(List<com.wjx.android.wanandroidmvp.bean.db.Banner> bannerList) {
        if (bannerList != null) {
            List<String> imageUrls = bannerList
                    .stream()
                    .map(banner -> banner.imagePath)
                    .collect(Collectors.toList());
            mBanner.setImages(imageUrls);
            List<String> titles = bannerList
                    .stream()
                    .map(banner -> banner.title)
                    .collect(Collectors.toList());
            mBanner.setBannerTitles(titles);
            mBanner.start();
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    JumpWebUtils.startWebView(mContext,
                            bannerList.get(position).title,
                            bannerList.get(position).url);
                }
            });
        }
    }

    @Override
    public void refreshBanner(List<com.wjx.android.wanandroidmvp.bean.db.Banner> bannerList) {
        loadBanner(bannerList);
    }

    @Override
    public void loadTopArticle(List<Article> topArticleList) {
        mTopArticleList.clear();
        mTopArticleList.addAll(0, topArticleList);
    }

    @Override
    public void refreshTopArticle(List<Article> topArticleList) {
        mTopArticleList.clear();
        mTopArticleList.addAll(0, topArticleList);
    }

    @Override
    public void loadArticle(List<Article> articleList) {
        // 解决首页加载两次问题
        if (mCurrentPage == 0) {
            mArticleList.clear();
            articleList.addAll(0, mTopArticleList);
        }
        mArticleList.addAll(articleList);
        mArticleAdapter.setArticleList(mArticleList);
    }

    @Override
    public void refreshArticle(List<Article> articleList) {
        mArticleList.clear();
        articleList.addAll(0, mTopArticleList);
        mArticleList.addAll(0, articleList);
        mArticleAdapter.setArticleList(mArticleList);
    }

    @Override
    public void onCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Constant.showSnackMessage(getActivity(), "收藏成功");
            } else {
                ToastUtils.showShort("收藏失败");
            }
        }
    }

    @Override
    public void onUnCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Constant.showSnackMessage(getActivity(), "取消收藏");
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    @Override
    public void onLoading() {
        startLoadingView();
    }

    @Override
    public void onLoadFailed() {
        stopLoadingView();
        setNetWorkError(false);
        ToastUtils.showShort("网络未连接请重试");
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadSuccess() {
        stopLoadingView();
        setNetWorkError(true);
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
        startRecyclerViewAnim();
    }

    public void startLoadingView() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_START_ANIMATION;
        EventBus.getDefault().post(e);
    }

    public void stopLoadingView() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
    }

    @OnClick(R.id.layout_error)
    public void onReTry() {
        setNetWorkError(true);
        mPresenter.loadBanner();
        mPresenter.loadTopArticle();
        mPresenter.loadArticle(0);
    }

    private void setNetWorkError(boolean isSuccess) {
        if (isSuccess) {
            mNormalView.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
        } else {
            mNormalView.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 开启recyclerview item加载动画
     */
    private void startRecyclerViewAnim() {
        mRecyclerView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                        for (int i = 1; i < mRecyclerView.getChildCount(); i++) {
                            ObjectAnimator animatorX = ObjectAnimator.ofFloat(mRecyclerView.getChildAt(i), "scaleX", 0.8f, 1.0f);
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(mRecyclerView.getChildAt(i), "scaleY", 0.8f, 1.0f);
                            AnimatorSet set = new AnimatorSet();
                            set.setDuration(1000);
                            set.setInterpolator(new ElasticScaleInterpolator(0.4f));
                            set.playTogether(animatorX, animatorY);
                            set.start();
                        }

                        return true;
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_HOME) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mArticleAdapter.notifyDataSetChanged();
                mPresenter.collect(articleId);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mArticleAdapter.notifyDataSetChanged();
                mPresenter.unCollect(articleId);
            } else if (event.type == Event.TYPE_LOGIN) {
                mArticleList.clear();
                mPresenter.refreshTopArticle();
                mPresenter.refreshArticle(0);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mArticleList.clear();
                mPresenter.refreshTopArticle();
                mPresenter.refreshArticle(0);
            } else if (event.type == Event.TYPE_COLLECT_STATE_REFRESH) {
                int articleId = Integer.valueOf(event.data);
                // 刷新的收藏状态一定是和之前的相反
                mArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect =
                        !mArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect;
                mArticleAdapter.notifyDataSetChanged();
            } else if (event.type == Event.TYPE_REFRESH_COLOR) {
                mToolbar.setBackgroundColor(Constant.getColor(mContext));
            }
        }
    }

    /**
     * 加载新文章从底部开始
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadArticle(mCurrentPage);
    }

    /**
     * 刷新文章从首页开始
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.refreshBanner();
        mPresenter.refreshTopArticle();
        mCurrentPage = 0;
        mPresenter.refreshArticle(mCurrentPage);
    }
}