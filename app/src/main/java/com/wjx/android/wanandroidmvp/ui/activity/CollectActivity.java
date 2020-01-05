//package com.wjx.android.wanandroidmvp.ui.activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.wjx.android.wanandroidmvp.R;
//import com.wjx.android.wanandroidmvp.adapter.CollectAdaper;
//import com.wjx.android.wanandroidmvp.base.activity.BaseActivity;
//import com.wjx.android.wanandroidmvp.bean.base.Event;
//import com.wjx.android.wanandroidmvp.bean.collect.CollectBean;
//
//import com.wjx.android.wanandroidmvp.contract.collect.Contract;
//import com.wjx.android.wanandroidmvp.presenter.collect.CollectPresenter;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//
//import butterknife.BindView;
//
//public class CollectActivity extends BaseActivity<Contract.ICollectView, CollectPresenter> implements Contract.ICollectView {
//
//    private CollectAdaper mArticleAdapter;
//    private int mCurpage = 0;
//
//
//    @BindView(R.id.article_recycler)
//    RecyclerView mRecyclerView;
//
//    @BindView(R.id.refresh_layout)
//    SmartRefreshLayout mSmartRefreshLayout;
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_collect;
//    }
//
//    @Override
//    protected void init(Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
//        initAdapter();
//        // 滑动流畅
//        mRecyclerView.setNestedScrollingEnabled(false);
////        mPresenter.Collect(mCurpage);
//
//    }
//    private void initAdapter() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mArticleAdapter = new CollectAdaper(mRecyclerView);
//    }
//
//
//    @Override
//    protected CollectPresenter createPresenter() {
//        return new CollectPresenter();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_collect);
//    }
//
//    @Override
//    public void loadCollect(CollectBean CollectBean) {
//        mArticleAdapter.setBeans(CollectBean);
//        mRecyclerView.setAdapter(mArticleAdapter);
//    }
//
//    @Override
//    public void onError(Throwable e) {
//
//    }
//
//    @Override
//    public void onComplete() {
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(Event event) {
//        if (event.target == Event.TARGET_COLLECT) {
//            if (event.type == Event.TYPE_UNCOLLECT) {
//                int articleId = Integer.valueOf(event.data.split(";")[0]);
//                int originId = Integer.valueOf(event.data.split(";")[1]);
////                mPresenter.unCollect(articleId, originId);
////                startAnim();
//            }
//        }
//    }
//}
