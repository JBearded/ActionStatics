package com.jstatic.conf.element;

/**
 * static配置job节点信息
 *
 * @author 谢俊权
 * @create 2016/1/30 16:34
 */

public class JobElement {

    private String action;
    private String file;

    public JobElement(String action, String file){
        this.action = action;
        this.file = file;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
