package com.wjx.android.wanandroidmvp.presenter.todo;

import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.todo.DeleteTodo;
import com.wjx.android.wanandroidmvp.bean.todo.FinishTodo;
import com.wjx.android.wanandroidmvp.bean.todo.Todo;
import com.wjx.android.wanandroidmvp.bean.todo.UpdateTodo;
import com.wjx.android.wanandroidmvp.contract.todo.Contract;
import com.wjx.android.wanandroidmvp.model.TodoModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 22:51
 */
public class TodoPresenter extends BasePresenter<Contract.ITodoView> implements Contract.ITodoPresenter {
    Contract.ITodoModel iTodoModel;

    public TodoPresenter() {
        iTodoModel = new TodoModel();
    }

    @Override
    public void loadTodo(int pageNum) {
        if (isViewAttached() && pageNum == 0) {
            getView().onLoading();
        }
        iTodoModel.loadTodo(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Todo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Todo> todos) {
                        if (isViewAttached()) {
                            getView().onLoadTodo(todos);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void refreshTodo(int pageNum) {
        iTodoModel.refreshTodo(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Todo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Todo> todos) {
                        if (isViewAttached()) {
                            getView().onRefreshTodo(todos);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteTodo(int id) {
        iTodoModel.deleteTodo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteTodo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DeleteTodo deleteTodo) {
                        if (isViewAttached()) {
                            getView().onDeleteTodo(deleteTodo, id);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void addTodo(String title, String content, String date, int type, int priority) {
        iTodoModel.addTodo(title, content, date, type, priority)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Todo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Todo todo) {
                        if (isViewAttached()) {
                            getView().onAddTodo(todo);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void finishTodo(int id, int status) {
        iTodoModel.finishTodo(id, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FinishTodo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(FinishTodo finishTodo) {
                        if (isViewAttached()) {
                            getView().onFinishTodo(finishTodo, id);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void updateTodo(String title, String content, String date, int type, int priority, int id) {
        iTodoModel.updateTodo(title, content, date, type, priority, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Todo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Todo todo) {
                        if (isViewAttached()) {
                            getView().onUpdateTodo(todo);
                            getView().onLoadSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
