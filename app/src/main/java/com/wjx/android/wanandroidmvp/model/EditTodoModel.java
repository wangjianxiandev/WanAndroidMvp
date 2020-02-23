package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;
import com.wjx.android.wanandroidmvp.contract.edittodo.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/02/23
 * Time: 12:41
 */
public class EditTodoModel extends BaseModel implements Contract.IEditTodoModel {
    public EditTodoModel(){
        setCookies(false);
    }

    @Override
    public Observable<Todo> addTodo(String title, String content, String date, int type, int priority) {
        return mApiServer.addTodo(title, content, date, type, priority)
                .filter(addTodo -> addTodo.getErrorCode() == Constant.SUCCESS)
                .map(addTodo -> {
                    Todo todo = new Todo();
                    todo.id = addTodo.getData().getId();
                    todo.completeDate = addTodo.getData().getId();
                    todo.content = addTodo.getData().getContent();
                    todo.title = addTodo.getData().getTitle();
                    todo.priority = addTodo.getData().getPriority();
                    todo.status = addTodo.getData().getStatus();
                    todo.type = addTodo.getData().getType();
                    todo.date = addTodo.getData().getDate();
                    todo.dateStr = addTodo.getData().getDateStr();
                    return todo;
                });
    }

    @Override
    public Observable<Todo> updateTodo(int id, String title, String content, String date, int type, int priority) {
        return mApiServer.updateTodo(id,title, content, date, type, priority)
                .filter(updateTodo -> updateTodo.getErrorCode() == Constant.SUCCESS)
                .map(updateTodo -> {
                    Todo todo = new Todo();
                    todo.id = updateTodo.getData().getId();
                    todo.completeDate = updateTodo.getData().getId();
                    todo.content = updateTodo.getData().getContent();
                    todo.title = updateTodo.getData().getTitle();
                    todo.priority = updateTodo.getData().getPriority();
                    todo.status = updateTodo.getData().getStatus();
                    todo.type = updateTodo.getData().getType();
                    todo.date = updateTodo.getData().getDate();
                    todo.dateStr = updateTodo.getData().getDateStr();
                    return todo;
                });
    }
}
