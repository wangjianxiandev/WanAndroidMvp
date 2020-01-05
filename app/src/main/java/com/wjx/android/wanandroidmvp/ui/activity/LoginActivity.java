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
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
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

                Event projectEvent = new Event();
                projectEvent.target = Event.TARGET_PROJECT;
                projectEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(projectEvent);

                Event wxEvent = new Event();
                wxEvent.target = Event.TARGET_WX;
                wxEvent.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(wxEvent);

                Event menuEvent = new Event();
                menuEvent.target = Event.TARGET_MENU;
                menuEvent.type = Event.TYPE_LOGIN;
                menuEvent.data = mUsername.getText().toString();
                EventBus.getDefault().post(menuEvent);

                finish();

                if (TextUtils.equals(Constant.EXTRA_VALUE_COLLECT, mReferrer)) {
//                    Intent intent = new Intent(mContext, CollectActivity.class);
//                    startActivity(intent);
                }

            } else {
                ToastUtils.showShort(loginData.getErrorMsg());
            }
        }
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
