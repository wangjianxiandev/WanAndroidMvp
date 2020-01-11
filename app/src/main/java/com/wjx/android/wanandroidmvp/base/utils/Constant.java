package com.wjx.android.wanandroidmvp.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

/**
 * Created with Android Studio.
 * Description: 静态类，用到的常量值，比如请求Url，响应时间等
 *
 * @author: 王拣贤
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
    public static final String LOGOUT_URL = "user/logout/json";

    /**
     * 收藏文章的url
     */
    public static final String URL_COLLECT = "/lg/collect/{id}/json";

    /**
     * 取消收藏文章的url
     */
    public static final String URL_UNCOLLECT = "/lg/uncollect_originId/{id}/json";

    /**
     * 取消收藏文章的url(包含自己录入的内容)
     */
    public static final String URL_UNCOLLECT_INCLUDE_ADD = "/lg/uncollect/{id}/json";

    /**
     * 收藏文章列表的url
     */
    public static final String URL_COLLECT_LIST = "/lg/collect/list/{PageNum}/json";

    /**
     * 添加站外收藏
     */
    public static final String URL_ADD_COLLECT = "/lg/collect/add/json";

    /**
     * 搜索的url
     */
    public static final String URL_SEARCH = "/article/query/{PageNum}/json";

    /**
     * 获取成功
     */
    public static final int SUCCESS = 0;

    /**
     * 每页数量
     */
    public static final int PAGE_SIZE = 20;

    /**
     * key-link
     */
    public static final String KEY_LINK = "link";

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
     * key-keyword
     */
    public static final String KEY_KEYOWRD = "keyword";

    /**
     * 夜间模式
     */
    public static final String KEY_NIGHT_MODE = "night_mode";

    /**
     * key-user
     */
    public static final String KEY_USER = "user";

    /**
     * key-cookie-username
     */
    public static final String KEY_USER_COOKIE = "loginUserName";

    /**
     * 设置文件的保存名称
     */
    public static final String CONFIG_SETTINGS = "settings";

    /**
     * Cookie文件的保存名称
     */
    public static final String CONFIG_COOKIE = "cookie";

    /**
     * Cookie过期时间
     */
    public static final String CONFIG_COOKIE_EXPIRE = "cookie_expire";

    /**
     * 最大有效期(ms),默认距离上次30天
     */
    public static final long TIME_MAX_EXPIRE = 30 * 24 * 3600 * 1000L;

    /**
     * 搜索结果正则表达式
     */
    public static final String REGEX = "<em class='highlight'>(.+)</em>";

    /**
     * 超时时间
     */
    public static final int DEFAULT_TIMEOUT = 15;

    /**
     * extra-key
     */
    public static final String EXTRA_KEY_REFERRER = "referrer";

    /**
     * extra-value
     */
    public static final String EXTRA_VALUE_COLLECT = "collect";

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
     * 获取ActionBar高度
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
}
