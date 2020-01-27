package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.bean.square.TreeData;
import com.wjx.android.wanandroidmvp.contract.square.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/08
 * Time: 11:11
 */
public class TreeModel extends BaseModel implements Contract.ITreeModel {
    public TreeModel() {
        setCookies(false);
    }
    @Override
    public Observable<TreeData> loadTreeData() {
        return mApiServer.loadTreeData();
    }
}
