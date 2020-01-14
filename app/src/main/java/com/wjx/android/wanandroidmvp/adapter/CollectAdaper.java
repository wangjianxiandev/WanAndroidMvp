package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.base.utils.JumpWebUtils;
import com.wjx.android.wanandroidmvp.base.utils.LoginUtils;
import com.wjx.android.wanandroidmvp.bean.base.Event;
import com.wjx.android.wanandroidmvp.bean.db.Collect;
import com.wjx.android.wanandroidmvp.ui.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blankj.utilcode.util.StringUtils.getString;

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
    private List<Collect> mCollectList = new ArrayList<>();

    private boolean isNightMode;

    public void setCollectList(List<Collect> collectList) {
        mCollectList.clear();
        mCollectList.addAll(collectList);
        notifyDataSetChanged();
    }

    public CollectAdaper(Context context, List<Collect> collectList) {
        mContext = context;
        mCollectList.addAll(collectList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public CollectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.project_item, parent, false);
        return new CollectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectHolder collectHolder, int i) {

        if (mCollectList != null) {
            Collect collect = mCollectList.get(i);
            collectHolder.mArticleContent.setText(
                    Html.fromHtml(collect.title, Html.FROM_HTML_MODE_COMPACT));
            if (!collect.author.equals("")) {
                collectHolder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                collect.author));
            } else {
                collectHolder.mArticleAuthor.setText(
                        String.format(mContext.getResources().getString(R.string.article_author),
                                getString(R.string.anonymous_user)));
            }
            collectHolder.mArticleDate.setText(collect.niceDate);
            String category = String.format(
                    mContext.getResources().getString(R.string.collect_category),
                    collect.chapterName);
            collectHolder.mArticleType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
            if (collect.isSupportPic) {
                collectHolder.mItemImageView.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(collect.envelopePic).into(collectHolder.mItemImageView);
            } else {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) collectHolder.mArticleType.getLayoutParams();
                layoutParams.setMargins(0, 90, 0, 20);
                collectHolder.mArticleType.setLayoutParams(layoutParams);
            }
            collectHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JumpWebUtils.startWebView(mContext,
                            collect.title,
                            collect.link);
                }
            });
            collectHolder.mCollectView.setSelected(true);
            collectHolder.mCollectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!LoginUtils.isLogin()) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } else {
                        Event event = new Event();
                        event.target = Event.TARGET_COLLECT;
                        event.type = Event.TYPE_UNCOLLECT;
                        event.data = collect.articleId + ";" + collect.originId;
                        EventBus.getDefault().post(event);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {


        return mCollectList != null ? mCollectList.size() : 0;
    }

    class CollectHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_project_author)
        TextView mArticleAuthor;
        @BindView(R.id.item_project_title)
        TextView mArticleContent;
        @BindView(R.id.item_project_type)
        TextView mArticleType;
        @BindView(R.id.item_project_date)
        TextView mArticleDate;
        @BindView(R.id.item_list_collect)
        ImageView mCollectView;
        @BindView(R.id.item_project_imageview)
        ImageView mItemImageView;

        CollectHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}