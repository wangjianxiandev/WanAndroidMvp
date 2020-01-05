package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * 本地持久化数据
 */
public class Tree extends LitePalSupport {

    public int id;

    public int parentId;

    public int treeId;

    public String name;

}
