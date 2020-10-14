package com.daop.javabase.netcoding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.daop.javabase.PathConstants;
import com.daop.javabase.utils.CloseUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description: 爬虫
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 **/
public class MyCrawler {

    /**
     * 图片：https://www.mpyit.com/html/ritu/api.php?cid=6&start=0&count=100
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 总条数 7237
        int count = 7237;
        //每次采取条数
        int everyCount = 50;
        //图片分类
        int cid=6;

        //循环次数=(总条数/每次采取条数)+1
        int i = (count / everyCount) + 1;
        HttpURLConnection urlConnection = null;
        FileWriter fw = null;
        //1.File实例化，指明写出的文件
        File file1 = new File(PathConstants.FILE_PATH + "Pic.html");
        //2.提供FileWriter对象，用于数据写出
        fw = new FileWriter(file1, true);
        //3.写出的操作
        fw.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n");

        for (int i1 = 0; i1 < i; i1++) {
            System.out.println("开始第"+(i1+1)+"次访问");
            String imgurl = "https://www.mpyit.com/html/ritu/api.php?cid="+cid+"&start=" + (everyCount * i1) + "&count=" + everyCount;
            URL url = new URL(imgurl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
            urlConnection.connect();
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.US_ASCII));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            //FIXME 若不是JSON对象怎么处理
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());

            List<JSONObject> data = JSONArray.parseArray(jsonObject.get("data").toString(), JSONObject.class);

            for (JSONObject datum : data) {
                fw.write("<img src='" + datum.get("img_1600_900") + "' height='50px' width='50px' alt=''/>");
            }
        }

        fw.write("</body>\n" +
                "</html>");
        CloseUtil.close(fw);
        urlConnection.disconnect();
    }

    @Test
    public void Test() {
        int i = 7237 / 50;
        System.out.println(i + 1);
    }
}
