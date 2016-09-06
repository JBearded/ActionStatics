package com.jstatic.task;

import com.jstatic.conf.element.ConfigElement;
import com.jstatic.conf.element.JobElement;
import com.jstatic.conf.element.SchedulerElement;
import com.jstatic.conf.element.StaticElement;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态化处理
 *
 * @author 谢俊权
 * @create 2016/1/31 13:44
 */
public class SchedulerTask implements Runnable {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SchedulerTask.class);

    private JobThreadExecutor jobThreadExecutor;

    private List<JobInfo> schedulerList = new ArrayList<>();

    public SchedulerTask(JobThreadExecutor jobThreadExecutor, StaticElement staticE) {
        this.jobThreadExecutor = jobThreadExecutor;
        initStaticConfig(staticE);
    }

    private void initStaticConfig(StaticElement staticE){
        ConfigElement configElement = staticE.getConfigElement();
        List<SchedulerElement> schedulerElementList = staticE.getSchedulerElementList();
        for (SchedulerElement schedulerElement: schedulerElementList) {
            initSchedulerConfig(configElement, schedulerElement);
        }
    }

    private void initSchedulerConfig(ConfigElement configElement, SchedulerElement schedulerElement){
        List<JobElement> jobElementList = schedulerElement.getJobList();
        for (JobElement jobElement : jobElementList) {
            JobInfo info = new JobInfo();
            info.setTimeout(schedulerElement.getTimeout());
            info.setAction(configElement.getFrom());
            info.setFile(configElement.getTo());
            initJobConfig(info, jobElement);
        }
    }

    private void initJobConfig(JobInfo info, JobElement jobElement){
        String action = info.getAction() + jobElement.getAction();
        String file = info.getFile() + jobElement.getFile();
        info.setAction(action);
        info.setFile(file);
        schedulerList.add(info);
        logger.info("finish init job config...");
    }

    @Override
    public void run() {
        for(JobInfo info : schedulerList){
            JobTask job = new JobTask(info);
            jobThreadExecutor.run(job);
        }
    }

}
