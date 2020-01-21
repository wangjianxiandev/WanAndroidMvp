package com.wjx.android.wanandroidmvp.Custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/21
 * Time: 20:25
 */
public class CoffeeDrawable extends Drawable {

    /**
     * 静止状态
     */
    public static final int STATE_NORMAL = 0;

    /**
     * 手指向杯子移动中
     */
    public static final int STATE_PREPARING = 1;

    /**
     * 搅拌中
     */
    public static final int STATE_STIRRING = 2;

    /**
     * 手指正在收回
     */
    public static final int STATE_HIDING = 3;

    private int mState;//当前状态
    private float mProgress;//当前进度

    private int mWidth;//总宽
    private int mHeight;//总高
    private float mCenterX;//水平中心点
    private float mCenterY;//垂直中心点
    private float mCupRadius;//杯子半径
    private float mCoffeeRadius;//咖啡半径
    private float mCupHandleWidth;//杯柄宽度
    private float mCupBottomOffset;//杯底偏移量
    private float mFingerWidth;//手指宽度
    private float mFinger1Length;//第一只手指的长度
    private float mFinger2Length;//第二只手指的长度
    private float mFingerORadius;//手指O型状的半径
    private float mArmWidth;//手臂宽度
    private float mCoffeeStickWidth;//搅拌棒宽度

    private float mStickAngle;//当前搅拌棒相对于杯子圆心的角度
    private float[] mPreparePath;//手指向杯子移动的路径

    private Path mOFingersPath = new Path();//手指O形状
    private Path mKFingersPath = new Path();//手指K形状
    private Path mArmPath = new Path();//手臂
    private Path mRipplePath = new Path();//水波纹
    private RectF mOFingerRect = new RectF();//手指O形状的外切矩形
    private PointF mStickStartPoint = new PointF();//搅拌棒的起始坐标点
    private PointF mStickEndPoint = new PointF();//搅拌棒的结束坐标点

    private int mCupBodyColor = Color.WHITE;//杯身颜色，默认：白色
    private int mCupBottomColor = 0xFF00574B;//杯底颜色，默认：青色
    private int mCoffeeColor = 0xFF7E5B3E;//咖啡颜色，默认：咖啡色
    private int mHandColor = 0xFFE9C794;//手颜色，默认：棕黄
    private int mStickColor = Color.WHITE;//搅拌棒颜色，默认：白色

    private long mStirringDuration = 500;//搅拌一圈的时长
    private float mStirringStartTime;//动画起始时间

    private long mHideDuration = 300;//手收起的时长
    private float mHideStartTime;//动画起始时间

    private long mEdgeFadeDuration = 500;//咖啡边缘渐变时长
    private float mEdgeFadeStartTime;//动画起始时间

    private boolean mFinished;//是否已被标记完成

    private ScaleHelper mScaleHelper;//缩放比例辅助类
    private RadialGradient mCupShader;//咖啡边缘渐变
    private Paint mPaint;

