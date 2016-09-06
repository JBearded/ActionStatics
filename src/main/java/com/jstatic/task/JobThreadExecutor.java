package com.jstatic.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 线程执行器
 *
 * @author 谢俊权
 * @create 2016/2/17 16:52
 */
public class JobThreadExecutor {

    private ExecutorService executor;

    public JobThreadExecutor(int maxThread){
        ThreadFactory tf = new BaseThreadFactory("static-job");
        executor = Executors.newFixedThreadPool(maxThread, tf);
    }

    public void run(Runnable task){
        executor.execute(task);
    }

}
