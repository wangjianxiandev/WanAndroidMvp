package com.wjx.android.wanandroidmvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.WeChatListAdapter;
import com.wjx.android.wanandroidmvp.base.fragment.BaseFragment;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatListData;
import com.wjx.android.wanandroidmvp.contract.wechat.Contract;
import com.wjx.android.wanandroidmvp.presenter.wechat.WeChatListPresenter;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 12:47
 */
public class WeChatListFragment extends BaseFragment<Contract.IWeChatListView, WeChatListPresenter> implements Contract.IWeChatListView {


    @BindView(R.id.normal_view)
    SmartRefreshLayout mSamrtRefreshLayout;
    @BindView(R.id.wechat_list_recycler_view)
    RecyclerView mRecyclerView;

    private WeChatListAdapter mWeChatListAdapter;
    private int mCurrentPage = 0;
    private int mCid = 0;

    public WeChatListFragment(int cid) {
        mCid = cid;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.wechatlist_fragment;
    }

    @Override
    protected void init() {
        initAdapter();
        mPresenter.loadWeChatList(mCid, mCurrentPage);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mWeChatListAdapter = new WeChatListAdapter(mRecyclerView);
    }

    @Override
    protected WeChatListPresenter createPresenter() {
        return new WeChatListPresenter();
    }

    @Override
    public void loadWeChatList(WeChatListData weChatListData) {
        mWeChatListAdapter.setBeans(weChatListData);
        mRecyclerView.setAdapter(mWeChatListAdapter);
    }

    @Override
    public void refreshWeChatList(WeChatListData weChatListData) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
