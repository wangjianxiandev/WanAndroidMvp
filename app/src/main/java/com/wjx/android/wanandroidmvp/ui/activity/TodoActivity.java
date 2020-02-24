package com.wjx.android.wanandroidmvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wjx.android.wanandroidmvp.Custom.loading.LoadingView;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.TodoAdapter;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.Utils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.todo.DeleteTodo;
import com.wjx.android.wanandroidmvp.bean.todo.FinishTodo;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;
import com.wjx.android.wanandroidmvp.contract.todo.Contract;
import com.wjx.android.wanandroidmvp.presenter.todo.TodoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class TodoActivity extends BaseActivity<Contract.ITodoView, TodoPresenter> implements Contract.ITodoView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private TodoAdapter mTodoAdapter;
    private int mCurrentPage = 1;
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
        mContext = this;
        mPresenter.loadTodo(mCurrentPage);
        initAdapter();
        initToolbar();
        initStatusBar();
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Utils.getColor(mContext));
        }
        if (ColorUtils.calculateLuminance(Utils.getColor(mContext)) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.todo_page);
        mToolbar.getBackground().setColorFilter(
                Utils.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mTodoAdapter = new TodoAdapter(mContext, mTodoList);
        mRecyclerView.setAdapter(mTodoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collect_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_collect:
                Intent intent = new Intent(TodoActivity.this, EditTodoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constant.KEY_TODO_HANDLE_TYPE, Constant.ADD_TODO);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected TodoPresenter createPresenter() {
        return new TodoPresenter();
    }

    @Override
    public void onLoadTodo(List<Todo> todoList) {
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
                ToastUtils.showShort("已删除");
            } else {
                ToastUtils.showShort("删除失败");
            }
        }
    }


    @Override
    public void onFinishTodo(FinishTodo todo, int id) {
        if (todo != null) {
            if (todo.getErrorCode() == Constant.SUCCESS) {
                ToastUtils.showShort("已完成");
            } else {
                ToastUtils.showShort("请求失败");
            }
        }
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
        mLoadingView.setVisibility(View.GONE);
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
        mCurrentPage = 1;
        mPresenter.refreshTodo(mCurrentPage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_TODO) {
            if (event.type == Event.TYPE_REFRESH_TODO) {
                onRefresh(mSmartRefreshLayout);
            } else if (event.type == Event.TYPE_DELETE_TODO) {
                int todoId = Integer.valueOf(event.data);
                List<Todo> tempList = mTodoList.stream().filter(todo -> todo.id != todoId).collect(Collectors.toList());
                mTodoAdapter.setTodoList(tempList);
                mPresenter.deleteTodo(todoId);
            } else if (event.type == Event.TYPE_FINISH_TODO) {
                int todoId = Integer.valueOf(event.data);
                mTodoList.stream().filter(todo -> todo.id == todoId).findFirst().get().status = 1;
                mTodoAdapter.notifyDataSetChanged();
                mPresenter.finishTodo(todoId, 1);
            }
        }
    }
}
