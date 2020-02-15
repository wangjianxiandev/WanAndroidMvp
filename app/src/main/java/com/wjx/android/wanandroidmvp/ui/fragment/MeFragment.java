package com.wjx.android.wanandroidmvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.wjx.android.wanandroidmvp.Custom.userimage.CustomUserAvatar;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;

import com.wjx.android.wanandroidmvp.bean.me.IntegralData;
import com.wjx.android.wanandroidmvp.contract.me.Contract;
import com.wjx.android.wanandroidmvp.presenter.me.MePresenter;
import com.wjx.android.wanandroidmvp.ui.activity.CollectActivity;
import com.wjx.android.wanandroidmvp.ui.activity.LoginActivity;
import com.wjx.android.wanandroidmvp.ui.activity.MeShareActivity;
import com.wjx.android.wanandroidmvp.ui.activity.RankActivity;
import com.wjx.android.wanandroidmvp.ui.activity.SettingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/26
 * Time: 20:13
 */
public class MeFragment extends BaseFragment<Contract.IMeView, MePresenter> implements Contract.IMeView,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.me_name)
    TextView meName;
    @BindView(R.id.me_info)
    TextView meInfo;
    @BindView(R.id.me_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.me_integral_rank)
    ViewGroup mIntegralRank;
    @BindView(R.id.me_about)
    ViewGroup mMeAbout;
    @BindView(R.id.me_collect)
    ViewGroup mMeCollect;
    @BindView(R.id.me_setting)
    ViewGroup mMeSetting;
    @BindView(R.id.me_article)
    ViewGroup mMeArticle;

    @BindView(R.id.user_imageView)
    CustomUserAvatar mCircleUserImageView;

    private int mCoinCount;

    private int mRank;

    private Context mContext;

    public static MeFragment getInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.me_fragment;
    }

    @Override
    protected void init() {
        mContext = getActivity().getApplicationContext();
        mPresenter.loadIntegralData();
        if (!TextUtils.isEmpty(LoginUtils.getLoginUser())) {
            meName.setText(Constant.getDecodeName(LoginUtils.getLoginUser()));
        }
        initUserImageView();
        initStatusBar();
        mSwipeRefreshLayout.setBackgroundColor(Constant.getColor(mContext));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initUserImageView() {
        if (LoginUtils.isLogin()) {
            mCircleUserImageView.setUserName(Constant.getDecodeName(LoginUtils.getLoginUser()));
        } else {
            mCircleUserImageView.setUserName("登录");
        }
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

    @OnClick(R.id.me_name)
    public void MeName() {
        if (LoginUtils.isLogin()) {
            Toast.makeText(mContext,
                    Constant.getDecodeName(LoginUtils.getLoginUser()), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.me_integral_rank)
    public void IntegralRank() {
        if (LoginUtils.isLogin()) {
            Intent intent = new Intent(getActivity(), RankActivity.class);
            intent.putExtra(Constant.KEY_RANK, mRank + "");
            intent.putExtra(Constant.KEY_COUNTCOIN, mCoinCount + "");
            Log.e("Intent", mRank + "; " + mCoinCount);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.me_collect)
    public void MeCollect() {
        if (LoginUtils.isLogin()) {
            Intent intent = new Intent(getActivity(), CollectActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.me_article)
    public void MeArticle() {
        if (LoginUtils.isLogin()) {
            Intent intent = new Intent(getActivity(), MeShareActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.me_about)
    public void MeAbout() {
        JumpWebUtils.startWebView(mContext,
                "玩Android",
                "https://www.wanandroid.com/");
    }

    @OnClick(R.id.me_join)
    public void MeJoin() {
        JumpWebUtils.startWebView(mContext,
                "WanAndroid——WJX",
                "https://github.com/wangjianxiandev/WanAndroidMvp");
    }

    @OnClick(R.id.me_setting)
    public void MeSetting() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        initStatusBar();
    }

    @Override
    protected MePresenter createPresenter() {
        return new MePresenter();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_ME) {
            if (event.type == Event.TYPE_LOGIN) {
                meName.setText(event.data);
                mPresenter.loadIntegralData();
                mCircleUserImageView.setUserName(Constant.getDecodeName(LoginUtils.getLoginUser()));
            } else if (event.type == Event.TYPE_LOGOUT) {
                meName.setText("请先登录~");
                mPresenter.refreshIntegralData();
                mCircleUserImageView.setUserName("登录");
            } else if (event.type == Event.TYPE_REFRESH_COLOR) {
                mSwipeRefreshLayout.setBackgroundColor(Constant.getColor(mContext));
                mCircleUserImageView.invalidate();
            }
        }
    }

    @Override
    public void onLoadIntegralData(IntegralData integral) {
        if (LoginUtils.isLogin()) {
            mCoinCount = integral.getData().getCoinCount();
            mRank = integral.getData().getRank();
            meInfo.setText("积分:" + mCoinCount + "  " + "排名:" + mRank);
            Event rankEvent = new Event();
            rankEvent.target = Event.TARGET_INTEGRAL_RANK;
            rankEvent.data = mRank + ";" + mCoinCount;
            EventBus.getDefault().post(rankEvent);
        }
    }


    @Override
    public void onRefreshIntegralData(IntegralData integral) {
        if (LoginUtils.isLogin()) {
            mCoinCount = integral.getData().getCoinCount();
            mRank = integral.getData().getRank();
            meInfo.setText("积分:" + mCoinCount + "  " + "排名:" + mRank);
            Event rankEvent = new Event();
            rankEvent.target = Event.TARGET_INTEGRAL_RANK;
            rankEvent.data = mRank + ";" + mCoinCount;
            EventBus.getDefault().post(rankEvent);
        } else {
            meInfo.setText("积分:" + "--" + "  " + "排名:" + "--");
        }
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed() {
        ToastUtils.showShort("网络未连接请重试");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadSuccess() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshIntegralData();
    }
}
