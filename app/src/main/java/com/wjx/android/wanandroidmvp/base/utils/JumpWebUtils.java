package com.wjx.android.wanandroidmvp.base.utils;

import android.content.Context;
import android.content.Intent;

import com.wjx.android.wanandroidmvp.ui.activity.WebViewActivity;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 21:32
 */
public class JumpWebUtils {

    public static void startWebView(Context context, String title, String url){
        Intent intent = new Intent();
        intent.setClass(context, WebViewActivity.class);
        intent.putExtra(Constant.ARTICLE_TITLE, title);
        intent.putExtra(Constant.ARTICLE_URL, url);
        context.startActivity(intent);
    }
}
