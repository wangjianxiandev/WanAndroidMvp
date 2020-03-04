package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.wjx.android.wanandroidmvp.Custom.loading.LoadingView;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.Utils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;
import com.wjx.android.wanandroidmvp.contract.edittodo.Contract;
import com.wjx.android.wanandroidmvp.presenter.todo.EditTodoPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTodoActivity extends BaseActivity<Contract.IEditTodoView, EditTodoPresenter> implements Contract.IEditTodoView {

    @BindView(R.id.edit_todo_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.edit_todo_title)
    EditText mEditTitle;

    @BindView(R.id.edit_todo_content)
    EditText mEditContent;

    @BindView(R.id.edit_todo_date)
    TextView mEditDate;

    @BindView(R.id.edit_todo_priority)
    TextView mEditPriority;

    @BindView(R.id.edit_todo_type)
    TextView mEditType;

    @BindView(R.id.edit_todo_submit)
    Button mEditSubmit;

    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    private Context mContext;

    private String mTitle;

    private String mContent;

    private String mDateStr;

    private int mPriority;

    private int mTodoId;

    private int mTodoType;

    private int mActivityType;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_todo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initIntent();
        initColor();
        initToolbar();
    }

    private void initIntent() {
        mActivityType = Integer.valueOf(getIntent().getStringExtra(Constant.KEY_TODO_HANDLE_TYPE));
        if (mActivityType == Integer.valueOf(Constant.EDIT_TODO)) {
            mTitle = getIntent().getStringExtra(Constant.KEY_TODO_TITLE);
            mContent = getIntent().getStringExtra(Constant.KEY_TODO_CONTENT);
            mDateStr = getIntent().getStringExtra(Constant.KEY_TODO_DATE);
            mPriority = Integer.valueOf(getIntent().getStringExtra(Constant.KEY_TODO_PRIORITY));
            mTodoId = Integer.valueOf(getIntent().getStringExtra(Constant.KEY_TODO_ID));
            mTodoType = Integer.valueOf(getIntent().getStringExtra(Constant.KEY_TODO_TYPE));
            mEditTitle.setText(mTitle);
            mEditContent.setText(mContent);
            mEditDate.setText(mDateStr);
            mEditType.setText(mTodoType == 1 || mTodoType == 0 ? R.string.todo_work : R.string.todo_study);
            mEditPriority.setText(mPriority == 1 ? R.string.todo_piority_important : R.string.todo_piority_normal);
        }
    }

    private void initToolbar() {
        mToolbar.setTitle(mActivityType == Integer.valueOf(Constant.ADD_TODO) ? R.string.add_todo : R.string.edit_todo);
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

    private void initColor() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitleTextColor(getColor(R.color.white));
        mEditSubmit.getBackground().setColorFilter(
                Utils.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
    }

    @OnClick(R.id.edit_todo_date)
    public void todoDateChoose() {
        // 日期选择器，月份加一
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) ->
                        mEditDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth),
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker();
        datePickerDialog.show();
    }

    @OnClick(R.id.edit_todo_priority)
    public void todoPriorityChoose() {
        // 底部弹出对话框
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_circle, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - Utils.dpToPx(this, 16);
        params.bottomMargin = Utils.dpToPx(this, 8);
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        contentView.findViewById(R.id.important).setOnClickListener(v -> {
            mPriority = Constant.TODO_IMPORTANT;
            mEditPriority.setText(R.string.todo_piority_important);
            bottomDialog.dismiss();
        });

        contentView.findViewById(R.id.normal).setOnClickListener(v -> {
            mPriority = Constant.TODO_NORMAL;
            mEditPriority.setText(R.string.todo_piority_normal);
            bottomDialog.dismiss();
        });
    }

    @OnClick(R.id.edit_todo_type)
    public void todoTypeChoose() {
        // 底部弹出对话框
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_circle, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - Utils.dpToPx(this, 16);
        params.bottomMargin = Utils.dpToPx(this, 8);
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        TextView work = contentView.findViewById(R.id.important);
        work.setText(R.string.todo_work);
        TextView study = contentView.findViewById(R.id.normal);
        study.setText(R.string.todo_study);
        contentView.findViewById(R.id.normal).setOnClickListener(v -> {
            mTodoType = Constant.TODO_STUDY;
            mEditType.setText(R.string.todo_study);
            bottomDialog.dismiss();
        });

        contentView.findViewById(R.id.important).setOnClickListener(v -> {
            mTodoType = Constant.TODO_WORK;
            mEditType.setText(R.string.todo_work);
            bottomDialog.dismiss();
        });
    }

    @OnClick(R.id.edit_todo_submit)
    public void todoSubmit() {
        if (mActivityType == Integer.valueOf(Constant.EDIT_TODO)) {
            mPresenter.updateTodo(
                    mTodoId,
                    mEditTitle.getText().toString(),
                    mEditContent.getText().toString(),
                    mEditDate.getText().toString(),
                    mTodoType,
                    mPriority);
        } else if (mActivityType == Integer.valueOf(Constant.ADD_TODO)) {
            mPresenter.addTodo(mEditTitle.getText().toString(),
                    mEditContent.getText().toString(),
                    mEditDate.getText().toString(),
                    mTodoType,
                    mPriority);
        }
    }

    @Override
    protected EditTodoPresenter createPresenter() {
        return new EditTodoPresenter();
    }

    @Override
    public void onAddTodo(Todo todo) {
    }

    @Override
    public void onUpdateTodo(Todo todo) {

    }

    @Override
    public void onLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startTranglesAnimation();
    }

    @Override
    public void onLoadFailed() {
        mLoadingView.setVisibility(View.GONE);
        ToastUtils.showShort("请求失败");
    }

    @Override
    public void onLoadSuccess() {
        mLoadingView.setVisibility(View.GONE);
        Event event = new Event();
        event.type = Event.TYPE_REFRESH_TODO;
        event.target = Event.TARGET_TODO;
        EventBus.getDefault().post(event);
        finish();
    }
}
