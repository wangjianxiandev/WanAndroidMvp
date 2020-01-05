package com.wjx.android.wanandroidmvp.bean.collect;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/04
 * Time: 18:35
 */
public class CollectBeanNew extends LitePalSupport {

    public int id;

    public int articleId;

    public int originId;

    public String author;

    public String title;

    public String category;

    public long time;

    public String link;

}