package com.wjx.android.wanandroidmvp.Custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;

import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.wjx.android.wanandroidmvp.R;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/21
 * Time: 20:53
 */
public class CoffeeHeader extends View implements RefreshHeader {
    private CoffeeDrawable mDrawable;
    private RefreshState state = RefreshState.None;
    private int coffeeColor;
    private int cupBodyColor;
    private int cupBottomColor;
    private int handColor;
    private int stickColor;
    private int cupRadius;

    public CoffeeHeader(Context context) {
        this(context, null);
    }

    public CoffeeHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoffeeHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CoffeeHeader, defStyleAttr, 0);
        coffeeColor = a.getColor(R.styleable.CoffeeHeader_coffeeColor, 0);
        cupBodyColor = a.getColor(R.styleable.CoffeeHeader_cupBodyColor, 0);
        cupBottomColor = a.getColor(R.styleable.CoffeeHeader_cupBottomColor, 0);
        handColor = a.getColor(R.styleable.CoffeeHeader_handColor, 0);
        stickColor = a.getColor(R.styleable.CoffeeHeader_stickColor, 0);
        cupRadius = a.getDimensionPixelSize(R.styleable.CoffeeHeader_cupRadius, 0);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
        if (state != RefreshState.None) {
            invalidate();
        }
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mDrawable = CoffeeDrawable.create(getMeasuredWidth(), getMeasuredHeight(),
                cupRadius > 0 ? cupRadius : getMeasuredWidth() / 15);
        if (coffeeColor != 0) {
            mDrawable.setCoffeeColor(coffeeColor);
        }
        if (cupBodyColor != 0) {
            mDrawable.setCupBodyColor(cupBodyColor);
        }
        if (cupBottomColor != 0) {
            mDrawable.setCupBottomColor(cupBottomColor);
        }
        if (handColor != 0) {
            mDrawable.setHandColor(handColor);
        }
        if (stickColor != 0) {
            mDrawable.setStickColor(stickColor);
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mDrawable.finish();
        return mDrawable.getFinishAnimationDuration();
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        mDrawable.start();
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        state = newState;
        if (newState == RefreshState.None) {
            mDrawable.reset();
        }
        invalidate();
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (isDragging || state == RefreshState.None || state == RefreshState.PullDownToRefresh || state == RefreshState.PullDownCanceled) {
            mDrawable.setProgress(percent <= .5F ? 0 : (percent - .5F) * 2);
        }
    }

    /**
     * 初始化尺寸
     *
     * @param cupRadius 杯子半径
     */
    public void updateSize(int cupRadius){
        mDrawable.updateSize(getMeasuredWidth(), getMeasuredHeight(), cupRadius);
    }

    /**
     * 更新尺寸
     *
     * @param width     Drawable宽度
     * @param height    Drawable高度
     * @param cupRadius 杯子半径
     */
    public void updateSize(int width, int height, int cupRadius){
        mDrawable.updateSize(width, height, cupRadius);
    }

    /**
     * 获取杯身颜色
     */
    public int getCupBodyColor() {
        return mDrawable.getCupBodyColor();
    }

    /**
     * 设置杯身颜色
     */
    public void setCupBodyColor(int color) {
        mDrawable.setCupBodyColor(color);
    }

    /**
     * 获取杯底颜色
     */
    public int getCupBottomColor() {
        return mDrawable.getCupBottomColor();
    }

    /**
     * 设置杯底颜色
     */
    public void setCupBottomColor(int color) {
        mDrawable.setCupBottomColor(color);
    }

    /**
     * 获取咖啡颜色
     */
    public int getCoffeeColor() {
        return mDrawable.getCoffeeColor();
    }

    /**
     * 设置咖啡颜色
     */
    public void setCoffeeColor(int color) {
        mDrawable.setCoffeeColor(color);
    }

    /**
     * 获取手颜色
     */
    public int getHandColor() {
        return mDrawable.getHandColor();
    }

    /**
     * 设置手颜色
     */
    public void setHandColor(int color) {
        mDrawable.setHandColor(color);
    }

    /**
     * 获取搅拌棒颜色
     */
    public int getStickColor() {
        return mDrawable.getStickColor();
    }

    /**
     * 设置搅拌棒颜色
     */
    public void setStickColor(int color) {
        mDrawable.setStickColor(color);
    }

    /**
     * 获取搅拌一圈的时长
     */
    public long getStirringDuration() {
        return mDrawable.getStirringDuration();
    }

    /**
     * 设置搅拌一圈的时长
     */
    public void setStirringDuration(long duration) {
        mDrawable.setStirringDuration(duration);
    }

    /**
     * 获取隐藏手臂的时长
     */
    public long getHideDuration() {
        return mDrawable.getHideDuration();
    }

    /**
     * 设置隐藏手臂的时长
     */
    public void setHideDuration(long duration) {
        mDrawable.setHideDuration(duration);
    }

    /**
     * 获取咖啡边缘渐变动画时长
     */
    public long getEdgeFadeDuration() {
        return mDrawable.getEdgeFadeDuration();
    }

    /**
     * 设置咖啡边缘渐变动画时长
     */
    public void setEdgeFadeDuration(long duration) {
        mDrawable.setEdgeFadeDuration(duration);
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
