package com.wjx.android.wanandroidmvp.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.wjx.android.wanandroidmvp.Custom.CustomScaleInterpolator;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.Utils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;
import com.wjx.android.wanandroidmvp.ui.activity.EditTodoActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 23:05
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private Context mContext;
    private List<Todo> mTodoList = new ArrayList<>();

    private boolean isNightMode;

    public void setTodoList(List<Todo> todoList) {
        mTodoList.clear();
        mTodoList.addAll(todoList);
        notifyDataSetChanged();
    }

    public TodoAdapter(Context context, List<Todo> todoList) {
        mContext = context;
        mTodoList.addAll(todoList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_todo, parent, false);
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        if (mTodoList != null) {
            Todo todo = mTodoList.get(position);
            holder.mTodoTitle.setText(
                    Html.fromHtml(todo.title, Html.FROM_HTML_MODE_COMPACT));
            holder.mTodoContent.setText(
                    Html.fromHtml(todo.content, Html.FROM_HTML_MODE_COMPACT));
            holder.mTodoDate.setText(
                    Html.fromHtml(todo.dateStr, Html.FROM_HTML_MODE_COMPACT));
            if (todo.status == 1) {
                holder.mTodoStatus.setVisibility(View.VISIBLE);
                holder.mTodoStatus.setImageResource(isNightMode ? R.drawable.todo_done_night : R.drawable.todo_done);
                holder.mTodoCardView.setForeground(mContext.getDrawable(R.drawable.todo_foreground));
            } else {
                if (todo.date < Utils.getNowTime().getTime()) {
                    holder.mTodoStatus.setVisibility(View.VISIBLE);
                    holder.mTodoStatus.setImageResource(isNightMode ? R.drawable.todo_not_done_night : R.drawable.todo_not_done);
                    holder.mTodoCardView.setForeground(mContext.getDrawable(R.drawable.todo_foreground));
                } else {
                    holder.mTodoStatus.setVisibility(View.GONE);
                    holder.mTodoCardView.setForeground(null);
                }
            }
            if (todo.priority == Constant.TODO_IMPORTANT) {
                holder.mTodoPriority.setText(R.string.todo_piority_important);
            } else {
                holder.mTodoPriority.setText(R.string.todo_piority_normal);
            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, EditTodoActivity.class);
                intent.putExtra(Constant.KEY_TODO_HANDLE_TYPE, Constant.EDIT_TODO);
                intent.putExtra(Constant.KEY_TODO_TITLE, todo.title);
                intent.putExtra(Constant.KEY_TODO_CONTENT, todo.content);
                intent.putExtra(Constant.KEY_TODO_DATE, todo.dateStr);
                intent.putExtra(Constant.KEY_TODO_PRIORITY, todo.priority + "");
                intent.putExtra(Constant.KEY_TODO_ID, todo.id + "");
                intent.putExtra(Constant.KEY_TODO_TYPE, todo.type + "");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });

            holder.mTodoMore.setOnClickListener(v -> {
                // 底部弹出对话框
                Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
                View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_handle_todo, null);
                bottomDialog.setContentView(contentView);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
                params.width = mContext.getResources().getDisplayMetrics().widthPixels - Utils.dpToPx(mContext, 16);
                params.bottomMargin = Utils.dpToPx(mContext, 8);
                contentView.setLayoutParams(params);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

                if (todo.status == 0) {
                    contentView.findViewById(R.id.done_todo).setVisibility(View.VISIBLE);
                }
                bottomDialog.show();
                // 点击对话框中编辑
                contentView.findViewById(R.id.edit_todo).setOnClickListener(v1 -> {
                    bottomDialog.dismiss();
                    Intent intent = new Intent(mContext, EditTodoActivity.class);
                    intent.putExtra(Constant.KEY_TODO_HANDLE_TYPE, Constant.EDIT_TODO);
                    intent.putExtra(Constant.KEY_TODO_TITLE, todo.title);
                    intent.putExtra(Constant.KEY_TODO_CONTENT, todo.content);
                    intent.putExtra(Constant.KEY_TODO_DATE, todo.dateStr);
                    intent.putExtra(Constant.KEY_TODO_PRIORITY, todo.priority + "");
                    intent.putExtra(Constant.KEY_TODO_ID, todo.id + "");
                    intent.putExtra(Constant.KEY_TODO_TYPE, todo.type + "");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });

                // 点击对话框删除
                contentView.findViewById(R.id.delete_todo).setOnClickListener(v1 -> {
                    Event event = new Event();
                    event.target = Event.TARGET_TODO;
                    event.type = Event.TYPE_DELETE_TODO;
                    event.data = todo.id + "";
                    EventBus.getDefault().post(event);
                    bottomDialog.dismiss();
                });

                // 点击完成该ToDo
                contentView.findViewById(R.id.done_todo).setOnClickListener(v1 -> {
                    Event event = new Event();
                    event.target = Event.TARGET_TODO;
                    event.type = Event.TYPE_FINISH_TODO;
                    event.data = todo.id + "";
                    EventBus.getDefault().post(event);
                    bottomDialog.dismiss();
                });
            });

            if (isNightMode) {
                holder.mTodoCardView.getBackground().setColorFilter(
                        mContext.getColor(R.color.primary_grey_dark), PorterDuff.Mode.SRC_ATOP);
            } else {
                GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BR_TL,
                        new int[]{Utils.evaluate(0.5f, Utils.randomColor(), Color.WHITE), Color.WHITE});
                holder.mViewGroup.setBackground(gradientDrawable);
            }
            holder.mTodoDate.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mTodoTitle.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mTodoContent.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mTodoMore.setImageResource(isNightMode ? R.drawable.todo_more_night : R.drawable.todo_more);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull TodoHolder holder) {
        super.onViewAttachedToWindow(holder);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.0f, 1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.0f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.setInterpolator(new CustomScaleInterpolator(0.4f));
        set.playTogether(animatorX, animatorY);
        set.start();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull TodoHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1.0f, 0.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 1.0f, 0.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.setInterpolator(new CustomScaleInterpolator(0.4f));
        set.playTogether(animatorX, animatorY);
        set.start();
    }

    @Override
    public int getItemCount() {
        return mTodoList != null ? mTodoList.size() : 0;
    }

    class TodoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_todo_title)
        TextView mTodoTitle;
        @BindView(R.id.item_todo_content)
        TextView mTodoContent;
        @BindView(R.id.item_todo_date)
        TextView mTodoDate;
        @BindView(R.id.item_todo_status)
        ImageView mTodoStatus;
        @BindView(R.id.item_todo_cardview)
        CardView mTodoCardView;
        @BindView(R.id.item_todo_more)
        ImageView mTodoMore;
        @BindView(R.id.constraint_view_group)
        ViewGroup mViewGroup;
        @BindView(R.id.item_todo_priority)
        TextView mTodoPriority;

        TodoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
