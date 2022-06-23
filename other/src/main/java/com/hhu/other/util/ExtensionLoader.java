package com.hhu.other.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author weiguo.liu
 * @date 2020/11/23
 * @description
 */
public class ExtensionLoader<C> {
    public C LoadClass(String directory, String classpath, Class<C> parentClass) throws ClassNotFoundException {
        File pluginsDir = new File("C:\\Users\\weiguo.liu\\Desktop\\java" + directory);
        for (File jar : pluginsDir.listFiles()) {
            try {
                ClassLoader loader = URLClassLoader.newInstance(
                        new URL[] { jar.toURL() },
                        getClass().getClassLoader()
                );
                Class<?> clazz = Class.forName(classpath, true, loader);
                Class<? extends C> newClass = clazz.asSubclass(parentClass);
                // Apparently its bad to use Class.newInstance, so we use
                // newClass.getConstructor() instead
                Constructor<? extends C> constructor = newClass.getConstructor();
                return constructor.newInstance();

            } catch (Exception e) {
                // There might be multiple JARs in the directory,
                // so keep looking
                e.printStackTrace();
                continue;
            }
        }
        throw new ClassNotFoundException("Class " + classpath
                + " wasn't found in directory " + "C:\\Users\\weiguo.liu\\Desktop\\java" + directory);
    }
}
