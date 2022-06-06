package com.hhu.bilibili.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author jacks
 * @date 2021/12/16
 */
public class RmDuplicate {
    public static void main(String[] args) {
        int[] arr = new int[] {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        // int len = removeDuplicates(arr);
        int len = removeDuplicates2(arr);
        System.out.println(len);

        for (int i = 0; i < len; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 1) {
            return nums == null ? 0 : 1;
        }

        int pre = 0;
        int count = 0;
        int useless = 0;
        for (int i = 0; i < nums.length - useless; i++) {
            if (i == 0) {
                count++;
                pre = nums[i];
                continue;
            }

            if (pre != nums[i]) {
                count++;
                pre = nums[i];
                continue;
            }

            // 直接往前覆盖
            int from = i;
            // 这里要先加否则会越界
            useless++;
            while (from < nums.length - useless) {
                nums[from++] = nums[from];
            }
            // 重要！这里元素前移后 i 要进行相应的回退，因为后面有个 i++ 的操作
            i--;
        }
        return count;
    }

    /**
     * 利用 hashtable 来优化局部移动
     */
    private static int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Map<Integer, Integer> items = new HashMap<>(nums.length);
        for (int num : nums) {
            items.put(num, Optional.ofNullable(items.get(num)).orElse(0) + 1);
        }

        // 一起移位
        int index = 0;
        for (int i = 0; i < nums.length;) {
            int count = items.get(nums[i]);
            nums[index++] = nums[i];
            i = i + count;
        }

        return items.size();
    }
}
