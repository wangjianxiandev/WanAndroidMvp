package com.wjx.android.wanandroidmvp.bean.home;

import java.util.List;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/11
 * Time: 18:36
 */
public class TopArticleBean {

    /**
     * data : [{"apkLink":"","audit":1,"author":"","chapterId":249,"chapterName":"干货资源","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10916,"link":"https://wanandroid.com/blog/show/2701","niceDate":"1天前","niceShareDate":"2019-12-17 13:15","origin":"","prefix":"","projectLink":"","publishTime":1578587641000,"selfVisible":0,"shareDate":1576559725000,"shareUser":"18818486692","superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"新建了一个免费星球","type":1,"userId":22014,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"xiaoyang","chapterId":440,"chapterName":"官方","collect":true,"courseId":13,"desc":"<p>今天的问题很有意思，yyyy-MM-dd 与 YYYY-MM-dd 的区别是什么？<\/p>\r\n<p>使用错误会造成什么 bug 吗？<\/p>","envelopePic":"","fresh":false,"id":11387,"link":"https://www.wanandroid.com/wenda/show/11387","niceDate":"2020-01-07 00:08","niceShareDate":"2020-01-07 00:07","origin":"","prefix":"","projectLink":"","publishTime":1578326898000,"selfVisible":0,"shareDate":1578326866000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问  据说很多 app 在 2019 年最后一周都出现了日期上的 bug ?","type":1,"userId":2,"visible":1,"zan":13},{"apkLink":"","audit":1,"author":"xiaoyang","chapterId":440,"chapterName":"官方","collect":true,"courseId":13,"desc":"<p>这个问题其实不算一个太好的问题，但是也能考察事件的分发流程，搞清楚 Window,Activity,DecorView 在事件分发环节的调用流程。<\/p>","envelopePic":"","fresh":false,"id":11363,"link":"https://www.wanandroid.com/wenda/show/11363","niceDate":"2020-01-06 00:34","niceShareDate":"2020-01-04 20:53","origin":"","prefix":"","projectLink":"","publishTime":1578242071000,"selfVisible":0,"shareDate":1578142403000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问  为什么 Dialog 弹出后 Activity 就无法响应用户事件了？","type":1,"userId":2,"visible":1,"zan":14},{"apkLink":"","audit":1,"author":"鸿洋","chapterId":249,"chapterName":"干货资源","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10285,"link":"http://gk.link/a/103Ei","niceDate":"2020-01-01 22:34","niceShareDate":"2019-11-15 01:01","origin":"","prefix":"","projectLink":"","publishTime":1577889274000,"selfVisible":0,"shareDate":1573750881000,"shareUser":"","superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"本站福利，极客199 礼包自取","type":1,"userId":-1,"visible":1,"zan":0}]
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<DataBean> data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * apkLink :
         * audit : 1
         * author :
         * chapterId : 249
         * chapterName : 干货资源
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 10916
         * link : https://wanandroid.com/blog/show/2701
         * niceDate : 1天前
         * niceShareDate : 2019-12-17 13:15
         * origin :
         * prefix :
         * projectLink :
         * publishTime : 1578587641000
         * selfVisible : 0
         * shareDate : 1576559725000
         * shareUser : 18818486692
         * superChapterId : 249
         * superChapterName : 干货资源
         * tags : []
         * title : 新建了一个免费星球
         * type : 1
         * userId : 22014
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private int audit;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String niceShareDate;
        private String origin;
        private String prefix;
        private String projectLink;
        private long publishTime;
        private int selfVisible;
        private long shareDate;
        private String shareUser;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private List<?> tags;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getNiceShareDate() {
            return niceShareDate;
        }

        public void setNiceShareDate(String niceShareDate) {
            this.niceShareDate = niceShareDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSelfVisible() {
            return selfVisible;
        }

        public void setSelfVisible(int selfVisible) {
            this.selfVisible = selfVisible;
        }

        public long getShareDate() {
            return shareDate;
        }

        public void setShareDate(long shareDate) {
            this.shareDate = shareDate;
        }

        public String getShareUser() {
            return shareUser;
        }

        public void setShareUser(String shareUser) {
            this.shareUser = shareUser;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }
    }
}
