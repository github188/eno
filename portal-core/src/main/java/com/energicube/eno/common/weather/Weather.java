package com.energicube.eno.common.weather;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-9-10
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 */
public class Weather implements Serializable {

    private Observation current_observation; //当前天气
    private Object response;
    private Observation forecast; //未来天气
    private History history;  //历史昨天的天气

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Observation getForecast() {
        return forecast;
    }

    public void setForecast(Observation forecast) {
        this.forecast = forecast;
    }

    public Observation getCurrent_observation() {
        return current_observation;
    }

    public void setCurrent_observation(Observation current_observation) {
        this.current_observation = current_observation;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "current_observation=" + current_observation +
                ", response=" + response +
                ", forecast=" + forecast +
                ", history=" + history +
                '}';
    }
}
