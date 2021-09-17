package com.zhaogang.other.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author jacks
 * @date 2021/7/19
 * @description
 */
public class Main {
    private static final Map<Integer, String> map = new HashMap<>();
    static {
        map.put(1, "ha");
        map.put(2, "heng");
    }

    public static void main(String[] args) throws InterruptedException {
        // System.out.println(BigDecimal.ZERO.compareTo(BigDecimal.ZERO));
        // System.out.println(map.get(1));
        // System.out.println(BigDecimal.ONE.divide(BigDecimal.TEN));
        //
        // System.out.println(new Integer(128).equals(new Integer(128)));
        int capacity = 100000;
        List<Integer> arrayList = new ArrayList<>(capacity);
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        System.out.println("======================== start =================================");
        addBeat();
        Integer re;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (Integer integer : arrayList) {
            re = integer;
        }
        System.out.println("arrayList(enhance for): " + stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < arrayList.size(); i++) {
            re = arrayList.get(i);
        }
        System.out.println("arrayList(fori): " + stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            re = iterator.next();
        }
        System.out.println("arrayList(iterator): " + stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        for (Integer integer : linkedList) {
            re = integer;
        }
        System.out.println("linkedList(enhance for): " + stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        Iterator<Integer> iterator2 = linkedList.iterator();
        while (iterator2.hasNext()) {
            re = iterator2.next();
        }
        System.out.println("linkedList(iterator): " + stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < linkedList.size(); i++) {
            re = linkedList.get(i);
        }
        System.out.println("linkedList(fori): " + stopWatch.getTime());

        System.exit(1);
    }

    private static void addBeat() {
        new Thread(() -> {
            while (true) {
                System.out.println("beating: " + System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
