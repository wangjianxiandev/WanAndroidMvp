package com.wjx.android.wanandroidmvp.base.presenter;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/18
 * Time: 19:52
 */
public class BasePresenter<V> {
    WeakReference<V> mWeakRef;

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void attachView(V view) {
        mWeakRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mWeakRef == null ? null : mWeakRef.get();
    }

    protected boolean isViewAttached() {
        return mWeakRef != null && mWeakRef.get() != null;
    }

    public void detachView() {
        if (mWeakRef != null) {
            mWeakRef.clear();
            mWeakRef = null;
        }
        mCompositeDisposable.clear();
    }
}
