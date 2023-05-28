package com.examsystem.utility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷工具类
 * @DATE: 2023/5/12 19:06
 */
public class ExamUtil {

    private static final String ANSWER_SPLIT = ",";

    /**
     * 总分除十
     * @param score
     * @return
     */
    public static String scoreToVM(Integer score) {
        if (score % 10 == 0) {
            return String.valueOf(score / 10);
        } else {
            return String.format("%.1f", score / 10.0);
        }
    }

    /**
     * 分数乘以十
     * @param score
     * @return
     */
    public static Integer scoreFromVM(String score) {
        if (score == null) {
            return null;
        } else {
            return (int) (Float.parseFloat(score) * 10);
        }
    }

    /**
     * 将字符串用，进行分割，然后返回字符串数组。并存入集合中
     * @param contentArray
     * @return
     */
    public static List<String> contentToArray(String contentArray) {
        return Arrays.asList(contentArray.split(ANSWER_SPLIT));
    }

    /**
     * 集合转字符串 用，分隔
     * @param contentArray 集合
     * @return
     */
    public static String contentToString(List<String> contentArray) {
        return contentArray.stream().sorted().collect(Collectors.joining(ANSWER_SPLIT));
    }

    /**
     * 根据总秒数计算时分秒
     * @param second 秒
     * @return
     */
    public static String secondToVM(Integer second) {
        String dateTimes;
        long days = second / (60 * 60 * 24);
        long hours = (second % (60 * 60 * 24)) / (60 * 60);
        long minutes = (second % (60 * 60)) / 60;
        long seconds = second % 60;
        if (days > 0) {
            dateTimes = days + "天 " + hours + "时 " + minutes + "分 " + seconds + "秒";
        } else if (hours > 0) {
            dateTimes = hours + "时 " + minutes + "分 " + seconds + "秒";
        } else if (minutes > 0) {
            dateTimes = minutes + "分 " + seconds + "秒";
        } else {
            dateTimes = seconds + " 秒";
        }
        return dateTimes;
    }
}
