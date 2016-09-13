package com.jstatic.conf.element;

/**
 * static配置文件中的元素属性常量
 *
 * @author 谢俊权
 * @create 2016/1/30 21:07
 */
public enum AttributeConstant {

    DELAY("delay"),
    INTERVAL("interval"),
    TIMEOUT("timeout"),
    NAME("name"),
    ACTION("action"),
    FILE("file");

    private final String a;

    AttributeConstant(String a) {
        this.a = a;
    }

    public String a() {
        return a;
    }
}
