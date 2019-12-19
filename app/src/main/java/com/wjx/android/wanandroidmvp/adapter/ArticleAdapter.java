package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBean;

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

    private List<ArticleBean.DataBean.DatasBean> mArticleBeans;

    public void setBeans(ArticleBean articleBean) {
        mArticleBeans = articleBean.getData().getDatas();
    }

    public ArticleAdapter(RecyclerView recyclerView) {
        mContext = recyclerView.getContext();
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        if (mArticleBeans != null) {
            ArticleBean.DataBean.DatasBean articleBean = mArticleBeans.get(position);
            holder.mArticleContent.setText(
                Html.fromHtml(articleBean.getTitle(), Html.FROM_HTML_MODE_COMPACT));

            if (articleBean.getAuthor() == null && articleBean.getShareUser() == null) {
                holder.mArtivcleAuthor.setText(
                    String.format(mContext.getResources().getString(R.string.article_author),
                    mContext.getResources().getString(R.string.anonymous_user)));
            } else if (articleBean.getAuthor() == null) {
                holder.mArtivcleAuthor.setText(
                    String.format(mContext.getResources().getString(R.string.article_author),
                    articleBean.getShareUser()));
            } else{
                holder.mArtivcleAuthor.setText(
                    String.format(mContext.getResources().getString(R.string.article_author),
                    articleBean.getAuthor()));
            }

            holder.mArticleDate.setText(articleBean.getNiceDate());
            String category = String.format(
                mContext.getResources().getString(R.string.article_category),
                articleBean.getOrigin(), articleBean.getChapterName());
            holder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
        }
    }

    @Override
    public int getItemCount() {
        return mArticleBeans != null ? mArticleBeans.size() : 0;
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
