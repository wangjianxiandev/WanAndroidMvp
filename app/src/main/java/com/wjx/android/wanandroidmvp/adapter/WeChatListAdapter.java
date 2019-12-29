package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 11:59
 */
public class WeChatListAdapter extends RecyclerView.Adapter<WeChatListAdapter.WeChatListHolder> {

    private Context mContext;

    private List<WeChatListData.DataBean.DatasBean> mWeChatDataBeans;

    public WeChatListAdapter(RecyclerView recyclerView) {
        mContext = recyclerView.getContext();
    }

    public void setBeans(WeChatListData weChatListData) {
        mWeChatDataBeans = weChatListData.getData().getDatas();
    }
    @NonNull
    @Override
    public WeChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new WeChatListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeChatListHolder holder, int position) {
        if (mWeChatDataBeans!= null) {
            WeChatListData.DataBean.DatasBean bean = mWeChatDataBeans.get(position);
            holder.mWechatTitle.setText(Html.fromHtml(bean.getTitle(), Html.FROM_HTML_MODE_COMPACT));

            holder.mWeChatAuthor.setText(String.format(mContext.getResources().getString(R.string.article_author),bean.getAuthor()));
            holder.mWechatDate.setText(bean.getNiceDate());
            String category = String.format(mContext.getResources().getString(R.string.article_category),
                    bean.getSuperChapterName(), bean.getChapterName());
            holder.mWechatType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JumpWebUtils.startWebView(mContext,
                            mWeChatDataBeans.get(position).getTitle(),
                            mWeChatDataBeans.get(position).getLink());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mWeChatDataBeans != null ? mWeChatDataBeans.size() : 0;
    }

    class WeChatListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_author)
        TextView mWeChatAuthor;
        @BindView(R.id.item_home_content)
        TextView mWechatTitle;
        @BindView(R.id.item_article_type)
        TextView mWechatType;
        @BindView(R.id.item_home_date)
        TextView mWechatDate;

        public WeChatListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
