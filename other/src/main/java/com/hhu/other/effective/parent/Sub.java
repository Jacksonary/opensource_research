package com.hhu.other.effective.parent;

/**
 * @author jacks
 * @date 2021/12/23
 */
public class Sub extends Foo {
    public Sub() {
        super();
    }

    protected void subProtected() {
        protectedMethod();
    }

    void subDefault() {
        defaultMethod();
    }

    void subPrivate() {}
}
