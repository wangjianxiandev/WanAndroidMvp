package com.wjx.android.wanandroidmvp.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.Custom.loading.LoadingView;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.TodoAdapter;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.todo.DeleteTodo;
import com.wjx.android.wanandroidmvp.bean.todo.FinishTodo;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;
import com.wjx.android.wanandroidmvp.contract.todo.Contract;
import com.wjx.android.wanandroidmvp.presenter.todo.TodoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TodoActivity extends BaseActivity<Contract.ITodoView, TodoPresenter> implements Contract.ITodoView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private TodoAdapter mTodoAdapter;
    private int mCurrentPage = 0;
    private Context mContext;
    private List<Todo> mTodoList = new ArrayList<>();

    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.todo_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_todo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mPresenter.loadTodo(mCurrentPage);
        initAdapter();
        initToolbar();
        initStatusBar();
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
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

    private void initToolbar() {
        mToolbar.setBackgroundColor(Constant.getColor(mContext));
        mToolbar.setTitle(R.string.todo_page);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTodoAdapter = new TodoAdapter(mContext, mTodoList);
        mRecyclerView.setAdapter(mTodoAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_collect:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected TodoPresenter createPresenter() {
        return new TodoPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onLoadTodo(List<Todo> todoList) {
        mLoadingView.setVisibility(View.GONE);
        mTodoList.addAll(todoList);
        mTodoAdapter.setTodoList(mTodoList);
    }

    @Override
    public void onRefreshTodo(List<Todo> todoList) {
        mTodoList.clear();
        mTodoList.addAll(0, todoList);
        mTodoAdapter.setTodoList(mTodoList);
    }

    @Override
    public void onDeleteTodo(DeleteTodo todo, int id) {
        if (todo != null) {
            if (todo.getErrorCode() == Constant.SUCCESS) {
                Constant.showSnackMessage(this, "已删除");
            } else {
                ToastUtils.showShort("删除失败");
            }
        }
    }

    @Override
    public void onAddTodo(Todo todo) {
        mTodoList.add(0, todo);
        mTodoAdapter.setTodoList(mTodoList);
    }

    @Override
    public void onFinishTodo(FinishTodo todo, int id) {
        if (todo != null) {
            if (todo.getErrorCode() == Constant.SUCCESS) {
                mLoadingView.setVisibility(View.GONE);
            } else {
                ToastUtils.showShort("请求失败");
            }
        }
    }

    @Override
    public void onUpdateTodo(Todo todo) {
        mTodoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startTranglesAnimation();
    }

    @Override
    public void onLoadFailed() {
        mLoadingView.setVisibility(View.GONE);
        ToastUtils.showShort("加载失败");
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadSuccess() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadTodo(mCurrentPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 0;
        mPresenter.refreshTodo(mCurrentPage);
    }
}
