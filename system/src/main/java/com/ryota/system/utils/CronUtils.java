package com.ryota.system.utils;
import org.quartz.CronExpression;

import java.text.ParseException;

/**
 * @Author quyifan
 * @create 2022/6/5 9:02
 */
public class CronUtils {
    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression)
    {
        return CronExpression.isValidExpression(cronExpression);
    }
}
