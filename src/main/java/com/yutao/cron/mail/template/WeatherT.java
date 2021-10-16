package com.yutao.cron.mail.template;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yutao.cron.entity.Color;
import com.yutao.cron.entity.Weather;
import com.yutao.cron.mail.template.base.BaseT;
import com.yutao.cron.util.HttpHelper;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Random;

/**
 * @author :RETURN
 * @date :2021/10/14 10:56
 */
public class WeatherT extends BaseT {

    private final static String KEY = "3be5a1e2fa7c40d3aed9b8bfa17f0ab6";
    private final static String WEATHER_API = "https://api.caiyunapp.com/v2.5/YrMIKQFjvTs2qZng/104.10194,30.65984/realtime.json";
    private final static String CHEND_DU = "101270101";
    private final static String NOW_WEATHER_API = "https://devapi.qweather.com/v7/weather/now?location="+CHEND_DU+"&key="+KEY;
    private final static String RECENTLY_WEATHER_API = "https://devapi.qweather.com/v7/weather/3d?location="+CHEND_DU+"&key="+KEY;
    private final static String AIR_QUALITY_API = "https://devapi.qweather.com/v7/air/now?location="+CHEND_DU+"&key="+KEY;


    private Weather weather = new Weather();
    private JSONObject currentWeather = new JSONObject();
    private JSONObject recentlyWeather = new JSONObject();
    private JSONObject airQuality = new JSONObject();

    public WeatherT(){
        requestWeather();
    }

    private void requestWeather() {
        currentWeather = HttpHelper.get(NOW_WEATHER_API)
                .execute()
                .getJson();

        recentlyWeather = HttpHelper.get(RECENTLY_WEATHER_API)
                .execute()
                .getJson();

        airQuality = HttpHelper.get(AIR_QUALITY_API)
                .execute()
                .getJson();

        weather.setWeatherName((String) JSONPath.eval(currentWeather,"$.now.text"));
        weather.setWindDirection((String) JSONPath.eval(currentWeather,"$.now.windDir"));
        weather.setCurrentTemp((String) JSONPath.eval(currentWeather,"$.now.temp"));
        weather.setLowTemp((String) JSONPath.eval(recentlyWeather,"$.daily[0].tempMin"));
        weather.setHighTemp((String) JSONPath.eval(recentlyWeather,"$.daily[0].tempMax"));
        weather.setAirQuality((String) JSONPath.eval(airQuality,"$.now.category"));
    }

    private String todayWeather(){
        return fromTemplate("今日天气",weather.getWeatherName());
    }

    private String currentTemp(){
        return fromTemplate("当前温度",weather.getCurrentTemp()+"度");
    }

    private String highTemp(){
        return fromTemplate("最高温度",weather.getHighTemp()+"度");
    }

    private String lowTemp(){
        return fromTemplate("最低温度",weather.getLowTemp()+"度");
    }

    private String airQuality(){
        return fromTemplate("空气质量",weather.getAirQuality());
    }

    private String windDirection(){
        return fromTemplate("风向",weather.getWindDirection());
    }

    private String fromTemplate(String text, String value){
        return "<div>"+text+" <span style=\"color:"+ Color.randomColor() +"\">"+value+"</span>"+"</div>";
    }

    @Override
    public String getTemplate() {
        return todayWeather() +
                currentTemp() +
                highTemp() +
                lowTemp() +
                airQuality() +
                windDirection();
    }
}
