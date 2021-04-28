package com.zhaogang.other.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author jacks
 * @date 2021/3/20
 * @description
 */
public class TimeCounter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeCounter.class);

    public static void main(String[] args) {
        Counter counter = new Counter();
        counter.start();
    }

    private static class Counter {
        private int start;

        public Counter() {
        }

        public Counter(int start) {
            this.start = start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public void start() {
            System.out.println("============= start count =====================");
            while (true) {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + ": " + start++);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    LOGGER.warn(">> TimeUnit.SECONDS.sleep error...");
                    e.printStackTrace();
                }
            }
        }
    }
}
