package com.wjx.android.wanandroidmvp.base.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created with Android Studio.
 * Description: 用到的常量值，比如请求Url，响应时间等
 *
 * @author: 王拣贤
 * @date: 2019/12/14
 * Time: 15:35
 */
public interface Constant {
    String BASE_URL = "https://www.wanandroid.com/";

    String KEY_URL = "";

    String KEY_ID = "";

    long SPLASH_TIME = 2000;

    long EXIT_TIME = 2000;

    long TIMEOUT_CONNECT = 10;

    long TIMEOUT_READ = 10;

    long TIMEOUT_WRITE = 10;

    /**
     * Shared Preference key
     */
    String ACCOUNT = "account";

    String PASSWORD = "password";

    String LOGIN_STATUS = "login_status";

    String AUTO_CACHE_STATE = "auto_cache_state";

    String NO_IMAGE_STATE = "no_image_state";

    String NIGHT_MODE_STATE = "night_mode_state";

    /**
     * banner获取成功
     */
    public static final int BANNER_SUCCESS = 0;

    /**
     * 文章title的key
     */
    public static final String ARTICLE_TITLE = "title";

    /**
     * 文章url的key
     */
    public static final String ARTICLE_URL = "url";

    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red =random.nextInt(150);
        //0-190
        int green =random.nextInt(150);
        //0-190
        int blue =random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red,green, blue);
    }
}
