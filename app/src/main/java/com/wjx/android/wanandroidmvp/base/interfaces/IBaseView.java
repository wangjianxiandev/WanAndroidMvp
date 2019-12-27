package com.wjx.android.wanandroidmvp.base.interfaces;

/**
 * Created with Android Studio.
 * Description: 用来指示错误和完成的接口
 *
 * @author: 王拣贤
 * @date: 2019/12/27
 * Time: 10:52
 */
public interface IBaseView {
    /**
     * 加载错误回调
     * @param e
     */
    void onError(Throwable e);

    /**
     * 加载完成
     */
    void onComplete();
}
