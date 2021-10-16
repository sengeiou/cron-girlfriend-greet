package com.yutao.cron.mail;

import com.yutao.cron.entity.Color;
import com.yutao.cron.mail.template.CurrentDateT;
import com.yutao.cron.mail.template.OneSentenceT;
import com.yutao.cron.mail.template.WeatherT;
import com.yutao.cron.mail.template.base.BaseT;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * @author :RETURN
 * @date :2021/10/14 10:38
 */
@Component
public class GreetMailTemplate {

    public String create(){
        return wrapTemplate(
                new CurrentDateT(),
                new WeatherT(),
                new OneSentenceT()
                );
    }

    private String wrapTemplate(BaseT... ts) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(ts).map(BaseT::getTemplate).forEach(sb::append);
        return "<div style=\"width:300px;height:500px;color: rgba(0,0,0,.87);border-radius: 4px!important; " +
                "box-shadow: 0 3px 1px -2px rgba(0,0,0,.2),0 2px 2px 0 rgba(0,0,0,.14),0 1px 5px 0 rgba(0,0,0,.12)!important;" +
                "background-color: "+ Color.randomBgColor() +" !important; border-color: #fff!important;font-family: 思源黑体,微软雅黑,sans-serif;" +
                "font-weight:300;font-size:1.4rem;line-height: 1.5;padding: 20px 16px;margin: 0 auto;\">"+
                sb.toString()
                +"</div>";
    }


}
