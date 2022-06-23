package com.hhu.other.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author jacks
 * @date 2021/11/30
 */
public class CustomRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable: " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
