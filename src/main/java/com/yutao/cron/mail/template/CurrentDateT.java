package com.yutao.cron.mail.template;

import com.yutao.cron.entity.Color;
import com.yutao.cron.mail.template.base.BaseT;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * @author :RETURN
 * @date :2021/10/15 23:28
 */
public class CurrentDateT extends BaseT {

    private final LocalDate inLoveDate = LocalDate.of(2021,1,2);
    private LocalDate birthDayDate = LocalDate.of(2021,10,16);
    private final int salaryDate = 5;

    private String currentDateTime(){
        return "<div style=\"color:"+Color.randomColor()+"\">"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 E"))+"</div>";
    }

    private String fromLoveTime(){
        return "<div>今天是 我们相恋的第<span style=\"color:#F06292\">"+ inLoveDate.until(LocalDate.now(), ChronoUnit.DAYS) +"</span>天</div>";
    }

    private String fromGainSalary(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int currentMonthDays = calendar.get(Calendar.DATE);
        int resultDay;
        int nowDays = LocalDate.now().getDayOfMonth();

        if (nowDays <= salaryDate){
            resultDay = salaryDate - nowDays;
        }else {
            resultDay = salaryDate + currentMonthDays - nowDays;
        }

        if (resultDay == 0){
            return "<div>今天是发工资的日子哦~</div>";
        }

        return "<div>距离你下次发工资还有<span style=\"color:"+Color.randomColor()+"\">"+resultDay+"</span>天</div>";
    }

    private String fromBirthday(){
        LocalDate now = LocalDate.now();
        int resultDay;
        if (now.getYear() != birthDayDate.getYear()){
            //同步到当前年份
            birthDayDate = birthDayDate.withYear(now.getYear());
        }
        if (now.isAfter(birthDayDate)){
            birthDayDate = birthDayDate.plusYears(1);
        }
        resultDay = (int) now.until(birthDayDate,ChronoUnit.DAYS);

        if (resultDay == 0){
            return "<div><span style=\"color:"+Color.randomColor()+"\">生日快乐！！！</span>亲爱的~</div>";
        }
        return "<div>距离你的生日还有<span style=\"color:"+Color.randomColor()+"\">"+resultDay+"</span>天~</div>";
    }

    @Override
    public String getTemplate() {
        return currentDateTime()+
                fromLoveTime()+
                fromGainSalary()+
                fromBirthday();
    }
}
