package com.wjx.android.wanandroidmvp.base.application;

import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.wjx.android.wanandroidmvp.base.utils.Constant;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

public class WanAndroidApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Utils.init(this);
        changeMode();
//        initSkin();
    }

    private void changeMode() {
        boolean isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).getBoolean
                (Constant.KEY_NIGHT_MODE, false);
        int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isSystemNightMode = currentMode == Configuration.UI_MODE_NIGHT_YES;
        AppCompatDelegate.setDefaultNightMode(isNightMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void initSkin() {
        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater())
                .addInflater(new SkinMaterialViewInflater())
                .addInflater(new SkinConstraintViewInflater())
                .addInflater(new SkinCardViewInflater())
                .setSkinWindowBackgroundEnable(true)
                .loadSkin();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
