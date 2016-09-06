package com.jstatic.task;

/**
 * 静态化的定时信息
 *
 * @author 谢俊权
 * @create 2016/1/31 19:01
 */
public class JobInfo {

    private String action;
    private String file;
    private int timeout;

    public JobInfo(){

    }
    public JobInfo(String action, String file, int timeout){
        this.action = action;
        this.file = file;
        this.timeout =timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
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
