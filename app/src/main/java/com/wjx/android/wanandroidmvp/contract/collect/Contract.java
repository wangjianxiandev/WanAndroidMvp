package com.wjx.android.wanandroidmvp.contract.collect;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.collect.CollectBean;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/01
 * Time: 22:37
 */
public class Contract {
    public interface ICollectModel {
        Observable<CollectBean> loadCollect(int pageNum);

    }

    public interface ICollectView extends IBaseView {
        void loadCollect (CollectBean CollectBean);
    }

    public interface ICollectPresenter {
        void loadCollect (int pageNum);
    }
}
