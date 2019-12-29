package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.adapter.IAdapterItemAnim;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBeansNew;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description: 首页文章adapter
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 18:30
 */
public class ArticleAdapter extends RecyclerView.Adapter <ArticleAdapter.ArticleHolder> {

    /**
     * Item type
     */
    private static final int TYPE_HEADER = 0;

    private static final int TYPE_NORMAL = 1;

    private Context mContext;

    private List<ArticleBeansNew> mArticleBeansNew;

    private View mHeaderView;

    private RecyclerView mRecyclerView;

    private NestedScrollView mNestedScrollView;

    /**
     * RecyclerView是否向上滑动
     */
    private boolean isPullingUp = false;

    /**
     * RecyclerView是否向下滑动
     */
    private boolean isPullingDown = false;

    /**
     * item从上向下滑动入场动画
     */
    private Animation bottomInAnimation;

    /**
     * item从下向上滑动入场动画
     */
    private Animation topInAnimation;

    public ArticleAdapter(RecyclerView recyclerView) {
        mContext = recyclerView.getContext();
        mRecyclerView = recyclerView;
    }

    /**
     * 为上滑刷新做准备
     * @param articleBeansNew
     */
    public void setBeans(List<ArticleBeansNew> articleBeansNew) {
        mArticleBeansNew = articleBeansNew;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ArticleHolder(mHeaderView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final int realPosition = getRealPosition(holder);
        if (mArticleBeansNew != null) {
            ArticleBeansNew articleBean = mArticleBeansNew.get(realPosition);
            holder.mArticleContent.setText(
                    Html.fromHtml(articleBean.title, Html.FROM_HTML_MODE_COMPACT));

            if (articleBean.author != null) {
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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JumpWebUtils.startWebView(mContext,
                            mArticleBeansNew.get(realPosition).title,
                            mArticleBeansNew.get(realPosition).link);
                }
            });

        }
    }

    private int getRealPosition(ArticleHolder articleHolder) {
        int position = articleHolder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        if (mArticleBeansNew == null) {
            return 0;
        }
        return mHeaderView == null ? mArticleBeansNew.size() : mArticleBeansNew.size() + 1;
    }

    class ArticleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_author)
        TextView mArticleAuthor;
        @BindView(R.id.item_home_content)
        TextView mArticleContent;
        @BindView(R.id.item_article_type)
        TextView mArticleType;
        @BindView(R.id.item_home_date)
        TextView mArticleDate;

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            ButterKnife.bind(this, itemView);
        }
    }


}
