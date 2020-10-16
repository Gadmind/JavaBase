package com.daop.javabase.crawler;

import com.daop.javabase.crawler.site.DownloadResult;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description: 补偿测试
 * @DATE: 2020-10-16
 * @AUTHOR: Administrator
 **/
public class CompensateTest {
    public static void main(String[] args) {
        /*
         *  √ http://p15.qhimg.com/bdr/2560_1600_100/t0191a83192104b549d.jpg	文件名 1602819608463_t0191a83192104b549d.jpg
         *  √ http://p17.qhimg.com/bdr/2560_1600_100/t017889ba1a5893a448.jpg	文件名 1602818289811_t017889ba1a5893a448.jpg
         *  √ http://p17.qhimg.com/bdr/2560_1600_100/d/_open360/beauty0327/8.jpg	文件名 1602822432988_8.jp
         *  √ http://p15.qhimg.com/bdr/2560_1600_100/d/_open360/big/zy5.jpg	文件名 1602825344662_zy5.jpg
         *
         *  http://p2.qhimg.com/bdr/2560_1600_100/t018565e4bff1829150.jpg
         * */
       CrawlerUtils.downloadImg("http://p15.qhimg.com/bdr/2560_1600_100/d/_open360/big/zy5.jpg", "F:\\Crawler Img\\TT\\");
    }
}
