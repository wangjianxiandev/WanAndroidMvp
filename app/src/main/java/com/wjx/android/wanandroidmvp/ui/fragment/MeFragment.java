package com.wjx.android.wanandroidmvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;

import com.wjx.android.wanandroidmvp.bean.me.IntegralData;
import com.wjx.android.wanandroidmvp.contract.me.Contract;
import com.wjx.android.wanandroidmvp.presenter.me.MePresenter;
import com.wjx.android.wanandroidmvp.ui.activity.CollectActivity;
import com.wjx.android.wanandroidmvp.ui.activity.LoginActivity;
import com.wjx.android.wanandroidmvp.ui.activity.RankActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/26
 * Time: 20:13
 */
public class MeFragment extends BaseFragment<Contract.IMeView, MePresenter> implements Contract.IMeView {

    @BindView(R.id.me_name)
    TextView meName;
    @BindView(R.id.me_info)
    TextView meInfo;
    @BindView(R.id.me_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.me_integral_rank)
    ViewGroup mIntegralRank;

    @BindView(R.id.me_collect)
    ViewGroup mMeCollect;

    private int mCoinCount;

    private int mRank;

    @Override
    protected int getContentViewId() {
        return R.layout.me_fragment;
    }

    @Override
    protected void init() {
        mPresenter.loadIntegralData();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshIntegralData();
            }
        });
        String loginUser = LoginUtils.getLoginUser();
        if (!TextUtils.isEmpty(loginUser)) {
            meName.setText(loginUser);
        }
        meName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mIntegralRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        mMeCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), CollectActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
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
            rankEvent.data = mRank +";" +mCoinCount;
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
            rankEvent.data = mRank +";" +mCoinCount;
            EventBus.getDefault().post(rankEvent);
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
        ToastUtils.showShort("加载失败");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadSuccess() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
