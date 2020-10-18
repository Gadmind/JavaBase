package com.daop.javabase.crawler;

import com.daop.javabase.crawler.site.WallPaper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description: 爬虫测试
 * @DATE: 2020-10-16
 * @AUTHOR: Administrator
 **/
public class CrawlerTest {

    private static ExecutorService threadPool = new ThreadPoolExecutor(
            5, 20, 10L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(5), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );
    private static List<WallPaper> types = new ArrayList<>(32);

    private static void putData() {
        types.add(new WallPaper("5", "游戏", ""));
        types.add(new WallPaper("6", "女生", ""));
        types.add(new WallPaper("7", "影视", ""));
        types.add(new WallPaper("9", "风景", ""));
        types.add(new WallPaper("10", "时尚", ""));
        types.add(new WallPaper("11", "明星", ""));
        types.add(new WallPaper("12", "汽车", ""));
        types.add(new WallPaper("13", "节日", ""));
        types.add(new WallPaper("14", "萌宠", ""));
        types.add(new WallPaper("15", "小清新", ""));
        types.add(new WallPaper("16", "体育", ""));
        types.add(new WallPaper("18", "BABY秀", ""));
        types.add(new WallPaper("22", "军事", ""));
        types.add(new WallPaper("26", "动漫", ""));
        types.add(new WallPaper("29", "月历", ""));
        types.add(new WallPaper("30", "爱情", ""));
        types.add(new WallPaper("35", "文字控", ""));
        types.add(new WallPaper("36", "4K", ""));
    }

    public static void main(String[] args) {
//        types.add(new WallPaper("5", "游戏", ""));
//        types.add(new WallPaper("36", "4K", ""));
//        types.add(new WallPaper("35", "文字控", ""));
        types.add(new WallPaper("6", "女生", ""));
        try {
            for (WallPaper wpt : types) {
                threadPool.execute(() -> {
                    CrawlerWallPaper myCrawler = new CrawlerWallPaper();
                    String cid = wpt.getCid();
                    String type = wpt.getCategory();
                    String tag = wpt.getTag();
                    int start = 0;
                    int everyCount = 200;
                    int cycle = 0;
                    String downloadPath = "F:" + File.separator + "Crawler Img" + File.separator;
                    myCrawler.startCrawler(cid, type, tag, start, everyCount, cycle, downloadPath);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

