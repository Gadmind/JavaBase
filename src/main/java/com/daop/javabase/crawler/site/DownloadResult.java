package com.daop.javabase.crawler.site;

import java.io.Serializable;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler.site
 * @Description: 下载失败的数据信息
 * @DATE: 2020-10-16
 * @AUTHOR: Administrator
 **/
public class DownloadResult implements Serializable {
    /**
     * 地址
     */
    private String fileUrl;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 失败原因
     */
    private String failReason;

    public DownloadResult(String fileUrl, String fileName, String failReason) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.failReason = failReason;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFailReason() {
        return failReason;
    }

    @Override
    public String toString() {
        return "下载失败{ 地址："+ fileUrl  +" , 文件名称：" + fileName + " 原因：" + failReason+"}";
    }
}
