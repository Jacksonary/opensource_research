package com.zhaogang.other.arithmetic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 5 my/2019-01-01T09:00:01 my/2019-01-01T09:00:01 abc/2018-12-24T08:00:00/test/you 1/2018-12-24T08:00:00/test/Test1
 * 123/2018-12-24T08:00:09/test/me
 *
 * @author jacks
 * @date 2021/11/17
 */
public class Honor {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static void main(String[] args) {
        // int i = Integer.MAX_VALUE - (int)Math.pow(2, 32);
        // System.out.println(i);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9999999; i++) {
            sb.append(i + "");
        }
        String s = sb.toString();
        System.out.println("===========");
        System.out.println(s.length());

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(5, 100, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), namedThreadFactory);

        Honor honor = new Honor();
        while (true) {
            executor.execute(() -> staticTest());
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                honor.test();
                staticTest();
            });
        }

        // sort();

    }

    private static synchronized void staticTest() {
        System.out.println(">> staticTest");
        System.out.println("sleeping...");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(">> done");
    }

    private static void sort() {
        Scanner scanner = new Scanner(System.in);
        int count = Integer.parseInt(scanner.nextLine());
        Map<Date, Set<String>> map = new HashMap<>(count);
        while (count > 0) {
            String input = scanner.nextLine();
            Date dateTime = getDateTime(input);
            Set<String> set = map.get(dateTime);
            if (set == null) {
                set = new TreeSet<>(new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        int len1 = o1.toString().length();
                        int len2 = o2.toString().length();
                        if (len1 == len2) {
                            return o1.toString().compareTo(o2.toString());
                        }
                        return len1 - len2;
                    }
                });
            }
            set.add(input);
            map.put(dateTime, set);
            count--;
        }

        Set<Date> keys = new TreeSet<>();
        for (Entry<Date, Set<String>> entry : map.entrySet()) {
            keys.add(entry.getKey());
        }

        for (Date key : keys) {
            Set<String> strs = map.get(key);
            strs.forEach(System.out::println);
        }
    }

    private static Date getDateTime(String str) {
        int len = str.length();
        Date date = new Date();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) > '0' && str.charAt(i) <= '9') {
                try {
                    date = FORMATTER.parse(str.substring(i, i + 19));
                    break;
                } catch (ParseException e) {
                    continue;
                }
            }
        }
        return date;
    }

    private static void getDays() {
        String[] arr = new Scanner(System.in).nextLine().split(" ");
        int year = Integer.parseInt(arr[0]);

        System.out.println(LocalDate.of(year, Integer.parseInt(arr[1]), Integer.parseInt(arr[2])).toEpochDay()
            - LocalDate.of(year, 1, 1).toEpochDay() + 1);
    }

    private synchronized void test() {
        System.out.println(">> test");
    }
}
