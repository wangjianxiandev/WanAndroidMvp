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
import com.wjx.android.wanandroidmvp.bean.collect.CollectBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/01
 * Time: 22:50
 */
public class CollectAdaper extends RecyclerView.Adapter<CollectAdaper.CollectHolder> {

    private Context mContext;
    private List<CollectBean.DataBean.DatasBean> mBeans;

    public void setBeans(CollectBean beans) {
        mBeans = beans.getData().getDatas();
    }

    public CollectAdaper(RecyclerView recyclerView) {
        mContext = recyclerView.getContext();
    }

    @NonNull
    @Override
    public CollectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new CollectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectHolder collectHolder, int i) {

        if (mBeans != null) {
            CollectBean.DataBean.DatasBean bean = mBeans.get(i);
            collectHolder.mArticleContent.setText(
                    Html.fromHtml(bean.getTitle(), Html.FROM_HTML_MODE_COMPACT));

            if (bean.getAuthor() != null) {
                collectHolder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                bean.getAuthor()));
            }

            collectHolder.mArticleDate.setText(bean.getNiceDate());
            String category = String.format(
                    mContext.getResources().getString(R.string.article_category),
                    bean.getChapterName(), bean.getChapterName());
            collectHolder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
        }
    }

    @Override
    public int getItemCount() {


        return mBeans != null ? mBeans.size() : 0;
    }

    class CollectHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_home_author)
        TextView mArticleAuthor;
        @BindView(R.id.item_home_content)
        TextView mArticleContent;
        @BindView(R.id.item_article_type)
        TextView mArticleType;
        @BindView(R.id.item_home_date)
        TextView mArticleDate;
        @BindView(R.id.item_list_collect)
        ImageView mCollectView;

        CollectHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}