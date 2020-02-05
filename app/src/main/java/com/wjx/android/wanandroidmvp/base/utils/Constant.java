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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kotlin.jvm.internal.Intrinsics;

/**
 * Created with Android Studio.
 * Description: 静态类，用到的常量值，比如请求Url，响应时间等
 *
 * @author: Wangjianxian
 * @date: 2019/12/14
 * Time: 15:35
 */
public class Constant {
    /**
     * 域名
     */
    public static final String BASE_URL = "https://www.wanandroid.com/";

    /**
     * 首页Banner的url
     */
    public static final String BANNER_URL = "/banner/json";

    /**
     * 首页文章URL
     */
    public static final String ARTICLE_URL = "/article/list/{pageNum}/json";

    /**
     * 置顶文章数据
     */
    public static final String TOP_ARTICLE_URL = "/article/top/json";

    /**
     * 导航URL
     */
    public static final String NAVI_URL = "/navi/json";

    /**
     * 体系URL
     */
    public static final String TREE_URL = "/tree/json";

    /**
     * 体系文章
     */
    public static final String TREE_ARTICLE_URL = "/article/list/{pageNum}/json";

    /**
     * 广场文章
     */
    public static final String HOME_SQUARE_URL = "/user_article/list/{pageNum}/json";

    /**
     * 分享文章
     */
    public static final String SQUARE_SHARE_ARTICLE = "/lg/user_article/add/json";

    /**
     * 项目种类URL
     */
    public static final String PROJECT_CATEGORY_URL = "/project/tree/json";

    /**
     * 项目列表URL
     */
    public static final String PROJECT_LIST_URL = "project/list/{pageNum}/json";

    /**
     * 公众号Tab URL
     */
    public static final String WE_CHAT_TAB_URL = "wxarticle/chapters/json";

    /**
     * 公众号列表的URL
     */
    public static final String WE_CHAT_LIST_URL = "wxarticle/list/{id}/{pageNum}/json";

    /**
     * 热搜URL
     */
    public static final String HOT_SEARCH_URL = "/hotkey/json";

    /**
     * 注册URL
     */
    public static final String REGISTER_URL = "/user/register";

    /**
     * 登录URL
     */
    public static final String LOGIN_URL = "/user/login";

    /**
     * 登出URL
     */
    public static final String LOGINOUT_URL = "user/logout/json";

    /**
     * 收藏文章的url
     */
    public static final String COLLECT_URL = "/lg/collect/{id}/json";

    /**
     * 取消收藏文章的url
     */
    public static final String UNCOLLECT_URL = "/lg/uncollect_originId/{id}/json";

    /**
     * 取消收藏文章的url(包含自己录入的内容)
     */
    public static final String UNCOLLECT_INCLUDE_ADD_URL = "/lg/uncollect/{id}/json";

    /**
     * 收藏文章列表的url
     */
    public static final String COLLECT_LIST_URL = "/lg/collect/list/{pageNum}/json";

    /**
     * 添加站外收藏
     */
    public static final String ADD_COLLECT_URL = "/lg/collect/add/json";

    /**
     * 搜索的url
     */
    public static final String SEARCH_URL = "/article/query/{pageNum}/json";

    /**
     * 获取个人积分排行
     */
    public static final String INTEGRAL_URL = "/lg/coin/userinfo/json";

    /**
     * 积分排行
     */
    public static final String RANK_URL = "/coin/rank/{pageNum}/json";

    /**
     * 获取分享的文章的列表
     */
    public static final String SHARE_ARTICLE_URL = "/user/lg/private_articles/{pageNum}/json";

    /**
     * 删除分享的文章
     */
    public static final String DELETE_SHARE_ARTICLE_URL = "/lg/user_article/delete/{id}/json";

    /**
     * 积分规则
     */
    public static final String INTEGRAL_HELP_URL = "https://www.wanandroid.com/blog/show/2653";

    /**
     * 获取成功
     */
    public static final int SUCCESS = 0;

    /**
     * 每页数量
     */
    public static final int PAGE_SIZE = 20;

    /**
     * key-articleid
     */
    public static final String KEY_ARTICLEID = "articleid";

    /**
     * key-title
     */
    public static final String KEY_TITLE = "title";

    /**
     * tree-cid
     */
    public static final String KEY_TREE_CID = "cid";

    /**
     * key-url
     */
    public static final String KEY_URL = "url";

    /**
     * key-iscollect
     */
    public static final String KEY_COLLECT = "iscollect";

    /**
     * key-rank
     */
    public static final String KEY_RANK = "rank";

    /**
     * key-countcoin
     */
    public static final String KEY_COUNTCOIN = "countcoin";

    /**
     * key-keyword
     */
    public static final String KEY_KEYWORD = "keyword";

    /**
     * key-night-mode
     */
    public static final String KEY_NIGHT_MODE = "night_mode";

    /**
     * 设置文件的保存名称
     */
    public static final String CONFIG_SETTINGS = "settings";


    /**
     * 搜索结果正则表达式
     */
    public static final String REGEX = "<em class='highlight'>(.+)</em>";

    /**
     * 非文章详情页
     */
    public static final int NOT_ARTICLE_WEBVIEW = 0;

    /**
     * user-name
     */
    public static final String EXTRA_KEY_USERNAME = "referrer";

    /**
     * user-password
     */
    public static final String EXTRA_VALUE_PASSWORD = "collect";

    /**
     * 两次点击返回键的时间差
     */
    public static final long EXIT_TIME = 2000;

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
     * @param context
     * @param milliseconds
     */
    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    /**
     * 显示SnackBar
     * @param activity
     * @param msg
     */
    public static void showSnackMessage(Activity activity, String msg) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
        final Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(activity,R.color.always_white_text));
        snackbar.setActionTextColor(ContextCompat.getColor(activity,R.color.always_white_text));
        view.setBackgroundColor(getColor(activity));
        snackbar.setAction("知道了", v -> {
            snackbar.dismiss();
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }).show();
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });
    }

}
