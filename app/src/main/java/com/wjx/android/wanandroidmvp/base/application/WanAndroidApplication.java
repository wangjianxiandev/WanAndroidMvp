package com.wjx.android.wanandroidmvp.base.application;


import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.wjx.android.wanandroidmvp.base.utils.Constant;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class WanAndroidApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Utils.init(this);
        boolean nightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).getBoolean
                (Constant.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
//        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
//        LogUtils.d("channel:" + channel);
    }
}
