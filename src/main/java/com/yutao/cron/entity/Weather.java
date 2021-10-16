package com.yutao.cron.entity;

import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/10/15 16:41
 */
@Data
public class Weather {
    private String weatherName;
    private String currentTemp;
    private String highTemp;
    private String lowTemp;
    private String airQuality;
    private String windDirection;
}
