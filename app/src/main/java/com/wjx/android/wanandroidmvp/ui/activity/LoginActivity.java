package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.contract.me.Contract;
import com.wjx.android.wanandroidmvp.presenter.me.LoginPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<Contract.ILoginView, LoginPresenter> implements Contract.ILoginView {
    private String mUserNamet;
    private String mPassWordt;

    @BindView(R.id.back)
    ImageView mBack;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    CustomEditText mPassword;

    @BindView(R.id.login)
    Button mLogin;

    @BindView(R.id.go_register)
    Button mRegister;

    @BindView(R.id.loading)
    ImageView mLoading;

    private Context mContext;

    private String mReferrer;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onLogin(LoginData loginData) {
        if (loginData.getErrorCode() == Constant.BANNER_SUCCESS) {
            Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
            Event meEvent = new Event();
            meEvent.target = Event.TARGET_MENU;
            meEvent.type = Event.TYPE_LOGIN;
            meEvent.data = mUserNamet;
            EventBus.getDefault().post(meEvent);
            finish();
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @OnClick(R.id.back)
    public void back() {
        onBackPressed();
        Toast.makeText(mContext, "login", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.login)
    public void login() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        startAnim();
        mUserNamet = mUsername.getText().toString();
        mPassWordt = mPassword.getText().toString();
        mPresenter.login(mUserNamet, mPassWordt);
    }


    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.loading);
        LinearInterpolator li = new LinearInterpolator();
        animation.setInterpolator(li);
        mLoading.startAnimation(animation);
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
        mLoading.clearAnimation();
    }
}
