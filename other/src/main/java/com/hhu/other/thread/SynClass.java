package com.hhu.other.thread;

/**
 * @author jacks
 * @date 2021/11/15
 * @description
 */
public class SynClass {
    private static volatile SynClass instance;

    private SynClass() {
    }

    private synchronized SynClass getInstance() {
        if (instance == null) {
            instance = new SynClass();
        }
        return instance;
    }

    public SynClass getInstanceDC() {
        if (instance == null) {
            synchronized (SynClass.class) {
                if (instance == null) {
                    instance = new SynClass();
                }
            }
        }
        return instance;
    }

    public synchronized SynClass getInstanceDis() {
        return new SynClass();
    }

    public SynClass getInstanceDis2() {
        return new SynClass();
    }
}
