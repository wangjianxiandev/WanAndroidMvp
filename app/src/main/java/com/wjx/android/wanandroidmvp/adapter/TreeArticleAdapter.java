package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.ui.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/09
 * Time: 11:56
 */
public class TreeArticleAdapter extends RecyclerView.Adapter<TreeArticleAdapter.TreeArticleHolder> {

    private Context mContext;

    private List<Article> mTreeArticleList = new ArrayList<>();

    private boolean isNightMode;



    public TreeArticleAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mTreeArticleList.addAll(articleList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    /**
     * 刷新数据
     *
     * @param articleList
     */
    public void setArticleList(List<Article> articleList) {
        mTreeArticleList.clear();
        mTreeArticleList.addAll(articleList);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public TreeArticleAdapter.TreeArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new TreeArticleAdapter.TreeArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeArticleAdapter.TreeArticleHolder holder, int position) {
        if (mTreeArticleList != null) {
            Article articleBean = mTreeArticleList.get(position);
            holder.mArticleContent.setText(
                    Html.fromHtml(articleBean.title, Html.FROM_HTML_MODE_COMPACT));
            if (!articleBean.author.equals("")) {
                holder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                articleBean.author));
            } else {
                holder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                articleBean.shareUser));
            }
            holder.mArticleDate.setText(articleBean.niceDate);
            String category = String.format(
                    mContext.getResources().getString(R.string.article_category),
                    articleBean.superChapterName, articleBean.chapterName);
            holder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));

            if (!LoginUtils.isLogin()) {
                holder.mCollectView.setSelected(false);
            } else {
                holder.mCollectView.setSelected(articleBean.collect);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JumpWebUtils.startWebView(mContext,
                            mTreeArticleList.get(position).title,
                            mTreeArticleList.get(position).link);
                }
            });
            holder.mCollectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!LoginUtils.isLogin()) {
                        Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } else {
                        Event event = new Event();
                        event.target = Event.TARGET_TREE;
                        event.type = articleBean.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                        event.data = articleBean.articleId + "";
                        EventBus.getDefault().post(event);
                    }

                }
            });

            if (isNightMode) {
                holder.itemView.setBackgroundColor(mContext.getColor(isNightMode ? R.color.primary_grey_dark : R.color.white));
                holder.mArticleDate.setTextColor(mContext.getColor(isNightMode ? R.color.white : R.color.colorBlack666));
                holder.mArticleType.setTextColor(mContext.getColor(isNightMode ? R.color.white : R.color.colorBlack666));
                holder.mArticleAuthor.setTextColor(mContext.getColor(isNightMode ? R.color.white : R.color.colorBlack666));
            }

        }
    }


    @Override
    public int getItemCount() {
        return mTreeArticleList == null ? 0 : mTreeArticleList.size();
    }

    class TreeArticleHolder extends RecyclerView.ViewHolder {
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

        public TreeArticleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
