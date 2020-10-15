package com.daop.javabase.netcoding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.daop.javabase.netcoding.MyCrawler.getUrlMessage;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description:
 * @DATE: 2020-10-15
 * @AUTHOR: Administrator
 * <p>
 * 默认方式：default
 * 时间：updated
 * https://www.zhihu.com/api/v4/questions/30460976/answers?offset=&limit=3&sort_by=default&platform=desktop
 **/
public class CrawlerZhiHu {
    public static final String ZHI_HU_INCLUDE = "include=data[*].is_normal,admin_closed_comment,reward_info,is_collapsed,annotation_action,annotation_detail,collapse_reason,is_sticky,collapsed_by,suggest_edit,comment_count,can_comment,content,editable_content,voteup_count,reshipment_settings,comment_permission,created_time,updated_time,review_info,relevant_info,question,excerpt,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp,is_labeled,is_recognized,paid_info,paid_info_content;data[*].mark_infos[*].url;data[*].author.follower_count,badge[*].topics";

    public static void main(String[] args) {
        //情侣头像 30460976
        //手机壁纸 310217875
        long questionsId = 310217875;
        int offset = 0;
        int limit = 10;

        String sortBy = "default";
        String platform = "desktop";
        String zhiHuQuestionsUrl = "https://www.zhihu.com/api/v4/questions/" + questionsId + "/answers?" + ZHI_HU_INCLUDE + "&offset=" + offset + "&limit=" + limit + "&sort_by=" + sortBy + "&platform=" + platform;
        System.out.println(zhiHuQuestionsUrl);
        JSONObject zhJsonObj = getUrlMessage(zhiHuQuestionsUrl, StandardCharsets.UTF_8);
        JSONObject pageObj = JSON.parseObject(zhJsonObj.get("paging").toString(), JSONObject.class);
        System.out.println(pageObj.get("totals"));

        List<JSONObject> zhAnswers = JSON.parseArray(zhJsonObj.get("data").toString(), JSONObject.class);
        long count = 0;
        for (JSONObject zhAnswer : zhAnswers) {
            String content = (String) zhAnswer.get("content");
            String reg = "<img src=\"http(.*?)\"";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                System.out.println(matcher.group().replaceAll("<img src=\"", "").replaceAll("\\?(.*)", ""));
                count++;
            }

        }
        System.out.println("共" + count + "张");
    }
}
