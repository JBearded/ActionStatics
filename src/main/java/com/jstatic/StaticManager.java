package com.jstatic;

import com.jstatic.conf.StaticConfigParser;
import com.jstatic.conf.element.SchedulerElement;
import com.jstatic.conf.element.StaticElement;
import com.jstatic.task.SchedulerTask;
import com.jstatic.task.BaseThreadFactory;
import com.jstatic.task.JobThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 谢俊权
 * @create 2016/1/30 16:55
 */
public class StaticManager {

    private static final Logger logger = LoggerFactory.getLogger(StaticManager.class);

    private List<StaticElement> staticConfigs = new ArrayList<>();

    private List<JobThreadExecutor> jobThreadExecutors = new ArrayList<>();

    public StaticManager(String... configs) {
        initConfig(configs);
    }

    private void initConfig(String... configs) {
        logger.info("init config begin......");
        try{
            for (int i = 0; i < configs.length; i++) {
                String configFileName = configs[i];
                StaticElement staticConfig = (new StaticConfigParser()).parse(configFileName);
                int maxThread = staticConfig.getConfigElement().getMaxThread();
                JobThreadExecutor jobThreadExecutor = new JobThreadExecutor(maxThread);
                jobThreadExecutors.add(jobThreadExecutor);
                staticConfigs.add(staticConfig);
            }
        }catch (Exception e){
            logger.error("error init config......");
            throw new RuntimeException(e);
        }
        logger.info("init config end......");
    }

    public void run(){

        for (int i = 0; i < staticConfigs.size(); i++) {
            StaticElement staticConfig = staticConfigs.get(i);
            JobThreadExecutor jobThreadExecutor = jobThreadExecutors.get(i);
            logger.info("begin to run scheduler......");
            List<SchedulerElement> list = staticConfig.getSchedulerElementList();
            int schedulerSize = list.size();
            ThreadFactory tf = new BaseThreadFactory("static-scheduler");
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(schedulerSize, tf);
            for (SchedulerElement info : list) {
                int delay = info.getDelay();
                int interval = info.getInterval();
                executor.scheduleWithFixedDelay(new SchedulerTask(jobThreadExecutor, staticConfig), delay, interval, TimeUnit.SECONDS);
            }
            logger.info("finish to run scheduler......");
        }


    }
}
