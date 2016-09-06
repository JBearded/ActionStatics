package com.jstatic.conf.element;

import java.util.List;

/**
 * static配置scheduler节点信息
 *
 * @author 谢俊权
 * @create 2016/1/30 16:27
 */
public class SchedulerElement {

    private String name;
    private int delay;
    private int interval;
    private int timeout;
    private List<JobElement> jobList;

    public SchedulerElement(String name, int delay, int interval, int timeout, List<JobElement> jobList) {
        this.name = name;
        this.delay = delay;
        this.interval = interval;
        this.timeout = timeout;
        this.jobList = jobList;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<JobElement> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobElement> jobList) {
        this.jobList = jobList;
    }

    public void setJob(JobElement job){
        this.jobList.add(job);
    }
}
