package com.daop.javabase;

import java.io.File;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase
 * @Description: 路径通用常量
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 **/
public class PathConstants {
    /**
     * 图片存放路径
     */
    public static final String PIC_PATH = System.getProperty("user.dir") + File.separator + "files" + File.separator + "pic" + File.separator;
    /**
     * 文件存放路径
     */
    public static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "files" + File.separator;
}
