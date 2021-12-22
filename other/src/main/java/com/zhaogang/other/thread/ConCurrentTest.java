package com.zhaogang.other.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jacks
 * @date 2021/12/1
 */
public class ConCurrentTest {
    public static void main(String[] args) {
        List<Obj> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(new Obj(i, String.format("name: %s", i)));
        }

        // 不能使用迭代器遍历时操作元素
        for (Obj obj : list) {
            if (obj.id == 2) {
                list.remove(obj);
            }
        }

        System.out.println("====================");
        for (Obj obj : list) {
            System.out.println(obj);
        }
    }

    private static class Obj {
        private Integer id;
        private String name;

        public Obj(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Obj{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }
}
