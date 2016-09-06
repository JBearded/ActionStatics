package com.jstatic.conf.element;

/**
 * static配置信息
 *
 * @author 谢俊权
 * @create 2016/1/30 16:22
 */
public class ConfigElement {

    private String from;
    private String to;
    private int maxThread;

    public ConfigElement(){

    }
    public ConfigElement(String from, String to, int maxThread) {
        this.from = from;
        this.to = to;
        this.maxThread = maxThread;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
