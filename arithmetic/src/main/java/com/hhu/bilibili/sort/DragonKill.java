package com.hhu.bilibili.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author jacks
 * @date 2021/11/9
 * @description
 * @formatter:off
 * 在你的王国里有一条n个头的恶龙，你希望雇一些骑士把它杀死(即砍掉所有头)。村里有m个骑士可以雇佣，一个能力值为m的骑士可以砍掉一个直径不超过x的头，且需要支付x个金币。如何雇佣骑士才能砍掉恶龙的所有头，且需要支付的金币最少？注意，一个骑士只能砍一个头(且不能被雇佣两次)
 *
 * 输入包含多组数据。每组数据的第一行为正整数n和m(1<=m，n<=20000)；以下n行每行为一个整数，即恶龙每个头的直径；以下m行每行为一个整数，即每个骑士的能力。输入结束标志为m=n=0。
 *
 * 对于每组数据，输出最少花费。如果无解，输出"Loowater is doomed!"。
 * @formatter:on
 */
public class DragonKill {

    private static final String FAILED_WARNING = "Loowater is doomed!";

    public static void main(String[] args) {
        List<Integer> dragonSize = new ArrayList<>();
        List<Integer> knightEnergy = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(">> please identity dragon and knight: ");
            String firstLine = scanner.nextLine();
            if (StringUtils.isEmpty(firstLine)) {
                System.out.println("error string!");
                continue;
            }
            String[] split = firstLine.split(" ");
            if (split == null || split.length != 2) {
                System.out.println("error string!");
                continue;
            }

            Integer dragonI = getInt(split[0]);
            Integer knightI = getInt(split[1]);
            if (dragonI == null || knightI == null) {
                System.out.println("error string!");
                continue;
            }

            while (dragonSize.size() < dragonI) {
                dragonSize.add(getInt(scanner.nextLine()));
            }

            while (knightEnergy.size() < knightI) {
                knightEnergy.add(getInt(scanner.nextLine()));
            }

            if (scanner.nextLine().equals("00")) {
                break;
            }
        }

        getOptKillStrategy(dragonSize, knightEnergy);
    }

    // 屠龙
    private static void getOptKillStrategy(List<Integer> dragonSize, List<Integer> knightEnergy) {
        if (CollectionUtils.isEmpty(dragonSize)) {
            System.out.print("success: " + 0);
            return;
        }

        if (CollectionUtils.isEmpty(knightEnergy) || dragonSize.size() > knightEnergy.size()) {
            System.out.println(FAILED_WARNING);
        }

        // 组织策略
        upOrder(dragonSize);
        upOrder(knightEnergy);

        int bingCount = 0;
        for (Integer dragon : dragonSize) {
            for (Integer knight : knightEnergy) {
                if (knight >= dragon) {
                    bingCount += knight;
                    knightEnergy.remove(knight);
                    break;
                }

                if (knight.equals(knightEnergy.get(knightEnergy.size() - 1))) {
                    System.out.print(FAILED_WARNING);
                    return;
                }
            }
        }

        System.out.println("result: " + bingCount);
    }

    private static void upOrder(List<Integer> list) {
        if (list == null || list.size() <= 1) {
            return;
        }

        for (int i = 1; i < list.size(); i++) {
            int tarIndex = i;
            int tar = list.get(i);
            while (tarIndex > 0 && list.get(tarIndex - 1) > tar) {
                list.set(tarIndex, list.get(--tarIndex));
            }
            list.set(tarIndex, tar);
        }

        // after sort
        //        System.out.print("after sort: ");
        //        for (Integer item : list) {
        //            System.out.print(item + " ");
        //        }
        //        System.out.println();
    }

    private static Integer getInt(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
