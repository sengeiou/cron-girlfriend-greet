package com.yutao.cron.mail.template;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yutao.cron.mail.template.base.BaseT;
import com.yutao.cron.util.HttpHelper;

/**
 * @author :RETURN
 * @date :2021/10/14 11:01
 */
public class OneSentenceT extends BaseT {

    private final static String ONE_API = "https://v1.hitokoto.cn";
    private String content;

    public OneSentenceT(){
        requestOneSentence();
    }

    private void requestOneSentence(){
        JSONObject jsonObject = HttpHelper.get(ONE_API)
                .execute()
                .getJson();
         content = (String) JSONPath.eval(jsonObject,"$.hitokoto");
    }

    private String getContent(){
        return "<div style=\"color:#757575\">"+content+"</div>";
    }

    private String getHead(){
        return "<div>每日一句</div>";
    }

    @Override
    public String getTemplate() {
        return getHead()+getContent();
    }
}
