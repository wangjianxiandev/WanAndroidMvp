package com.wjx.android.wanandroidmvp.bean.base;

/**
 * Created with Android Studio.
 * Description: EventBus
 *
 * @author: Wangjianxian
 * @date: 2019/12/31
 * Time: 23:30
 */
public class Event {

    /**
     * 登录成功
     */
    public static final int TYPE_LOGIN = 1;

    /**
     * 退出登录
     */
    public static final int TYPE_LOGOUT = 2;

    /**
     * 收藏
     */
    public static final int TYPE_COLLECT = 3;

    /**
     * 取消收藏
     */
    public static final int TYPE_UNCOLLECT = 4;

    /**
     * 切换夜间模式
     */
    public static final int TYPE_CHANGE_DAY_NIGHT_MODE = 5;

    /**
     * 开始动画
     */
    public static final int TYPE_START_ANIMATION = 6;

    /**
     * 停止动画
     */
    public static final int TYPE_STOP_ANIMATION = 7;

    /**
     * 取消收藏刷新列表
     */
    public static final int TYPE_COLLECT_STATE_REFRESH = 8;

    /**
     * 更换主题颜色
     */
    public static final int TYPE_REFRESH_COLOR = 9;

    /**
     * 删除分享的文章
     */
    public static final int TYPE_DELETE_SHARE = 10;

    /**
     * 删除搜索历史纪录
     */
    public static final int TYPE_DELETE_SEARCH = 11;

    /**
     * 目标界面-MainActivity
     */
    public static final int TARGET_MAIN = 1;

    /**
     * 目标界面-我的
     */
    public static final int TARGET_ME = 2;

    /**
     * 目标界面-首页
     */
    public static final int TARGET_HOME = 3;

    /**
     * 目标界面-体系
     */
    public static final int TARGET_TREE = 4;

    /**
     * 目标界面-项目
     */
    public static final int TARGET_PROJECT = 5;

    /**
     * 目标界面-公众号
     */
    public static final int TARGET_WX = 6;

    /**
     * 目标界面-广场
     */
    public static final int TARGET_SQUARE = 7;

    /**
     * 目标界面-收藏列表
     */
    public static final int TARGET_COLLECT = 8;

    /**
     * 目标界面-搜索结果
     */
    public static final int TARGET_SEARCH_RESULT = 9;

    /**
     * 目标界面-积分排行
     */
    public static final int TARGET_INTEGRAL_RANK = 10;

    /**
     * 目标界面-设置界面
     */
    public static final int TARGET_SETTING = 11;

    /**
     * 目标界面-主广场页
     */
    public static final int TARGET_PARENT_SQUARE = 12;

    /**
     * 目标界面-闪屏页
     */
    public static final int TARGET_SPLASH = 13;

    /**
     * 目标界面-导航界面
     */
    public static final int TARGET_NAVIGATION = 14;

    /**
     * 目标界面-分享页面
     */
    public static final int TARGET_SQUARE_SHARE = 15;

    /**
     * 目标界面-分享列表页面
     */
    public static final int TARGET_ME_SHARE = 16;

    /**
     * 目标界面-搜索页面
     */
    public static final int TARGET_SEARCH = 17;

    /**
     * 目标页面-文章详情
     */
    public static final int TARGET_WEB_VIEW = 18;


    public int target;

    public int type;

    public String data;
}
