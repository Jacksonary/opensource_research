package com.hhu.bilibili.dp;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jacks
 * @date 2021/12/24
 */
public class MaxScore {
    private int getMaxScore(int[] arr) {
        if (arr == null) {
            return 0;
        }

        Set<Integer> set = new HashSet<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            set.add(i);

        }
        return 1;
    }

    private int getNearest(int[] arr, int cur, Set<Integer> set) {
        // 向右找
        int r = 1;
        for (int i = cur; i < arr.length; i++) {
            if (!set.contains(i)) {
                r = arr[i];
                break;
            }
        }

        // 向左找
        int l = 1;
        for (int i = cur; i > 0; i--) {
            if (!set.contains(i)) {
                l = arr[i];
                break;
            }
        }

        return arr[cur] * r * l;
    }
}
