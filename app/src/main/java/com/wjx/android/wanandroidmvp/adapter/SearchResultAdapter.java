package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.ui.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/27
 * Time: 19:09
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {
    
    private Context mContext;
    
    private List<Article> mSearchResultList = new ArrayList<>();

    private boolean isNightMode;

    private static Pattern mPattern = Pattern.compile(Constant.REGEX);
    
    public void setSearchResultList(List<Article> searchResultList) {
        mSearchResultList.clear();
        mSearchResultList.addAll(searchResultList);
        notifyDataSetChanged();
    }
    
    public SearchResultAdapter(Context context, List<Article> searchResultList) {
        mContext = context;
        mSearchResultList.addAll(searchResultList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new SearchResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {
        if (mSearchResultList != null) {
            Article article = mSearchResultList.get(position);
            holder.mArticleContent.setText(
                    Html.fromHtml(article.title, Html.FROM_HTML_MODE_COMPACT));
            String highLightTitle = article.title;
            Matcher matcher = mPattern.matcher(highLightTitle);
            if (matcher.find()) {
                highLightTitle = highLightTitle.replace("<em class='highlight'>", "").replace("</em>", "");
                setText(holder.mArticleContent, highLightTitle, matcher.group(1),
                        isNightMode ? mContext.getColor(R.color.card_bg) : Constant.getColor(mContext));
            }
            if (!article.author.equals("")) {
                holder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                article.author));
            } else {
                holder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                getString(R.string.anonymous_user)));
            }
            holder.mArticleDate.setText(article.niceDate);
            String category = String.format(
                    mContext.getResources().getString(R.string.collect_category),
                    article.chapterName);
            holder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));

            holder.itemView.setOnClickListener(v -> JumpWebUtils.startWebView(mContext,
                    article.title,
                    article.link));

            holder.mQuestionView.setVisibility(View.VISIBLE);
            holder.mQuestionView.setText(R.string.search_hint);

            if (!LoginUtils.isLogin()) {
                holder.mCollectView.setSelected(false);
            } else {
                holder.mCollectView.setSelected(article.collect);
            }

            holder.mCollectView.setOnClickListener(v -> {
                if (!LoginUtils.isLogin()) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    Event event = new Event();
                    event.target = Event.TARGET_SEARCH_RESULT;
                    event.type = article.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                    event.data = article.articleId + "";
                    EventBus.getDefault().post(event);
                }
            });

            holder.itemView.getBackground().setColorFilter(
                    mContext.getColor(isNightMode ? R.color.primary_grey_dark : R.color.card_bg), PorterDuff.Mode.SRC_ATOP);
            holder.mArticleDate.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mArticleType.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mArticleAuthor.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));

        }
    }

    private void setText(TextView tv, String text, String key, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        // 第一个出现的索引位置
        int index = text.indexOf(key);
        while (index != -1) {
            builder.setSpan(new ForegroundColorSpan(color), index, index + key.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 从这个索引往后开始第一个出现的位置
            index = text.indexOf(key, index + 1);
        }
        tv.setText(builder);
    }

    @Override
    public int getItemCount() {
        return mSearchResultList != null ? mSearchResultList.size() : 0;
    }

    class SearchResultHolder extends RecyclerView.ViewHolder {


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
        @BindView(R.id.item_home_question)
        TextView mQuestionView;

        SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
