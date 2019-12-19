package com.wjx.android.wanandroidmvp.adapter;

import android.view.View;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 21:17
 */
public interface OnItemClickListener {

    /**
     * 文章点击事件
     * @param view 点击的view
     * @param position 点击的位置
     */
    void onItemClick(View view, int position);

    /**
     * 收藏点击事件
     * @param view 点击的view
     * @param position 点击的位置
     */
    void onCollectClick(View view, int position);
}
