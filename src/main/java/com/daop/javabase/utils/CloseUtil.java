package com.daop.javabase.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase
 * @Description:
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 **/
public class CloseUtil {
    public static void close(Closeable... close) {
        try {
            for (Closeable closeable : close) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            System.err.println("连接关闭出现异常");
            e.printStackTrace();
        }
    }
}