    /**
     * 通过目标View创建CoffeeDrawable对象
     * CoffeeDrawable宽高 = View的宽高
     */
    public static CoffeeDrawable create(final View targetView) {
        //杯半径 取 总宽度的 1/15
        int cupRadius = targetView.getWidth() / 15;
        final CoffeeDrawable drawable = new CoffeeDrawable(targetView.getWidth(), targetView.getHeight(), cupRadius);
        if (targetView.getWidth() == 0 || targetView.getHeight() == 0) {
            //无效宽高，等待布局完成后更新一次尺寸
            targetView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (targetView.getWidth() > 0 || targetView.getHeight() > 0) {
                        //杯半径 取 总宽度的 1/15
                        int cupRadius = targetView.getWidth() / 15;
                        //更新有效宽高
                        drawable.updateSize(targetView.getWidth(), targetView.getHeight(), cupRadius);
                        //移除监听器，不再需要
                        targetView.removeOnLayoutChangeListener(this);
                    }
                }
            });
        }
        return drawable;
    }

    /**
     * 通过目标View创建CoffeeDrawable对象
     *
     * @param targetView 目标View
     * @param cupRadius  杯子半径
     */
    public static CoffeeDrawable create(final View targetView, final int cupRadius) {
        final CoffeeDrawable drawable = new CoffeeDrawable(targetView.getWidth(), targetView.getHeight(), cupRadius);
        if (targetView.getWidth() == 0 || targetView.getHeight() == 0) {
            targetView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (targetView.getWidth() > 0 || targetView.getHeight() > 0) {
                        drawable.updateSize(targetView.getWidth(), targetView.getHeight(), cupRadius);
                        targetView.removeOnLayoutChangeListener(this);
                    }
                }
            });
        }
        return drawable;
    }

    /**
     * 指定尺寸创建CoffeeDrawable对象
     *
     * @param width  CoffeeDrawable的宽
     * @param height CoffeeDrawable的高
     */
    public static CoffeeDrawable create(int width, int height) {
        return create(width, height, width / 15);
    }

    /**
     * 指定尺寸创建CoffeeDrawable对象
     *
     * @param width     CoffeeDrawable的宽
     * @param height    CoffeeDrawable的高
     * @param cupRadius 杯子半径
     */
    public static CoffeeDrawable create(int width, int height, int cupRadius) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid size!");
        }
        return new CoffeeDrawable(width, height, cupRadius);
    }

    private CoffeeDrawable(int width, int height, int cupRadius) {
        mScaleHelper = new ScaleHelper(1, 0, .2F, 1);
        initPaint();
        updateSize(width, height, cupRadius);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * 初始化尺寸
     *
     * @param width     Drawable宽度
     * @param height    Drawable高度
     * @param cupRadius 杯子半径
     */
    public void updateSize(int width, int height, int cupRadius) {
        mWidth = width;
        mHeight = height;
        //水平中心点
        mCenterX = mWidth / 2F;
        //垂直中心点
        mCenterY = mHeight / 2F;
        //杯子半径
        mCupRadius = cupRadius;
        //咖啡半径
        mCoffeeRadius = mCupRadius * .95F;
        //杯子手柄宽度
        mCupHandleWidth = mCupRadius / 3;
        //杯底偏移量
        mCupBottomOffset = mCupHandleWidth / 2;
        //手指宽度
        mFingerWidth = mCupHandleWidth;
        //第二根手指长度
        mFinger2Length = mCupRadius * 1.2F;
        //第一根手指长度
        mFinger1Length = mFinger2Length * .8F;
        //手指O形状半径
        mFingerORadius = mCupRadius / 2;
        //手臂宽度
        mArmWidth = mCupRadius;
        //搅拌棒宽度
        mCoffeeStickWidth = mCupHandleWidth / 2;

        initCupShader();
        initPreparePath();
        invalidateSelf();
    }

    /**
     * 初始化咖啡边缘渐变
     */
    private void initCupShader() {
        if (mCoffeeRadius > 0) {
            int a = 128;
            int r = Color.red(mCupBodyColor);
            int g = Color.green(mCupBodyColor);
            int b = Color.blue(mCupBodyColor);
            int endColor = Color.argb(a, r, g, b);
            int[] colors = new int[]{Color.TRANSPARENT, endColor};
            float[] stops = new float[]{.7F, 1F};
            mCupShader = new RadialGradient(mCenterX, mCenterY, mCoffeeRadius, colors, stops, Shader.TileMode.CLAMP);
        }
    }

    /**
     * 初始化手指向杯子移动的路径
     */
    private void initPreparePath() {
        Path path = new Path();
        path.moveTo(mCenterX + mCupRadius / 2, mCenterY);
        path.quadTo(mCenterX + mCupRadius + mFingerORadius * 2, mCenterY,
                mCenterX + mCupRadius + mFingerORadius * 2,
                mHeight + mFinger2Length + mFingerWidth / 2);
        mPreparePath = decomposePath(path);
    }

    private PointF mTempPoint = new PointF();

    /**
     * 根据当前进度获取手指在移动路径上的坐标
     *
     * @param progress 当前进度
     * @return 移动路径上的坐标
     */
    private PointF getPathPointByProgress(float progress) {
        int index;
        if (progress == 0) {
            index = 0;
        } else if (progress == 1) {
            index = mPreparePath.length - 2;
        } else {
            index = (int) (mPreparePath.length * progress);
            if (index % 2 != 0) {
                index--;
            }
        }
        mTempPoint.set(mPreparePath[index], mPreparePath[index + 1]);
        return mTempPoint;
    }

    /**
     * 根据半径和角度计算坐标点
     *
     * @param radius 半径
     * @param angle  角度
     * @return 对应的坐标点
     */
    private PointF getPointByAngle(float radius, float angle) {
        //先把角度转成弧度
        double radian = angle * Math.PI / 180;
        //x轴坐标值
        float x = (float) (radius * Math.cos(radian));
        //y轴坐标值
        float y = (float) (radius * Math.sin(radian));
        mTempPoint.set(x, y);
        return mTempPoint;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawCup(canvas);
        if (mState != STATE_NORMAL) {
            updateStickLocation();
        }
        switch (mState) {
            case STATE_PREPARING:
                drawStick(canvas);
                drawHand(canvas);
                break;
            case STATE_STIRRING:
                updateStickAngle();
            case STATE_HIDING:
                drawRipple(canvas);
                drawStick(canvas);
                drawHand(canvas);
                invalidateSelf();
                break;
        }
    }

    /**
     * 更新搅拌棒角度
     */
    private void updateStickAngle() {
        if (mStirringStartTime > 0) {
            float percent = (SystemClock.uptimeMillis() - mStirringStartTime) / mStirringDuration;
            if (percent >= 1) {
                percent = 1;
                //如果隐藏手臂动画未开始
                if (mHideStartTime == 0) {
                    //转完一圈，重新开始
                    mStirringStartTime = SystemClock.uptimeMillis();
                }
            }
            mStickAngle = percent * -360;//逆时针旋转所以是负数
        }
    }

    private boolean mFullRipple;//水波纹是否已完全延伸

    /**
     * 画水波纹
     */
    private void drawRipple(Canvas canvas) {
        if (mState == STATE_NORMAL) {
            return;
        }
        updateRipplePath();

        setPaintFillAndColor(mStickColor);
        //以最小缩放时的直径为精确度（确保在最小圆点之间也不会有空隙）
        int precision = (int) (mCoffeeStickWidth * .2F);
        float[] points = decomposePath(mRipplePath, precision);
        //一半的透明度=128，但因为精度是mCoffeeStickWidth的1/5（0.2），
        //也就是Path上一段长度为mCoffeeStickWidth的路径范围内最多会有5个点
        //也就是会有5个半透明的点在叠加，为了保持这个透明度不变，还要用128 * 2 或 / 5
        float baseAlpha = 128F * .2F;
        final int length = points.length;
        float fraction;
        float radius;
        for (int i = 0; i < length; i += 2) {
            fraction = ((float) i) / length;
            radius = mCoffeeStickWidth * mScaleHelper.getScale(fraction) / 2;
            mPaint.setAlpha((int) (baseAlpha * (1 - fraction)));
            canvas.drawCircle(points[i], points[i + 1], radius, mPaint);
        }
    }

    private RectF mTempRect = new RectF();

    /**
     * 更新水波纹Path
     */
    private void updateRipplePath() {
        float halfSize = mCupRadius / 2;
        float left = mCenterX - halfSize;
        float right = mCenterX + halfSize;
        float top = mCenterY - halfSize;
        float bottom = mCenterY + halfSize;
        mTempRect.set(left, top, right, bottom);
        float sweepAngle;
        if (mFullRipple) {
            sweepAngle = 180;
            if (mState == STATE_HIDING) {
                float percent = (SystemClock.uptimeMillis() - mHideStartTime) / mHideDuration;
                sweepAngle -= 180 * percent;
            }
        } else {
            //因为现在的mStickAngle为负数(逆时针)，所以要取反
            sweepAngle = -mStickAngle / 2;
            if (sweepAngle >= 180) {
                sweepAngle = 180;
                mFullRipple = true;
            }
        }
        mRipplePath.reset();
        mRipplePath.addArc(mTempRect, mStickAngle, sweepAngle);
    }

    /**
     * 画静态的杯子
     */
    private void drawCup(Canvas canvas) {
        canvas.translate(0, mCupBottomOffset);
        //杯底颜色
        setPaintFillAndColor(mCupBottomColor);
        //杯底
        canvas.drawCircle(mCenterX, mCenterY, mCupRadius, mPaint);
        setPaintStrokeAndWidth(mCupHandleWidth);
        //手柄底部
        canvas.drawLine(mCenterX, mCenterY, mCenterX + mCupRadius, mCenterY + mCupRadius, mPaint);

        canvas.translate(0, -mCupBottomOffset);
        //杯身颜色
        setPaintFillAndColor(mCupBodyColor);
        //杯身
        canvas.drawCircle(mCenterX, mCenterY, mCupRadius, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        //手柄
        canvas.drawLine(mCenterX, mCenterY, mCenterX + mCupRadius, mCenterY + mCupRadius, mPaint);
        //咖啡颜色
        setPaintFillAndColor(mCoffeeColor);
        //咖啡
        canvas.drawCircle(mCenterX, mCenterY, mCoffeeRadius, mPaint);

        if (mState == STATE_STIRRING) {
            updateCoffeeEdgeAlpha();
        }
        mPaint.setShader(mCupShader);
        canvas.drawCircle(mCenterX, mCenterY, mCoffeeRadius, mPaint);
        mPaint.setShader(null);
    }

    /**
     * 更新咖啡边沿渐变透明度
     */
    private void updateCoffeeEdgeAlpha() {
        float percent = (SystemClock.uptimeMillis() - mEdgeFadeStartTime) / mEdgeFadeDuration;
        if (percent >= 1) {
            percent = 1;
        } else if (percent <= -1) {
            percent = -1;
        }
        if (Math.abs(percent) == 1) {
            mEdgeFadeStartTime = SystemClock.uptimeMillis();
            mEdgeFadeDuration = -mEdgeFadeDuration;
        }
        int alpha = (int) (255 * percent);
        checkIsFull(alpha);
        //加上255，如果是负数，刚好递减，如果是正数，让他溢出
        mPaint.setAlpha(255 + alpha);
    }

    /**
     * 检查是否可以播放隐藏动画
     */
    private void checkIsFull(int alpha) {
        //如果被标记已完成，并且现在的透明度为0
        if (mFinished && alpha == 255) {
            //开始隐藏手臂
            mState = STATE_HIDING;
            mStirringStartTime = 0;
            mHideStartTime = SystemClock.uptimeMillis();
        }
    }

    /**
     * 画搅拌棒
     */
    private void drawStick(Canvas canvas) {
        mPaint.setColor(mStickColor);
        setPaintStrokeAndWidth(mCoffeeStickWidth);

        canvas.drawLine(mStickStartPoint.x, mStickStartPoint.y, mStickEndPoint.x, mStickEndPoint.y, mPaint);
    }

    /**
     * 更新搅拌棒的坐标点
     */
    private void updateStickLocation() {
        final float startRadius = mCupRadius / 2;
        final float endRadius = mCupRadius / 3;
        switch (mState) {
            case STATE_STIRRING:
                //如果是搅拌中，搅拌棒的坐标跟随当前角度来确定
                updateStickByAngle(startRadius, endRadius);
                break;
            case STATE_HIDING:
                //如果手臂正在隐藏，则跟随隐藏动画的进度来确定
                updateStickByHidingAnimation(startRadius, endRadius);
                break;
            default:
                //其他情况跟随拖动进度来确定
                updateStickByProgress(startRadius, endRadius, 1F - mProgress);
                break;
        }
    }

    /**
     * 根据当前角度来确定搅拌棒的坐标
     *
     * @param startRadius 搅拌棒起始半径
     * @param endRadius   搅拌棒结束半径
     */
    private void updateStickByAngle(float startRadius, float endRadius) {
        mStickStartPoint.set(getPointByAngle(startRadius, mStickAngle));
        mStickStartPoint.offset(mCenterX, mCenterY);
        mStickEndPoint.set(getPointByAngle(endRadius, mStickAngle));
        mStickEndPoint.offset(mCenterX + mCupRadius, mCenterY);
    }

    /**
     * 根据手臂隐藏动画的进度来确定搅拌棒的坐标
     */
    private void updateStickByHidingAnimation(float startRadius, float endRadius) {
        float percent = (SystemClock.uptimeMillis() - mHideStartTime) / mHideDuration;
        if (percent >= 1) {
            percent = 1;
            mHideStartTime = 0;
            mState = STATE_NORMAL;
        }
        updateStickByProgress(startRadius, endRadius, percent);
    }

    private Interpolator mPrepareInterpolator = new AccelerateInterpolator();

    /**
     * 根据指定进度来确定搅拌棒的坐标
     */
    private void updateStickByProgress(float startRadius, float endRadius, float progress) {
        mStickStartPoint.set(getPathPointByProgress(
                mPrepareInterpolator.getInterpolation(progress)));
        mStickEndPoint.set(mStickStartPoint);
        mStickEndPoint.offset(startRadius + endRadius, 0);
    }

    /**
     * 画手
     */
    private void drawHand(Canvas canvas) {
        updateOFingerRect();

        updateOFingersPath();
        updateKFingersPath();
        updateArmPath();

        drawArm(canvas);
        drawOKFingers(canvas);
    }

    /**
     * 画OK形状的手指
     */
    private void drawOKFingers(Canvas canvas) {
        setPaintStrokeAndWidth(mFingerWidth);
        canvas.drawPath(mOFingersPath, mPaint);
        canvas.drawPath(mKFingersPath, mPaint);
    }

    /**
     * 画手臂
     */
    private void drawArm(Canvas canvas) {
        setPaintFillAndColor(mHandColor);
        canvas.drawPath(mArmPath, mPaint);
    }

    /**
     * 更新手臂Path
     */
    private void updateArmPath() {
        float oCenterY = mStickEndPoint.y;
        float halfFingerWidth = mFingerWidth / 2;
        mArmPath.reset();
        mArmPath.addRect(mOFingerRect.right + halfFingerWidth - mArmWidth, oCenterY,
                mOFingerRect.right + halfFingerWidth, mHeight, Path.Direction.CW);
        mArmPath.op(mArmPath, mOFingersPath, Path.Op.DIFFERENCE);
    }

    /**
     * 更新O手指Path
     */
    private void updateOFingersPath() {
        float halfFingerWidth = mFingerWidth / 2;
        //预留开口角度
        float reservedAngle = (float) Math.toDegrees(Math.asin((mCoffeeStickWidth / 2 + halfFingerWidth) / mFingerORadius));
        mOFingersPath.reset();
        mOFingersPath.addArc(mOFingerRect, 180 + reservedAngle, 360 - reservedAngle * 2);
    }

    /**
     * 更新K手指Path
     */
    private void updateKFingersPath() {
        float oCenterY = mStickEndPoint.y;
        float finger1X = (mFingerORadius * (float) Math.sin(mStickAngle * Math.PI / 180));
        float finger2X = finger1X / 2;
        mKFingersPath.reset();
        mKFingersPath.moveTo(mOFingerRect.right, oCenterY);
        mKFingersPath.rLineTo(-finger1X - mFingerWidth, -mFinger1Length);
        mKFingersPath.moveTo(mOFingerRect.right, oCenterY);
        mKFingersPath.rLineTo(-finger2X, -mFinger2Length);
    }

    /**
     * 更新O手指Path
     */
    private void updateOFingerRect() {
        float oCenterX = mStickEndPoint.x + mFingerORadius;
        float oCenterY = mStickEndPoint.y;
        float halfFingerWidth = mFingerWidth / 2;
        //o手势的右边，只移动左边一半的距离，造成一个视差效果
        float rightOffset = mState == STATE_STIRRING ?
                (mStickEndPoint.x - mCenterX - mCupRadius) / 2 : halfFingerWidth;

        float left = oCenterX - mFingerORadius;
        float top = oCenterY - mFingerORadius;
        float right = left + mFingerORadius * 2 - rightOffset;
        float bottom = top + mFingerORadius * 2;
        mOFingerRect.set(left - halfFingerWidth, top, right, bottom);
    }

    /**
     * 设置画笔为实心并且设置颜色
     *
     * @param color 新颜色
     */
    private void setPaintFillAndColor(int color) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
    }

    /**
     * 设置画笔为空心并且设置笔宽
     *
     * @param width 新的笔宽
     */
    private void setPaintStrokeAndWidth(float width) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(width);
    }

    /**
     * 开始播放搅拌动画
     */
    public void start() {
        if (mProgress == 1F && mState != STATE_STIRRING) {
            mState = STATE_STIRRING;
            mStickAngle = 0;
            mStirringStartTime = SystemClock.uptimeMillis();
            mEdgeFadeStartTime = SystemClock.uptimeMillis();
            //变成负数，因为每次都要先消失
            mEdgeFadeDuration = -Math.abs(mEdgeFadeDuration);
            mFullRipple = false;
            mFinished = false;
            invalidateSelf();
        }
    }

    /**
     * 重置所有状态
     */
    public void reset() {
        if (mState == STATE_STIRRING) {
            mState = STATE_NORMAL;
            mStickAngle = 0;
            mStirringStartTime = 0;
            mFullRipple = false;
            mFinished = true;
            invalidateSelf();
        }
    }

    /**
     * 标记搅拌动画结束
     */
    public void finish() {
        if (mState == STATE_STIRRING && !mFinished) {
            mFinished = true;
        }
    }

    /**
     * 设置手指的移动进度
     */
    public void setProgress(@FloatRange(from = 0F, to = 1F) float progress) {
        //只有普通状态和准备状态下才能设置进度
        if (mState == STATE_NORMAL || mState == STATE_PREPARING) {
            mState = STATE_PREPARING;
            if (progress > 1) {
                progress = 1;
            } else if (progress < 0) {
                progress = 0;
            }
            mProgress = progress;
            invalidateSelf();
        }
    }

    /**
     * 获取当前进度
     */
    public float getProgress() {
        return mProgress;
    }

    /**
     * 获取结束动画时长
     */
    public int getFinishAnimationDuration() {
        return (int) (mStirringDuration * 2 + mHideDuration);
    }

    /**
     * 获取杯身颜色
     */
    public int getCupBodyColor() {
        return mCupBodyColor;
    }

    /**
     * 设置杯身颜色
     */
    public void setCupBodyColor(int color) {
        this.mCupBodyColor = color;
        initCupShader();
        invalidateSelf();
    }

    /**
     * 获取杯底颜色
     */
    public int getCupBottomColor() {
        return mCupBottomColor;
    }

    /**
     * 设置杯底颜色
     */
    public void setCupBottomColor(int color) {
        this.mCupBottomColor = color;
        invalidateSelf();
    }

    /**
     * 获取咖啡颜色
     */
    public int getCoffeeColor() {
        return mCoffeeColor;
    }

    /**
     * 设置咖啡颜色
     */
    public void setCoffeeColor(int color) {
        this.mCoffeeColor = color;
        invalidateSelf();
    }

    /**
     * 获取手颜色
     */
    public int getHandColor() {
        return mHandColor;
    }

    /**
     * 设置手颜色
     */
    public void setHandColor(int color) {
        this.mHandColor = color;
        invalidateSelf();
    }

    /**
     * 获取搅拌棒颜色
     */
    public int getStickColor() {
        return mStickColor;
    }

    /**
     * 设置搅拌棒颜色
     */
    public void setStickColor(int color) {
        this.mStickColor = color;
        invalidateSelf();
    }

    /**
     * 获取搅拌一圈的时长
     */
    public long getStirringDuration() {
        return mStirringDuration;
    }

    /**
     * 设置搅拌一圈的时长
     */
    public void setStirringDuration(long duration) {
        this.mStirringDuration = duration;
    }

    /**
     * 获取隐藏手臂的时长
     */
    public long getHideDuration() {
        return mHideDuration;
    }

    /**
     * 设置隐藏手臂的时长
     */
    public void setHideDuration(long duration) {
        this.mHideDuration = duration;
    }

    /**
     * 获取咖啡边缘渐变动画时长
     */
    public long getEdgeFadeDuration() {
        return mEdgeFadeDuration;
    }

    /**
     * 设置咖啡边缘渐变动画时长
     */
    public void setEdgeFadeDuration(long duration) {
        this.mEdgeFadeDuration = duration;
    }

    /**
     * 分解Path
     *
     * @return Path上的全部坐标点
     */
    private float[] decomposePath(Path path) {
        return decomposePath(path, 1);
    }

    /**
     * 分解Path
     *
     * @return Path上的全部坐标点
     */
    private float[] decomposePath(Path path, int precision) {
        if (path.isEmpty()) {
            return new float[0];
        }
        PathMeasure pathMeasure = new PathMeasure(path, false);
        final float pathLength = pathMeasure.getLength();
        if (precision <= 0) {
            precision = 1;
        }
        int numPoints = (int) (pathLength / precision) + 1;
        float[] points = new float[numPoints * 2];
        final float[] position = new float[2];
        int index = 0;
        float distance;
        for (int i = 0; i < numPoints; ++i) {
            distance = (i * pathLength) / (numPoints - 1);
            pathMeasure.getPosTan(distance, position, null);
            points[index] = position[0];
            points[index + 1] = position[1];
            index += 2;
        }
        return points;
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mHeight;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
