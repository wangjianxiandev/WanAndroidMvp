package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WebViewActivity extends AppCompatActivity {

    Unbinder mbinder;
    @BindView(R.id.web_content)
    LinearLayout mLinearLayout;

    @BindView(R.id.web_title)
    TextView mTitle;

    @BindView(R.id.web_toolbar)
    Toolbar mToolbar;

    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra(Constant.ARTICLE_TITLE);
        String url = intent.getStringExtra(Constant.ARTICLE_URL);
        initToolbar(title);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private void initToolbar(String title) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mTitle.setSingleLine(true);
            mTitle.setSelected(true);
            mTitle.setFocusable(true);
            mTitle.setFocusableInTouchMode(true);
            mTitle.setText(title);
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
        super.onDestroy();
        mbinder.unbind();
    }
}
