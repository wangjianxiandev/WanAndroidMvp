package com.wjx.android.wanandroidmvp.base.utils;

/**
 * Created with Android Studio.
 * Description: 常量，用到的常量值，比如请求Url，响应时间等
 *
 * @author: Wangjianxian
 * @date: 2019/12/14
 * Time: 15:35
 */
public class Constant {
    /**
     * 域名
     */
    public static final String BASE_URL = "https://www.wanandroid.com/";

    /**
     * 首页Banner的url
     */
    public static final String BANNER_URL = "/banner/json";

    /**
     * 首页文章URL
     */
    public static final String ARTICLE_URL = "/article/list/{pageNum}/json";

    /**
     * 置顶文章数据
     */
    public static final String TOP_ARTICLE_URL = "/article/top/json";

    /**
     * 导航URL
     */
    public static final String NAVI_URL = "/navi/json";

    /**
     * 体系URL
     */
    public static final String TREE_URL = "/tree/json";

    /**
     * 体系文章
     */
    public static final String TREE_ARTICLE_URL = "/article/list/{pageNum}/json";

    /**
     * 广场文章
     */
    public static final String HOME_SQUARE_URL = "/user_article/list/{pageNum}/json";

    /**
     * 分享文章
     */
    public static final String SQUARE_SHARE_ARTICLE = "/lg/user_article/add/json";

    /**
     * 项目种类URL
     */
    public static final String PROJECT_CATEGORY_URL = "/project/tree/json";

    /**
     * 项目列表URL
     */
    public static final String PROJECT_LIST_URL = "project/list/{pageNum}/json";

    /**
     * 公众号Tab URL
     */
    public static final String WE_CHAT_TAB_URL = "wxarticle/chapters/json";

    /**
     * 公众号列表的URL
     */
    public static final String WE_CHAT_LIST_URL = "wxarticle/list/{id}/{pageNum}/json";

    /**
     * 热搜URL
     */
    public static final String HOT_SEARCH_URL = "/hotkey/json";

    /**
     * 注册URL
     */
    public static final String REGISTER_URL = "/user/register";

    /**
     * 登录URL
     */
    public static final String LOGIN_URL = "/user/login";

    /**
     * 登出URL
     */
    public static final String LOGINOUT_URL = "user/logout/json";

    /**
     * 收藏文章的url
     */
    public static final String COLLECT_URL = "/lg/collect/{id}/json";

    /**
     * 取消收藏文章的url
     */
    public static final String UNCOLLECT_URL = "/lg/uncollect_originId/{id}/json";

    /**
     * 取消收藏文章的url(包含自己录入的内容)
     */
    public static final String UNCOLLECT_INCLUDE_ADD_URL = "/lg/uncollect/{id}/json";

    /**
     * 收藏文章列表的url
     */
    public static final String COLLECT_LIST_URL = "/lg/collect/list/{pageNum}/json";

    /**
     * 添加站外收藏
     */
    public static final String ADD_COLLECT_URL = "/lg/collect/add/json";

    /**
     * 搜索的url
     */
    public static final String SEARCH_URL = "/article/query/{pageNum}/json";

    /**
     * 获取个人积分排行
     */
    public static final String INTEGRAL_URL = "/lg/coin/userinfo/json";

    /**
     * 积分排行
     */
    public static final String RANK_URL = "/coin/rank/{pageNum}/json";

    /**
     * 获取分享的文章的列表
     */
    public static final String SHARE_ARTICLE_URL = "/user/lg/private_articles/{pageNum}/json";

    /**
     * 删除分享的文章
     */
    public static final String DELETE_SHARE_ARTICLE_URL = "/lg/user_article/delete/{id}/json";

    /**
     * 积分规则
     */
    public static final String INTEGRAL_HELP_URL = "https://www.wanandroid.com/blog/show/2653";

    /**
     * Todo列表
     */
    public static final String TODO_LIST_URL = "/lg/todo/v2/list/{pageNum}/json";

    /**
     * 更新一个Todo
     */
    public static final String UPDATE_TODO_URL = "/lg/todo/update/{id}/json";

    /**
     * 完成一个Todo
     */
    public static final String FINISH_TODO_URL = "/lg/todo/done/{id}/json";

    /**
     * 添加一个Todo
     */
    public static final String ADD_TODO_URL = "/lg/todo/add/json";

    /**
     * 删除一个Todo
     */
    public static final String DELETE_TODO_URL = "/lg/todo/delete/{id}/json";

    /**
     * 获取成功
     */
    public static final int SUCCESS = 0;

    /**
     * 每页数量
     */
    public static final int PAGE_SIZE = 20;

    /**
     * key-articleid
     */
    public static final String KEY_ARTICLEID = "articleid";

    /**
     * key-title
     */
    public static final String KEY_TITLE = "title";

    /**
     * tree-cid
     */
    public static final String KEY_TREE_CID = "cid";

    /**
     * key-url
     */
    public static final String KEY_URL = "url";

    /**
     * key-iscollect
     */
    public static final String KEY_COLLECT = "iscollect";

    /**
     * key-rank
     */
    public static final String KEY_RANK = "rank";

    /**
     * key-countcoin
     */
    public static final String KEY_COUNTCOIN = "countcoin";

    /**
     * key-keyword
     */
    public static final String KEY_KEYWORD = "keyword";

    /**
     * key-night-mode
     */
    public static final String KEY_NIGHT_MODE = "night_mode";

    /**
     * key-todo-handle-type
     */
    public static final String KEY_TODO_HANDLE_TYPE = "todo_handle";

    /**
     * 添加Todo
     */
    public static final String ADD_TODO = 1 + "";

    /**
     * 编辑Todo
     */
    public static final String EDIT_TODO = 2 + "";

    /**
     * 重要
     */
    public static final int TODO_IMPORTANT = 1;

    /**
     * 一般
     */
    public static final int TODO_NORMAL = 2;

    /**
     * 工作
     */
    public static final int TODO_WORK = 1;

    /**
     * 学习
     */
    public static final int TODO_STUDY = 2;

    /**
     * tkey-todo-title
     */
    public static final String KEY_TODO_TITLE = "todo_title";

    /**
     * key-todo-content
     */
    public static final String KEY_TODO_CONTENT = "todo_content";

    /**
     * key-todo-date
     */
    public static final String KEY_TODO_DATE = "todo_date";

    /**
     * key-todo-priority
     */
    public static final String KEY_TODO_PRIORITY = "todo_priority";

    /**
     * key-todo-id
     */
    public static final String KEY_TODO_ID = "todo_id";

    /**
     * key-todo-type
     */
    public static final String KEY_TODO_TYPE = "todo_type";

    /**
     * 设置文件的保存名称
     */
    public static final String CONFIG_SETTINGS = "settings";


    /**
     * 搜索结果正则表达式
     */
    public static final String REGEX = "<em class='highlight'>(.+)</em>";

    /**
     * 非文章详情页
     */
    public static final int NOT_ARTICLE_WEBVIEW = 0;

    /**
     * user-name
     */
    public static final String EXTRA_KEY_USERNAME = "referrer";

    /**
     * user-password
     */
    public static final String EXTRA_VALUE_PASSWORD = "collect";

    /**
     * 两次点击返回键的时间差
     */
    public static final long EXIT_TIME = 2000;
}
