package com.wjx.android.wanandroidmvp.contract.wechat;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatClassifyData;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatListData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description: 公众号契约类
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 12:01
 */
public class Contract {
    /**
     * WeChatTab
     */
    public interface IWeChatModel{
        /**
         * 获取公众号Tab数据
         * @return banner数据
         */
        Observable<WeChatClassifyData> loadWeChatClassify();
    }

    public interface IWeChatView extends IBaseView {
        /**
         * 获取公众号Tab据进行显示
         * @param weChatClassifyData
         */
        void loadWeChatClassify(WeChatClassifyData weChatClassifyData);
    }

    public interface IWeChatPresenter{
        /**
         * 公众号Tab
         */
        void loadWeChatClassify();

    }

    /**
     * ProjectList
     */

    public interface IWeChatListModel{
        /**
         * 获取项目数据
         * @return 文章数据
         */
        Observable<WeChatListData> loadWeChatList(int cid, int pageNum);

        /**
         * 刷新项目列表
         * @return
         */
        Observable<WeChatListData> refreshWeChatList();
    }

    public interface IWeChatListView extends IBaseView {

        /**
         * 获取项目数据进行显示
         * @param weChatListData
         */
        void loadWeChatList(WeChatListData weChatListData);

        /**
         * 刷新项目列表
         * @param weChatListData
         */
        void refreshWeChatList(WeChatListData weChatListData);
    }

    public interface IWeChatListPresenter{

        /**
         * 项目列表
         */
        void loadWeChatList(int cid, int pageNum);

        /**
         * 刷新项目列表
         */
        void refreshWeChatList();
    }
}
