package com.daop.javabase.crawler.enums;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler.enums
 * @Description:
 * @DATE: 2020-10-17 11:56
 * @AUTHOR: Daop
 **/
public enum ErrorEnum {
    PROTOCOL_ERROR("协议错误"),
    FILE_NOT_FOUND("文件未找到"),
    OTHER("");

    private final String reason;

    public String getReason() {
        return reason;
    }

    ErrorEnum(String reason) {
        this.reason = reason;
    }
}
