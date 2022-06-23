package com.hhu.other.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author jacks
 * @date 2022/6/9
 */
public class CustomSynchronizer extends AbstractQueuedSynchronizer {
    private static final long serialVersionUID = 5550968303618658698L;

    private AtomicInteger state;

    public CustomSynchronizer() {
        this.state = new AtomicInteger(0);
    }

    @Override
    protected boolean tryAcquire(int arg) {
        return state.compareAndSet(0, 1);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return state.compareAndSet(1, 0);
    }
}
