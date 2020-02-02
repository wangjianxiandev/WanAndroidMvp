package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
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
import com.bumptech.glide.Glide;
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
 * @author: Wangjianxian
 * @date: 2019/12/27
 * Time: 18:03
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectListHolder> {

    private Context mContext;

    private List<Article> mProjectList = new ArrayList<>();

    /**
     * 是否为夜间模式
     */
    private boolean isNightMode;

    public void setProjectList(List<Article> projectList) {
        mProjectList.clear();
        mProjectList.addAll(projectList);
        notifyDataSetChanged();
    }

    public ProjectListAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mProjectList.addAll(articleList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public ProjectListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.project_item, parent, false);
        return new ProjectListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectListHolder holder, int position) {
        if (mProjectList != null) {
            Article bean = mProjectList.get(position);
            holder.mProjectTitle.setText(Html.fromHtml(bean.title, Html.FROM_HTML_MODE_COMPACT));

            holder.mProjectAuthor.setText(String.format(mContext.getResources().getString(R.string.article_author), bean.author));
            holder.mProjectDate.setText(bean.niceDate);
            String category = String.format(mContext.getResources().getString(R.string.article_category),
                    bean.superChapterName, bean.chapterName);
            holder.mProjectType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
            holder.mProjectContent.setText(bean.desc);
            holder.mProjectImageView.setVisibility(View.VISIBLE);
            holder.mItemTop.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(bean.envelopePic).into(holder.mProjectImageView);
            holder.itemView.setOnClickListener(view -> JumpWebUtils.startWebView(mContext,
                    mProjectList.get(position).title,
                    mProjectList.get(position).link,
                    mProjectList.get(position).articleId,
                    mProjectList.get(position).collect));
            if (!LoginUtils.isLogin()) {
                holder.mCollectView.setSelected(false);
            } else {
                holder.mCollectView.setSelected(bean.collect);
            }

            holder.mCollectView.setOnClickListener(view -> {
                Constant.Vibrate(mContext, 50);
                if (!LoginUtils.isLogin()) {
                    Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    Event event = new Event();
                    event.target = Event.TARGET_PROJECT;
                    event.type = bean.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                    event.data = bean.articleId + ";" + bean.projectType;
                    EventBus.getDefault().post(event);
                }
            });

            holder.itemView.getBackground().setColorFilter(
                    mContext.getColor(isNightMode ? R.color.primary_grey_dark : R.color.card_bg), PorterDuff.Mode.SRC_ATOP);
            holder.mProjectDate.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mProjectType.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mProjectAuthor.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
            holder.mProjectContent.setTextColor(mContext.getColor(isNightMode ? R.color.card_bg : R.color.colorGray666));
        }

    }

    @Override
    public int getItemCount() {
        return mProjectList == null ? 0 : mProjectList.size();
    }

    class ProjectListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_project_author)
        TextView mProjectAuthor;
        @BindView(R.id.item_project_title)
        TextView mProjectTitle;
        @BindView(R.id.item_project_type)
        TextView mProjectType;
        @BindView(R.id.item_project_date)
        TextView mProjectDate;
        @BindView(R.id.item_project_imageview)
        ImageView mProjectImageView;
        @BindView(R.id.item_project_content)
        TextView mProjectContent;
        @BindView(R.id.item_list_collect)
        ImageView mCollectView;
        @BindView(R.id.item_project_top)
        TextView mItemTop;

        public ProjectListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
