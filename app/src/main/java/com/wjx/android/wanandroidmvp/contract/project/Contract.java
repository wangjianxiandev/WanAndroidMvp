package com.wjx.android.wanandroidmvp.contract.project;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.project.ProjectClassifyData;
import com.wjx.android.wanandroidmvp.bean.project.ProjectListData;

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
    public interface IProjectModel{
        /**
         * 获取项目Tab数据
         * @return banner数据
         */
        Observable<ProjectClassifyData> loadProjectClassify();

        /**
         * 获取项目数据
         * @return 文章数据
         */
        Observable<List<ProjectListData>> loadProjectList(int pageNum, int cid);

        /**
         * 刷新项目列表
         * @return
         */
        Observable<List<ProjectListData>> refreshProjectList();
    }

    public interface IProjectView extends IBaseView {
        /**
         * 获取项目Tab据进行显示
         * @param projectClassifyData
         */
        void loadProjectClassify(ProjectClassifyData projectClassifyData);

        /**
         * 获取项目数据进行显示
         * @param projectListData
         */
        void loadProjectList(List<ProjectListData> projectListData);

        /**
         * 刷新项目列表
         * @param projectListData
         */
        void refreshProjectList(List<ProjectListData> projectListData);
    }

    public interface IProjectPresenter{
        /**
         * 项目Tab
         */
        void loadProjectClassify();

        /**
         * 项目列表
         */
        void loadProjectList(int pageNum, int cid);

        /**
         * 刷新项目列表
         */
        void refreshProjectList();
    }
}
