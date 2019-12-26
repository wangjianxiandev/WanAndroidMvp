package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBeansNew;

import org.w3c.dom.Text;

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
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    private Context mContext;

    private List<ArticleBeansNew> mArticleBeansNew;

    public ArticleAdapter(RecyclerView recyclerView) {
        mContext = recyclerView.getContext();
    }

    public void setBeans(List<ArticleBeansNew> articleBeansNew) {
        mArticleBeansNew = articleBeansNew;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        if (mArticleBeansNew != null) {
            ArticleBeansNew articleBean = mArticleBeansNew.get(position);
            holder.mArticleContent.setText(
                Html.fromHtml(articleBean.title, Html.FROM_HTML_MODE_COMPACT));

            if (articleBean.author == null && articleBean.shareUser == null) {
                holder.mArtivcleAuthor.setText(
                    String.format(mContext.getResources().getString(R.string.article_author),
                    mContext.getResources().getString(R.string.anonymous_user)));
            } else if (articleBean.author == null) {
                holder.mArtivcleAuthor.setText(
                    String.format(mContext.getResources().getString(R.string.article_author),
                    articleBean.shareUser));
            } else{
                holder.mArtivcleAuthor.setText(
                    String.format(mContext.getResources().getString(R.string.article_author),
                    articleBean.author));
            }

            holder.mArticleDate.setText(articleBean.niceDate);
            String category = String.format(
                mContext.getResources().getString(R.string.article_category),
                articleBean.origin, articleBean.chapterName);
            holder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JumpWebUtils.startWebView(mContext,
                            mArticleBeansNew.get(position).title,
                            mArticleBeansNew.get(position).link);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mArticleBeansNew != null ? mArticleBeansNew.size() : 0;
    }

    class ArticleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_author)
        TextView mArtivcleAuthor;
        @BindView(R.id.item_home_content)
        TextView mArticleContent;
        @BindView(R.id.item_article_type)
        TextView mArticleType;
        @BindView(R.id.item_home_date)
        TextView mArticleDate;

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
