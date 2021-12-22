package com.zhaogang.other.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author jacks
 * @date 2021/11/30
 */
public class ThreadTest {
    private static Object lock = new Object();
    private String name;

    public ThreadTest() {}

    public ThreadTest(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(8, 10, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        for (int i = 0; i < 5; i++) {
            // CustomThread thread = new CustomThread();
            // Thread thread = new Thread(new CustomRunnable());
            // thread.start();

            // CustomCallable customCallable = new CustomCallable();
            // Future submit = executor.submit(customCallable);
            // Object o = submit.get();

            executor.execute(ThreadTest::staticPrint1);
            executor.execute(ThreadTest::staticPrint2);
        }

        // System.out.println("=========");
        // CustomThread thread = new CustomThread();
        // thread.start();
        //
        // System.out.println(Thread.currentThread().getThreadGroup());
        // doFunction(ThreadTest::customFunction, "test");

        // executor.execute(ThreadTest::commonPrint);
        // executor.execute(ThreadTest::commonPrint);
        // executor.execute(ThreadTest::print1);
        // executor.execute(ThreadTest::print2);

        // ThreadTest tt = new ThreadTest();
        // executor.execute(tt::print3);
        // executor.execute(tt::print4);

        // MutilAdd mutilAdd = new MutilAdd();
        // executor.execute(mutilAdd);
        // executor.execute(mutilAdd);

        // ReferenceQueue<ThreadTest> queue = new ReferenceQueue<>();
        // WeakReference<ThreadTest> wr1 = new WeakReference<>(new ThreadTest("test1"), queue);
        // WeakReference<ThreadTest> wr2 = new WeakReference<>(new ThreadTest("test2"), queue);
        // System.out.println(queue);
        // System.out.println(wr1.get());
        // System.out.println(wr2.get());
        // System.out.println("=====================");
        // System.gc();
        // TimeUnit.SECONDS.sleep(2);
        // System.out.println(queue);
        // System.out.println(wr1.get());
        // System.out.println(wr2.get());
        // Reference<? extends ThreadTest> poll;
        // do {
        // poll = queue.poll();
        // System.out.println(poll);
        // } while (poll != null);

        // MyExcludeLock lock = new MyExcludeLock();
        // for (int i = 0; i < 5; i++) {
        // executor.execute(() -> {
        // lock.lock();
        // System.out.println(Thread.currentThread() + ": " + System.currentTimeMillis());
        // try {
        // TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // lock.unlock();
        // });
        // }
    }

    private static void commonPrint() {
        synchronized (ThreadTest.class) {
            for (int i = 0; i < 100; i++) {
                System.out.printf("%s: %d%n", Thread.currentThread().getName(), i);
            }
        }
    }

    private static void print1() {
        synchronized (lock) {
            for (int i = 0; i < 100; i++) {
                System.out.printf("%s: %d%n", Thread.currentThread().getName(), i);
                lock.notify();

                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(">> wait exception");
                }
            }
        }
    }

    private static void print2() {
        synchronized (lock) {
            for (int i = 0; i < 100; i++) {
                System.out.printf("%s: %d%n", Thread.currentThread().getName(), i);
                lock.notify();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(">> wait exception");
                }
            }
        }
    }

    private static synchronized void staticPrint1() {
        System.out.println("staticPrint1");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void staticPrint2() {
        System.out.println("staticPrint2");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void doFunction(Function<String, Integer> function, String args) {
        System.out.println(Thread.currentThread().getThreadGroup());
        Integer apply = function.apply(args);
        System.out.println("result: " + apply);
    }

    private static Integer customFunction(String args) {
        return args.length();
    }

    private synchronized void print3() {
        for (int i = 0; i < 100; i++) {
            System.out.printf("%s: %d%n", Thread.currentThread().getName(), i);
            notify();

            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(">> wait exception");
            }
        }
    }

    private synchronized void print4() {
        for (int i = 0; i < 100; i++) {
            System.out.printf("%s: %d%n", Thread.currentThread().getName(), i);
            notify();

            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(">> wait exception");
            }
        }
    }
}
