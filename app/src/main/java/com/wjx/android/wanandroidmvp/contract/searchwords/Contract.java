package com.wjx.android.wanandroidmvp.contract.searchwords;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.searchwords.SearchWordData;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 18:34
 */
public class Contract {

    public interface ISearchModel {
        /**
         * 获取热词数据
         * @return
         */
        Observable<SearchWordData> loadSearchWordData();
    }

    public interface ISearchView extends IBaseView {
        /**
         * 获取数据后显示
         * @param searchWordData
         */
        void loadSearchWordData (SearchWordData searchWordData);
    }

    public interface ISearchPresenter {
        /**
         * presenter接口实现model和view通信
         */
        void loadSearchWordData();
    }
}
