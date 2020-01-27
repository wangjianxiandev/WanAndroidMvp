package com.wjx.android.wanandroidmvp.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * Created with Android Studio.
 * Description: 图片加载器
 *
 * @author: Wangjianxian
 * @date: 2019/12/18
 * Time: 21:32
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

}
