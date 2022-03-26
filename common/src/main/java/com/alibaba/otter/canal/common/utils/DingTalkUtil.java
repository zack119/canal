package com.alibaba.otter.canal.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @author liubin
 * @date 2022/03/16 18:37
 */
public class DingTalkUtil {

    private static final Logger logger  = LoggerFactory.getLogger(DingTalkUtil.class);

    public static final String DING_TALK_URL = "https://oapi.dingtalk.com/robot/send?access_token=798421da7325fca646eb8983b6cc0c6dfbf512ee63040e1c0f90273836749b9c";

    public static void main(String[] args) {
        pushMessage("test");
    }

    public static String pushMessage(String content) {
        return pushMessage(DING_TALK_URL, content);
    }

    public static String pushMessage(String dingTalkUrl, String content) {
        StringBuilder stringBuilder = new StringBuilder("Canal云监控报警\n");
        String ip = IpUtil.getIp();
        stringBuilder.append("IP: ").append(ip).append("\n ").append(content);
        Map<String, String> contentMap = new HashMap<>(16);
        contentMap.put("content", stringBuilder.toString());
        Map<String, Object> param = new HashMap<>(16);
        param.put("msgtype", "text");
        param.put("text", contentMap);
        String body = JSON.toJSONString(param);
        logger.info("dingtalk push message: {}", body);

        return HttpUtil.post(dingTalkUrl, body);
    }

    public static String pushMsgAsMarkdown(String dingTalkUrl, String title, String markdown) {
        Map<String, String> contentMap = new HashMap<>(16);
        contentMap.put("text", markdown);
        contentMap.put("title", title);

        Map<String, String> param = new HashMap<>(16);
        param.put("msgtype", "markdown");
        param.put("markdown", JSON.toJSONString(contentMap));
        String body = JsonUtils.toJson(param);

        return HttpUtil.post(dingTalkUrl, body);
    }
}
