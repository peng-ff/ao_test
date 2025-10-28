package com.square.checkin.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 日期工具类
 */
public class DateUtil {
    
    /**
     * 获取当前日期
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * 获取当前时间
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    
    /**
     * 计算两个日期之间的天数差
     * @param start 开始日期
     * @param end 结束日期
     * @return 天数差（end - start）
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(start, end);
    }
    
    /**
     * 判断是否是昨天
     * @param date 要判断的日期
     * @return true-是昨天，false-不是昨天
     */
    public static boolean isYesterday(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate yesterday = getCurrentDate().minusDays(1);
        return date.equals(yesterday);
    }
    
    /**
     * 判断是否是今天
     * @param date 要判断的日期
     * @return true-是今天，false-不是今天
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.equals(getCurrentDate());
    }
    
    /**
     * 判断日期是否在指定范围内
     * @param date 要判断的日期
     * @param start 开始日期（包含）
     * @param end 结束日期（包含）
     * @return true-在范围内，false-不在范围内
     */
    public static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null) {
            return false;
        }
        if (start != null && date.isBefore(start)) {
            return false;
        }
        if (end != null && date.isAfter(end)) {
            return false;
        }
        return true;
    }
}
