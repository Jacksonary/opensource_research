package com.zhaogang.other.exc;

/**
 * @author weiguo.liu
 * @date 2021/1/15
 * @description
 */
public class NoClassDefFoundErrorDemo {
    public static void main(String[] args) {
//        try {
//            // The following line would throw ExceptionInInitializerError
//            SimpleCalculator calculator1 = new SimpleCalculator();
//        } catch (Throwable t) {
//            System.out.println(t);
//        }
        // The following line would cause NoClassDefFoundError
        SimpleCalculator.test();
    }

}
