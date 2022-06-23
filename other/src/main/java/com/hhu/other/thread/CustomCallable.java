package com.hhu.other.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author jacks
 * @date 2021/11/30
 */
public class CustomCallable implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println("Callable: " + Thread.currentThread().getName());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
