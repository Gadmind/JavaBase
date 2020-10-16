package com.daop.javabase.crawler.site;

import java.io.Serializable;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description: 360壁纸参数
 * @DATE: 2020-10-16
 * @AUTHOR: Administrator
 **/
public class WallPaper implements Serializable {
    /**
     * 类别ID
     */
    String cid;
    /**
     * 类别名称
     */
    String category;
    /**
     * 过滤标签
     */
    String tag;

    public WallPaper(String cid, String category, String tag) {
        this.cid = cid;
        this.category = category;
        this.tag = tag;
    }

    public String getCid() {
        return cid;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }
}
