package com.wjx.android.wanandroidmvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.wjx.android.wanandroidmvp.Custom.CustomEditText;
import com.wjx.android.wanandroidmvp.Custom.loading.LoadingView;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.contract.login.Contract;
import com.wjx.android.wanandroidmvp.presenter.me.LoginPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<Contract.ILoginView, LoginPresenter> implements Contract.ILoginView {
    private String mUserNameText;
    private String mPassWordText;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    CustomEditText mPassword;

    @BindView(R.id.login)
    Button mLoginButton;

    @BindView(R.id.go_register)
    Button mRegister;

    @BindView(R.id.loading_view)
    LoadingView mLoading;

    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;

    private Context mContext;

    private String mRegisterName;

    private String mRegisterPassword;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        try {
            mRegisterName = getIntent().getExtras().getString(Constant.EXTRA_KEY_USERNAME);
            mRegisterPassword = getIntent().getExtras().getString(Constant.EXTRA_VALUE_PASSWORD);
            mUsername.setText(mRegisterName);
            mPassword.setText(mRegisterPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initToolbar();
        mLoginButton.getBackground().setColorFilter(
                Constant.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Constant.getColor(mContext));
        mToolbar.setBackgroundColor(Constant.getColor(mContext));
        mToolbar.setTitle(R.string.login);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onLogin(LoginData loginData) {
        stopAnim();
        if (loginData != null) {
            if (loginData.getErrorCode() == 0) {
                Event event = new Event();
                event.target = Event.TARGET_HOME;
                event.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(event);

                Event treeEvent = new Event();
                treeEvent.target = Event.TARGET_TREE;
                treeEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(treeEvent);

                Event squareEvent = new Event();
                squareEvent.target = Event.TARGET_SQUARE;
                squareEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(squareEvent);

                Event projectEvent = new Event();
                projectEvent.target = Event.TARGET_PROJECT;
                projectEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(projectEvent);

                Event wxEvent = new Event();
                wxEvent.target = Event.TARGET_WX;
                wxEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(wxEvent);

                Event menuEvent = new Event();
                menuEvent.target = Event.TARGET_ME;
                menuEvent.type = Event.TYPE_LOGIN;
                menuEvent.data = mUsername.getText().toString();
                EventBus.getDefault().post(menuEvent);

                Event meShareEvent = new Event();
                meShareEvent.target = Event.TARGET_ME_SHARE;
                meShareEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(meShareEvent);

                Event webEvent = new Event();
                webEvent.target = Event.TARGET_WEB_VIEW;
                webEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(webEvent);
                finish();
            } else {
                ToastUtils.showShort(loginData.getErrorMsg());
            }
        }
    }

    @OnClick(R.id.login)
    public void login() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        startAnim();
        mUserNameText = mUsername.getText().toString();
        mPassWordText = mPassword.getText().toString();
        mPresenter.login(mUserNameText, mPassWordText);
    }

    @OnClick(R.id.go_register)
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        mLoading.startTranglesAnimation();
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed() {
        stopAnim();
    }

    @Override
    public void onLoadSuccess() {

    }
}
