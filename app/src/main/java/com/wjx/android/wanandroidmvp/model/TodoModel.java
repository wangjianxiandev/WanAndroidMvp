package com.wjx.android.wanandroidmvp.model;

import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.todo.DeleteTodo;
import com.wjx.android.wanandroidmvp.bean.todo.FinishTodo;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;
import com.wjx.android.wanandroidmvp.contract.todo.Contract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 21:47
 */
public class TodoModel extends BaseModel implements Contract.ITodoModel {
    public TodoModel() {
        setCookies(false);
    }
    @Override
    public Observable<List<Todo>> loadTodo(int pageNum) {
        Observable<List<Todo>> loadFromNet = loadTodoFromNet(pageNum);
        return loadFromNet;
    }

    private Observable<List<Todo>> loadTodoFromNet(int pageNum) {
        return mApiServer.loadTodoData(pageNum).filter(todoData -> todoData.getErrorCode() == Constant.SUCCESS)
                .map(todoData -> {
                    List<Todo> todoList = new ArrayList<>();
                    todoData.getData().getDatas().stream().forEach(datasBean -> {
                        Todo todo = new Todo();
                        todo.id = datasBean.getId();
                        todo.title = datasBean.getTitle();
                        todo.content = datasBean.getContent();
                        todo.dateStr = datasBean.getDateStr();
                        todo.priority = datasBean.getPriority();
                        todo.status = datasBean.getStatus();
                        todo.completeDate = datasBean.getCompleteDate();
                        todoList.add(todo);
                    });
                    return todoList;
                });
    }

    @Override
    public Observable<List<Todo>> refreshTodo(int pageNum) {
        return loadTodoFromNet(pageNum);
    }

    @Override
    public Observable<DeleteTodo> deleteTodo(int id) {
        return mApiServer.deleteTodo(id);
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
                    todo.dateStr = addTodo.getData().getDateStr();
                    return todo;
                });
    }

    @Override
    public Observable<FinishTodo> finishTodo(int id, int status) {
        return mApiServer.finishTodo(id, status);
    }

    @Override
    public Observable<Todo> updateTodo(String title, String content, String date, int type, int priority, int id) {
        return mApiServer.updateTodo(title, content, date, type, priority, id)
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
                    todo.dateStr = updateTodo.getData().getDateStr();
                    return todo;
                });
    }
}
