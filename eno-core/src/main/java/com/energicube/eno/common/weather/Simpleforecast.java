package com.energicube.eno.common.weather;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-9-10
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class Simpleforecast implements Serializable {
    private List<Observation> forecastday;

    public List<Observation> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<Observation> forecastday) {
        this.forecastday = forecastday;
    }
}
