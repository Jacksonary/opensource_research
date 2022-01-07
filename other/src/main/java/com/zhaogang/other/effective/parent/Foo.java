package com.zhaogang.other.effective.parent;

/**
 * @author jacks
 * @date 2021/12/23
 */
public class Foo {
    private String name;

    public void publicMethod() {
        System.out.printf("public %s%n", name);
    }

    protected void protectedMethod() {
        System.out.printf("protected %s%n", name);
    }

    void defaultMethod() {
        System.out.printf("default %s%n", name);
    }

    private void privateMethod() {
        System.out.printf("private %s%n", name);
    }
}
