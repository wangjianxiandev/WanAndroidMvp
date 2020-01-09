package com.wjx.android.wanandroidmvp.contract.square;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.bean.square.NavigationData;
import com.wjx.android.wanandroidmvp.bean.square.TreeData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableAll;

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

    public interface ITreeModel {
        /**
         * 体系数据
         *
         * @return
         */
        Observable<TreeData> loadTreeData();
    }

    public interface ITreeView extends IBaseView {
        /**
         * 加载体系数据
         *
         * @param treeData
         */
        void loadTreeData(TreeData treeData);
    }

    public interface ITreePresenter {
        /**
         * 加载体系数据
         */
        void loadTree();
    }

    public interface ITreeListModel {
        /**
         * 加载体系文章
         *
         * @param pageNum
         * @param cid
         * @return
         */
        Observable<List<Article>> loadTreeList(int pageNum, int cid);

        /**
         * 刷新体系文章
         *
         * @param pageNum
         * @param cid
         * @return
         */
        Observable<List<Article>> refreshTreeList(int pageNum, int cid);

        /**
         * 收藏体系文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏体系文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);
    }

    public interface ITreeListView extends IBaseView {
        /**
         * 加载体系文章
         *
         * @param treeListData
         */
        void onLoadTreeList(List<Article> treeListData);

        /**
         * 刷新体系文章
         *
         * @param treeListData
         */
        void onRefreshTreeList(List<Article> treeListData);

        /**
         * 收藏体系文章
         *
         * @param collect
         * @param articleId
         */
        void onCollect(Collect collect, int articleId);

        /**
         * 取消收藏体系文章
         *
         * @param collect
         * @param articleId
         */
        void onUnCollect(Collect collect, int articleId);
    }

    public interface ITreeListPresenter {

        /**
         * 加载体系列表
         */
        void loadTreeList(int pageNum, int cid);

        /**
         * 刷新体系列表
         */
        void refreshTreeList(int pageNum, int cid);

        void collect(int articleId);

        void unCollect(int articleId);
    }

    public interface IHomeSquareModel {
        /**
         * 加载广场首页数据
         *
         * @param pageNum
         * @return
         */
        Observable<List<Article>> loadHomeSquareData(int pageNum);

        /**
         * 刷新广场数据
         *
         * @param pageNum
         * @return
         */
        Observable<List<Article>> refreshHomeSquareData(int pageNum);

        /**
         * 收藏广场文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏广场文章
         *
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);
    }

    public interface IHomeSquareView extends IBaseView {
        void loadHomeSquareData(List<Article> homeSquareData);

        void refreshHomeSquareData(List<Article> homeSquareData);

        void onCollect(Collect collect, int articleId);

        void onUnCollect(Collect collect, int articleId);
    }

    public interface IHomeSquarePresenter {
        void loadHomeSquareData(int pageNum);

        void refreshHomeSquareData(int pageNum);

        void collect(int articleId);

        void unCollect(int articleId);
    }
}
