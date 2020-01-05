package com.wjx.android.wanandroidmvp.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.kingja.loadsir.core.LoadService;
import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/18
 * Time: 21:26
 */
public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {

    protected P mPresenter;

    protected Unbinder unbinder;

    protected abstract int getContentViewId();

    protected View mRootView;


    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 创建Presenter
     *
     * @return p
     */
    protected abstract P createPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(getContentViewId(), container, false);
        }
        mPresenter = createPresenter();
        unbinder = ButterKnife.bind(this, mRootView);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        init();
        return mRootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mRootView != null) {
            ((ViewGroup)mRootView.getParent()).removeView(mRootView);
        }
    }
}
