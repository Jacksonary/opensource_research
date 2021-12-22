package com.zhaogang.other.arithmetic.ali;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

/**
 * @formatter:off
 * 题目 交易日管理 
 * 背景描述: 在金融业务中，经常涉及到交易日期的计算，交易日和自然日是有区别的，比如法定节假日，周末，都是不能交易的，我们叫非交易日，允许交易的日我们称之为交易日。交易日每天也不是24小时，比如中国股市的股票交易时间在每个交易日的[AM9:30-11:30) 和 [PM13:00-15:00),为了满足7*24小时服务，我们对用户提供的服务在非交易日或是非交易时段也是有支持的，所以将非交易时段产生的业务，它的交易日会算到下一个最近交易时段的交易日中，
 *
 * 为了表述方便，我们用T+0表示当前交易日，T+1表示下一个交易日。D+0表示当天自然日，D+1表示下一个自然日，以此类推可能会有：D+n，D-n T+n，T-n等这样的日期表述方式。
 *
 * 举例说明： 20160701 20160704 20160705 是交易日，交易日的交易时间是[AM9:30-11:30) 和 [PM13:00-15:00),20160702和20160703是非交易日，则
 * 20160701上午8点的交易日是20160701 ，20160701下午5点的交易日则是 20160704 ，用T+0标示某个时间的当前交易日，如20160701下午5点的T+0为20160704，T+1为20160705。
 * @formatter:on
 * 
 * @author jacks
 * @date 2021/12/6
 */
public class WorkDayServiceImpl implements WorkDayService {
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
    public static final DateTimeFormatter STF = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static List<LocalDate> tradeDayContainer = new ArrayList<>();

    public static void main(String[] args) throws ParseException {
        WorkDayServiceImpl workDayService = new WorkDayServiceImpl();
        workDayService.init(Lists.newArrayList("20160701", "20160704", "20160705"));

        Date date = new Date();
        date.setYear(2016);
        date.setMonth(7);
        date.setDate(2);
        date.setHours(12);
        date.setMinutes(30);
        String tradeDay = workDayService.getTradeDay(date, 0);
        System.out.println(tradeDay);
    }

    /**
     * 工具初始化，初始化的目的是让工具具备更加合适各的数据结构，方便计算提高效率
     *
     * @param tradeDayList
     *            包含一年内所有的交易日起，格式如：20160701 20160704 20160705，非交易日20160702 20160703不在其中.
     */
    @Override
    public void init(List<String> tradeDayList) {
        // 排序
        Collections.sort(tradeDayList);
        tradeDayContainer = tradeDayList.stream().map(o -> {
            try {
                Date parse = SDF.parse(o);
                return parse.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     *
     * 给定任意时间，返回给定时间的T+n交易日
     *
     * @param time
     *            给定要计算的时间。
     * @param offsetDays
     *            交易日偏移量，offsetDays可以为负数，表示T-n的计算。
     */
    @Override
    public String getTradeDay(Date time, int offsetDays) {
        LocalDateTime localDateTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(offsetDays);

        LocalDateTime start =
            LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 9, 30);
        LocalDateTime end =
            LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 15, 0);

        // 0-当前 -1-之前 1-之后
        boolean forward = true;
        LocalDate expected = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (localDateTime.isBefore(start)) {
            // 往前推1天
            expected = expected.plusDays(-1);
            forward = false;
        } else if (localDateTime.isAfter(end)) {
            // 往后推一天
            expected = expected.plusDays(1);
            forward = true;
        } else {
            // 当天
        }

        return getResultDay(expected, forward);
    }

    private String getResultDay(LocalDate expected, boolean forward) {
        for (int i = 0; i < tradeDayContainer.size(); i++) {
            LocalDate date = tradeDayContainer.get(i);
            if (date.isEqual(expected)) {
                return date.format(STF);
            }
            if (forward) {
                // 往后
                if (date.isAfter(expected)) {
                    return date.format(STF);
                }
            } else {
                // 往前
                if (date.isBefore(expected)
                    && (i >= tradeDayContainer.size() - 1 || tradeDayContainer.get(i + 1).isAfter(expected))) {
                    return date.format(STF);
                }
            }
        }

        return null;
    }

}
