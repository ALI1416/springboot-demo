package com.demo.util.hutool.date;

import cn.hutool.core.date.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * <h1>时间</h1>
 *
 * <p>
 * createDate 2022/03/04 14:07:51
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        dateUtil();
        dateTime();
        chineseDate();
        try {
            timeInterval();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 日期时间工具
     */
    public static void dateUtil() {
        Date date = DateUtil.date();
        /*Date、long、Calendar之间的相互转换*/
        log.info("---------- Date、long、Calendar之间的相互转换 ----------");
        log.info("当前时间:" + date);
        log.info("当前时间:" + DateUtil.date(Calendar.getInstance()));
        log.info("当前时间:" + DateUtil.date(System.currentTimeMillis()));
        log.info("当前时间字符串，格式：yyyy-MM-dd HH:mm:ss:" + DateUtil.now());
        log.info("当前日期字符串，格式：yyyy-MM-dd:" + DateUtil.today());

        /*字符串转日期*/
        log.info("---------- 字符串转日期 ----------");
        log.info("自动识别一些常用格式:" + DateUtil.parse("2017-03-01"));
        log.info("自定义日期格式转化:" + DateUtil.parse("2017-03-01", "yyyy-MM-dd"));

        /*格式化日期输出*/
        log.info("---------- 格式化日期输出 ----------");
        log.info("自定义格式:" + DateUtil.format(date, "yyyy/MM/dd"));
        log.info("yyyy-MM-dd格式:" + DateUtil.formatDate(date));
        log.info("yyyy-MM-dd HH:mm:ss格式:" + DateUtil.formatDateTime(date));
        log.info("HH:mm:ss格式:" + DateUtil.formatTime(date));

        /*获取Date对象的某个部分*/
        log.info("---------- 获取Date对象的某个部分 ----------");
        log.info("获得年的部分:" + DateUtil.year(date));
        log.info("获得月份，从0开始计数:" + DateUtil.month(date));
        log.info("获得月份枚举:" + DateUtil.monthEnum(date));

        /*开始和结束时间*/
        log.info("---------- 开始和结束时间 ----------");
        log.info("一天的开始:" + DateUtil.beginOfDay(date));
        log.info("一天的结束:" + DateUtil.endOfDay(date));

        /*日期时间偏移*/
        log.info("---------- 日期时间偏移 ----------");
        log.info("+2月:" + DateUtil.offset(date, DateField.DAY_OF_MONTH, 2));
        log.info("+3天:" + DateUtil.offsetDay(date, 3));
        log.info("-3小时:" + DateUtil.offsetHour(date, -3));
        log.info("昨天:" + DateUtil.yesterday());
        log.info("明天:" + DateUtil.tomorrow());
        log.info("上周:" + DateUtil.lastWeek());
        log.info("下周:" + DateUtil.nextWeek());
        log.info("上月:" + DateUtil.lastMonth());
        log.info("下月:" + DateUtil.nextMonth());

        /*日期时间差*/
        log.info("---------- 日期时间差 ----------");
        log.info("相差天数:" + DateUtil.between(date, DateUtil.offsetDay(date, 3), DateUnit.DAY));

        /*格式化时间差*/
        log.info("---------- 格式化时间差 ----------");
        log.info("相差(精确到分钟):" + DateUtil.formatBetween(123456789, BetweenFormatter.Level.MINUTE));

        /*星座和属相*/
        log.info("---------- 星座和属相 ----------");
        log.info("星座:" + DateUtil.getZodiac(Month.JANUARY.getValue(), 19));
        log.info("属相:" + DateUtil.getChineseZodiac(1994));

        /*其它*/
        log.info("---------- 其它 ----------");
        log.info("年龄:" + DateUtil.ageOfNow("1990-01-30"));
        log.info("是否闰年:" + DateUtil.isLeapYear(2017));
    }

    /**
     * 日期时间对象
     */
    private static void dateTime() {
        Date date = new Date();
        /*新建对象*/
        log.info("当前时间:" + DateTime.now());
        log.info("new创建:" + new DateTime(date));
        log.info("of创建:" + DateTime.of(date));
        /*使用对象*/
        DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
        log.info("格式化创建:" + dateTime);
        log.info("年:" + dateTime.year());
        log.info("季度:" + dateTime.quarterEnum());
        log.info("月:" + dateTime.monthEnum());
        log.info("日:" + dateTime.dayOfMonth());
        /*对象的可变性*/
        DateTime offset = dateTime.offset(DateField.YEAR, 1);
        log.info("可变:" + (offset == dateTime));
        dateTime.setMutable(false);
        offset = dateTime.offset(DateField.YEAR, 1);
        log.info("不可变:" + (offset == dateTime));
    }

    /**
     * 农历日期
     */
    private static void chineseDate() {
        //通过农历构建
        ChineseDate date = new ChineseDate(2022, 1, 15);
        log.info("通过农历构建:" + date);
        //通过公历构建
        ChineseDate date2 = new ChineseDate(DateUtil.parseDate("1993-01-06"));
        log.info("通过公历构建:" + date2);
        log.info("农历月份（中文，例如二月，十二月，或者润一月）:" + date.getChineseMonth());
        log.info("农历月称呼（中文，例如二月，腊月，或者润正月）:" + date.getChineseMonthName());
        log.info("农历日:" + date.getChineseDay());
        log.info("年的天干地支:" + date.getCyclical());
        log.info("年份生肖:" + date.getChineseZodiac());
        log.info("节日:" + date.getFestivals());
        log.info("干支纪年信息:" + date.getCyclicalYMD());
        log.info("获取公历的Date:" + date.getGregorianDate());
    }

    /**
     * 计时器工具
     */
    private static void timeInterval() throws Exception {
        TimeInterval timer = DateUtil.timer();
        /*普通计时*/
        Thread.sleep(123);
        log.info("返回花费时间，并重置开始时间:" + timer.intervalRestart());
        Thread.sleep(341);
        log.info("花费毫秒数:" + timer.interval());
        /*分组计时*/
        // 分组1
        timer.start("1");
        Thread.sleep(134);
        log.info("分组1花费毫秒数:" + timer.intervalMs("1"));
        // 分组2
        timer.start("2");
        Thread.sleep(234);
        log.info("分组2花费毫秒数:" + timer.intervalMs("2"));
    }

}
