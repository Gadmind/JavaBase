package com.daop.javabase.crawler.site;

import com.daop.javabase.crawler.enums.ErrorEnum;

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
     * 图片类别
     */
    private String category;

    /**
     * 失败原因枚举
     */
    private ErrorEnum failReason;
    /**
     * 其他失败原因
     */
    private String reason;

    public DownloadResult(String category, String fileName, String url) {
        this.category = category;
        this.fileName = fileName;
        this.fileUrl = url;
    }

    public DownloadResult(String fileUrl, String fileName, ErrorEnum failReason) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.failReason = failReason;
    }


    public DownloadResult(String fileUrl, String fileName, ErrorEnum failReason, String reason) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.failReason = failReason;
        this.reason = reason;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public ErrorEnum getFailReason() {
        return failReason;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFailReason(ErrorEnum failReason) {
        this.failReason = failReason;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        if (failReason == ErrorEnum.OTHER) {
            return "下载失败{ 文件分类：" + category + " 地址：" + fileUrl + " , 文件名称：" + fileName + " 原因：" + failReason + "：" + reason + "}";
        }
        return "下载失败{ 文件分类：" + category + " 地址：" + fileUrl + " , 文件名称：" + fileName + " 原因：" + failReason + "}";
    }

}
