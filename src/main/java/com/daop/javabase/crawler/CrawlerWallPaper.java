package com.daop.javabase.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daop.javabase.crawler.site.DownloadResult;
import com.daop.javabase.crawler.utils.CrawlerUtils;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description: 爬虫
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 **/
public class CrawlerWallPaper {

    /**
     * @param cid          类别ID
     * @param type         类别名称
     * @param tag          筛选条件
     * @param start        开始下标：从 start 开始获取
     * @param everyCount   每次获取（1-200）
     * @param cycle        循环周期：若不大于0则自动生成
     * @param downloadPath 下载到本地路径：盘符 + File.separator + 文件目录 + File.separator;
     *                     E:\\file\\
     */
    public void startCrawler(@NotNull String cid, @NotNull String type, String tag, int start, int everyCount, int cycle, String downloadPath) {
        //总数
        int count = 20000;

        if (start <= 0) {
            start = 0;
        }
        //获取次数
        if (everyCount <= 0) {
            everyCount = 50;
        } else if (everyCount >= 200) {
            everyCount = 200;
        }
        //循环次数=(总数/每次获取的次数)+1
        if (cycle <= 0) {
            cycle = (count / everyCount) + 1;
        }
        String reqUrl = "http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByCategory&cid=" + cid + "&start=" + start + "&count=" + everyCount;


        int success = 0, fail = 0;
        List<DownloadResult> failList = new ArrayList<>(20);

        for (int i = 0; i < cycle; i++) {
            //开始下标
            System.out.println("开始第" + (i + 1) + "次访问" + type + "类型壁纸");
            reqUrl = "http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByCategory&cid=" + cid + "&start=" + start + "&count=" + everyCount;
            System.out.println(reqUrl);
            JSONObject urlMessage = CrawlerUtils.getUrlResponseToJsonObject(reqUrl, StandardCharsets.UTF_8);
            try {
                List<JSONObject> data = JSONArray.parseArray(urlMessage.get("data").toString(), JSONObject.class);
                if (data.size() == 0) {
                    System.out.println("没有数据了");
                    break;
                }
                for (JSONObject datum : data) {
                    String imgUrl = datum.get("url").toString();
                    imgUrl = imgUrl.replaceAll("\\d*_\\d*_\\d*", "2560_1600_100");
                    DownloadResult df = null;
                    if (tag.length() != 0) {
                        String utag = (String) datum.get("utag");
                        if (utag != null) {
                            if (utag.matches(".*" + tag + ".*")) {
                                df = CrawlerUtils.downloadImg(imgUrl, downloadPath + type + File.separator + tag + File.separator);
                            }
                        }
                    } else {
                        df = CrawlerUtils.downloadImg(imgUrl, downloadPath + type + File.separator);
                    }
                    if (df != null) {
                        fail++;
                        failList.add(df);
                    } else {
                        success++;
                    }
                    System.out.println("第" + (success + fail) + "张，成功" + success + "张，失败" + fail);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO 失败重新访问
                System.out.println("第" + (i + 1) + "次访问失败，重新访问");
                continue;
            }
            start += (everyCount);

        }
        System.out.println("下载" + (success + fail) + "张，成功" + success + "张，失败" + fail + "张\n失败列表");
        for (DownloadResult s : failList) {
            System.out.println(s);
        }
    }

    /**
     * 获取该所有类别和类别总条数
     */
    public void getTypes() {
        String imgurl = "http://cdn.apc.360.cn/index.php?c=WallPaper&a=getAllCategoriesV2";
        JSONObject jsonObject = CrawlerUtils.getUrlResponseToJsonObject(imgurl, StandardCharsets.UTF_8);
        List<JSONObject> data = JSONArray.parseArray(jsonObject.get("data").toString(), JSONObject.class);

        for (JSONObject datum : data) {
            Object cid = datum.get("id");
            String url = "http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByCategory&cid=" + cid;
            JSONObject urlJson = CrawlerUtils.getUrlResponseToJsonObject(url, StandardCharsets.UTF_8);
            System.out.println("类别ID为: " + datum.get("id") + " " + datum.get("name") + "壁纸共有" + urlJson.get("total") + "张");
        }
    }
}