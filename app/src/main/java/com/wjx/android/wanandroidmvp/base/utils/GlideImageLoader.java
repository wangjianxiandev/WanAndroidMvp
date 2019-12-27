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
 * @author: 王拣贤
 * @date: 2019/12/18
 * Time: 21:32
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RoundedCorners roundedCorners= new RoundedCorners(dp2px(10));
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(context).load(path).apply(options).into(imageView);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

}
