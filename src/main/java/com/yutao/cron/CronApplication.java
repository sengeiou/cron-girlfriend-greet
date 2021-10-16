package com.yutao.cron;

import com.yutao.cron.service.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

/**
 * @author RETURN
 */
@EnableScheduling
@SpringBootApplication
public class CronApplication {

    public static void main(String[] args) {
        SpringApplication.run(CronApplication.class, args);
    }

}
