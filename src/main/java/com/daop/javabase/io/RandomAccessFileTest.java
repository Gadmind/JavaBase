package com.daop.javabase.io;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.io
 * @Description: RandomAccessFile的使用
 * @DATE: 2020-10-13 23:23
 * @AUTHOR: Daop
 *
 * RandomAccessFile既可以作为输入流，又可以作为输出流
 * 继承java.lang.Object类，实现DataInput和DataOutput接口
 * 如果RandomAccessFile作为输出流时，写出到的文件不存在则自动创建
 * 如果文件存在，则会覆盖文件原有内容，从头覆盖
 * 同通过seek()方法可以实现“插入”的功能
 *
 **/
public class RandomAccessFileTest {
}
