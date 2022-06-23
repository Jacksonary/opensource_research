package com.hhu.other.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ISO8601 DateTime 帮助类
 *
 * @author Chundong Gao
 */
public class ISO8601DateTimeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ISO8601DateTimeUtils.class);

    private static final String DATE_FORMAT_ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSS'Z']";
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern(DATE_FORMAT_ISO8601_PATTERN);

    public static String getPattern() {
        return DATE_FORMAT_ISO8601_PATTERN;
    }

    public static DateTimeFormatter getFormatter() {
        return DATE_TIME_FORMATTER;
    }

    public static String format(LocalDateTime dateTime) {
        return DATE_TIME_FORMATTER.format(dateTime);
    }

    public static String format(Long epochMilliseconds) {
        return format(toEpochDateTime(epochMilliseconds));
    }

    public static LocalDateTime toEpochDateTime(Long epochMilliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilliseconds), ZoneId.of("UTC"));
    }

    public static String formattedUtcNow() {
        return format(utcNow());
    }

    public static LocalDateTime utcNow() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDateTime parse(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }
}
