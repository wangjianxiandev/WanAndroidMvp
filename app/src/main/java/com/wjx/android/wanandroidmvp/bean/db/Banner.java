package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * 本地持久化数据
 */
public class Banner extends LitePalSupport {

    public int id;

    public String title;

    public String imagePath;

    public String url;

}
