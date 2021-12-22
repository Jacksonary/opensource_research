package com.zhaogang.other.thread;

import lombok.SneakyThrows;

/**
 * @author jacks
 * @date 2021/12/6
 */
public class Counter {

    public static void main(String[] args) {
        Object obj = new Object();
        Thread t1 = new Thread(new SelfCounter1(obj));
        t1.start();
        Thread t2 = new Thread(new SelfCounter2(obj));
        t2.start();
    }

    static class SelfCounter1 implements Runnable {
        private Object object;

        public SelfCounter1(Object object) {
            this.object = object;
        }

        @SneakyThrows
        @Override
        public void run() {
            synchronized (object) {
                for (int i = 1; i <= 50; i++) {
                    System.out.println(2 * i - 1);
//                    if (i == 50) {
//                        return;
//                    }
                    object.notify();
                    object.wait();
                }
            }
        }
    }

    static class SelfCounter2 implements Runnable {

        private Object object;

        public SelfCounter2(Object object) {
            this.object = object;
        }

        @SneakyThrows
        @Override
        public void run() {
            synchronized (object) {
                for (int i = 1; i <= 50; i++) {
                    System.out.println(2 * i);
//                    if (i == 50) {
//                        return;
//                    }
                    object.notify();
                    object.wait();
                }
            }
        }
    }
}
