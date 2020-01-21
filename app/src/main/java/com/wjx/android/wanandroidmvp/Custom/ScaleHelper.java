package com.wjx.android.wanandroidmvp.Custom;


import androidx.annotation.NonNull;

import java.util.Arrays;
/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/21
 * Time: 20:52
 */

@SuppressWarnings({"unused", "WeakerAccess"})
final class ScaleHelper {

    private float[] mScales;

    ScaleHelper(float... scales) {
        updateScales(scales);
    }

    /**
     * 更新缩放比例
     */
    void updateScales(float... scales) {
        if (scales.length == 0) {
            scales = new float[]{1, 0, 1, 1};
        }
        for (float tmp : scales) {
            if (tmp < 0) {
                throw new IllegalArgumentException("Array value can not be negative!");
            }
        }
        if (!Arrays.equals(mScales, scales)) {
            if (scales.length < 2 || scales.length % 2 != 0) {
                throw new IllegalArgumentException("Array length no match!");
            }
            mScales = scales;
            appendIfNeed();
            checkIsArrayLegal();
        }
    }

    /**
     * 获取指定位置的缩放比例
     * @param fraction 当前位置(0~1)
     */
    float getScale(float fraction) {
        float minScale = 1;
        float maxScale = 1;
        float scalePosition;
        float minFraction = 0, maxFraction = 1;
        //顺序遍历，找到小于fraction的，最贴近的scale
        for (int i = 1; i < mScales.length; i += 2) {
            scalePosition = mScales[i];
            if (scalePosition <= fraction) {
                minScale = mScales[i - 1];
                minFraction = mScales[i];
            } else {
                break;
            }
        }
        //倒序遍历，找到大于fraction的，最贴近的scale
        for (int i = mScales.length - 1; i >= 1; i -= 2) {
            scalePosition = mScales[i];
            if (scalePosition >= fraction) {
                maxScale = mScales[i - 1];
                maxFraction = mScales[i];
            } else {
                break;
            }
        }
        //计算当前点fraction，在起始点minFraction与结束点maxFraction中的百分比
        fraction = solveTwoPointForm(minFraction, maxFraction, fraction);
        float distance = maxScale - minScale;
        float scale = distance * fraction;
        float result = minScale + scale;
        return isFinite(result) ? result : minScale;
    }

    /**
     * 将基于总长度的百分比转换成基于某个片段的百分比 (解两点式直线方程)
     *
     * @param startX   片段起始百分比
     * @param endX     片段结束百分比
     * @param currentX 总长度百分比
     * @return 该片段的百分比
     */
    private float solveTwoPointForm(float startX, float endX, float currentX) {
        return (currentX - startX) / (endX - startX);
    }

    /**
     * 判断数值是否合法
     *
     * @param value 要判断的数值
     * @return 合法为true，反之
     */
    private boolean isFinite(float value) {
        return !Float.isNaN(value) && !Float.isInfinite(value);
    }

    /**
     * 检查是否需要在两端追加元素
     */
    private void appendIfNeed() {
        if (mScales[1] != 0) {
            mScales = insertElement(true, mScales, 1, 0);
        }
        if (mScales[mScales.length - 1] != 1) {
            mScales = insertElement(false, mScales, 1, 1);
        }
    }

    /**
     * 检查数组是否合法
     */
    private void checkIsArrayLegal() {
        float min = mScales[1];
        float temp;
        for (int i = 1; i < mScales.length; i += 2) {
            temp = mScales[i];
            if (min > temp) {
                throw new IllegalArgumentException("Incorrect array value! position must be from small to large");
            } else {
                min = temp;
            }
        }
    }

    /**
     * 扩展数组元素
     *
     * @param isAddFromHead 是否从头部添加
     * @param target        目标数组
     * @param elements      需要插入的数值
     * @return 扩展后的数组
     */
    private float[] insertElement(boolean isAddFromHead, @NonNull float[] target, @NonNull float... elements) {
        float[] result = new float[target.length + elements.length];
        if (isAddFromHead) {
            System.arraycopy(elements, 0, result, 0, elements.length);
            System.arraycopy(target, 0, result, elements.length, target.length);
        } else {
            System.arraycopy(target, 0, result, 0, target.length);
            System.arraycopy(elements, 0, result, target.length, elements.length);
        }
        return result;
    }
}
