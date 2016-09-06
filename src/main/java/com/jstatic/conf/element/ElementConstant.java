package com.jstatic.conf.element;

/**
 * @author 谢俊权
 * @create 2016/9/6 15:50
 */
public enum ElementConstant {

    STATIC("static"),
    CONFIG("config"),
    FROM("from"),
    TO("to"),
    THREAD("max-thread"),
    SCHEDULERS("schedulers"),
    SCHEDULER("scheduler"),
    JOB("job");

    private final String e;

    private ElementConstant(String e) {
        this.e = e;
    }

    public String e() {
        return e;
    }
}
