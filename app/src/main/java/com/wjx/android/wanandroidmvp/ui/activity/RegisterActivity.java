package com.wjx.android.wanandroidmvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.me.RegisterData;
import com.wjx.android.wanandroidmvp.contract.register.Contract;
import com.wjx.android.wanandroidmvp.presenter.register.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/26
 * Time: 15:26
 */
public class RegisterActivity extends BaseActivity<Contract.IRegisterView, RegisterPresenter> implements Contract.IRegisterView {

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    CustomEditText mPassword;

    @BindView(R.id.repassword)
    CustomEditText mRePassword;

    @BindView(R.id.loading)
    ImageView mLoading;

    private Context mContext;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initToolbar();
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Constant.getColor(mContext));
        mToolbar.setBackgroundColor(Constant.getColor(mContext));
        mToolbar.setTitle(R.string.register);
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
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void onRegister(RegisterData registerData) {
        stopAnim();
        if (registerData != null) {
            if (registerData.getErrorCode() == Constant.SUCCESS) {
                ToastUtils.showShort(mContext.getString(R.string.register_success));
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra(Constant.EXTRA_KEY_USERNAME, mUsername.getText().toString());
                intent.putExtra(Constant.EXTRA_VALUE_PASSWORD, mPassword.getText().toString());
                startActivity(intent);
                finish();
            } else {
                ToastUtils.showShort(registerData.getErrorMsg());
            }
        }
    }

    @OnClick(R.id.register)
    public void register() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText()) ||
                TextUtils.isEmpty(mRePassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        if (!TextUtils.equals(mPassword.getText(), mRePassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.password_not_match));
            return;
        }
        mPresenter.register(mUsername.getText().toString(), mPassword.getText().toString());
        startAnim();
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
