package com.hhu.bilibili.tree.num;

/**
 * @formatter:off 
 * 一组数，有且只有一个数出现奇数次，找出来
 * 进阶版：若有2个数出现奇数，找出来
 * @formatter:on 
 * @author jacks
 * @date 2021/12/10
 */
public class OddNum {
    public static void main(String[] args) {
        int[] arr = new int[] {1, 1, 2, 2, 3, 3, 7, 7, 8, 8, 10};
        System.out.println(getOddTimesNum(arr));
    }

    /**
     * 异或的妙用
     */
    private static int getOddTimesNum(int[] arr) {
        int re = 0;
        for (int i = 0; i < arr.length; i++) {
            re = arr[i] ^ re;
        }

        return re;
    }
}
