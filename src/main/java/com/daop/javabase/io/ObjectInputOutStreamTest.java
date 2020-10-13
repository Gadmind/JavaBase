package com.daop.javabase.io;

import com.daop.javabase.PathConstants;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.io
 * @Description: 对象流的使用
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 * <p>
 * ObjectInputStream 和 ObjectOutputStream
 * 用于存储和读取基本数据类型数据或对象的处理流。可以把java对象写入到数据源中，也能从数据源中还原回来
 **/
public class ObjectInputOutStreamTest {
    /**
     * 序列化：将内存中的对象保存到磁盘中或通过网络传输
     * 使用ObjectOutputStream实现
     */
    @Test
    public void objectOutputStreamTest() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(PathConstants.FILE_PATH + "object.dat"));
            oos.writeObject(new String("这是一个字符串"));
            oos.flush();
            oos.writeObject(new Person("name", 123,1));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert oos != null;
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反序列化：将磁盘中或通过网络传输的对象还原为内存中的对象
     * 使用ObjectInputStream实现
     * 除了当前Person类需要实现Serializable接口之外，还必须保证其内部所有属性也是可序列化的（默认情况下，基本数据类型都可以序列化）
     * 被static 和 transient修饰的成员变量不能被序列化
     * 序列化机制
     */
    @Test
    public void objectInputStreamTest() {

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstants.FILE_PATH + "object.dat"));
            Object obj = ois.readObject();
            String str = (String) obj;
            Person p = (Person) ois.readObject();
            System.out.println(str);
            System.out.println(p.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert ois != null;
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 满足如下要求可以实现序列化
 * Serializable：标识接口
 * 提供全局常量SerialVersionUID
 */
class Person implements Serializable {
//    public static final long SerialVersionUID = 123465L;

    private String name;
    private int age;
    private int id;
    private int tt;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public Person() {
    }
}
