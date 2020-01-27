package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.db.Rank;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/13
 * Time: 11:20
 */
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankHolder> {

    private Context mContext;

    private List<Rank> mRankList = new ArrayList<>();

    private boolean isNightMode;

    public RankAdapter(Context context, List<Rank> rankList) {
        mContext = context;
        mRankList.addAll(rankList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    public void setRankList(List<Rank> rankList) {
        mRankList.clear();
        mRankList.addAll(rankList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rank_item, parent, false);
        return new RankHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankHolder holder, int position) {
        if (mRankList != null) {
            Rank rank = mRankList.get(position);
            holder.mCount.setText(
                    Html.fromHtml(String.valueOf(rank.coinCount), Html.FROM_HTML_MODE_COMPACT));
            holder.mName.setText(
                    Html.fromHtml(rank.username, Html.FROM_HTML_MODE_COMPACT));
            holder.mRank.setText(
                    Html.fromHtml(String.valueOf(rank.rank), Html.FROM_HTML_MODE_COMPACT));
        }
        holder.itemView.getBackground().setColorFilter(
                mContext.getColor(isNightMode ? R.color.primary_grey_dark : R.color.card_bg), PorterDuff.Mode.SRC_ATOP);
        holder.mCount.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
        holder.mName.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
        holder.mRank.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
    }

    @Override
    public int getItemCount() {
        return mRankList == null ? 0 : mRankList.size();
    }

    class RankHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.integral_rank)
        TextView mRank;
        @BindView(R.id.integral_name)
        TextView mName;
        @BindView(R.id.integral_count)
        TextView mCount;

        public RankHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
