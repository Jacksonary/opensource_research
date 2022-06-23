package com.hhu.other.functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author jacks
 * @date 2022/3/11
 */
public class FunctionTest {
    public static void main(String[] args) {
        List<String> result = loopOpt(o -> {
            return Arrays.asList("string" + o, "integer" + o);
        }, 1);
        System.out.println(result);
    }

    private static List<String> loopOpt(Function<Integer, List<String>> function, Integer param) {
        return function.apply(param);
    }
}
