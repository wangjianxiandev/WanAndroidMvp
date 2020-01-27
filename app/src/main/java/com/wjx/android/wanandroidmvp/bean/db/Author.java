package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description: 作者持久化数据
 *
 * @author: Wangjianxian
 * @date: 2020/01/05
 * Time: 21:50
 */
public class Author extends LitePalSupport {

    public int id;

    public int authorId;

    public String author;
}
