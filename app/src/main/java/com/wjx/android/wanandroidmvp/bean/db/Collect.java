package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description: 收藏持久化数据
 *
 * @author: Wangjianxian
 * @date: 2020/01/05
 * Time: 21：50
 */
public class Collect extends LitePalSupport {

    public int id;

    public int articleId;

    public int originId;

    public String envelopePic;

    public String author;

    public String title;

    public String category;

    public long time;

    public String link;

    public String chapterName;

    public boolean isSupportPic;

    public String niceDate;
}
