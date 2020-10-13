package com.daop.javabase.io;

import com.daop.javabase.PathConstants;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.io
 * @Description: FileInputStream和FileOutputStream测试
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 * 文本文件（txt、java、c、cpp），使用字符流处理
 * 非文本文件（jpg、mp3、mp4、avi、doc、ppt、...），使用字节流处理
 * <p>
 * 使用字节流处理文本文件可能出现乱码
 *
 **/
public class FileInputOutputTest {

    /**
     * 实现对图片复制
     */
    @Test
    public void fileInputStream() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File srcFile = new File(PathConstants.PIC_PATH + "2d93140e27e2b8310ca2bb84c6c26ab7.jpg");
            File destFile = new File(PathConstants.PIC_PATH + "CopyImg.jpg");

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            byte[] buffer = new byte[5];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fis != null;
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void copyFileTest() {
        long start = System.currentTimeMillis();
        String srcPath = PathConstants.PIC_PATH + "2d93140e27e2b8310ca2bb84c6c26ab7.jpg";
        String destPath = PathConstants.PIC_PATH + "CopyImg.jpg";
        copyFile(srcPath, destPath);
        long end = System.currentTimeMillis();
        System.out.println("复制操作花费时间：" + (end - start));
    }


    /**
     * 字节流文件拷贝方法
     * Non-text file Copy
     *
     * @param srcPath  source Path
     * @param destPath target path
     */
    public void copyFile(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fis != null;
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
