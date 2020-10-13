package com.daop.javabase.io;

import com.daop.javabase.PathConstants;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.io
 * @Description: 缓冲流的使用
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 * <p>
 * 1.缓冲流
 * BufferInputStream
 * BufferOutputStream
 * BufferReader
 * BufferWriter
 * <p>
 * 2.作用：提供流的读取，写入的速度。原因：内部提供了一个缓冲区
 * 3.处理流：就是“套接”在已有的流的基础上
 **/
public class BufferTest {

    /**
     * 实现非文本文件的复制（字节流）
     */
    @Test
    public void bufferStreamTest() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //选择文件
            File srcFile = new File(PathConstants.PIC_PATH + "2d93140e27e2b8310ca2bb84c6c26ab7.jpg");
            File destFile = new File(PathConstants.PIC_PATH + "CopyImg1.jpg");
            //创造节点流
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            //缓冲流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流的时候先关外面的流，再关闭内层的流
            //关闭外层流的同时，内层流也会自动进行关闭，关于内层流的关闭，可以省略
            try {
                assert bos != null;
                bos.close();

                bis.close();

                fis.close();

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
        String destPath = PathConstants.PIC_PATH + "CopyImg1.jpg";
        copyFileWithBuffered(srcPath, destPath);
        long end = System.currentTimeMillis();
        System.out.println("复制操作花费时间：" + (end - start));//537
    }

    /**
     * 通过缓冲流对文件进行复制
     *
     * @param srcPath  source Path
     * @param destPath target Path
     */
    public void copyFileWithBuffered(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //选择文件
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);
            //创造节点流
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            //缓冲流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流的时候先关外面的流，再关闭内层的流
            //关闭外层流的同时，内层流也会自动进行关闭，关于内层流的关闭，可以省略
            try {
                assert bos != null;
                bos.close();

                bis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void bufferReaderAndWritTest() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(PathConstants.FILE_PATH + "OtherThesaurus.txt"));
            bw = new BufferedWriter(new FileWriter(PathConstants.FILE_PATH + "OtherThesaurus1.txt"));
            //方式一
            char[] cbuf = new char[1024];
            int len;
            while ((len = br.read(cbuf)) != -1) {
                bw.write(cbuf, 0, len);
            }
            //方式二
            String data;
            while ((data = br.readLine()) != null) {
                bw.write(data);
                bw.newLine();//换行
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                assert bw != null;
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
