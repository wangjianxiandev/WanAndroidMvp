package com.wjx.android.wanandroidmvp.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.blankj.utilcode.util.ColorUtils.getColor;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/07
 * Time: 20:30
 */
public class ParentSquareFragment extends BaseFragment {

    @BindView(R.id.square_tab)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.square_divider)
    View mDivider;

    @BindView(R.id.square_viewpager)
    ViewPager mViewPager;

    private int mCurTab;

    private Context mContext;

    private List<String> mTabNames = new ArrayList<>();

    private ArrayList<Fragment> mFragmentSparseArray = new ArrayList<>();

    public static ParentSquareFragment getInstance() {
        ParentSquareFragment fragment = new ParentSquareFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.parentsquare_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        mContext = getActivity().getApplicationContext();
        initStatusBar();
        setChildViewVisibility(View.VISIBLE);
        mCurTab = 0;
        initFragment();
        initViewPager();
        initTabColor();
    }

    private void initTabColor() {
        mSlidingTabLayout.setIndicatorColor(Constant.getColor(mContext));
        mSlidingTabLayout.setDividerColor(Constant.getColor(mContext));
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (ColorUtils.calculateLuminance(getColor(R.color.white)) >= 0.5) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragmentSparseArray.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentSparseArray == null ? 0 : mFragmentSparseArray.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabNames.get(position);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurTab = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private void initFragment() {
        mTabNames.add(0, "广场");
        mTabNames.add(1, "体系");
        mTabNames.add(2, "导航");
        mFragmentSparseArray.add(0, new HomeSquareFragment());
        mFragmentSparseArray.add(1, new TreeFragment());
        mFragmentSparseArray.add(2, new SquareFragment());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void setChildViewVisibility(int visibility) {
        mSlidingTabLayout.setVisibility(visibility);
        mDivider.setVisibility(visibility);
        mViewPager.setVisibility(visibility);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_PARENT_SQUARE) {
            if (event.type == Event.TYPE_REFRESH_COLOR) {
                initTabColor();
            }
        }
    }
}
