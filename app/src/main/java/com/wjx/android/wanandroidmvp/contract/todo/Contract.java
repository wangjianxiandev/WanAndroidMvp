package com.wjx.android.wanandroidmvp.contract.todo;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.todo.DeleteTodo;
import com.wjx.android.wanandroidmvp.bean.todo.FinishTodo;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;
import com.wjx.android.wanandroidmvp.bean.todo.UpdateTodo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 21:25
 */
public class Contract {
    public interface ITodoModel {
        /**
         * 加载Todo列表
         *
         * @param pageNum
         * @return
         */
        Observable<List<Todo>> loadTodo(int pageNum);

        /**
         * 刷新Todo
         *
         * @param pageNum
         * @return
         */
        Observable<List<Todo>> refreshTodo(int pageNum);

        /**
         * 删除一个Todo
         *
         * @param id
         * @return
         */
        Observable<DeleteTodo> deleteTodo(int id);

        /**
         * 添加一个Todo
         *
         * @param title
         * @param content
         * @param date
         * @param type
         * @param priority
         * @return
         */
        Observable<Todo> addTodo(String title, String content, String date, int type, int priority);

        /**
         * 完成一个Todo
         *
         * @param id
         * @return
         */
        Observable<FinishTodo> finishTodo(int id, int status);

        /**
         * 更新一个Todo
         *
         * @param title
         * @param content
         * @param date
         * @param type
         * @param priority
         * @param id
         * @return
         */
        Observable<Todo> updateTodo(String title, String content, String date, int type, int priority, int id);
    }

    public interface ITodoView extends IBaseView {
        void onLoadTodo(List<Todo> todoList);

        void onRefreshTodo(List<Todo> todoList);

        void onDeleteTodo(DeleteTodo todo, int id);

        void onAddTodo(Todo todo);

        void onFinishTodo(FinishTodo todo, int id);

        void onUpdateTodo(Todo todo);
    }

    public interface ITodoPresenter {
        void loadTodo(int pageNum);

        void refreshTodo(int pageNum);

        void deleteTodo(int id);

        void addTodo(String title, String content, String date, int type, int priority);

        void finishTodo(int id, int status);

        void updateTodo(String title, String content, String date, int type, int priority, int id);
    }
}
