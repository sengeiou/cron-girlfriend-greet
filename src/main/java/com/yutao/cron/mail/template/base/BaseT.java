package com.yutao.cron.mail.template.base;

/**
 * @author :RETURN
 * @date :2021/10/14 10:55
 */
public abstract class BaseT {

    protected String label;

    public String preHtmlLabel(){
        return "<"+label+">";
    }

    public String sufHtmlLabel(){
        return "</"+label+">";
    }

    public abstract String getTemplate();
}
