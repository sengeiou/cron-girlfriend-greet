package com.yutao.cron;

import com.yutao.cron.service.MainService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CronApplicationTests {

    @Resource
    MainService mainService;

    @Test
    void contextLoads() {
    }

}
