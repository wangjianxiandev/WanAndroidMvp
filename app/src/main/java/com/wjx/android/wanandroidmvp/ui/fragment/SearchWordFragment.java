package com.wjx.android.wanandroidmvp.ui.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.CustomAnim.CircularRevealAnim;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.KeyBoardUtils;
import com.wjx.android.wanandroidmvp.bean.searchwords.SearchWordData;
import com.wjx.android.wanandroidmvp.contract.searchwords.Contract;
import com.wjx.android.wanandroidmvp.presenter.searchwords.SearchWordPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 19:03
 */
public class SearchWordFragment extends BaseFragment<Contract.ISearchView, SearchWordPresenter> implements Contract.ISearchView,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnDrawListener {
    private CircularRevealAnim mCircularRevealAnim;
    @BindView(R.id.search_back_ib)
    ImageButton mBackButton;
    @BindView(R.id.search_hint_tv)
    TextView mHintText;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    ImageView mSearchTv;
    @BindView(R.id.search_text_top)
    TextView mTopSearchWord;
    @BindView(R.id.search_scroll_view)
    NestedScrollView mSearchScrollView;
    @BindView(R.id.search_clear)
    TextView mSearchClear;
    @BindView(R.id.search_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_flowlayout)
    TagFlowLayout mTopSearchFlowLayout;

    @OnClick({R.id.search_back_ib, R.id.search_clear})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_ib:
                backPressed();
                break;
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
        KeyBoardUtils.closeKeyboard(getActivity(), mSearchEdit);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.search_fragment;
    }

    @Override
    protected void init() {
        initCircleAnimation();
        initRecyclerView();
        mPresenter.loadSearchWordData();
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mSearchEdit.getText().toString().equals("")) {
                    mHintText.setText(R.string.search_hint);
                } else {
                    mHintText.setText("");
                }
            }
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
        if (searchWordData.getErrorCode() == Constant.BANNER_SUCCESS) {
            List<String> tabNames = searchWordData.getData()
                    .stream()
                    .map(SearchWordData.DataBean::getName)
                    .collect(Collectors.toList());
            mTopSearchFlowLayout.setAdapter(new TagAdapter(tabNames) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    TextView tagText = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_layout,
                            parent, false);
                    tagText.setText(tabNames.get(position));
                    tagText.setBackgroundColor(Constant.randomColor());
                    tagText.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTopSearchFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            return true;
                        }
                    });
                    return tagText;
                }
            });
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onDraw() {

    }

    @Override
    public void onHideAnimationEnd() {
        mSearchEdit.setText("");
    }

    @Override
    public void onShowAnimationEnd() {
        KeyBoardUtils.openKeyboard(getActivity(), mSearchEdit);
    }
}
