package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * 本地持久化数据
 */
public class Collect extends LitePalSupport {

    public int id;

    public int articleId;

    public int originId;

    public String author;

    public String title;

    public String category;

    public long time;

    public String link;

}
