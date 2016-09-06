package com.jstatic.task;

import com.jstatic.http.HttpClient;
import com.jstatic.util.EmptyUtil;
import com.jstatic.util.FileUtil;
import com.jstatic.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * 处理静态任务
 *
 * @author 谢俊权
 * @create 2016/2/17 16:22
 */
public class JobTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(JobTask.class);

    private JobInfo schedulerInfo;

    public JobTask(JobInfo info){
        this.schedulerInfo = info;
    }

    @Override
    public void run() {
        String action = schedulerInfo.getAction();
        String filePath = schedulerInfo.getFile();
        int timeout = schedulerInfo.getTimeout();

        HttpClient client = HttpClient.getInstance();
        try {
            logger.info("begin to request action {}", action);
            String content = client.get(action, timeout);
            File file = FileUtil.createFile(filePath);
            if(EmptyUtil.isEmpty(content)){
                logger.info("request action result empty");
            }else if(!isSameContent(content, file)){
                FileUtil.write(content, file);
            }
        } catch (Exception e) {
            logger.error("failed to request action {}", action, e);
        }
    }

    private boolean isSameContent(String from, File file){

        try {
            byte[] fromBytes = from.getBytes("UTF-8");
            String fromMD5 = MD5Util.getMD5(fromBytes);
            String toMD5 = MD5Util.getMD5(file);
            if(fromMD5.equals(toMD5)){
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("failed get bytes width utf-8", e);
        }
        return false;
    }
}
