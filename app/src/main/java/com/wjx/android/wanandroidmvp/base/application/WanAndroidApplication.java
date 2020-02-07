package com.wjx.android.wanandroidmvp.base.application;

import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.wjx.android.wanandroidmvp.base.utils.Constant;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created with Android Studio.
 * Description: Base Application
 *
 * @author: Wangjianxian
 * @date: 2019/12/18
 * Time: 21:26
 */
public class WanAndroidApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Utils.init(this);
        changeMode();
    }

    private void changeMode() {
        boolean isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).getBoolean
                (Constant.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(isNightMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
    }
}
