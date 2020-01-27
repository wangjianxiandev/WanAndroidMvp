package com.wjx.android.wanandroidmvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.KeyBoardUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.searchwords.SearchWordData;
import com.wjx.android.wanandroidmvp.contract.searchwords.Contract;
import com.wjx.android.wanandroidmvp.presenter.searchwords.SearchWordPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2019/12/29
 * Time: 20:29
 */
public class SearchWordActivity extends BaseActivity<Contract.ISearchView, SearchWordPresenter> implements Contract.ISearchView {

    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_text_top)
    TextView mTopSearchWord;
    @BindView(R.id.search_clear)
    TextView mSearchClear;
    @BindView(R.id.search_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_flowlayout)
    TagFlowLayout mTopSearchFlowLayout;

    @BindView(R.id.search_toolbar)
    Toolbar mSearchToolbar;

    @BindView(R.id.anim_img)
    ImageView mSearchAnimImage;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.search_clear)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_clear:
                clearHistoryData();
                break;
            default:
                break;
        }
    }

    private void clearHistoryData() {
    }

    private void backPressed() {
        KeyBoardUtils.closeKeyboard(this, mSearchEdit);
        onBackPressed();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initColor();
        initStatusBar();
        initSearchEdit();
        initCircleAnimation();
        initRecyclerView();
        mPresenter.loadSearchWordData();
    }

    private void initColor() {
        mSearchToolbar.setBackgroundColor(Constant.getColor(mContext));
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Constant.getColor(mContext));
        }
        if (ColorUtils.calculateLuminance(Constant.getColor(mContext)) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    private void initSearchEdit() {
        // 将焦点放到ImageView上
        mSearchAnimImage.setFocusable(true);
        mSearchAnimImage.setFocusableInTouchMode(true);
        mSearchAnimImage.requestFocus();
        mSearchAnimImage.requestFocusFromTouch();
        mSearchEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(
                        SearchWordActivity.this, R.drawable.animated_vector_search);
                mSearchAnimImage.setImageDrawable(animatedVectorDrawableCompat);
                ((Animatable) mSearchAnimImage.getDrawable()).start();
            }
        });
        mSearchAnimImage.setOnClickListener(v -> {
            Intent intent = new Intent(SearchWordActivity.this, SearchResultActivity.class);
            intent.putExtra(Constant.KEY_KEYWORD, mSearchEdit.getText().toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

    }

    private void initCircleAnimation() {
    }

    private void initRecyclerView() {

    }

    @Override
    protected SearchWordPresenter createPresenter() {
        return new SearchWordPresenter();
    }

    @Override
    public void loadSearchWordData(SearchWordData searchWordData) {
        if (searchWordData.getErrorCode() == Constant.SUCCESS) {
            List<String> tabNames = searchWordData.getData()
                    .stream()
                    .map(SearchWordData.DataBean::getName)
                    .collect(Collectors.toList());
            mTopSearchFlowLayout.setAdapter(new TagAdapter(tabNames) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView tagText = (TextView) LayoutInflater.from(SearchWordActivity.this).inflate(R.layout.flow_layout,
                            parent, false);
                    tagText.setText(tabNames.get(position));
                    tagText.getBackground().setColorFilter(Constant.randomColor(), PorterDuff.Mode.SRC_ATOP);
                    tagText.setTextColor(getColor(R.color.white));
                    mTopSearchFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            Toast.makeText(SearchWordActivity.this, tabNames.get(position), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    return tagText;
                }
            });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event){
        if (event.target == Event.TARGET_SEARCH) {
            if (event.type == Event.TYPE_REFRESH_COLOR) {
                mSearchToolbar.setBackgroundColor(Constant.getColor(mContext));
            }
        }
    }
}
