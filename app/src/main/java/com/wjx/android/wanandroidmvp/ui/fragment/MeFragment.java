package com.wjx.android.wanandroidmvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wjx.android.wanandroidmvp.R;
import com.wjx.android.wanandroidmvp.adapter.MeItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/26
 * Time: 20:13
 */
public class MeFragment extends Fragment {

    private MeItemAdapter mMeItemAdapter;

    private HashMap<Integer, Integer> mItemIconMap = new HashMap<>();

    private ArrayList<String> mItemNameList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_item);
        init(recyclerView);
        return view;
    }

    private void init(RecyclerView recyclerView) {
        initAdapter(recyclerView);
    }

    private void initAdapter(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(
                new GridLayoutManager(getContext(), 2));
        initData();
        mMeItemAdapter = new MeItemAdapter(recyclerView, mItemIconMap, mItemNameList);
        recyclerView.setAdapter(mMeItemAdapter);
    }
    private void initData() {
        mItemIconMap.put(0, R.drawable.jifen);
        mItemIconMap.put(1, R.drawable.collect);
        mItemIconMap.put(2, R.drawable.wenzhang);
        mItemIconMap.put(3, R.drawable.renwu);
        mItemIconMap.put(4, R.drawable.web);
        mItemIconMap.put(5, R.drawable.shezhi);
        mItemNameList.add("我的积分");
        mItemNameList.add("我的收藏");
        mItemNameList.add("我的文章");
        mItemNameList.add("待办清单");
        mItemNameList.add("开源网站");
        mItemNameList.add("系统设置");
    }
}
