package com.hhu.other.vm;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author jacks
 * @date 2021/11/29
 */
public class ReferenceAndQueue {
    public static void main(String[] args) {
        GcTest gcTest = new GcTest("strong");
        System.out.println(gcTest.get());

        SoftReference<GcTest> softReference = new SoftReference<>(new GcTest("soft"));
        printName(softReference.get(), "softReference");

        WeakReference<GcTest> weakReference = new WeakReference<>(new GcTest("week"));
        printName(weakReference.get(), "weakReference");

        System.gc();
        System.out.println("==================================");
        printName(softReference.get(), "softReference");
        printName(weakReference.get(), "weakReference");
    }

    private static void printName(GcTest gcTest, String prefix) {
        if (gcTest == null) {
            System.out.println(">> " + prefix + ": gcTests is null");
            return;
        }

        System.out.println(prefix + ": " + gcTest.get());
    }
}
