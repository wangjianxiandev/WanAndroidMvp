package com.wjx.android.wanandroidmvp.contract.project;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.bean.db.Article;
import com.wjx.android.wanandroidmvp.bean.db.ProjectClassify;


import java.util.List;

import io.reactivex.Observable;


/**
 * Created with Android Studio.
 * Description: 项目契约类
 *
 * @author: 王拣贤
 * @date: 2019/12/27
 * Time: 14:43
 */
public class Contract {

    /**
     * ProjectTab
     */
    public interface IProjectModel{
        /**
         * 获取项目Tab数据
         * @return banner数据
         */
        Observable<List<ProjectClassify>> loadProjectClassify();

        /**
         * 刷新项目类别
         * @return
         */
        Observable<List<ProjectClassify>> refreshProjectClassify();
    }

    public interface IProjectView extends IBaseView {
        /**
         * 获取项目Tab据进行显示
         * @param projectClassifies
         */
        void onLoadProjectClassify(List<ProjectClassify> projectClassifies);

        void onRefreshProjectClassify(List<ProjectClassify> projectClassifies);
    }

    public interface IProjectPresenter{
        /**
         * 项目Tab
         */
        void loadProjectClassify();

        void refreshProjectClassify();

    }

    /**
     * ProjectList
     */

    public interface IProjectListModel{
        /**
         * 获取项目数据
         * @return 文章数据
         */
        Observable<List<Article>> loadProjectList(int pageNum, int cid);

        /**
         * 刷新项目列表
         * @return
         */
        Observable<List<Article>> refreshProjectList(int pageNum, int cid);

        Observable<Collect> collect(int articleId);

        Observable<Collect> unCollect(int articleId);
    }

    public interface IProjectListView extends IBaseView {

        /**
         * 获取项目数据进行显示
         * @param projectListData
         */
        void onLoadProjectList(List<Article> projectListData);

        /**
         * 刷新项目列表
         * @param projectListData
         */
        void onRefreshProjectList(List<Article> projectListData);

        void onCollect(Collect collect, int articleId);

        void onUnCollect(Collect collect, int articleId);
    }

    public interface IProjectListPresenter{

        /**
         * 项目列表
         */
        void loadProjectList(int pageNum, int cid);

        /**
         * 刷新项目列表
         */
        void refreshProjectList(int pageNum, int cid);

        void collect(int articleId);

        void unCollect(int articleId);
    }
}
