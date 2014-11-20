package com.energicube.eno.common.weather;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-9-10
 * Time: 下午1:27
 * To change this template use File | Settings | File Templates.
 */
public class Observation implements Serializable {

    //================取当前的数据时会有下面的数据================================
    private String observation_time;//发布时间     "Last Updated on 九月 10, 9:30 AM CST"
    private String observation_time_rfc822;//发布时间     "Last Updated on 九月 10, 9:30 AM CST"
    private String weather;//天气描述        "晴"
    private String relative_humidity;//相对湿度   "47%"
    private String temp_c;//温度
    private String wind_dir;//风向      	"NNW"
    private String wind_kph;//风速
    private String solarradiation;//太阳辐射
    private String pressure_mb;//气压  hPa
    private String icon;//表示天气的图片。


    //================取昨天的数据时会有下面的数据================================
    private String fog;//1 表示有，0表示没有
    private String rain;//1 表示有，0表示没有
    private String snow;//1 表示有，0表示没有
    private String mintempm;//最小温度
    private String maxtempm;//最大温度

    private String meanwdire;//中文 风向
    private String meanpressurem;//气压
    private String humidity;//相对湿度  只有数字
    private String meanwindspdm;//风速
    private WeatherDate date;

    //====================未来10天=========================================
    private Simpleforecast simpleforecast;
    private String avehumidity; //相对湿度
    private String conditions; //天气
    private Avewind avewind;  //风
    private Temperatrue high;   //最高温度
    private Temperatrue low;     //最低温度


    public WeatherDate getDate() {
        return date;
    }

    public void setDate(WeatherDate date) {
        this.date = date;
    }

    public String getAvehumidity() {
        return avehumidity;
    }

    public void setAvehumidity(String avehumidity) {
        this.avehumidity = avehumidity;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Avewind getAvewind() {
        return avewind;
    }

    public void setAvewind(Avewind avewind) {
        this.avewind = avewind;
    }

    public Temperatrue getHigh() {
        return high;
    }

    public void setHigh(Temperatrue high) {
        this.high = high;
    }

    public Temperatrue getLow() {
        return low;
    }

    public void setLow(Temperatrue low) {
        this.low = low;
    }

    public Simpleforecast getSimpleforecast() {
        return simpleforecast;
    }

    public void setSimpleforecast(Simpleforecast simpleforecast) {
        this.simpleforecast = simpleforecast;
    }


    public String getObservation_time() {
        return observation_time;
    }

    public void setObservation_time(String observation_time) {
        this.observation_time = observation_time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getRelative_humidity() {
        return relative_humidity;
    }

    public void setRelative_humidity(String relative_humidity) {
        this.relative_humidity = relative_humidity;
    }

    public String getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(String temp_c) {
        this.temp_c = temp_c;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_kph() {
        return wind_kph;
    }

    public void setWind_kph(String wind_kph) {
        this.wind_kph = wind_kph;
    }

    public String getSolarradiation() {
        return solarradiation;
    }

    public void setSolarradiation(String solarradiation) {
        this.solarradiation = solarradiation;
    }

    public String getPressure_mb() {
        return pressure_mb;
    }

    public void setPressure_mb(String pressure_mb) {
        this.pressure_mb = pressure_mb;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFog() {
        return fog;
    }

    public void setFog(String fog) {
        this.fog = fog;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getSnow() {
        return snow;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }

    public String getMintempm() {
        return mintempm;
    }

    public void setMintempm(String mintempm) {
        this.mintempm = mintempm;
    }

    public String getMaxtempm() {
        return maxtempm;
    }

    public void setMaxtempm(String maxtempm) {
        this.maxtempm = maxtempm;
    }

    public String getMeanwdire() {
        return meanwdire;
    }

    public void setMeanwdire(String meanwdire) {
        this.meanwdire = meanwdire;
    }

    public String getMeanpressurem() {
        return meanpressurem;
    }

    public void setMeanpressurem(String meanpressurem) {
        this.meanpressurem = meanpressurem;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMeanwindspdm() {
        return meanwindspdm;
    }

    public void setMeanwindspdm(String meanwindspdm) {
        this.meanwindspdm = meanwindspdm;
    }

    public String getObservation_time_rfc822() {
        return observation_time_rfc822;
    }

    public void setObservation_time_rfc822(String observation_time_rfc822) {
        this.observation_time = observation_time_rfc822;
        this.observation_time_rfc822 = observation_time_rfc822;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "observation_time='" + observation_time + '\'' +
                ", weather='" + weather + '\'' +
                ", relative_humidity='" + relative_humidity + '\'' +
                ", temp_c='" + temp_c + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_kph='" + wind_kph + '\'' +
                ", solarradiation='" + solarradiation + '\'' +
                ", pressure_mb='" + pressure_mb + '\'' +
                ", icon='" + icon + '\'' +
                ", fog='" + fog + '\'' +
                ", rain='" + rain + '\'' +
                ", snow='" + snow + '\'' +
                ", mintempm='" + mintempm + '\'' +
                ", maxtempm='" + maxtempm + '\'' +
                ", meanwdire='" + meanwdire + '\'' +
                ", meanpressurem='" + meanpressurem + '\'' +
                ", humidity='" + humidity + '\'' +
                ", meanwindspdm='" + meanwindspdm + '\'' +
                ", simpleforecast=" + simpleforecast +
                ", avehumidity='" + avehumidity + '\'' +
                ", conditions='" + conditions + '\'' +
                ", avewind=" + avewind +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}
