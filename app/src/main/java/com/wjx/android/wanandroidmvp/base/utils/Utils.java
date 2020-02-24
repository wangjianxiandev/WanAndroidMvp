package com.wjx.android.wanandroidmvp.base.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.wjx.android.wanandroidmvp.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import kotlin.jvm.internal.Intrinsics;

/**
 * Created with Android Studio.
 * Description: 静态工具方法
 *
 * @author: Wangjianxian
 * @date: 2019/12/14
 * Time: 17:00
 */
public class Utils {
    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red = random.nextInt(150);
        //0-190
        int green = random.nextInt(150);
        //0-190
        int blue = random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }

    /**
     * 计算渐变颜色值 ARGB
     *
     * @param fraction   变化比率 0~1
     * @param startValue 初始色值
     * @param endValue   结束色值
     * @return
     */
    public static int evaluate(float fraction, Object startValue, Integer endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (startA + (int) (fraction * (endA - startA)) << 24)
                | (startR + (int) (fraction * (endR - startR)) << 16)
                | (startG + (int) (fraction * (endG - startG)) << 8)
                | (startB + (int) (fraction * (endB - startB)));
    }

    /**
     * 谷歌原生方法设置状态栏字体颜色
     */
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    /**
     * dp与px转换
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dpToPx(Context context, int dp) {
        float density;
        density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxToDp(Context context, float pxValue) {
        if (context == null) {
            return -1;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取ActionBar高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * 获取主题颜色
     *
     * @param context
     * @return
     */
    public static int getColor(Context context) {
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
        int defaultColor = ContextCompat.getColor(context, R.color.colorPrimary);
        int color = setting.getInt("color", defaultColor);
        if (color != 0 && Color.alpha(color) != 255) {
            return defaultColor;
        } else {
            return color;
        }
    }

    /**
     * 获取切换夜间模式之前的主题色
     *
     * @param context
     * @return
     */
    public static int getLastColor(Context context) {
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
        int defaultColor = ContextCompat.getColor(context, R.color.colorPrimary);
        int color = setting.getInt("lastColor", defaultColor);
        if (color != 0 && Color.alpha(color) != 255) {
            return defaultColor;
        } else {
            return color;
        }
    }

    /**
     * 设置主题颜色
     *
     * @param context
     * @param color
     */
    public static void setColor(Context context, int color) {
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
        setting.edit().putInt("color", color).apply();
    }

    /**
     * 设置切换夜间模式之前的主题颜色
     *
     * @param context
     * @param color
     */
    public static void setLastColor(Context context, int color) {
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
        setting.edit().putInt("lastColor", color).apply();
    }

    /**
     * BottomNavigation 适配颜色
     *
     * @param context
     * @return
     */
    public static final ColorStateList getColorStateList(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        int[] colors = new int[]{getColor(context), ContextCompat.getColor(context, R.color.colorGray)};
        int[][] states = new int[][]{{android.R.attr.state_checked, android.R.attr.state_checked}, new int[0]};
        return new ColorStateList(states, colors);
    }

    /**
     * Float button 适配颜色
     *
     * @param context
     * @return
     */
    public static final ColorStateList getOneColorStateList(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        int[] colors = new int[]{getColor(context)};
        int[][] states = new int[][]{new int[0]};
        return new ColorStateList(states, colors);
    }

    /**
     * 给颜色添加透明度
     *
     * @param context
     * @param alpha
     * @return
     */
    public static int getColorWithAlpha(Context context, float alpha) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & getColor(context);
        return a + rgb;
    }


    private final static String PREFERENCE_NAME = "service_name";
    private final static String SEARCH_HISTORY = "search_history";

    /**
     * 保存历史记录
     *
     * @param editText
     * @param context
     */
    public static void setSearchHistory(String editText, Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(editText)) {
            return;
        }
        //获取之前保存的历史记录
        String savedHistory = sp.getString(SEARCH_HISTORY, "");
        //逗号截取 保存在数组中
        String[] tmpHistory = savedHistory.split(",");
        //将改数组转换成ArrayList
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0) {
            // 移除之前重复添加的元素
            for (int i = 0; i < historyList.size(); i++) {
                if (editText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }
            // 将新输入的文字添加集合的第0位也就是最前面(实现倒序)
            historyList.add(0, editText);
            // 最多保存10条搜索记录 删除最早搜索的那一项
            if (historyList.size() > 10) {
                historyList.remove(historyList.size() - 1);
            }
            // 逗号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + ",");
            }
            // 保存到sp
            editor.putString(SEARCH_HISTORY, sb.toString());
            editor.commit();
        } else {
            // 之前未添加过
            editor.putString(SEARCH_HISTORY, editText + ",");
            editor.commit();
        }
    }

    /**
     * 删除某项历史记录
     *
     * @param deleteText
     * @param context
     */
    public static void deleteSearchHistory(String deleteText, Context context) {
        // 获取所有的历史记录
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String longHistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(",");
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0) {
            // 移除之前重复添加的元素
            for (int i = 0; i < historyList.size(); i++) {
                if (deleteText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }
            // 逗号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + ",");
            }
            // 保存到sp
            editor.putString(SEARCH_HISTORY, sb.toString());
            editor.commit();
        }
    }

    /**
     * 获取所有搜索历史
     *
     * @param context
     * @return
     */
    public static List<String> getAllSearchHistory(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String longHistory = sp.getString(SEARCH_HISTORY, "");
        // split后长度为1有一个空串对象
        String[] tmpHistory = longHistory.split(",");
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        // 如果没有搜索记录，split之后第0位是个空串的情况下
        if (historyList.size() == 1 && historyList.get(0).equals("")) {
            // 清空集合，这个很关键
            historyList.clear();
        }
        return historyList;
    }

    /**
     * 清空历史
     *
     * @param context
     */
    public static void deleteAllSearchHistory(Context context) {
        // 获取所有的历史记录
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String longHistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(",");
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0) {
            // 移除之前重复添加的元素
            historyList.clear();
            // 保存到sp
            editor.putString(SEARCH_HISTORY, "");
            editor.commit();
        }
    }

    /**
     * 设置震动
     *
     * @param context
     * @param milliseconds
     */
    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    /**
     * 显示SnackBar
     *
     * @param activity
     * @param msg
     */
    public static void showSnackMessage(Activity activity, String msg) {
        final Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(activity, R.color.always_white_text));
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.always_white_text));
        view.setBackgroundColor(getColor(activity));
        snackbar.setAction("知道了", v -> {
            snackbar.dismiss();
        }).show();
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
            }
        });
    }

    /**
     * 获取Decode的中文
     *
     * @param encodeName
     * @return
     */
    public static String getDecodeName(String encodeName) {
        String decodeName = "";
        try {
            decodeName = java.net.URLDecoder.decode(encodeName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeName;
    }


    /**
     * 获取当前时刻
     *
     * @return
     */
    public static Date getNowTime() {
        return formatDate("yyyy-MM-dd", new Date((new Date()).getTime()));
    }

    /**
     * 日期格式化
     *
     * @param formatStyle
     * @param date
     * @return
     */
    public static Date formatDate(@NotNull String formatStyle, @Nullable Date date) {
        Intrinsics.checkParameterIsNotNull(formatStyle, "formatStyle");
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
            String formatDate = sdf.format(date);

            try {
                Date var10000 = sdf.parse(formatDate);
                Intrinsics.checkExpressionValueIsNotNull(var10000, "sdf.parse(formatDate)");
                return var10000;
            } catch (ParseException var6) {
                var6.printStackTrace();
                return new Date();
            }
        } else {
            return new Date();
        }
    }
}
