package com.wjx.android.wanandroidmvp.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description: Banner持久化数据
 *
 * @author: Wangjianxian
 * @date: 2020/01/05
 * Time: 21:50
 */
public class Banner extends LitePalSupport {

    public int id;

    public String title;

    public String imagePath;

    public String url;

}
