package com.zhaogang.other.util;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Map;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * In-memory compile Java source code as String.
 *
 * @author michael
 */
@Slf4j
public class JavaStringCompiler<T extends ClassLoader> {

    private JavaCompiler compiler;
    private StandardJavaFileManager stdManager;
    private T classLoader;

    public JavaStringCompiler(T classLoader) {
        this.classLoader = classLoader;
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.stdManager = compiler.getStandardFileManager(null, null, null);
    }

    /**
     * Compile a Java source file in memory.
     *
     * @param fileName Java file name, e.g. "Test.java"
     * @param source   The source code as String.
     * @return The compiled results as Map that contains class name as key,
     * class binary as value.
     * @throws IOException If compile error.
     */
    public Map<String, byte[]> compile(String fileName, String source) throws IOException {
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(fileName, source);

            //获取lib的项目路径和项目的包
            log.info(">>>>>>>  classLoader={}", classLoader);
            URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
            URL[] urls = urlClassLoader.getURLs();
            StringBuilder classpath = new StringBuilder();
            for (URL url : urls) {
                String p = url.getFile();
                classpath.append(p).append(File.pathSeparator);
            }
            log.info(">>>>>>>  classpath={}", classpath);
            URL springBootUrl = urlClassLoader.getResource("./BOOT-INF/lib/*");
            if (null != springBootUrl) {
                log.info(">>>>>>>  jar:classpath={}", springBootUrl);
                classpath.append(springBootUrl);
            }
            Iterable<String> options = Arrays.asList("-encoding", "UTF-8", "-classpath", classpath.toString());

            CompilationTask task = compiler.getTask(null, manager, null, options, null, Arrays.asList(javaFileObject));
            Boolean result = task.call();
            if (result == null || !result.booleanValue()) {
                throw new RuntimeException("Compilation failed.");
            }
            return manager.getClassBytes();
        }
    }

    private static String buildClassPath(String path) {
        return URI.create(path).getPath();
    }

    /**
     * Load class from compiled classes.
     *
     * @param name       Full class name.
     * @param classBytes Compiled results as a Map.
     * @return The Class instance.
     * @throws ClassNotFoundException If class not found.
     * @throws IOException            If load error.
     */
    public Class<?> loadClass(String name, Map<String, byte[]> classBytes) {
        try {
            MemoryClassLoader memoryClassLoader = new MemoryClassLoader(classLoader, classBytes);
            return memoryClassLoader.loadClass(name);
        } catch (Exception ex) {
            log.error(">> JavaStringCompiler#loadClass error! name={}", name, ex);
            return null;
        }
    }
}
