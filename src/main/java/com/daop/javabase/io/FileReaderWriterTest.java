package com.daop.javabase.io;

import com.daop.javabase.PathConstants;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.io
 * @Description: IO流代码
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 * I/O原理
 * I/O为Input/Output的缩写，用于处理设备之间的数据传输。
 * 对于数据输入/输出操作以“流（stream）”方式进行，
 * 通过java.io包下的各种“流”类和接口提供的标准方法进行输入/输出以及获取不同种类的数据
 * <p>
 * 输入input：读取外部数据到程序中
 * 输出output：将程序中数据输出到磁盘、光盘等存储设备中
 * <p>
 * 流分类
 * 数据单位：字节流（8bit）非文本、字符流（16bit）文本
 * 流向：输入流、输出流
 * 角色：节点流、处理流
 * 抽象基本类     字节流     字符流
 * 输入流      InputStream  Reader
 * 输出流      OutputStream Writer
 * <p>
 * 抽象基类          节点流（或文件流）                                  缓冲流（处理流的一种）
 * InputStream      FileInputStream  read(byte[] buffer)            BufferInputStream  read(byte[] buffer)
 * OutputStream     FileOutputStream write(byte[] buffer,0,len)     BufferOutputStream write(byte[] buffer,0,len)
 * Reader           FileReader       read(char[] buffer)            BufferReader       read(char[] buffer)/readLine()
 * Writer           FileWriter       write(char[] cbuf,0,len)     BufferWriter       write(char[] cbuf,0,len)
 **/
public class FileReaderWriterTest {

    /**
     * 读取文件内容1
     */
    @Test
    public void fileReaderTest() {
        FileReader fr = null;
        try {
            //实例化File类对象，指明操作文件
            File file1 = new File(PathConstants.FILE_PATH + "OtherThesaurus.txt");
            //提供具体的流
            fr = new FileReader(file1);
            //数据读入 read(): 返回读入的类型，如果达到文件末尾返回-1
            //方式一
            /*int data = fr.read();
            while (data != -1) {
                System.out.print((char) data);
                data = fr.read();
            }*/
            //方式二
            int data1;
            while ((data1 = fr.read()) != -1) {
                System.out.print((char) data1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //流的关闭
            try {
                assert fr != null;
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件内容2
     */
    @Test
    public void fileReaderTest1() {
        FileReader fr = null;
        try {
            //1.File实例化
            File file1 = new File(PathConstants.FILE_PATH + "hello.txt");

            //2.FileReader流的实例化
            fr = new FileReader(file1);

            //3.读入操作 read(char[] cbuf): 返回每次读入数组中的字符的个数，如果达到文件末尾返回-1
            char[] cb = new char[5];
            int len;
            while ((len = fr.read(cb)) != -1) {
                for (int i = 0; i < len; i++) {
                    System.out.print(cb[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.资源关闭
            try {
                assert fr != null;
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把内存中的数据写到硬盘上
     * File对应的文件若不存在，在输出的过程中，会自动创建文件
     * File对应的文件若存在，且流使用的构造器是 FileWriter(file,false)/FileWriter(file)：对原文件进行覆盖
     * 若流使用的构造器是 FileWriter(file,true)：不会覆盖原文件，而是在原文件后面进行追加
     */
    @Test
    public void fileWriterTest() {
        FileWriter fw = null;
        try {
            //1.File实例化，指明写出的文件
            File file1 = new File(PathConstants.FILE_PATH + "hello1.txt");
            //2.提供FileWriter对象，用于数据写出
            fw = new FileWriter(file1, true);
            //3.写出的操作
            fw.write("I have a dreams!\n");
            fw.write("I have two dreams!\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流资源
            try {
                assert fw != null;
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文本文件复制
     */
    @Test
    public void FileReaderAndWriterTest() {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            //1.File实例化，指明读入和写出的文件
            File srcFile = new File(PathConstants.FILE_PATH + "hello1.txt");
            File destFile = new File(PathConstants.FILE_PATH + "hello2.txt");

            //2.创建输入流和输出流的对象
            fr = new FileReader(srcFile);
            fw = new FileWriter(destFile);

            //3.数据的读入和写出操作
            char[] cbuf = new char[5];
            int len;
            while ((len = fr.read(cbuf)) != -1) {
                fw.write(cbuf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流资源
            try {
                assert fw != null;
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert fr != null;
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
