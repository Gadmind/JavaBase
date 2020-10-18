package com.daop.javabase.crawler.enums;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description: 文件枚举
 * @DATE: 2020-10-17 11:11
 * @AUTHOR: Daop
 **/
public enum FileEnum {
    /**
     * JPG文件
     */
    JPG(".jpg"),
    /**
     * PNG文件
     */
    PNG(".png"),
    ;

    /**
     *
     */
    private String fileType;

    public String getFileType() {
        return fileType;
    }

    FileEnum(String fileType) {
        this.fileType = fileType;
    }
}
