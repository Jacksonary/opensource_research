package com.hhu.other.filter;

import java.util.Random;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author jacks
 * @date 2021/9/26
 * @description
 */
public class BloomFilterTest {
    private static CustomBloomFilter customBloomFilter = new CustomBloomFilter();

    public static void main(String[] args) {
        // testCustom();
        testGuava();
    }

    private static void testCustom() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            int anInt = random.nextInt(10);
            System.out.print("before|" + anInt + ": " + customBloomFilter.contains(anInt));
            customBloomFilter.add(anInt);
            System.out.print("  after|" + anInt + ": " + customBloomFilter.contains(anInt));
            System.out.println();
        }

        System.out.println(" done ");
    }

    private static void testGuava() {
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 1500, 0.01);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
//            int anInt = random.nextInt(10);
            int anInt = i;
            System.out.print("before|" + anInt + ": " + bloomFilter.mightContain(anInt));
            bloomFilter.put(i);
            System.out.print("  after|" + anInt + ": " + bloomFilter.mightContain(anInt));
            System.out.println();
        }
    }
}
