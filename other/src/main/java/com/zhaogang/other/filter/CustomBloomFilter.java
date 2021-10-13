package com.zhaogang.other.filter;

import java.util.BitSet;

/**
 * @author jacks
 * @date 2021/9/26
 * @description
 */
public class CustomBloomFilter {
    private static final int DEFAULT_SIZE = 2 << 24;
    private static final int[] SEEDS = new int[] {3, 13, 46, 71, 91, 134};
    private static final SimpleHash[] func = new SimpleHash[SEEDS.length];
    private BitSet bits = new BitSet(DEFAULT_SIZE);

    public CustomBloomFilter() {
        for (int i = 0; i < SEEDS.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, SEEDS[i]);
        }
    }

    public void add(Object obj) {
        for (SimpleHash simpleHash : func) {
            bits.set(simpleHash.hash(obj), true);
        }
    }

    public boolean contains(Object obj) {
        for (SimpleHash simpleHash : func) {
            if (!bits.get(simpleHash.hash(obj))) {
                return false;
            }
        }
        return true;
    }

    private class SimpleHash {
        public int cap;
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(Object obj) {
            int h;
            return obj == null ? 0 : Math.abs(seed * (cap - 1)) & ((h = obj.hashCode()) ^ (h >>> 16));
        }
    }
}
