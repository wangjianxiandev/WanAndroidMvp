package com.wjx.android.wanandroidmvp.contract.square;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 14:52
 */
public class Contract {

    public interface ISquareModel {
        /**
         * 导航数据
         */
        Observable<NavigationData> loadNavigation();
    }


    public interface ISquareView extends IBaseView {
        /**
         * 加载导航数据
         */
        void loadNavigation(NavigationData navigationData);
    }


    public interface ISquarePresenter {
        /**
         * 加载导航数据
         */
        void loadNavigation();
    }
}
