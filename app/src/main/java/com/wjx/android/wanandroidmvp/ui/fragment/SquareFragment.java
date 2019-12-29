package com.wjx.android.wanandroidmvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.NavigationAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;
import com.wjx.android.wanandroidmvp.contract.square.Contract;
import com.wjx.android.wanandroidmvp.presenter.square.SquarePresenter;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/26
 * Time: 20:13
 */
public class SquareFragment extends BaseFragment<Contract.ISquareView, SquarePresenter> implements Contract.ISquareView {

    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout mVerticalTabLayout;

    @BindView(R.id.normal_view)
    LinearLayout mViewGroup;

    @BindView(R.id.navigation_divider)
    View mDivider;

    @BindView(R.id.navigation_recyclerview)
    RecyclerView mRecyclerView;

    private NavigationAdapter mNavigationAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getContentViewId() {
        return R.layout.square_navigation;
    }

    @Override
    protected void init() {
        initAdapter();
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mPresenter.loadNavigation();
        setChildViewVisibility(View.VISIBLE);

    }

    private void initAdapter() {
        mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mNavigationAdapter = new NavigationAdapter(mRecyclerView);
    }

    @Override
    protected SquarePresenter createPresenter() {
        return new SquarePresenter();
    }

    @Override
    public void loadNavigation(NavigationData navigationData) {
        mNavigationAdapter.setBeans(navigationData);
        mRecyclerView.setAdapter(mNavigationAdapter);
        if (navigationData.getErrorCode() == Constant.BANNER_SUCCESS) {
            List<String> tabNames = navigationData.getData()
                    .stream()
                    .map(NavigationData.DataBean::getName)
                    .collect(Collectors.toList());
            mVerticalTabLayout.setTabAdapter(new TabAdapter() {
                @Override
                public int getCount() {
                    return tabNames == null ? 0 : tabNames.size();
                }

                @Override
                public ITabView.TabBadge getBadge(int position) {
                    return null;
                }

                @Override
                public ITabView.TabIcon getIcon(int position) {
                    return null;
                }

                @Override
                public ITabView.TabTitle getTitle(int position) {
                    return new TabView.TabTitle.Builder()
                            .setContent(tabNames.get(position))
                            .setTextColor(ContextCompat.getColor(getContext(), R.color.white),
                                    Constant.randomColor())
                            .build();
                }

                @Override
                public int getBackground(int position) {
                    return -1;
                }
            });
            leftRightLinkTogether();
        }
    }

    /**
     * 实现VerticalTabLayout 和 RecyclerView 二级联动
     */
    private void leftRightLinkTogether() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 将VerticalTabLayout 的选中位置与RecyclerView的第一条的标题相同
                mVerticalTabLayout.setTabSelected(mLinearLayoutManager.findFirstVisibleItemPosition());
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mVerticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                // 将RecyclerView的第一条的位置和VerticalTabLayout的 选中的index的position设置相同
                mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }


    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    private void setChildViewVisibility(int visibility) {
        mViewGroup.setVisibility(visibility);
        mVerticalTabLayout.setVisibility(visibility);
        mDivider.setVisibility(visibility);
    }
}
