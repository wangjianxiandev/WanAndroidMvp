package com.wjx.android.wanandroidmvp.model;

import com.blankj.utilcode.util.NetworkUtils;
import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.ApiServer;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.db.Author;
import com.wjx.android.wanandroidmvp.bean.wechat.WeChatClassifyData;
import com.wjx.android.wanandroidmvp.contract.wechat.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/29
 * Time: 12:06
 */
public class WeChatModel extends BaseModel implements Contract.IWeChatModel {

    public WeChatModel() {
        setCookies(false);
    }

    @Override
    public Observable<List<Author>> loadWeChatClassify() {
        Observable<List<Author>> loadFromLocal = Observable.create(emitter -> {
            List<Author> weChatAuthorList = LitePal.findAll(Author.class);
            emitter.onNext(weChatAuthorList);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Author>> loadFromNet = loadWeChatClassifyFromNet();
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    private Observable<List<Author>> loadWeChatClassifyFromNet() {
        return mApiServer.loadWeChatClassify().filter(weChatClassifyData ->
                weChatClassifyData.getErrorCode() == Constant.SUCCESS)
                .map(weChatClassifyData -> {
                    List<Author> weChatClassifyList = new ArrayList<>();
                    weChatClassifyData.getData().stream().forEach(dataBean -> {
                        Author authorDB = new Author();
                        authorDB.authorId = dataBean.getId();
                        authorDB.author = dataBean.getName();
                        weChatClassifyList.add(authorDB);
                    });
                    if (weChatClassifyList.size() > 0) {
                        LitePal.deleteAll(Author.class);
                    }
                    LitePal.saveAll(weChatClassifyList);
                    return weChatClassifyList;
                });

    }

    @Override
    public Observable<List<Author>> refreshWeChatClassify() {
        return loadWeChatClassifyFromNet();
    }
}
