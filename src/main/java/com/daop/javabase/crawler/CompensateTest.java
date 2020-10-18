package com.daop.javabase.crawler;

import com.daop.javabase.crawler.site.DownloadResult;
import com.daop.javabase.crawler.utils.CrawlerUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description: 补偿测试
 * @DATE: 2020-10-16
 * @AUTHOR: Administrator
 **/
public class CompensateTest {
    static String filePath = "F:\\Crawler Img\\Compensate\\";

    public static void main(String[] args) {
        /*
         *  √ http://p15.qhimg.com/bdr/2560_1600_100/t0191a83192104b549d.jpg	文件名 1602819608463_t0191a83192104b549d.jpg
         *  √ http://p17.qhimg.com/bdr/2560_1600_100/t017889ba1a5893a448.jpg	文件名 1602818289811_t017889ba1a5893a448.jpg
         *  √ http://p17.qhimg.com/bdr/2560_1600_100/d/_open360/beauty0327/8.jpg	文件名 1602822432988_8.jp
         *  √ http://p15.qhimg.com/bdr/2560_1600_100/d/_open360/big/zy5.jpg	文件名 1602825344662_zy5.jpg
         *
         *  http://p2.qhimg.com/bdr/2560_1600_100/t018565e4bff1829150.jpg
         *
         * 文字
         * 下载失败{ 地址：http://p16.qhimg.com/bdr/2560_1600_100/t016164d4de6068fa43.jpg , 文件名称：1602900602664_t016164d4de6068fa43.jpg 原因：java.net.ConnectException: Connection timed out: connect}
         * 下载失败{ 地址：http://p17.qhimg.com/bdr/2560_1600_100/t018aba5fd242e01e70.jpg , 文件名称：1602901009894_t018aba5fd242e01e70.jpg 原因：java.net.ConnectException: Connection timed out: connect}
         * 下载失败{ 地址：http://p17.qhimg.com/bdr/2560_1600_100/t01dc15bc421a284098.jpg , 文件名称：1602901121890_t01dc15bc421a284098.jpg 原因：java.net.ConnectException: Connection timed out: connect}
         *
         * 游戏
         * 下载失败{ 地址：http://p4.qhimg.com/bdr/2560_1600_100/t011159af9f862fc82e.jpg , 文件名称：1602900622637_t011159af9f862fc82e.jpg 原因：java.net.ConnectException: Connection timed out: connect}
         *
         * 4K
         * 下载失败{ 地址：http://p4.qhimg.com/bdr/2560_1600_100/t017600ae8bdaaed942.jpg , 文件名称：1602900732812_t017600ae8bdaaed942.jpg 原因：java.net.ConnectException: Connection timed out: connect}
         * 下载失败{ 地址：http://p17.qhimg.com/bdr/2560_1600_100/t01c25aab76de07ab87.jpg , 文件名称：1602902363874_t01c25aab76de07ab87.jpg 原因：java.net.ConnectException: Connection timed out: connect}
         *
         * */
        List<DownloadResult> resultList = new ArrayList<>();
//        resultList.add(new DownloadResult("文字控", "1602900602664_t016164d4de6068fa43.jpg", "http://p17.qhimg.com/bdr/2560_1600_100/t018aba5fd242e01e70.jpg"));
//        resultList.add(new DownloadResult("文字控", "1602901009894_t018aba5fd242e01e70.jpg", "http://p17.qhimg.com/bdr/2560_1600_100/t018aba5fd242e01e70.jpg"));
//        resultList.add(new DownloadResult("文字控", "1602901121890_t01dc15bc421a284098.jpg", "http://p17.qhimg.com/bdr/2560_1600_100/t01dc15bc421a284098.jpg"));
//
//        resultList.add(new DownloadResult("游戏", "1602900622637_t011159af9f862fc82e.jpg", "http://p4.qhimg.com/bdr/2560_1600_100/t011159af9f862fc82e.jpg"));
//
//        resultList.add(new DownloadResult("4K", "1602900732812_t017600ae8bdaaed942.jpg", "http://p4.qhimg.com/bdr/2560_1600_100/t017600ae8bdaaed942.jpg"));
//        resultList.add(new DownloadResult("4K", "1602902363874_t01c25aab76de07ab87.jpg", "http://p17.qhimg.com/bdr/2560_1600_100/t01c25aab76de07ab87.jpg"));

        resultList.add(new DownloadResult("女生", "1602909698942_t014deb28e1225555c5.jpg", "http://p15.qhimg.com/bdr/2560_1600_100/t014deb28e1225555c5.jpg"));
        resultList.add(new DownloadResult("女生", "1602910448033_t0191a83192104b549d.jpg", "http://p15.qhimg.com/bdr/2560_1600_100/t0191a83192104b549d.jpg"));
        resultList.add(new DownloadResult("女生", "1602911902850_11.jpg", "http://p15.qhimg.com/bdr/2560_1600_100/d/_open360/meinv1113/11.jpg"));
        resultList.add(new DownloadResult("女生", "1602912666770_6.jpg", "http://p19.qhimg.com/bdr/2560_1600_100/d/_open360/meinv0902/6.jpg"));

        for (DownloadResult downloadResult : resultList) {
            String path = filePath + downloadResult.getCategory() + File.separator;
            CrawlerUtils.downloadImg(downloadResult.getFileUrl(), path, downloadResult.getFileName());
        }
    }
}
