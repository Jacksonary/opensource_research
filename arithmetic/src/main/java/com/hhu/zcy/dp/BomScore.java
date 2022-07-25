package com.hhu.zcy.dp;

/**
 * todo: 待定
 * 给定一个数组 arr，代表一排有分数的气球。每打爆一个气球都能获得分数，假设打爆气球 的分数为 X，获得分数的规则如下： </br>
 * 1）如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L；如果被打爆气球的右边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 R。 获得分数为 L×X×R。</br>
 * 2）如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L；如果被打爆气球的右边所有气球都已经被打爆。获得分数为 L×X。 </br>
 * 3）如果被打爆气球的左边所有的气球都已经被打爆；如果被打爆气球的右边有没被打爆的 气球，找到离被打爆气球最近的气球，假设分数为 R；如果被打爆气球的右边所有气球都已经 被打爆。获得分数为 X×R。 </br>
 * 4）如果被打爆气球的左边和右边所有的气球都已经被打爆。获得分数为 X。 目标是打爆所有气球，获得每次打爆的分数。通过选择打爆气球的顺序，可以得到不同的 总分，请返回能获得的最大分数</br>
 *
 * 举例 arr = {3,2,5} 如果先打爆 3，获得 3×2；再打爆 2，获得 2×5；最后打爆 5，获得 5。最后总分 21。 如果先打爆 3，获得 3×2；再打爆 5，获得 2×5；最后打爆 2，获得 2。最后总分 18。
 * 如果先打爆 2，获得 3×2×5；再打爆 3，获得 3×5；最后打爆 5，获得 5。最后总分 50。 如果先打爆 2，获得 3×2×5；再打爆 5，获得 3×5；最后打爆 3，获得 3。最后总分 48。 如果先打爆 5，获得
 * 2×5；再打爆 3，获得 3×2；最后打爆 2，获得 2。最后总分 18。 如果先打爆 5，获得 2×5；再打爆 2，获得 3×2；最后打爆 3，获得 3。最后总分 19。 能获得的最大分数为 50
 *
 * @author jacks
 * @date 2022/6/23
 */
public class BomScore {

    public static void main(String[] args) {
        int[] arr = new int[] {3, 2, 5};
        int aim = 0;
        System.out.println(getMaxScore(arr, aim));
    }

    private static int getScore(int[] arr) {
        return 0;
    }

    private static int getMaxScore(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            result = Math.max(getNearScore(arr, aim, true) * getNearScore(arr, aim, false) * arr[aim], result);
            arr[aim] = -1;
        }

        return result;
    }

    private int process(int l, int r) {
        // l 最后被打爆

        return -1;
    }

    /**
     * 双向寻找距离 aim 最近的那个分数 aim == 0 -> right aim == arr.length -1 -> left aim == arr.length, 0 <-, <-left
     */
    private static int getNearScore(int[] arr, int aim, boolean isRight) {
        if (isRight) {
            for (int i = aim + 1; i < arr.length; i++) {
                if (arr[i] != -1) {
                    return arr[i];
                }
            }
            return 1;
        }

        for (int i = aim - 1; i > 0; i--) {
            if (arr[i] != -1) {
                return arr[i];
            }
        }
        return 1;
    }
}
