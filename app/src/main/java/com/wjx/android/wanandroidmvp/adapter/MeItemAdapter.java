package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/30
 * Time: 20:46
 */
public class MeItemAdapter extends RecyclerView.Adapter<MeItemAdapter.MeItemHolder> {
    private Context mContext;

    private HashMap<Integer, Integer> mItemIconMap;

    private ArrayList<String> mItemNameList;

    public MeItemAdapter(RecyclerView recyclerView, HashMap<Integer, Integer> hashMap, ArrayList<String> arrayList) {
        mContext = recyclerView.getContext();
        mItemIconMap = hashMap;
        mItemNameList = arrayList;

    }

    @NonNull
    @Override
    public MeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.me_item, parent, false);
        return new MeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeItemHolder holder, int position) {
        holder.mCardView.setCardBackgroundColor(Constant.randomColor());
        holder.mItemIcon.setImageResource(mItemIconMap.get(position));
        holder.mItemName.setTextColor(Color.BLACK);
        holder.mItemName.setText(mItemNameList.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, holder.mItemName.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemNameList.size();
    }

    class MeItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.item_icon)
        ImageView mItemIcon;

        @BindView(R.id.item_name)
        TextView mItemName;

        public MeItemHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
