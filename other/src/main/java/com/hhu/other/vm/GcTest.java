package com.hhu.other.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jacks
 * @date 2021/11/25
 */
public class GcTest {
    private String name;

    public GcTest() {}

    public GcTest(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();
        while (true) {
            list.add("test");
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public String get() {
        return name;
    }
}
