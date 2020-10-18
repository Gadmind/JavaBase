package com.daop.javabase.crawler;

import com.daop.javabase.crawler.site.DownloadResult;
import com.daop.javabase.crawler.utils.CrawlerUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description: 爬虫测试
 * @DATE: 2020-10-16
 * @AUTHOR: Administrator
 **/
public class WallHavenCrawler {

    public static void main(String[] args) {
        String filePath = "F:\\Crawler Img\\wallhaven\\cityserver\\";
        //https://wallhaven.cc/search?categories=110&purity=100&topRange=1M&sorting=toplist&order=desc&page=3

        //https://w.wallhaven.cc/full/ox/wallhaven-oxzk8m.jpg
        //https://w.wallhaven.cc/full/g8/wallhaven-g83m3d.jpg

        //https://th.wallhaven.cc/small/g8/g83m3d.jpg
        /*for (int i = 1; i <= 2; i++) {
            String pathUrl = "https://wallhaven.cc/search?categories=110&purity=110&topRange=1M&sorting=toplist&order=desc&page=" + i;
            startDownload(pathUrl,filePath);
        }*/
            String pathUrl = "https://wallhaven.cc/user/cityserver/uploads?purity=111";
        long start = System.currentTimeMillis();
        startDownload(pathUrl,filePath);
        long end = System.currentTimeMillis();
        System.out.println("用时"+(end-start));
    }



    private static void startDownload(String pathUrl, String filePath) {
        List<DownloadResult> downloadResultList = new ArrayList<>(20);
        String html = CrawlerUtils.getUrlResponseToString(pathUrl, StandardCharsets.UTF_8);
        String totalPage = "class=\"thumb-listing-page-num.*/ (\\d+)";
        String imgReg = "data-src=\"[a-zA-z]+://[^\\s]+?(\\.jpg|\\.png)";
        Pattern pattern = Pattern.compile(totalPage);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        pattern = Pattern.compile(imgReg);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
            String fileName = group.substring(group.lastIndexOf("/") + 1);
            String imgUrl = getFullPath(fileName);
            if (downloadJpg(imgUrl, filePath) != null) {
                DownloadResult dr = downloadPng(imgUrl, filePath);
                if (dr != null) {
                    downloadResultList.add(dr);
                }
            }
        }
        downloadResultList.forEach(System.out::println);
    }

    private static DownloadResult downloadJpg(String url, String filePath) {
        url = url.replaceAll("(\\.jpg|\\.png)", ".jpg");
        return CrawlerUtils.downloadImg(url, filePath);
    }


    private static DownloadResult downloadPng(String url, String filePath) {
        url = url.replaceAll("(\\.jpg|\\.png)", ".png");
        return CrawlerUtils.downloadImg(url, filePath);
    }

    public static String getFullPath(String fileName) {
        String prefix = fileName.substring(0, 2);
        return "https://w.wallhaven.cc/full/" + prefix + "/wallhaven-" + fileName;
    }
}
