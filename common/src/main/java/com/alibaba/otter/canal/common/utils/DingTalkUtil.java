package com.alibaba.otter.canal.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liubin
 * @date 2022/03/16 18:37
 */
public class DingTalkUtil {

    private static final String dingTalkUrl = "";

    public String pushMessage(String content) {
        return pushMessage(dingTalkUrl, content);
    }

    public String pushMessage(String dingTalkUrl, String content) {
        Map<String, String> contentMap = new HashMap<>(16);
        contentMap.put("content", content);

        Map<String, String> param = new HashMap<>(16);
        param.put("msgtype", "text");
        param.put("text", JsonUtils.toJson(contentMap));
        String body = JsonUtils.toJson(param);

        return HttpUtil.post(dingTalkUrl, body);
    }

    public String pushMsgAsMarkdown(String dingTalkUrl, String title, String markdown) {
        Map<String, String> contentMap = new HashMap<>(16);
        contentMap.put("text", markdown);
        contentMap.put("title", title);

        Map<String, String> param = new HashMap<>(16);
        param.put("msgtype", "markdown");
        param.put("markdown", JsonUtils.toJson(contentMap));
        String body = JsonUtils.toJson(param);

        return HttpUtil.post(dingTalkUrl, body);
    }
}
