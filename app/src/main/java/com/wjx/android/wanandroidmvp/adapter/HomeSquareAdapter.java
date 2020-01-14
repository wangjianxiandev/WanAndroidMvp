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
 * Time: 16:37
 */
public class HomeSquareAdapter extends RecyclerView.Adapter<HomeSquareAdapter.HomeSquareListHolder> {

    private Context mContext;

    private List<Article> mHomeSquareList = new ArrayList<>();

    /**
     * 是否为夜间模式
     */
    private boolean isNightMode;

    public HomeSquareAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mHomeSquareList.addAll(articleList);
        isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).
                getBoolean(Constant.KEY_NIGHT_MODE, false);
    }

    public void setHomeSquareList(List<Article> articleList) {
        mHomeSquareList.clear();
        mHomeSquareList.addAll(articleList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeSquareListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new HomeSquareListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSquareListHolder holder, int position) {
        if (mHomeSquareList != null) {
            Article bean = mHomeSquareList.get(position);
            holder.mHomeSquareTitle.setText(Html.fromHtml(bean.title, Html.FROM_HTML_MODE_COMPACT));

            holder.mHomeSquareAuthor.setText(String.format(mContext.getResources().getString(R.string.article_author), bean.shareUser));
            holder.mHomeSquareDate.setText(bean.niceDate);
            String category = String.format(mContext.getResources().getString(R.string.article_category),
                    bean.superChapterName, bean.chapterName);
            holder.mHomeSquareType.setText(Html.fromHtml(category, Html.FROM_HTML_MODE_COMPACT));
            if (bean.isFresh) {
                holder.mNewView.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JumpWebUtils.startWebView(mContext,
                            mHomeSquareList.get(position).title,
                            mHomeSquareList.get(position).link);
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
                        event.target = Event.TARGET_SQUARE;
                        event.type = bean.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                        event.data = bean.articleId+"";
                        EventBus.getDefault().post(event);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHomeSquareList != null ? mHomeSquareList.size() : 0;
    }

    class HomeSquareListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_author)
        TextView mHomeSquareAuthor;
        @BindView(R.id.item_home_content)
        TextView mHomeSquareTitle;
        @BindView(R.id.item_article_type)
        TextView mHomeSquareType;
        @BindView(R.id.item_home_date)
        TextView mHomeSquareDate;
        @BindView(R.id.item_list_collect)
        ImageView mCollectView;
        @BindView(R.id.item_home_new)
        TextView mNewView;

        public HomeSquareListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
