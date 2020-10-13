package com.daop.javabase.file;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase
 * @Description: IO练习
 * @DATE: 2020-10-12
 * @AUTHOR: Administrator
 * <p>
 * File类的使用
 * <p>
 * 1.File类的一个对象，代表一个文件或一个文件目录（文件夹）
 * 2.File类声明在java.io包下
 * 3.File类中涉及到关于文件或文件目录的创建、删除、重名名、修改删除、文件大小等方法；未涉及写/读文件内容操作，如需要读取或写入文件，需要使用IO流完成
 * 4.File类对象常会作为参数传递到流的构造器中，指明读/写的“终点”
 **/
public class FileDemo {
    public static String path = System.getProperty("user.dir") + "\\files\\";

    /**
     * 创建File类实例
     * 相对路径：相较于某个路径下，指明的路径
     * 绝对路径：包含盘符在内的文件或文件目录的路径
     * 路径分隔符：Windows：\\；unix：/
     * IDEA中JUnit单元测试中相对路径为当前Module下；main()相对路径为当前Project下
     * Eclipse中JUnit单元测试和main()相对路径为当前Project下
     */
    @Test
    public void fileTest1() {
        File file1 = new File(File.separator + "files" + File.separator + "hi.txt");
        File file2 = new File(path + "hi.txt");
        System.out.println(file1);
        System.out.println(file2);

        File file3 = new File("\\files", "JavaSenior");
        System.out.println(file3);

        File file4 = new File(file3, "hi.txt");
        System.out.println(file4);
    }

    /**
     * File的获取功能方法
     * String getAbsolutePath(): 获取绝对路径
     * String getPath(): 获取路径
     * String getName(): 获取名称
     * String getParent(): 获取上层文件目录路径。若无，返回null
     * Long length(): 获取文件长度（即字节数）。不能获取目录的长度
     * Long lastModified(): 获取最后一次的修改时间，毫秒数
     */
    @Test
    public void fileGetMethods() {
        File file1 = new File( File.separator + "files" + File.separator + "hi.txt");
        File file2 = new File(path + "hi.txt");
        System.out.println("绝对路径: " + file1.getAbsolutePath());
        System.out.println("路径: " + file1.getPath());
        System.out.println("名称: " + file1.getName());
        System.out.println("目录路径: " + file1.getParent());
        System.out.println("文件长度: " + file1.length());
        System.out.println("修改时间: " + file1.lastModified());
        System.out.println("================");
        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.getPath());
        System.out.println(file2.getParent());
        System.out.println(file2.getName());
        System.out.println(file2.length());
        System.out.println(file2.lastModified());
    }

    /**
     * 文件目录操作方法
     * String[] list(): 获取指定目录下的所有文件或者文件目录的名称数组
     * Files[] listFiles(): 获取指定目录下的所有文件或文件目录的File数组
     */
    @Test
    public void filesDirGetMethod() {
        File file = new File(System.getProperty("user.dir"));
        String[] list = file.list();
        assert list != null;
        for (String s : list) {
            System.out.println(s);
        }
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            System.out.println(f);
        }
    }

    /**
     * 文件重命名
     * boolean renameTo(File dest): 把文件重命名为指定的文件路径
     * 保证返回true，file1要在硬盘中存在且file2不存在
     */
    @Test
    public void fileRenameMethod() {
        File file1 = new File(path + "hi1.txt");
        File file2 = new File(path + "hi2.txt");
        boolean renameTo = file1.renameTo(file2);
        System.out.println(renameTo);
    }

    /**
     * File的判断
     * boolean isDirectory(): 是否是文件目录
     * boolean isFile(): 是否是文件
     * boolean exists(): 是否存在
     * boolean canRead(): 是否可读
     * boolean canWrite(): 是否可写
     * boolean isHidden(): 是否隐藏
     */
    @Test
    public void fileJudge() {
        File file1 = new File(path + "hi2.txt");
        System.out.println("是否是文件目录:" + file1.isDirectory());
        System.out.println("是否是文件:" + file1.isFile());
        System.out.println("是否存在:" + file1.exists());
        System.out.println("是否可读:" + file1.canRead());
        System.out.println("是否可写:" + file1.canWrite());
        System.out.println("是否隐藏:" + file1.isHidden());
    }

    /**
     * 文件创建
     * boolean createNewFile(): 创建文件，若文件存在则不创建，返回false
     * boolean delete(): 删除硬盘中的文件或文件目录，不走回收站
     */
    @Test
    public void fileCreate() throws IOException {
        File file1 = new File(path + "hi3.txt");
        if (!file1.exists()) {
            boolean fileCreate = file1.createNewFile();
            System.out.println("Create Success!!");
        } else {
            boolean fileDelete = file1.delete();
            System.out.println("Delete Success!!");
        }
    }

    /**
     * 文件目录的创建
     * boolean mkdir(): 创建文件目录，如果文件存在就不创建了，若文件上层路径不存在则不创建
     * boolean mkdirs(): 创建文件目录，如果文件上层不存在，一并创建。
     */
    @Test
    public void fileDirCreate() {
        File file1 = new File(path + "files1\\file3");
        boolean mkdir1 = file1.mkdir();
        if (mkdir1) {
            System.out.println("files1 Create Success!!");
        }

        File file2 = new File(path + "files2\\file4");
        boolean mkdir2 = file2.mkdirs();
        if (mkdir2) {
            System.out.println("files2 Create Success!!");
        }
    }
}
