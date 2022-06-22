package com.hhu.bilibili.bit;

import com.hhu.bilibili.util.ArrUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 一组数，其中有1个数出现奇数次，其他数出现偶数次，找出出现奇数次的那个数
 * 
 * @author jacks
 * @date 2022/6/8
 */
@Slf4j
public class DifferTime {

    public static void main(String[] args) {
        int[] arr = new int[] {1, 1, 2, 2, 3, 3, 4, 6, 6};
        int oddTimeNumber = findOddTimeNumber(arr);
        System.out.println(oddTimeNumber);

        int[] arr2 = new int[] {1, 1, 2, 2, 3, 3, 4, 6, 6, 7};
        int[] oddTimeNumber2 = findOddTimeNumber2(arr2);
        ArrUtils.printResult(oddTimeNumber2);
    }

    /**
     * 有1个数出现奇数次，其他数出现偶数次，找出出现奇数次的那个数
     *
     * 思路: a^a=0 0^a=a 所以全员异或最后的结果就是奇数次的数值
     *
     * @param arr
     * @return
     */
    private static int findOddTimeNumber(int[] arr) {
        int re = 0;
        for (int i : arr) {
            re = re ^ i;
        }
        return re;
    }

    /**
     * 进阶: 出现奇数次的数有2个，找出这2个出现奇数次的数</br>
     * 思路: 假定目标数值为 a 和 b, 根据 1 个出现奇数次数值的思路可以推算出全员异或后的结果为 a^b</br>
     * 然后找出 a 和 b 的差异(重要)，有了 2 数异或操作的结果，就可以找到一位不同的即可(即为1的位置)，</br>
     * 然后将全员就分成了那一位为 1 和那一位为 0 的 2 组数，而且这2组数各自的拥有的数字个数必定为奇数个</br>
     * 只要将其中任意一组的数值进行异或即可找到出现对应组中出现既奇数次的那个数 a 或 b，完了结果出来了</br>
     * 
     * @param arr
     * @return
     */
    private static int[] findOddTimeNumber2(int[] arr) {
        int re = 0;
        for (int i : arr) {
            re = re ^ i;
        }

        // 找出最右侧的1,即出现奇数次的2个数必定至少有一位不同，这里直接找到其中一位
        int rightOne = re & (~re + 1);
        log.info(">> right one: {}", rightOne);
        int aOrb = 0;
        for (int i : arr) {
            if ((rightOne & i) == 1) {
                aOrb = aOrb ^ i;
            }
        }

        return new int[] {aOrb, aOrb ^ re};
    }
}
