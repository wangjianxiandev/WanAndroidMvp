package com.wjx.android.wanandroidmvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.db.Author;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatClassifyData;
import com.wjx.android.wanandroidmvp.contract.wechat.Contract;
import com.wjx.android.wanandroidmvp.presenter.wechat.WeChatPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/26
 * Time: 20:13
 */
public class WeChatFragment extends BaseFragment<Contract.IWeChatView, WeChatPresenter> implements Contract.IWeChatView {

    @BindView(R.id.wechat_tab)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.wechat_divider)
    View mDivider;

    @BindView(R.id.wechat_viewpager)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragmentSparseArray = new ArrayList<>();

    private int mCurPage = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.wechat_fragment;
    }

    @Override
    protected void init() {
        mPresenter.loadWeChatClassify();
        setChildViewVisibility(View.VISIBLE);
    }

    @Override
    protected WeChatPresenter createPresenter() {
        return new WeChatPresenter();
    }

    @Override
    public void onLoadWeChatClassify(List<Author> weChatClassifyData) {
        List<String> tabNames = weChatClassifyData.stream()
                .map(weChatClassify -> weChatClassify.author)
                .collect(Collectors.toList());
        weChatClassifyData.stream().forEach(weChatClassify ->{
            WeChatListFragment weChatListFragment = new WeChatListFragment(weChatClassify.authorId);
            mFragmentSparseArray.add(weChatListFragment);
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
    public void onRefreshWeChatClassify(List<Author> weChatClasssifyData) {
        onLoadWeChatClassify(weChatClasssifyData);
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
