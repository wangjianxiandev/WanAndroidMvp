package com.wjx.android.wanandroidmvp.model;

import com.blankj.utilcode.util.NetworkUtils;
import com.wjx.android.wanandroidmvp.base.model.BaseModel;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.collect.AddCollect;
import com.wjx.android.wanandroidmvp.bean.collect.Collect;
import com.wjx.android.wanandroidmvp.contract.collect.Contract;

import org.litepal.LitePal;
import org.litepal.util.Const;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/01
 * Time: 22:40
 */
public class CollectModel extends BaseModel implements Contract.ICollectModel {

    public CollectModel() {
        setCookies(false);
    }

    @Override
    public Observable<List<com.wjx.android.wanandroidmvp.bean.db.Collect>> loadCollectData(int pageNum) {
        Observable<List<com.wjx.android.wanandroidmvp.bean.db.Collect>> loadFromNet = loadCollectArticleFromNet(pageNum);
        return loadFromNet;

    }

    private Observable<List<com.wjx.android.wanandroidmvp.bean.db.Collect>> loadCollectArticleFromNet(int pageNum) {
        return mApiServer.loadCollect(pageNum).filter(collectData -> collectData.getErrorCode() == Constant.SUCCESS)
                .map(collectData -> {
                    List<com.wjx.android.wanandroidmvp.bean.db.Collect> list = new ArrayList<>();
                    collectData.getData().getDatas().stream().forEach(d -> {
                        com.wjx.android.wanandroidmvp.bean.db.Collect collect = new com.wjx.android.wanandroidmvp.bean.db.Collect();
                        collect.articleId = d.getId();
                        collect.originId = d.getOriginId();
                        collect.author = d.getAuthor();
                        collect.title = d.getTitle();
                        collect.chapterName = d.getChapterName();
                        collect.time = d.getPublishTime();
                        collect.link = d.getLink();
                        collect.niceDate = d.getNiceDate();
                        collect.envelopePic = d.getEnvelopePic();
                        if (collect.envelopePic.equals("")) {
                            collect.isSupportPic = false;
                        } else {
                            collect.isSupportPic = true;
                        }
                        list.add(collect);
                    });
                    return list;
                });
    }

    @Override
    public Observable<List<com.wjx.android.wanandroidmvp.bean.db.Collect>> refreshCollectData(int pageNum) {
        return loadCollectArticleFromNet(pageNum);
    }

    @Override
    public Observable<com.wjx.android.wanandroidmvp.bean.db.Collect> addCollect(String title, String author, String link) {
        return mApiServer.addCollect(title, author, link)
                .filter(addCollect -> addCollect.getErrorCode() == Constant.SUCCESS)
                .map(addCollect -> {
                            com.wjx.android.wanandroidmvp.bean.db.Collect collect = new com.wjx.android.wanandroidmvp.bean.db.Collect();
                            collect.articleId = addCollect.getData().getId();
                            collect.author = addCollect.getData().getAuthor();
                            collect.link = addCollect.getData().getLink();
                            collect.time = addCollect.getData().getPublishTime();
                            collect.title = addCollect.getData().getTitle();
                            collect.chapterName = "站外";
                            return collect;
                        }
                );
    }

    @Override
    public Observable<Collect> unCollect(int articleId, int originId) {
        return mApiServer.unCollect(articleId, originId);
    }
}
