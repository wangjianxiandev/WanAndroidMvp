package com.wjx.android.wanandroidmvp.base.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;

/**
 * Created with Android Studio.
 * Description: 判断登陆状态
 *
 * @author: Wangjianxian
 * @date: 2020/01/01
 * Time: 13:46
 */
public class LoginUtils {

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static String getLoginUser() {
        long expire = SPUtils.getInstance("cookie").getLong("cookie_expire", 0);
        if (expire > System.currentTimeMillis()) {
            String cookies = SPUtils.getInstance("cookie").getString("user");
            if (!TextUtils.isEmpty(cookies)) {
                for (String cookie : cookies.split(";")) {
                    if (TextUtils.equals("loginUserName", cookie.split("=")[0])) {
                        if (!TextUtils.isEmpty(cookie.split("=")[1])) {
                            return cookie.split("=")[1];
                        }
                        break;
                    }
                }
            }
        }
        return "";
    }

    /**
     * 清空登录信息
     */
    public static void clearLoginInfo() {
        SPUtils.getInstance("cookie").put("user", "");
    }

    /**
     * 是否已经登录
     *
     * @return
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getLoginUser());
    }

}

