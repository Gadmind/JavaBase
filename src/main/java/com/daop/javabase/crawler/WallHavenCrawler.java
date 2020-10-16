package com.daop.javabase.crawler;

import com.daop.javabase.crawler.utils.CrawlerUtils;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description: 爬虫测试
 * @DATE: 2020-10-16
 * @AUTHOR: Administrator
 **/
public class WallHavenCrawler {

    public static void main(String[] args) {
        String filePath = "F:\\Crawler Img\\wallhaven\\toplist\\";
        //https://wallhaven.cc/search?categories=110&purity=100&topRange=1M&sorting=toplist&order=desc&page=3

        //https://w.wallhaven.cc/full/ox/wallhaven-oxzk8m.jpg
        //https://w.wallhaven.cc/full/g8/wallhaven-g83m3d.jpg

        //https://th.wallhaven.cc/small/g8/g83m3d.jpg
        for (int i = 1; i <= 2; i++) {
            String pathUrl = "https://wallhaven.cc/search?categories=110&purity=110&topRange=1M&sorting=toplist&order=desc&page=" + i;
            String html = CrawlerUtils.getUrlResponseToString(pathUrl, StandardCharsets.UTF_8);
            String totalPage = "class=\"thumb-listing-page-num.*/ (\\d+)";
            String imgReg = "data-src=\"[a-zA-z]+://[^\\s]*\\.+(png|jpg|gif)";
            Pattern pattern = Pattern.compile(totalPage);
            Matcher matcher = pattern.matcher(html);
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
            pattern = Pattern.compile(imgReg);
            matcher = pattern.matcher(html);
            while (matcher.find()) {
                String group = matcher.group();
                System.out.print(group + "\t");

                String fileName = group.substring(group.lastIndexOf("/") + 1);
                System.out.println(getFullPath(fileName));
                CrawlerUtils.downloadImg(getFullPath(fileName), filePath);
            }
        }
    }

    public static String getFullPath(String fileName) {
        String prefix = fileName.substring(0, 2);
        return "https://w.wallhaven.cc/full/" + prefix + "/wallhaven-" + fileName;
    }
}
