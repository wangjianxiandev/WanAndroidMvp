package com.wjx.android.wanandroidmvp.contract.edittodo;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.todo.DeleteTodo;
import com.wjx.android.wanandroidmvp.bean.todo.FinishTodo;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;

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
    public interface IEditTodoModel {
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
        Observable<Todo> updateTodo(int id, String title, String content, String date, int type, int priority);
    }

    public interface IEditTodoView extends IBaseView {

        void onAddTodo(Todo todo);

        void onUpdateTodo(Todo todo);
    }

    public interface IEditTodoPresenter {
        void addTodo(String title, String content, String date, int type, int priority);

        void updateTodo(int id, String title, String content, String date, int type, int priority);
    }
}
