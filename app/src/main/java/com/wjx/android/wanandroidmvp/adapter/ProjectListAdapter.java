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

import com.bumptech.glide.Glide;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListDataNew;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/27
 * Time: 18:03
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectListHolder> {

    private Context mContext;

    private List<ProjectListDataNew> mProjectListDataBeans;

    public void setBeans(List<ProjectListDataNew> projectListData) {
        mProjectListDataBeans = projectListData;
    }

    public ProjectListAdapter(RecyclerView recyclerView) {
        mContext = recyclerView.getContext();
    }
    @NonNull
    @Override
    public ProjectListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.project_item, parent, false);
        return new ProjectListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectListHolder holder, int position) {
        if (mProjectListDataBeans !=  null) {
            ProjectListDataNew bean = mProjectListDataBeans.get(position);
            holder.mProjectTitle.setText(Html.fromHtml(bean.title, Html.FROM_HTML_MODE_COMPACT));

            holder.mProjectAuthor.setText(String.format(mContext.getResources().getString(R.string.article_author),bean.author));
            holder.mProjectDate.setText(bean.niceDate);
            String category = String.format(mContext.getResources().getString(R.string.article_category),
                    bean.superChapterName, bean.chapterName);
            holder.mProjectType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
            holder.mProjectContent.setText(bean.desc);
            Glide.with(mContext).load(bean.envelopePic).into(holder.mProjectImageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JumpWebUtils.startWebView(mContext,
                            mProjectListDataBeans.get(position).title,
                            mProjectListDataBeans.get(position).link);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mProjectListDataBeans == null ? 0 : mProjectListDataBeans.size();
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

        public ProjectListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
