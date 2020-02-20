package com.wjx.android.wanandroidmvp.Custom.interpolator;

import android.view.animation.Interpolator;

/**
 * Created with Android Studio.
 * Description: 弹性动画
 *
 * @author: Wangjianxian
 * @date: 2020/02/20
 * Time: 20:03
 */
public class ElasticScaleInterpolator implements Interpolator {
    private float elasticFactor;

    public ElasticScaleInterpolator(float elasticFactor) {
        this.elasticFactor = elasticFactor;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - elasticFactor / 4) * (2 * Math.PI) / elasticFactor) + 1);
    }
}
