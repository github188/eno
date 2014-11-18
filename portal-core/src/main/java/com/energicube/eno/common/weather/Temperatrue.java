package com.energicube.eno.common.weather;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-9-10
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class Temperatrue implements Serializable {
    private String celsius;
    private String fahrenheit;

    public String getCelsius() {
        return celsius;
    }

    public void setCelsius(String celsius) {
        this.celsius = celsius;
    }

    public String getFahrenheit() {
        return fahrenheit;
    }

    public void setFahrenheit(String fahrenheit) {
        this.fahrenheit = fahrenheit;
    }

    @Override
    public String toString() {
        return "Temperatrue{" +
                "celsius='" + celsius + '\'' +
                ", fahrenheit='" + fahrenheit + '\'' +
                '}';
    }
}
