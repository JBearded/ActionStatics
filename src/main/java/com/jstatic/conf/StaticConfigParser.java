package com.jstatic.conf;

import com.jstatic.conf.element.*;
import com.jstatic.util.RegularUtil;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析static配置文件
 *
 * @author 谢俊权
 * @create 2016/1/30 20:20
 */
public class StaticConfigParser{

    private static final Logger logger = LoggerFactory.getLogger(StaticConfigParser.class);

    private static final int DEFAULT_MAX_THREAD = 10;
    private static final int DEFAULT_TIMEOUT_SECONDS = 120;
    private static final int DEFAULT_DELAY_SECONDS = 60;
    private static final int DEFAULT_INTERVAL_SECONDS = 120;

    public StaticElement parse(String configFileName) throws DocumentException {
        Element root = getRoot(configFileName);
        return parseStaticElement(root);
    }

    private Element getRoot(String configFileName) throws DocumentException {
        SAXReader reader = new SAXReader();
        URL url = ClassLoader.getSystemResource(configFileName);
        Document document = reader.read(url);
        Element root = document.getRootElement();
        return root;
    }

    private StaticElement parseStaticElement(Element staticElement){

        Element configElement = staticElement.element(ElementConstant.CONFIG.e());
        Element schedulersElement = staticElement.element(ElementConstant.SCHEDULERS.e());

        ConfigElement config = parseConfigElement(configElement);
        List<SchedulerElement> schedulerList = parseSchedulersElement(schedulersElement);
        StaticElement staticE = new StaticElement(config, schedulerList);
        return staticE;
    }

    private ConfigElement parseConfigElement(Element configElement){
        Element fromElement = configElement.element(ElementConstant.FROM.e());
        Element toElement = configElement.element(ElementConstant.TO.e());
        Element threadElement = configElement.element(ElementConstant.THREAD.e());
        String from = fromElement.getTextTrim();
        String to = toElement.getTextTrim();
        String threadStr = threadElement.getTextTrim();
        int thread = DEFAULT_MAX_THREAD;
        if(RegularUtil.isInteger(threadStr))
            thread = Integer.valueOf(threadStr);
        ConfigElement config = new ConfigElement(from, to, thread);
        return config;
    }

    private List<SchedulerElement> parseSchedulersElement(Element schedulersElement){
        List<SchedulerElement> schedulerList = new ArrayList<>();
        List<Element> schedulerElementList = schedulersElement.elements(ElementConstant.SCHEDULER.e());
        for (Element schedulerElement : schedulerElementList) {
            SchedulerElement scheduler = parseSchedulerElement(schedulerElement);
            schedulerList.add(scheduler);
        }
        return schedulerList;
    }

    private SchedulerElement parseSchedulerElement(Element schedulerElement){
        Attribute delayAttr = schedulerElement.attribute(AttributeConstant.DELAY.a());
        Attribute intervalAttr = schedulerElement.attribute(AttributeConstant.INTERVAL.a());
        Attribute timeOutAttr = schedulerElement.attribute(AttributeConstant.TIMEOUT.a());
        Attribute nameAttr = schedulerElement.attribute(AttributeConstant.NAME.a());

        String delayStr = delayAttr.getStringValue();
        String intervalStr = intervalAttr.getStringValue();
        String timeOutStr = timeOutAttr.getStringValue();
        String name = nameAttr.getStringValue();
        int delay = DEFAULT_DELAY_SECONDS;
        int interval = DEFAULT_INTERVAL_SECONDS;
        int timeout = DEFAULT_TIMEOUT_SECONDS;
        if(RegularUtil.isInteger(delayStr))
            delay = Integer.valueOf(delayStr);
        if(RegularUtil.isInteger(intervalStr))
            interval = Integer.valueOf(intervalStr);
        if(RegularUtil.isInteger(timeOutStr))
            timeout = Integer.valueOf(timeOutStr);
        List<Element> jobElementList = schedulerElement.elements(ElementConstant.JOB.e());
        List<JobElement> jobList = new ArrayList<>();
        for (Element jobElement : jobElementList) {
            JobElement job = parseJobElement(jobElement);
            jobList.add(job);
        }
        SchedulerElement scheduler = new SchedulerElement(name, delay, interval, timeout, jobList);
        return scheduler;
    }

    private JobElement parseJobElement(Element jobElement){
        Attribute actionAttr = jobElement.attribute(AttributeConstant.ACTION.a());
        Attribute fileAttr = jobElement.attribute(AttributeConstant.FILE.a());
        String action = actionAttr.getStringValue();
        String file = fileAttr.getStringValue();
        JobElement job = new JobElement(action, file);
        return job;
    }
}
