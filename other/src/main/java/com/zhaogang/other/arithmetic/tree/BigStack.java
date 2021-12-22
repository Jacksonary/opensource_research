package com.zhaogang.other.arithmetic.tree;

import com.zhaogang.other.arithmetic.util.ArrUtils;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author jacks
 * @date 2021/11/9
 * @description
 */
public class BigStack {
    public static void main(String[] args) {
        //        int[] arr = new int[] {0, 5, 6, 10, 8, 3, 2, 19, 9, 11};
        int[] arr = new int[] {1, 2, 3, 4, 5};
        //        ArrUtils.printResult(arr);
        //
        //        constructBigStack(arr);
        //        ArrUtils.printResult(arr);
        //        headSort(arr);

        buildMaxHeapWithQueue(arr);
    }

    /**
     * @formatter:off
     * 各个根节点的子节点为 2 * n + 1 和 2 * n + 2
     * 默认根节点为为arr[0]，构建大根堆
     * @formatter:on
     */
    private static void constructBigStack(int[] arr) {
        if (arr == null) {
            return;
        }

        int len = arr.length;
        for (int i = 0; i < len / 2; i++) {
            int left = 2 * i + 1;
            int right = left + 1;

            int maxSubIndex;
            if (right >= len) {
                maxSubIndex = left;
            } else {
                maxSubIndex = arr[left] > arr[right] ? left : right;
            }

            if (arr[i] < arr[maxSubIndex]) {
                int tmp = arr[i];
                arr[i] = arr[maxSubIndex];
                arr[maxSubIndex] = tmp;
            }
        }
    }

    private static void addStack(int[] arr, int i, int size) {
        int j, tmp, post;
        j = 2 * i;
        tmp = arr[i];
        post = 0;
        while (j <= size && post == 0) {
            if (j < size) {
                // 小顶堆比较
                if (arr[j] < arr[j + 1]) {
                    j++;
                }
            }

            if (tmp < arr[j]) {
                //                post = 1
            }
        }
    }

    private static void headSort(int[] arr) {
        //初始建堆，array[0]为第一趟值最大的元素
        buildMaxHeap(arr);
        for (int i = arr.length - 1; i > 1; i--) {
            //将堆顶元素和堆低元素交换，即得到当前最大元素正确的排序位置
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            //整理，将剩余的元素整理成堆
            adjust(arr, 0, i);
        }

        ArrUtils.printResult(arr);
    }

    private static void buildMaxHeap(int[] arr) {
        //从最后一个节点array.length-1的父节点（array.length-1-1）/2开始，直到根节点0，反复调整堆（即最后一个非叶子节点）
        for (int i = (arr.length - 2) / 2; i >= 0; i--) {
            adjust(arr, i, arr.length);
        }

        // 调整
        ArrUtils.printResult(arr);
    }

    private static void adjust(int[] arr, int root, int length) {
        int tmp = arr[root];
        //i为初始化为节点 root 的左孩子，沿节点较大的子节点向下调整
        for (int i = 2 * root + 1; i < length - 1; i = 2 * i + 1) {
            //取节点较大的子节点的下标
            if (i < length && arr[i] < arr[i + 1]) {
                //如果节点的右孩子>左孩子，则取右孩子节点的下标
                i++;
            }
            //根节点>=左右子女中关键字较大者，调整结束
            if (tmp >= arr[i]) {
                break;
            }
            //将左右子结点中较大值array[i]调整到双亲节点上
            arr[root] = arr[i];
            //修改根节点，以便继续向下调整
            root = i;
        }
        //被调整的结点的值最终位置
        arr[root] = tmp;
    }

    /**
     * 删除堆顶元素（即序列中的最大值）：先将堆的最后一个元素与堆顶元素交换，由于此时堆的性质被破坏，需对此时的根节点进行向下调整操作
     */
    private static int[] deleteMax(int[] array) {
        //将堆的最后一个元素与堆顶元素交换，堆底元素值设为-99999
        array[0] = array[array.length - 1];
        array[array.length - 1] = -99999;
        //对此时的根节点进行向下调整
        adjust(array, 0, array.length);
        return array;
    }

    private static void buildMaxHeapWithQueue(int[] arr) {
        Queue<Integer> queue = new PriorityQueue<>(arr.length, (o1, o2) -> o2 - o1);
        queue.offer(arr[0]);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < queue.peek()) {
                queue.poll();
                queue.offer(arr[i]);
            }
        }

        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
    }

    /**
     * 对堆的插入操作：先将新节点放在堆的末端，再对这个新节点执行向上调整操作。 假设数组的最后一个元素array[array.length-1]为空，新插入的结点初始时放置在此处
     */
    public int[] insertData(int[] array, int data) {
        array[array.length - 1] = data; //将新节点放在堆的末端
        int k = array.length - 1;  //需要调整的节点
        int parent = (k - 1) / 2;    //双亲节点
        while (parent >= 0 && data > array[parent]) {
            array[k] = array[parent];  //双亲节点下调
            k = parent;
            if (parent != 0) {
                parent = (parent - 1) / 2;  //继续向上比较
            } else {  //根节点已调整完毕，跳出循环
                break;
            }
        }
        array[k] = data;  //将插入的结点放到正确的位置
        return array;
    }
}
