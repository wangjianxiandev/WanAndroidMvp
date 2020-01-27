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
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.db.Author;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatClassifyData;
import com.wjx.android.wanandroidmvp.contract.wechat.Contract;
import com.wjx.android.wanandroidmvp.presenter.wechat.WeChatPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

import static com.blankj.utilcode.util.ColorUtils.getColor;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
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

    private int mCurrentPage = 0;

    private Context mContext;

    public static WeChatFragment getInstance() {
        WeChatFragment fragment = new WeChatFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wechat_fragment;
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
        mPresenter.loadWeChatClassify();
        setChildViewVisibility(View.VISIBLE);
        initTabColor();
    }

    private void initTabColor() {
        mSlidingTabLayout.setDividerColor(Constant.getColor(mContext));
        mSlidingTabLayout.setIndicatorColor(Constant.getColor(mContext));
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

    @Override
    protected WeChatPresenter createPresenter() {
        return new WeChatPresenter();
    }

    @Override
    public void onLoadWeChatClassify(List<Author> weChatClassifyData) {
        List<String> tabNames = weChatClassifyData.stream()
                .map(weChatClassify -> weChatClassify.author)
                .collect(Collectors.toList());
        weChatClassifyData.stream().forEach(weChatClassify -> {
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
                mCurrentPage = position;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_WX) {
            if (event.type == Event.TYPE_REFRESH_COLOR) {
                initTabColor();
            }
        }
    }
}
