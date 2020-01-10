package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * 本地持久化数据
 */
public class Article extends LitePalSupport {

    /**
     * 首页文章
     */
    public static final int TYPE_HOME = 1;

    /**
     * 体系
     */
    public static final int TYPE_TREE = 2;

    /**
     * 项目
     */
    public static final int TYPE_PROJECT = 3;

    /**
     * 公众号
     */
    public static final int TYPE_WX = 4;

    /**
     * 广场
     */
    public static final int TYPE_SQUARE = 5;

    public int id;

    public int type;

    public int articleId;

    public String title;

    public String desc;

    public int authorId;

    public String author;

    public String category;

    public String chapterName;

    public String superChapterName;

    public String niceDate;

    public long time;

    public String link;

    public String envelopePic;

    public int projectType;

    public int treeType;

    public boolean collect;

    public String shareUser;

}
