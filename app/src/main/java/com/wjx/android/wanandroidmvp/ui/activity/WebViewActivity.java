package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.contract.webview.Contract;
import com.wjx.android.wanandroidmvp.presenter.webview.WebViewPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity<Contract.IWebView, WebViewPresenter> implements Contract.IWebView {

    @BindView(R.id.web_content)
    LinearLayout mLinearLayout;

    @BindView(R.id.web_title)
    TextView mTitleTextView;

    @BindView(R.id.web_toolbar)
    Toolbar mToolbar;

    private AgentWeb mAgentWeb;

    private Context mContext;

    private String mTitle;

    private String mUrl;

    private int mArticleId;

    private boolean isCollect;

    private MenuItem mCollectItem;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        getIntentInfo();
        initToolbar(mTitle);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private void getIntentInfo() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(Constant.KEY_TITLE);
        mUrl = intent.getStringExtra(Constant.KEY_URL);
        mArticleId = intent.getIntExtra(Constant.KEY_ARTICLEID, 0);
        isCollect = intent.getBooleanExtra(Constant.KEY_COLLECT, false);
    }

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter();
    }

    private void initToolbar(String title) {
        getWindow().setStatusBarColor(Constant.getColor(mContext));
        mToolbar.setBackgroundColor(Constant.getColor(mContext));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mTitleTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mTitleTextView.setSingleLine(true);
            mTitleTextView.setSelected(true);
            mTitleTextView.setFocusable(true);
            mTitleTextView.setFocusableInTouchMode(true);
            mTitleTextView.setText(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mArticleId != Constant.NOT_ARTICLE_WEBVIEW) {
            getMenuInflater().inflate(R.menu.article_menu, menu);
            mCollectItem = menu.findItem(R.id.item_collect);
            mCollectItem.setIcon(isCollect ? R.drawable.collect_selector_icon : R.drawable.uncollect_selector_icon);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_share:
                handleShare();
            case R.id.item_collect:
                handleCollect();
                break;
            case R.id.item_system_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_title_url,
                getString(R.string.app_name), mTitle, mUrl));
        intent.setType("text/plain");
        startActivity(intent);
    }

    /**
     * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置mOptionalIconsVisible为true，
     * 给菜单设置图标时才可见 让菜单同时显示图标和文字
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void handleCollect() {
        if (!LoginUtils.isLogin()) {
            Intent intent = new Intent(WebViewActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            if (isCollect) {
                notifyChangeCollectState(mArticleId);
                mCollectItem.setIcon(R.drawable.uncollect_selector_icon);
                mPresenter.unCollect(mArticleId);
                isCollect = false;
            } else {
                notifyChangeCollectState(mArticleId);
                mCollectItem.setIcon(R.drawable.collect_selector_icon);
                mPresenter.collect(mArticleId);
                isCollect = true;
            }
        }
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCollect(Collect collect) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                ToastUtils.showShort("收藏成功");
            } else {
                ToastUtils.showShort("收藏失败");
            }
        }
    }

    @Override
    public void onUnCollect(Collect collect) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                ToastUtils.showShort("取消收藏");
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
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

    /**
     * 通知其他页面更改收藏状态
     */
    private void notifyChangeCollectState(int articleId) {
        // 收藏状态传递给home
        Event homeEvent = new Event();
        homeEvent.target = Event.TARGET_HOME;
        homeEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        homeEvent.data = articleId + "";
        EventBus.getDefault().post(homeEvent);

        // 收藏状态传递给project
        Event projectEvent = new Event();
        projectEvent.target = Event.TARGET_PROJECT;
        projectEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        projectEvent.data = articleId + "";
        EventBus.getDefault().post(projectEvent);

        // 收藏状态传递给square
        Event squareEvent = new Event();
        squareEvent.target = Event.TARGET_SQUARE;
        squareEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        squareEvent.data = articleId + "";
        EventBus.getDefault().post(squareEvent);

        // 收藏状态传递给tree
        Event treeEvent = new Event();
        treeEvent.target = Event.TARGET_TREE;
        treeEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        treeEvent.data = articleId + "";
        EventBus.getDefault().post(treeEvent);

        // 收藏状态传递给wechat
        Event weChatEvent = new Event();
        weChatEvent.target = Event.TARGET_WX;
        weChatEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        weChatEvent.data = articleId + "";
        EventBus.getDefault().post(weChatEvent);

        // 收藏状态传递给meCollect
        Event collectEvent = new Event();
        collectEvent.target = Event.TARGET_COLLECT;
        collectEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        collectEvent.data = articleId + "";
        EventBus.getDefault().post(collectEvent);

        // 收藏状态传递给meShare
        Event meShareEvent = new Event();
        meShareEvent.target = Event.TARGET_ME_SHARE;
        meShareEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        meShareEvent.data = articleId + "";
        EventBus.getDefault().post(meShareEvent);

        Event searchResultEvent = new Event();
        searchResultEvent.target = Event.TARGET_SEARCH_RESULT;
        searchResultEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        searchResultEvent.data = articleId + "";
        EventBus.getDefault().post(searchResultEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_WEB_VIEW) {
            if (event.type == Event.TYPE_LOGIN) {
                mCollectItem.setIcon(isCollect ? R.drawable.collect_selector_icon : R.drawable.uncollect_selector_icon);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mCollectItem.setIcon(R.drawable.uncollect_selector_icon);
            }
        }
    }
}
