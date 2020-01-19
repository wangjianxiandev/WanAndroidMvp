package com.wjx.android.wanandroidmvp.bean.share;

import java.util.List;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/19
 * Time: 21:46
 */
public class ShareData {

    /**
     * data : {"coinInfo":{"coinCount":1218,"level":13,"rank":333,"userId":29633,"username":"w**bless"},"shareArticles":{"curPage":1,"datas":[{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11594,"link":"https://blog.csdn.net/qq_39424143/article/details/103338476","niceDate":"33分钟前","niceShareDate":"33分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439582000,"selfVisible":0,"shareDate":1579439582000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"TextView设置文本自动调整大小","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11593,"link":"https://blog.csdn.net/zhangying1994/article/details/104029685","niceDate":"41分钟前","niceShareDate":"41分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439121000,"selfVisible":0,"shareDate":1579439121000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android 8.0 Activity启动流程分析","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11592,"link":"https://blog.csdn.net/qq_18604209/article/details/104033262","niceDate":"42分钟前","niceShareDate":"42分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439076000,"selfVisible":0,"shareDate":1579439076000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"OpenCV-Android教程-OpenCV Manager 环境搭建","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11589,"link":"https://blog.csdn.net/smile_Running/article/details/104024391","niceDate":"46分钟前","niceShareDate":"46分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579438825000,"selfVisible":0,"shareDate":1579438825000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android Material Design 之 Activity 跳转水波纹扩散动画&nbsp;\n\n","type":0,"userId":29633,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":4}}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * coinInfo : {"coinCount":1218,"level":13,"rank":333,"userId":29633,"username":"w**bless"}
         * shareArticles : {"curPage":1,"datas":[{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11594,"link":"https://blog.csdn.net/qq_39424143/article/details/103338476","niceDate":"33分钟前","niceShareDate":"33分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439582000,"selfVisible":0,"shareDate":1579439582000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"TextView设置文本自动调整大小","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11593,"link":"https://blog.csdn.net/zhangying1994/article/details/104029685","niceDate":"41分钟前","niceShareDate":"41分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439121000,"selfVisible":0,"shareDate":1579439121000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android 8.0 Activity启动流程分析","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11592,"link":"https://blog.csdn.net/qq_18604209/article/details/104033262","niceDate":"42分钟前","niceShareDate":"42分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439076000,"selfVisible":0,"shareDate":1579439076000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"OpenCV-Android教程-OpenCV Manager 环境搭建","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11589,"link":"https://blog.csdn.net/smile_Running/article/details/104024391","niceDate":"46分钟前","niceShareDate":"46分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579438825000,"selfVisible":0,"shareDate":1579438825000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android Material Design 之 Activity 跳转水波纹扩散动画&nbsp;\n\n","type":0,"userId":29633,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":4}
         */

        private CoinInfoBean coinInfo;
        private ShareArticlesBean shareArticles;

        public CoinInfoBean getCoinInfo() {
            return coinInfo;
        }

        public void setCoinInfo(CoinInfoBean coinInfo) {
            this.coinInfo = coinInfo;
        }

        public ShareArticlesBean getShareArticles() {
            return shareArticles;
        }

        public void setShareArticles(ShareArticlesBean shareArticles) {
            this.shareArticles = shareArticles;
        }

        public static class CoinInfoBean {
            /**
             * coinCount : 1218
             * level : 13
             * rank : 333
             * userId : 29633
             * username : w**bless
             */

            private int coinCount;
            private int level;
            private int rank;
            private int userId;
            private String username;

            public int getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(int coinCount) {
                this.coinCount = coinCount;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class ShareArticlesBean {
            /**
             * curPage : 1
             * datas : [{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11594,"link":"https://blog.csdn.net/qq_39424143/article/details/103338476","niceDate":"33分钟前","niceShareDate":"33分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439582000,"selfVisible":0,"shareDate":1579439582000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"TextView设置文本自动调整大小","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11593,"link":"https://blog.csdn.net/zhangying1994/article/details/104029685","niceDate":"41分钟前","niceShareDate":"41分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439121000,"selfVisible":0,"shareDate":1579439121000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android 8.0 Activity启动流程分析","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11592,"link":"https://blog.csdn.net/qq_18604209/article/details/104033262","niceDate":"42分钟前","niceShareDate":"42分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579439076000,"selfVisible":0,"shareDate":1579439076000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"OpenCV-Android教程-OpenCV Manager 环境搭建","type":0,"userId":29633,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":11589,"link":"https://blog.csdn.net/smile_Running/article/details/104024391","niceDate":"46分钟前","niceShareDate":"46分钟前","origin":"","prefix":"","projectLink":"","publishTime":1579438825000,"selfVisible":0,"shareDate":1579438825000,"shareUser":"wjxbless","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android Material Design 之 Activity 跳转水波纹扩散动画&nbsp;\n\n","type":0,"userId":29633,"visible":0,"zan":0}]
             * offset : 0
             * over : true
             * pageCount : 1
             * size : 20
             * total : 4
             */

            private int curPage;
            private int offset;
            private boolean over;
            private int pageCount;
            private int size;
            private int total;
            private List<DatasBean> datas;

            public int getCurPage() {
                return curPage;
            }

            public void setCurPage(int curPage) {
                this.curPage = curPage;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public boolean isOver() {
                return over;
            }

            public void setOver(boolean over) {
                this.over = over;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<DatasBean> getDatas() {
                return datas;
            }

            public void setDatas(List<DatasBean> datas) {
                this.datas = datas;
            }

            public static class DatasBean {
                /**
                 * apkLink :
                 * audit : 1
                 * author :
                 * chapterId : 494
                 * chapterName : 广场
                 * collect : false
                 * courseId : 13
                 * desc :
                 * envelopePic :
                 * fresh : true
                 * id : 11594
                 * link : https://blog.csdn.net/qq_39424143/article/details/103338476
                 * niceDate : 33分钟前
                 * niceShareDate : 33分钟前
                 * origin :
                 * prefix :
                 * projectLink :
                 * publishTime : 1579439582000
                 * selfVisible : 0
                 * shareDate : 1579439582000
                 * shareUser : wjxbless
                 * superChapterId : 494
                 * superChapterName : 广场Tab
                 * tags : []
                 * title : TextView设置文本自动调整大小
                 * type : 0
                 * userId : 29633
                 * visible : 0
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
    }
}
