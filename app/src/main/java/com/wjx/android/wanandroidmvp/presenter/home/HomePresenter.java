package com.wjx.android.wanandroidmvp.presenter.home;

import com.wjx.android.wanandroidmvp.base.presenter.BasePresenter;
import com.wjx.android.wanandroidmvp.bean.home.ArticleBeansNew;
import com.wjx.android.wanandroidmvp.contract.home.Contract;
import com.wjx.android.wanandroidmvp.bean.home.BannerBean;
import com.wjx.android.wanandroidmvp.model.HomeModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:对发送请求进行封装，利用RxJava处理封装的请求，并将数据和View进行绑定
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 16:00
 */
public class HomePresenter extends BasePresenter<Contract.IHomeView> implements Contract.IHomePresenter {

    Contract.IHomeModel iHomeModel;

    public HomePresenter() {
        iHomeModel = new HomeModel();
    }

    @Override
    public void loadBanner() {
        iHomeModel.loadBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        if (isViewAttached()) {
                            getView().loadBanner(bannerBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadArticle(int pageNum) {
        iHomeModel.loadArticle(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ArticleBeansNew>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<ArticleBeansNew> articleBeansNews) {
                        if (isViewAttached()) {
                            getView().loadArticle(articleBeansNews);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (isViewAttached()) {
                            getView().onComplete();
                        }
                    }
                });
    }

    @Override
    public void refreshArticle() {
        iHomeModel.refreshArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ArticleBeansNew>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<ArticleBeansNew> articleBeansNews) {
                        if (isViewAttached()) {
                            getView().refreshArticle(articleBeansNews);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (isViewAttached()) {
                            getView().onComplete();
                        }
                    }
                });
    }
}
