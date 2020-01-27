package com.wjx.android.wanandroidmvp.contract.squaresharearticle;

import com.wjx.android.wanandroidmvp.base.interfaces.IBaseView;
import com.wjx.android.wanandroidmvp.bean.db.Article;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/19
 * Time: 20:01
 */
public class Contract {

    public interface IShareModel {
        Observable<Article> addArticle(String title, String link);
    }

    public interface IShareView extends IBaseView {
        void onAddArticle(Article addArticle);
    }

    public interface ISharePresenter {
        void addArticle(String title, String link);
    }
}
