package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description: 文章持久化数据
 *
 * @author: Wangjianxian
 * @date: 2020/01/05
 * Time: 21:50
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

    /**
     * 首页置顶文章
     */
    public static final int TYPE_TOP = 6;

    public int id;

    public int type;

    public int articleId;

    public String title;

    public String desc;

    public int authorId;

    public String author;

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

    public boolean isTop;

    public boolean isFresh;

    public boolean isQuestion;
}
