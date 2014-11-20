package com.energicube.eno.common.weather;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-9-10
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
public class History implements Serializable {

    private List<Observation> dailysummary; //一天的摘要

    public List<Observation> getDailysummary() {
        return dailysummary;
    }

    public void setDailysummary(List<Observation> dailysummary) {
        this.dailysummary = dailysummary;
    }
}
