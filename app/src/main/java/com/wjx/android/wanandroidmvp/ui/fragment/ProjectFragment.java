package com.wjx.android.wanandroidmvp.ui.fragment;

import android.icu.util.ValueIterator;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.db.ProjectClassify;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.contract.project.Contract;
import com.wjx.android.wanandroidmvp.presenter.project.ProjectPresenter;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 17:17
 */
public class ProjectFragment extends BaseFragment<Contract.IProjectView, ProjectPresenter> implements Contract.IProjectView {

    @BindView(R.id.project_tab)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.project_divider)
    View mDivider;

    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragmentSparseArray = new ArrayList<>();

    private int mCurPage;

    @Override
    protected int getContentViewId() {
        return R.layout.project_fragment;
    }

    @Override
    protected void init() {
        mPresenter.loadProjectClassify();
        mCurPage = 1;
        setChildViewVisibility(View.VISIBLE);
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    public void onLoadProjectClassify(List<ProjectClassify> projectClassifies) {
        List<String> tabNames = projectClassifies
                .stream()
                .map(projectClassify -> projectClassify.name)
                .collect(Collectors.toList());
        projectClassifies.stream().forEach(projectClassify -> {
            ProjectListFragment projectListFragment = new ProjectListFragment(projectClassify.categoryId);
            mFragmentSparseArray.add(projectListFragment);
        });

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragmentSparseArray.get(position);
            }

            @Override
            public int getCount() {
                return tabNames == null ? 0 : tabNames.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabNames.get(position);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onRefreshProjectClassify(List<ProjectClassify> projectClassifies) {
        onLoadProjectClassify(projectClassifies);
    }



    private void setChildViewVisibility(int visibility) {
        mSlidingTabLayout.setVisibility(visibility);
        mDivider.setVisibility(visibility);
        mViewPager.setVisibility(visibility);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onLoadSuccess() {

    }
}
