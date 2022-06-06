package com.zhaogang.other.functional;

import java.util.List;

/**
 * @author jacks
 * @date 2022/3/11
 */
@FunctionalInterface
public interface LoopOptFunction<T, R> {
    List<R> apply(T param);
}
