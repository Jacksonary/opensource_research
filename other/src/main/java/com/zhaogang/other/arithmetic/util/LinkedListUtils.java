package com.zhaogang.other.arithmetic.util;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.zhaogang.other.arithmetic.linklist.DoubleNode;
import com.zhaogang.other.arithmetic.linklist.Node;

/**
 * @author jacks
 * @date 2021/7/8
 * @description
 */
public class LinkedListUtils {

    /**
     * 产生单链表
     */
    public static Node generate(int[] arr, boolean circle) {
        if (arr == null) {
            return null;
        }

        Node root = null;
        Node tail = null;
        Node cur = null;
        for (int j : arr) {
            tail = new Node(j);
            if (root == null) {
                root = tail;
                cur = root;
                continue;
            }

            cur.next = tail;
            cur = cur.next;
        }

        if (circle) {
            tail.next = root;
        }
        return root;
    }

    public static void print(Node root) {
        System.out.print(">> print: ");
        if (root == null) {
            System.out.println();
            return;
        }

        /**
         * 维护一个容器，用于环形链表的打印
         */
        Set<Node> set = new HashSet<>();
        while (root != null) {
            if (set.contains(root)) {
                break;
            }
            set.add(root);
            System.out.print(root.value + " ");
            root = root.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        DoubleNode doubleNode = generateDouble(new int[] {1, 2, 3});
        printDouble(doubleNode);
    }

    /**
     * 产生双向链表
     */
    public static DoubleNode generateDouble(int[] arr) {
        if (arr == null) {
            return null;
        }

        DoubleNode head = new DoubleNode(arr[0]);
        DoubleNode cur = head;
        for (int i = 1; i < arr.length; i++) {
            DoubleNode node = new DoubleNode(arr[i]);
            node.pre = cur;
            cur.next = node;
            // 指向next
            cur = node;
        }

        return head;
    }

    public static void printDouble(DoubleNode root) {
        System.out.print(">> print: ");
        if (root == null) {
            System.out.println();
            return;
        }

        while (root != null) {
            System.out.print(root.value + " ");
            root = root.next;
        }
        System.out.println();
    }

    public static Stack<Integer> getValueStack(Node head) {
        Stack<Integer> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur.value);
            cur = cur.next;
        }
        return stack;
    }

    public static Stack<Node> getNodeStack(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        return stack;
    }

}
