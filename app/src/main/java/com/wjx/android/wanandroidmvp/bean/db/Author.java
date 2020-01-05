package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * 本地持久化数据
 */
public class Author extends LitePalSupport {

    public int id;

    public int authorId;

    public String author;
}
