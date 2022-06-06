package com.hhu.bilibili.stack;

import java.text.MessageFormat;
import java.util.Stack;

/**
 * @author jacks
 * @date 2021/6/22
 * @description 汉诺塔相关
 */
public class HanoiPro {
    /**
     * 将3个杆分别定义为起始杆、目标杆、辅助杆（代指左、中、右3个杆）
     */
    private static final String ORIGINAL_PLACE = "left";
    private static final String TARGET_PLACE = "mid";
    private static final String SECONDARY_PLACE = "right";

    private static final String TEMPLATE = "move from {0} to {1}";

    public static void main(String[] args) {
        // 正常玩法
        normalWithRecursive(3, ORIGINAL_PLACE, TARGET_PLACE, SECONDARY_PLACE);
    }

    /**
     * @formatter:off
     * 普通方式汉诺塔玩法，只要从起始杆移到目标杆即可
     * 递归思想：
     * 直到只有最后一个盘，将其放到目标杆即可（临界条件）
     *
     * 1. 将n-1个盘移到辅助杆上
     * 2. 将最后一个盘（最大的那个盘）移动到目标杆
     * 3. 将 n-1 个盘移到目标杆
     *
     * @formatter:on
     */
    private static void normalWithRecursive(int towerNum, String source, String target, String helper) {
        if (towerNum == 1) {
            System.out.println(MessageFormat.format(TEMPLATE, source, target));
            return;
        }

        normalWithRecursive(towerNum - 1, source, helper, target);
        System.out.println(MessageFormat.format(TEMPLATE, source, target));
        normalWithRecursive(towerNum - 1, helper, target, source);
    }

    private static void normalWithStack(Stack<Integer> source, Stack<Integer> target, Stack<Integer> helper) {

    }

    private static void move2Other(Stack<Integer> original, Stack<Integer> target) {
        if (original == null || original.isEmpty()) {
            return;
        }

        while (!original.isEmpty()) {
            target.push(original.pop());
        }
    }

    /**变种
     * @formatter:off
     * 汉诺塔问题比较经典，这里修改一下游戏规则：现在限制不能从最左侧的塔直接移动到最
     * 右侧，也不能从最右侧直接移动到最左侧，而是必须经过中间。求当塔有 N 层的时候，打印最
     * 优移动过程和最优移动总步数。
     *
     * 例如，当塔数为两层时，最上层的塔记为 1，最下层的塔记为 2，则打印：
     * Move 1 from left to mid
     * Move 1 from mid to right
     * Move 2 from left to mid
     * Move 1 from right to mid
     * Move 1 from mid to left
     * Move 2 from mid to right
     * Move 1 from left to mid
     * Move 1 from mid to right
     * It will move 8 steps.
     * @formatter:on
     */
    private void abnormalWithRecursive() {

    }
}
