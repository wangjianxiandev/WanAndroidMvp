package com.wjx.android.wanandroidmvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatListData;
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
 * @date: 2019/12/29
 * Time: 11:59
 */
public class WeChatListAdapter extends RecyclerView.Adapter<WeChatListAdapter.WeChatListHolder> {

    private Context mContext;

    private List<Article> mWeChatList = new ArrayList<>();

    /**
     * 是否为夜间模式
     */
    private boolean isNightMode;

    public WeChatListAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mWeChatList.addAll(articleList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    public void setWeChatList(List<Article> articleList) {
        mWeChatList.clear();
        mWeChatList.addAll(articleList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new WeChatListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeChatListHolder holder, int position) {
        if (mWeChatList != null) {
            Article bean = mWeChatList.get(position);
            holder.mWechatTitle.setText(Html.fromHtml(bean.title, Html.FROM_HTML_MODE_COMPACT));

            holder.mWeChatAuthor.setText(String.format(mContext.getResources().getString(R.string.article_author), bean.author));
            holder.mWechatDate.setText(bean.niceDate);
            String category = String.format(mContext.getResources().getString(R.string.article_category),
                    bean.superChapterName, bean.chapterName);
            holder.mWechatType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));

            if (bean.isFresh) {
                holder.mNewView.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JumpWebUtils.startWebView(mContext,
                            mWeChatList.get(position).title,
                            mWeChatList.get(position).link);
                }
            });

            if (!LoginUtils.isLogin()) {
                holder.mCollectView.setSelected(false);
            } else {
                holder.mCollectView.setSelected(bean.collect);
            }
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
                        event.target = Event.TARGET_WX;
                        event.type = bean.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                        event.data = bean.articleId + ";" + bean.authorId;
                        EventBus.getDefault().post(event);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mWeChatList != null ? mWeChatList.size() : 0;
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
        @BindView(R.id.item_list_collect)
        ImageView mCollectView;
        @BindView(R.id.item_home_new)
        TextView mNewView;

        public WeChatListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
