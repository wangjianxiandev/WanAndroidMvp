package com.wjx.android.wanandroidmvp.bean.home;

import java.util.List;

/**
 * Created with Android Studio.
 * Description: 首页文章列表原始实体类
 *
 * @author: 王拣贤
 * @date: 2019/12/19
 * Time: 18:14
 */
public class ArticleBean {

    /**
     * data : {"curPage":2,"datas":[{"apkLink":"","audit":1,"author":"小编","chapterId":430,"chapterName":"Flutter","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10877,"link":"https://flutterchina.club/get-started/install/","niceDate":"2019-12-14 17:58","niceShareDate":"2019-12-14 17:58","origin":"","prefix":"","projectLink":"","publishTime":1576317524000,"selfVisible":0,"shareDate":1576317524000,"shareUser":"","superChapterId":272,"superChapterName":"导航主Tab","tags":[{"name":"导航","url":"/navi#430"}],"title":"Flutter 中文网","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"小编","chapterId":430,"chapterName":"Flutter","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10876,"link":"https://guoshuyu.cn/home/wx/Flutter-1.html","niceDate":"2019-12-14 17:57","niceShareDate":"2019-12-14 17:57","origin":"","prefix":"","projectLink":"","publishTime":1576317445000,"selfVisible":0,"shareDate":1576317445000,"shareUser":"","superChapterId":272,"superChapterName":"导航主Tab","tags":[{"name":"导航","url":"/navi#430"}],"title":"Flutter 教程","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"小编","chapterId":430,"chapterName":"Flutter","collect":true,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10875,"link":"https://flutter.cn/","niceDate":"2019-12-14 17:57","niceShareDate":"2019-12-14 17:57","origin":"","prefix":"","projectLink":"","publishTime":1576317431000,"selfVisible":0,"shareDate":1576317431000,"shareUser":"","superChapterId":272,"superChapterName":"导航主Tab","tags":[{"name":"导航","url":"/navi#430"}],"title":"Flutter 社区中文","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10864,"link":"https://blog.csdn.net/a1018875550/article/details/103533668","niceDate":"2019-12-13 22:50","niceShareDate":"2019-12-13 22:50","origin":"","prefix":"","projectLink":"","publishTime":1576248611000,"selfVisible":0,"shareDate":1576248611000,"shareUser":"jokar","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"这可能是最好用、实现最简单的Android权限请求库了","type":0,"userId":35905,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10840,"link":"https://juejin.im/post/5df340a7e51d45582112570c","niceDate":"2019-12-13 15:53","niceShareDate":"2019-12-13 15:53","origin":"","prefix":"","projectLink":"","publishTime":1576223635000,"selfVisible":0,"shareDate":1576223619000,"shareUser":"winlee28","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"全新的视图绑定工具 &mdash; ViewBinding 使用指南 ","type":0,"userId":25211,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":375,"chapterName":"Flutter","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10827,"link":"https://juejin.im/post/5df18c6ae51d45580f7c9fdd","niceDate":"2019-12-13 00:10","niceShareDate":"2019-12-13 00:07","origin":"","prefix":"","projectLink":"","publishTime":1576167022000,"selfVisible":0,"shareDate":1576166832000,"shareUser":"鸿洋","superChapterId":375,"superChapterName":"跨平台","tags":[],"title":"[- Flutter 跨界篇-]昨晚简记+Flutter桌面、Web开发","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":172,"chapterName":"deep link","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10829,"link":"https://www.jianshu.com/p/da8a83c61d37?utm_source=desktop&amp;utm_medium=timeline","niceDate":"2019-12-13 00:10","niceShareDate":"2019-12-13 00:09","origin":"","prefix":"","projectLink":"","publishTime":1576167008000,"selfVisible":0,"shareDate":1576166941000,"shareUser":"鸿洋","superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"Deeplink实践原理分析","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":78,"chapterName":"性能优化","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10828,"link":"https://www.jianshu.com/p/38b627adaecd?utm_source=desktop&amp;utm_medium=timeline","niceDate":"2019-12-13 00:09","niceShareDate":"2019-12-13 00:08","origin":"","prefix":"","projectLink":"","publishTime":1576166995000,"selfVisible":0,"shareDate":1576166897000,"shareUser":"鸿洋","superChapterId":185,"superChapterName":"热门专题","tags":[],"title":"Android性能优化--内存优化","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":169,"chapterName":"gradle","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10822,"link":"https://juejin.im/post/5df1a8a56fb9a0165c7116d4","niceDate":"2019-12-13 00:05","niceShareDate":"2019-12-12 11:08","origin":"","prefix":"","projectLink":"","publishTime":1576166713000,"selfVisible":0,"shareDate":1576120084000,"shareUser":"1432967264@qq.com","superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"自定义Gradle插件给应用加固","type":0,"userId":24385,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":74,"chapterName":"反编译","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10824,"link":"https://juejin.im/post/5c2253f6f265da616d54377b","niceDate":"2019-12-13 00:04","niceShareDate":"2019-12-12 13:47","origin":"","prefix":"","projectLink":"","publishTime":1576166688000,"selfVisible":0,"shareDate":1576129677000,"shareUser":"秉心说","superChapterId":185,"superChapterName":"热门专题","tags":[],"title":"Android逆向笔记 &mdash;&mdash; AndroidManifest.xml 文件格式解析","type":0,"userId":22057,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10851,"link":"https://mp.weixin.qq.com/s/zfiw4a17VCKw43EfKpysgA","niceDate":"2019-12-13 00:00","niceShareDate":"2019-12-13 20:48","origin":"","prefix":"","projectLink":"","publishTime":1576166400000,"selfVisible":0,"shareDate":1576241295000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"String和Color的硬编码多达3千处，咋办？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10855,"link":"https://mp.weixin.qq.com/s/J0EQBFKAmIrQl8JL_vqd_g","niceDate":"2019-12-13 00:00","niceShareDate":"2019-12-13 20:50","origin":"","prefix":"","projectLink":"","publishTime":1576166400000,"selfVisible":0,"shareDate":1576241413000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"Kotlin为跨端开发带来哪些影响？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10859,"link":"https://mp.weixin.qq.com/s/lOG3YDDnolm3FCn5M8F1pA","niceDate":"2019-12-13 00:00","niceShareDate":"2019-12-13 20:51","origin":"","prefix":"","projectLink":"","publishTime":1576166400000,"selfVisible":0,"shareDate":1576241506000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"Spring Boot 面试，一个问题就干趴下了","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"谷歌开发者","chapterId":415,"chapterName":"谷歌开发者","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10861,"link":"https://mp.weixin.qq.com/s/M-lA6DADpgcoMqpnn9zV5g","niceDate":"2019-12-13 00:00","niceShareDate":"2019-12-13 20:52","origin":"","prefix":"","projectLink":"","publishTime":1576166400000,"selfVisible":0,"shareDate":1576241574000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/415/1"}],"title":"如何处理手势冲突 | 手势导航连载 (三)","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10823,"link":"https://blog.csdn.net/Rain_9155/article/details/103501136","niceDate":"2019-12-12 16:48","niceShareDate":"2019-12-12 13:16","origin":"","prefix":"","projectLink":"","publishTime":1576140500000,"selfVisible":0,"shareDate":1576127764000,"shareUser":"rain9155","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android消息机制解析(java层)","type":0,"userId":12884,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10825,"link":"https://www.jianshu.com/p/b6a1939b3cdb","niceDate":"2019-12-12 14:15","niceShareDate":"2019-12-12 14:15","origin":"","prefix":"","projectLink":"","publishTime":1576131312000,"selfVisible":0,"shareDate":1576131312000,"shareUser":"吊儿郎当","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"RxJava2.x+ReTrofit2.x多线程下载文件","type":0,"userId":2554,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":228,"chapterName":"辅助 or 工具类","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10802,"link":"https://juejin.im/post/5b6d26016fb9a04f86065cdf","niceDate":"2019-12-12 00:31","niceShareDate":"2019-12-11 09:10","origin":"","prefix":"","projectLink":"","publishTime":1576081906000,"selfVisible":0,"shareDate":1576026651000,"shareUser":"灰丨色","superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android 高性能日志写入方案","type":0,"userId":28694,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":424,"chapterName":"协程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10803,"link":"https://www.jianshu.com/p/a303606fe3eb","niceDate":"2019-12-12 00:30","niceShareDate":"2019-12-11 09:53","origin":"","prefix":"","projectLink":"","publishTime":1576081850000,"selfVisible":0,"shareDate":1576029223000,"shareUser":"深红骑士","superChapterId":232,"superChapterName":"Kotlin","tags":[],"title":"Kotlin Coroutines 协程不复杂, 我来帮你理一理","type":0,"userId":29303,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10848,"link":"https://mp.weixin.qq.com/s/VLhUBYiQ1kYXomm9_WqAEQ","niceDate":"2019-12-12 00:00","niceShareDate":"2019-12-13 20:46","origin":"","prefix":"","projectLink":"","publishTime":1576080000000,"selfVisible":0,"shareDate":1576241196000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"AS 3.6 Canary 中推出新技术 视图绑定 View Binding","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10854,"link":"https://mp.weixin.qq.com/s/AlzMVAv2dgiQeSNaQSkLFw","niceDate":"2019-12-12 00:00","niceShareDate":"2019-12-13 20:49","origin":"","prefix":"","projectLink":"","publishTime":1576080000000,"selfVisible":0,"shareDate":1576241393000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"Google Play 上架的那些坑","type":0,"userId":-1,"visible":1,"zan":0}],"offset":20,"over":false,"pageCount":386,"size":20,"total":7709}
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
         * curPage : 2
         * datas : [{"apkLink":"","audit":1,"author":"小编","chapterId":430,"chapterName":"Flutter","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10877,"link":"https://flutterchina.club/get-started/install/","niceDate":"2019-12-14 17:58","niceShareDate":"2019-12-14 17:58","origin":"","prefix":"","projectLink":"","publishTime":1576317524000,"selfVisible":0,"shareDate":1576317524000,"shareUser":"","superChapterId":272,"superChapterName":"导航主Tab","tags":[{"name":"导航","url":"/navi#430"}],"title":"Flutter 中文网","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"小编","chapterId":430,"chapterName":"Flutter","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10876,"link":"https://guoshuyu.cn/home/wx/Flutter-1.html","niceDate":"2019-12-14 17:57","niceShareDate":"2019-12-14 17:57","origin":"","prefix":"","projectLink":"","publishTime":1576317445000,"selfVisible":0,"shareDate":1576317445000,"shareUser":"","superChapterId":272,"superChapterName":"导航主Tab","tags":[{"name":"导航","url":"/navi#430"}],"title":"Flutter 教程","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"小编","chapterId":430,"chapterName":"Flutter","collect":true,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10875,"link":"https://flutter.cn/","niceDate":"2019-12-14 17:57","niceShareDate":"2019-12-14 17:57","origin":"","prefix":"","projectLink":"","publishTime":1576317431000,"selfVisible":0,"shareDate":1576317431000,"shareUser":"","superChapterId":272,"superChapterName":"导航主Tab","tags":[{"name":"导航","url":"/navi#430"}],"title":"Flutter 社区中文","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10864,"link":"https://blog.csdn.net/a1018875550/article/details/103533668","niceDate":"2019-12-13 22:50","niceShareDate":"2019-12-13 22:50","origin":"","prefix":"","projectLink":"","publishTime":1576248611000,"selfVisible":0,"shareDate":1576248611000,"shareUser":"jokar","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"这可能是最好用、实现最简单的Android权限请求库了","type":0,"userId":35905,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10840,"link":"https://juejin.im/post/5df340a7e51d45582112570c","niceDate":"2019-12-13 15:53","niceShareDate":"2019-12-13 15:53","origin":"","prefix":"","projectLink":"","publishTime":1576223635000,"selfVisible":0,"shareDate":1576223619000,"shareUser":"winlee28","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"全新的视图绑定工具 &mdash; ViewBinding 使用指南 ","type":0,"userId":25211,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":375,"chapterName":"Flutter","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10827,"link":"https://juejin.im/post/5df18c6ae51d45580f7c9fdd","niceDate":"2019-12-13 00:10","niceShareDate":"2019-12-13 00:07","origin":"","prefix":"","projectLink":"","publishTime":1576167022000,"selfVisible":0,"shareDate":1576166832000,"shareUser":"鸿洋","superChapterId":375,"superChapterName":"跨平台","tags":[],"title":"[- Flutter 跨界篇-]昨晚简记+Flutter桌面、Web开发","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":172,"chapterName":"deep link","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10829,"link":"https://www.jianshu.com/p/da8a83c61d37?utm_source=desktop&amp;utm_medium=timeline","niceDate":"2019-12-13 00:10","niceShareDate":"2019-12-13 00:09","origin":"","prefix":"","projectLink":"","publishTime":1576167008000,"selfVisible":0,"shareDate":1576166941000,"shareUser":"鸿洋","superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"Deeplink实践原理分析","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":78,"chapterName":"性能优化","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10828,"link":"https://www.jianshu.com/p/38b627adaecd?utm_source=desktop&amp;utm_medium=timeline","niceDate":"2019-12-13 00:09","niceShareDate":"2019-12-13 00:08","origin":"","prefix":"","projectLink":"","publishTime":1576166995000,"selfVisible":0,"shareDate":1576166897000,"shareUser":"鸿洋","superChapterId":185,"superChapterName":"热门专题","tags":[],"title":"Android性能优化--内存优化","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":169,"chapterName":"gradle","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10822,"link":"https://juejin.im/post/5df1a8a56fb9a0165c7116d4","niceDate":"2019-12-13 00:05","niceShareDate":"2019-12-12 11:08","origin":"","prefix":"","projectLink":"","publishTime":1576166713000,"selfVisible":0,"shareDate":1576120084000,"shareUser":"1432967264@qq.com","superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"自定义Gradle插件给应用加固","type":0,"userId":24385,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":74,"chapterName":"反编译","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10824,"link":"https://juejin.im/post/5c2253f6f265da616d54377b","niceDate":"2019-12-13 00:04","niceShareDate":"2019-12-12 13:47","origin":"","prefix":"","projectLink":"","publishTime":1576166688000,"selfVisible":0,"shareDate":1576129677000,"shareUser":"秉心说","superChapterId":185,"superChapterName":"热门专题","tags":[],"title":"Android逆向笔记 &mdash;&mdash; AndroidManifest.xml 文件格式解析","type":0,"userId":22057,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10851,"link":"https://mp.weixin.qq.com/s/zfiw4a17VCKw43EfKpysgA","niceDate":"2019-12-13 00:00","niceShareDate":"2019-12-13 20:48","origin":"","prefix":"","projectLink":"","publishTime":1576166400000,"selfVisible":0,"shareDate":1576241295000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"String和Color的硬编码多达3千处，咋办？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10855,"link":"https://mp.weixin.qq.com/s/J0EQBFKAmIrQl8JL_vqd_g","niceDate":"2019-12-13 00:00","niceShareDate":"2019-12-13 20:50","origin":"","prefix":"","projectLink":"","publishTime":1576166400000,"selfVisible":0,"shareDate":1576241413000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"Kotlin为跨端开发带来哪些影响？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10859,"link":"https://mp.weixin.qq.com/s/lOG3YDDnolm3FCn5M8F1pA","niceDate":"2019-12-13 00:00","niceShareDate":"2019-12-13 20:51","origin":"","prefix":"","projectLink":"","publishTime":1576166400000,"selfVisible":0,"shareDate":1576241506000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"Spring Boot 面试，一个问题就干趴下了","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"谷歌开发者","chapterId":415,"chapterName":"谷歌开发者","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10861,"link":"https://mp.weixin.qq.com/s/M-lA6DADpgcoMqpnn9zV5g","niceDate":"2019-12-13 00:00","niceShareDate":"2019-12-13 20:52","origin":"","prefix":"","projectLink":"","publishTime":1576166400000,"selfVisible":0,"shareDate":1576241574000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/415/1"}],"title":"如何处理手势冲突 | 手势导航连载 (三)","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10823,"link":"https://blog.csdn.net/Rain_9155/article/details/103501136","niceDate":"2019-12-12 16:48","niceShareDate":"2019-12-12 13:16","origin":"","prefix":"","projectLink":"","publishTime":1576140500000,"selfVisible":0,"shareDate":1576127764000,"shareUser":"rain9155","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Android消息机制解析(java层)","type":0,"userId":12884,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":502,"chapterName":"自助","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10825,"link":"https://www.jianshu.com/p/b6a1939b3cdb","niceDate":"2019-12-12 14:15","niceShareDate":"2019-12-12 14:15","origin":"","prefix":"","projectLink":"","publishTime":1576131312000,"selfVisible":0,"shareDate":1576131312000,"shareUser":"吊儿郎当","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"RxJava2.x+ReTrofit2.x多线程下载文件","type":0,"userId":2554,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":228,"chapterName":"辅助 or 工具类","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10802,"link":"https://juejin.im/post/5b6d26016fb9a04f86065cdf","niceDate":"2019-12-12 00:31","niceShareDate":"2019-12-11 09:10","origin":"","prefix":"","projectLink":"","publishTime":1576081906000,"selfVisible":0,"shareDate":1576026651000,"shareUser":"灰丨色","superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android 高性能日志写入方案","type":0,"userId":28694,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":424,"chapterName":"协程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10803,"link":"https://www.jianshu.com/p/a303606fe3eb","niceDate":"2019-12-12 00:30","niceShareDate":"2019-12-11 09:53","origin":"","prefix":"","projectLink":"","publishTime":1576081850000,"selfVisible":0,"shareDate":1576029223000,"shareUser":"深红骑士","superChapterId":232,"superChapterName":"Kotlin","tags":[],"title":"Kotlin Coroutines 协程不复杂, 我来帮你理一理","type":0,"userId":29303,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10848,"link":"https://mp.weixin.qq.com/s/VLhUBYiQ1kYXomm9_WqAEQ","niceDate":"2019-12-12 00:00","niceShareDate":"2019-12-13 20:46","origin":"","prefix":"","projectLink":"","publishTime":1576080000000,"selfVisible":0,"shareDate":1576241196000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"AS 3.6 Canary 中推出新技术 视图绑定 View Binding","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":10854,"link":"https://mp.weixin.qq.com/s/AlzMVAv2dgiQeSNaQSkLFw","niceDate":"2019-12-12 00:00","niceShareDate":"2019-12-13 20:49","origin":"","prefix":"","projectLink":"","publishTime":1576080000000,"selfVisible":0,"shareDate":1576241393000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"Google Play 上架的那些坑","type":0,"userId":-1,"visible":1,"zan":0}]
         * offset : 20
         * over : false
         * pageCount : 386
         * size : 20
         * total : 7709
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
             * author : 小编
             * chapterId : 430
             * chapterName : Flutter
             * collect : false
             * courseId : 13
             * desc :
             * envelopePic :
             * fresh : false
             * id : 10877
             * link : https://flutterchina.club/get-started/install/
             * niceDate : 2019-12-14 17:58
             * niceShareDate : 2019-12-14 17:58
             * origin :
             * prefix :
             * projectLink :
             * publishTime : 1576317524000
             * selfVisible : 0
             * shareDate : 1576317524000
             * shareUser :
             * superChapterId : 272
             * superChapterName : 导航主Tab
             * tags : [{"name":"导航","url":"/navi#430"}]
             * title : Flutter 中文网
             * type : 0
             * userId : -1
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
            private List<TagsBean> tags;

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

            public List<TagsBean> getTags() {
                return tags;
            }

            public void setTags(List<TagsBean> tags) {
                this.tags = tags;
            }

            public static class TagsBean {
                /**
                 * name : 导航
                 * url : /navi#430
                 */

                private String name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
