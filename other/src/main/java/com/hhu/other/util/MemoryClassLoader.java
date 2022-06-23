package com.hhu.other.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Load class from byte[] which is compiled in memory.
 *
 * @author michael
 */
public class MemoryClassLoader extends ClassLoader {

    Map<String, byte[]> classBytes = new HashMap<>();

    public MemoryClassLoader(ClassLoader classLoader, Map<String, byte[]> classBytes) {
        super(classLoader);
        this.classBytes.putAll(classBytes);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] buf = classBytes.get(name);
        if (buf == null) {
            return super.findClass(name);
        }
        classBytes.remove(name);
        return defineClass(name, buf, 0, buf.length);
    }

}
