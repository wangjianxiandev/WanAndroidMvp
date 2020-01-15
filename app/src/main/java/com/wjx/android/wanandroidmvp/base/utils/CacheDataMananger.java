//package com.wjx.android.wanandroidmvp.base.utils;
//
//import android.content.Context;
//
//import java.io.File;
//import java.math.BigDecimal;
//import java.text.Bidi;
//
///**
// * Created with Android Studio.
// * Description: 缓存设置
// *
// * @author: 王拣贤
// * @date: 2020/01/15
// * Time: 21:30
// */
//public class CacheDataMananger {
//
//    public String getTotalCacheSize(Context context) {
//        int
//    }
//
//    /**
//     * 获取文件
//     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
//     * 目录，一般放一些长时间保存的数据
//     * Context.getExternalCacheDir() -->
//     * SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
//     */
//    public Long getFolderSize(File file){
//        Long size= 0
//        file?.run {
//            try {
//                val fileList =listFiles()
//                for (i in fileList.indices) {
//                    // 如果下面还有文件
//                    size += if (fileList[i].isDirectory) {
//                        getFolderSize(fileList[i])
//                    } else {
//                        fileList[i].length()
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        return size
//    }
//
//    /**
//     * 格式化单位
//     */
//    public String getFormatSize(Double size) {
//
//        double kiloByte = size / 1024;
//        if (kiloByte < 1) {
//            return size.toString() + "Byte";
//        }
//
//        double megaByte = kiloByte / 1024;
//
//        if (megaByte < 1) {
//            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
//            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
//        }
//
//        double gigaByte = megaByte / 1024;
//
//        if (gigaByte < 1) {
//            BigDecimal result2 = new BigDecimal(java.lang.Double.toString(megaByte));
//            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
//        }
//
//        double teraBytes = gigaByte / 1024;
//
//        if (teraBytes < 1) {
//            BigDecimal result3 = new BigDecimal(java.lang.Double.toString(gigaByte));
//            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
//        }
//
//        BigDecimal result4 = new BigDecimal(teraBytes);
//        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
//    }
//}
