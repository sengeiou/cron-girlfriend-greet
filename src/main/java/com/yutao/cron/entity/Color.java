package com.yutao.cron.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author :RETURN
 * @date :2021/10/15 18:23
 */
public class Color {

    private static List<String> colors = new ArrayList<>();
    private static List<String> bgColors = new ArrayList<>();
    private static final Random rd = new Random(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
    static {
        colors.add("#2196F3");
        colors.add("#03A9F4");
        colors.add("#26C6DA");
        colors.add("#F44336");
        colors.add("#66BB6A");
        colors.add("#F9A825");
        colors.add("#FF5722");
        colors.add("#8BC34A");
        colors.add("#E040FB");
        colors.add("#795548");
        colors.add("#607D8B");

        bgColors.add("#FEF2F2");
        bgColors.add("#FFF7ED");
        bgColors.add("#FFFBEB");
        bgColors.add("#FEFCE8");
        bgColors.add("#F7FEE7");
        bgColors.add("#F0FDF4");
        bgColors.add("#ECFDF5");
        bgColors.add("#F0FDFA");
        bgColors.add("#ECFEFF");
        bgColors.add("#F0F9FF");
        bgColors.add("#EFF6FF");
        bgColors.add("#EEF2FF");
        bgColors.add("#F5F3FF");
        bgColors.add("#FAF5FF");
        bgColors.add("#FDF4FF");
        bgColors.add("#FDF2F8");
        bgColors.add("#FFF1F2");
        bgColors.add("#FAFAF9");
        bgColors.add("#FAFAFA");
        bgColors.add("#F9FAFB");
        bgColors.add("#F8FAFC");
    }

    public static String randomColor(){
        int randInt = rd.nextInt(colors.size());
        return colors.get(randInt);
    }

    public static String randomBgColor(){
        int randInt = rd.nextInt(bgColors.size());
        return bgColors.get(randInt);
    }

}
