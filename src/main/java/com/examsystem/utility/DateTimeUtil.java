package com.examsystem.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 日期工具类
 * @DATE: 2023/5/8 22:09
 */
public class DateTimeUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
    //日期格式常量
    public static final String STANDER_FORMAT = "yyyy-MM-dd HH:mm:ss";
    //日期格式常量
    public static final String STANDER_SHORT_FORMAT = "yyyy-MM-dd";

    /**
     * 解析日期
     * @param dateStr 日期
     * @param format 转换后格式
     * @return
     */
    public static Date parse(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr); //parse将输入的特定字符串转换成Date类的对象
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取开始日期
     * @return
     */
    public static Date getMonthStartDay() {
        //格式化日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        //获取当前时间
        Calendar cale = Calendar.getInstance();
        //当前月份
        cale.add(Calendar.MONTH, 0);
        //设置号数为2
        cale.set(Calendar.DAY_OF_MONTH, 1);
        //将当前时间格式转换为指定格式
        String dateStr = formatter.format(cale.getTime());
        return parse(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取结束日期
     * @return
     */
    public static Date getMonthEndDay() {
        //格式化日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        //获取当前时间
        Calendar cale = Calendar.getInstance();
        //月份加1
        cale.add(Calendar.MONTH, 1);
        //设置号数为1
        cale.set(Calendar.DAY_OF_MONTH, 0);
        //将当前时间格式转换为指定格式
        String dateStr = formatter.format(cale.getTime());
        return parse(dateStr, STANDER_FORMAT);
    }

    /**
     * 格式化日期
     * @return
     */
    public static List<String> MothStartToNowFormat() {
        //设置当前月的日期数从1开始
        Date startTime = getMonthStartDay();
        //获取当前时间
        Calendar nowCalendar = Calendar.getInstance();
        //设置日历的时间为给定的日期 Date
        nowCalendar.setTime(new Date());

        //获取号数
        int mothDayCount = nowCalendar.get(Calendar.DAY_OF_MONTH);
        List<String> mothDays = new ArrayList<>(mothDayCount);

        //设置当月的开始日期
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(startTime);

        //设置日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        mothDays.add(formatter.format(startTime));

        //拿到开始到结束每一天
        for (int i = 0; i < mothDayCount - 1; i++) {
            startCalendar.add(Calendar.DATE, 1);  //日期+1
            Date end_date = startCalendar.getTime();    //每一天
            mothDays.add(formatter.format(end_date));
        }
        return mothDays;
    }

    /**
     * 将日期Date转换为字符串
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        if (null == date) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(STANDER_FORMAT);
        return dateFormat.format(date);
    }

    //测试
    public static void main(String[] args) {
        System.out.println(MothStartToNowFormat());
    }
}
