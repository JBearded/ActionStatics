package com.jstatic.conf.element;

import java.util.List;

/**
 * @author 谢俊权
 * @create 2016/1/30 20:51
 */
public class StaticElement {
    private ConfigElement configElement;
    private List<SchedulerElement> schedulerElementList;


    public StaticElement(ConfigElement configElement, List<SchedulerElement> schedulerElementList) {
        this.configElement = configElement;
        this.schedulerElementList = schedulerElementList;
    }

    public ConfigElement getConfigElement() {
        return configElement;
    }

    public void setConfigElement(ConfigElement configElement) {
        this.configElement = configElement;
    }

    public List<SchedulerElement> getSchedulerElementList() {
        return schedulerElementList;
    }

    public void setSchedulerElementList(List<SchedulerElement> schedulerElementList) {
        this.schedulerElementList = schedulerElementList;
    }

    public void setSchedulerElement(SchedulerElement schedulerElement){
        this.schedulerElementList.add(schedulerElement);
    }
}
