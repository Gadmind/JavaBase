package com.daop.javabase.crawler;

import com.daop.javabase.crawler.utils.CrawlerUtils;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description:
 * @DATE: 2020-10-17 16:08
 * @AUTHOR: Daop
 **/
public class MusicCrawler {
    public static void main(String[] args) {
//      小兔和狼  https://webfs.yun.kugou.com/202010171611/8667dbcf84cd88faff330db6e5abeb71/G193/M06/06/0F/oZQEAF5i5vGAC6DsACTUdCQbkpg555.mp3
//      初音未来-甩葱歌  https://webfs.yun.kugou.com/202010171605/b46d835618d5c29c4aa9951157e863cd/G201/M00/14/03/aYcBAF5oNEKAEfC7ACO_BWLHvXc906.mp3
//      Purple Passion  https://webfs.yun.kugou.com/202010171613/ca283f4e95035ad6e71c456463b6e51d/G202/M01/0D/05/Cg4DAF5319CAPzRfADf7bd79W0w013.mp3
//      狗熊与面包  https://webfs.yun.kugou.com/202010171613/9060c66c62acc4463312587505696a41/G180/M00/08/17/lJQEAF4W70eIQwLQAAs3sQkNg9YAABe8wGvOmYACzfJ362.mp3
//      闲聊波尔卡  https://webfs.yun.kugou.com/202010171615/ccc6220a9f5a5e18bc7441b176d2a5a7/G196/M0B/0D/07/pJQEAF56bkaAOhrSAClmicmW-2Q352.mp3
//      瑞典狂想曲  https://webfs.yun.kugou.com/202010171624/a2d6fd7edbbd1a238db9adbe11905391/G196/M0B/10/07/ZIcBAF5WmTaAPFR0AC6BpFSW3kI043.mp3

        String url = "https://webfs.yun.kugou.com/202010171624/a2d6fd7edbbd1a238db9adbe11905391/G196/M0B/10/07/ZIcBAF5WmTaAPFR0AC6BpFSW3kI043.mp3",
                filePath = "F:\\music\\",fileName="瑞典狂想曲";
        CrawlerUtils.downloadImg(url, filePath,fileName+".mp3");
    }
}
