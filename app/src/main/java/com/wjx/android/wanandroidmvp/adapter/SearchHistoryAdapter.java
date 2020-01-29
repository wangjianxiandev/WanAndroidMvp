package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.ui.activity.SearchResultActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/29
 * Time: 12:06
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder> {

    private Context mContext;
    private List<String> mSearchHistoryList = new ArrayList<>();

    private boolean isNightMode;

    public SearchHistoryAdapter(Context context, List<String> searchHistoryList) {
        mContext = context;
        mSearchHistoryList.addAll(searchHistoryList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    public void setSearchHistoryList(List<String> searchHistoryList) {
        mSearchHistoryList.clear();
        mSearchHistoryList.addAll(searchHistoryList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SearchHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_history_item, parent, false);
        return new SearchHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryHolder holder, int position) {
        holder.mHistoryText.setText(mSearchHistoryList.get(position));
        holder.mHistoryDelete.setOnClickListener(v -> {
            Constant.deleteSearchHistory(mSearchHistoryList.get(position), mContext);
            Event event = new Event();
            event.target = Event.TARGET_SEARCH;
            event.type = Event.TYPE_DELETE_SEARCH;
            event.data = position + "";
            EventBus.getDefault().post(event);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SearchResultActivity.class);
            intent.putExtra(Constant.KEY_KEYWORD, mSearchHistoryList.get(position));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
        holder.mHistoryText.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
        holder.mHistoryDelete.setImageDrawable(mContext.getDrawable(isNightMode ? R.drawable.ic_delete_night : R.drawable.ic_delete));
    }

    @Override
    public int getItemCount() {
        return mSearchHistoryList != null ? mSearchHistoryList.size() : 0;
    }

    class SearchHistoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_history_text)
        TextView mHistoryText;
        @BindView(R.id.item_history_delete)
        ImageView mHistoryDelete;

        public SearchHistoryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
