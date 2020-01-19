package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.contract.squaresharearticle.Contract;
import com.wjx.android.wanandroidmvp.presenter.share.SquareSharePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class ShareArticleActivity extends BaseActivity<Contract.IShareView, SquareSharePresenter> implements Contract.IShareView {

    @BindView(R.id.share_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.share_title)
    EditText mShareTitle;

    @BindView(R.id.share_url)
    EditText mShareUrl;

    @BindView(R.id.share_username)
    TextView mShareUserName;

    @BindView(R.id.share_submit)
    Button mShareSubmit;

    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_share_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mShareUserName.setText(LoginUtils.getLoginUser());
        initColor();
        initToolbar();
    }

    private void initColor() {
        getWindow().setStatusBarColor(Constant.getColor(mContext));
        mToolbar.setBackgroundColor(Constant.getColor(mContext));
        mToolbar.setTitleTextColor(getColor(R.color.white));
        mShareSubmit.setBackgroundColor(Constant.getColor(mContext));
    }

    @OnClick(R.id.share_submit)
    public void onSubmitClick() {
        if (mShareTitle.getText().toString().isEmpty()) {
            ToastUtils.showShort("请填写文章标题");
        } else if (mShareUrl.getText().toString().isEmpty()) {
            ToastUtils.showShort("请填写文章链接");
        } else {
            mPresenter.addArticle(mShareTitle.getText().toString()
                    , mShareUrl.getText().toString());
            finish();
        }

    }

    @Override
    protected SquareSharePresenter createPresenter() {
        return new SquareSharePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.share_article);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }


    @Override
    public void onAddArticle(Article addArticle) {
    }



    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onLoadSuccess() {

    }
}
