package com.wjx.android.wanandroidmvp.bean.home;

/**
 * Created with Android Studio.
 * Description: 首页文章列表刷新实体类
 *
 * @author: 王拣贤
 * @date: 2019/12/25
 * Time: 12:36
 */
public class ArticleBeansNew {
    public int id;
    public String title;
    public String author;
    public String niceDate;
    public long publishTime;
    public String chapterName;
    public String superChapterName;
    public boolean collect;
    public String link;
    public String shareUser;
    public String origin;


    @Override
    public String toString() {
        return "ArticleBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", niceDate='" + niceDate + '\'' +
                ", publishTime=" + publishTime +
                ", chapterName='" + chapterName + '\'' +
                ", superChapterName='" + superChapterName + '\'' +
                ", collect=" + collect +
                ", link='" + link + '\'' +
                '}';
    }
}

