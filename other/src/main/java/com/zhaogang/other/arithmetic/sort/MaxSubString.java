package com.zhaogang.other.arithmetic.sort;

/**
 * @author weiguo.liu
 * @date 2021/5/8
 * @description 最长子串
 */
public class MaxSubString {

    public static void main(String[] args) {
        // System.out.println(find("pwwkew"));
        int[] nums1 = new int[] {1, 2}, nums2 = new int[] {3,4};
        System.out.println(findMedianSortedArrays(nums1, nums2));
    }

    /**
     * 寻找最长子串的长度
     */
    private static int find(String s) {
        if (s == null) {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }

        int maxLen = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            StringBuilder sb = new StringBuilder(String.valueOf(s.charAt(i)));
            int currentLen = 1;
            for (int j = i + 1; j < len; j++) {
                if (sb.indexOf(String.valueOf(s.charAt(j)), 0) != -1) {
                    break;
                }
                sb.append(s.charAt(j));
                currentLen = currentLen + 1;
            }
            if (currentLen > maxLen) {
                maxLen = currentLen;
            }
        }

        return maxLen;
    }

    /**
     * 寻找有序数组的联合中位数
     */
    private static boolean isSs;
    private static int pass = 0;
    private static int target1;
    private static int result;
    private static int result2 = 0;
    private static boolean findFlag = false;
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        // 1个 否则 2 个
        isSs = len % 2 != 0;
        target1 = len / 2;

        int index1 = 0;
        int index2 = 0;
        while (index1 < nums1.length && index2 < nums2.length) {
            result = nums1[index1] < nums2[index2] ? nums1[index1++] : nums2[index2++];
            double target = findTarget();
            if (findFlag) {
                return target;
            }
        }

        while (index1 < nums1.length) {
            result = nums1[index1++];
            double target = findTarget();
            if (findFlag) {
                return target;
            }
        }

        while (index2 < nums2.length) {
            result = nums2[index2++];
            double target = findTarget();
            if (findFlag) {
                return target;
            }
        }

        return 0.0;
    }

    private static double findTarget() {
        // 除不尽，取中
        if (isSs) {
            if (pass == target1) {
                findFlag = true;
                return result;
            } else {
                pass++;
                return 0.0;
            }
        }

        // 除尽，取中位数
        if (pass == target1 - 1) {
            result2 = result;
        } else if (pass == target1) {
            findFlag = true;
            return (result + result2) / 2.0;
        }

        pass++;
        return 0.0;
    }
}
