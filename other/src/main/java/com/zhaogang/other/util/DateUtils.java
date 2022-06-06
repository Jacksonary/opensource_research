package com.zhaogang.other.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.util.Assert;

/**
 * @author jacks
 * @date 2022/1/24
 */
public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_FORMATTER_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static String getStrDate(Date var) {
        return DATE_FORMATTER_DATE.format(Optional.ofNullable(var).orElse(new Date()));
    }

    public static String getStrDate(Date var, SimpleDateFormat formatter) {
        return Optional.ofNullable(formatter).orElse(DATE_FORMATTER_DATE)
            .format(Optional.ofNullable(var).orElse(new Date()));
    }

    public static String getStrDate(LocalDateTime var) {
        return Optional.ofNullable(var).orElse(LocalDateTime.now()).format(DATE_FORMATTER);
    }

    public static String getStrDate(LocalDateTime var, DateTimeFormatter formatter) {
        return Optional.ofNullable(var).orElse(LocalDateTime.now())
            .format(Optional.ofNullable(formatter).orElse(DATE_FORMATTER));
    }

    public static LocalDateTime getDistTime(LocalDateTime origin, ZoneId from, ZoneId to) {
        Assert.notNull(origin, "");
        Assert.notNull(from, "fromZoneId is null");
        Assert.notNull(to, "toZoneId is null");
        return origin.atZone(from).withZoneSameInstant(to).toLocalDateTime();
    }

    public static LocalDateTime convertUTC2BeiJing(LocalDateTime utcTime) {
        Assert.notNull(utcTime, "utcTime is null");
        return getDistTime(utcTime, ZoneId.from(ZoneOffset.UTC), ZoneOffset.ofHours(8));
    }

    public static Date getOffsetDate(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

}
