package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                holder.mTodoStatus.setImageResource(isNightMode? R.drawable.todo_done_night : R.drawable.todo_done);
                holder.mTodoCardView.setForeground(mContext.getDrawable(R.drawable.todo_foreground));
            } else {
                holder.mTodoStatus.setVisibility(View.VISIBLE);
                holder.mTodoStatus.setImageResource(isNightMode? R.drawable.todo_not_done_night : R.drawable.todo_not_done);
                holder.mTodoCardView.setForeground(mContext.getDrawable(R.drawable.todo_foreground));
            }
            holder.itemView.getBackground().setColorFilter(
                    mContext.getColor(isNightMode ? R.color.primary_grey_dark : R.color.card_bg), PorterDuff.Mode.SRC_ATOP);
            holder.mTodoDate.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mTodoTitle.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mTodoContent.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mTodoMore.setImageResource(isNightMode ? R.drawable.todo_more_night : R.drawable.todo_more);
        }
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

        TodoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
