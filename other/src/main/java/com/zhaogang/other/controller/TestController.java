package com.zhaogang.other.controller;

import com.zhaogang.other.arithmetic.tree.TreeNode;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaogang.other.bean.User;
import com.zhaogang.other.util.JavaStringCompiler;
import com.zhaogang.starter.services.CustomService;

/**
 * @author weiguo.liu
 * @date 2020/11/23
 * @description
 */
@RestController
@RequestMapping("")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private User user;
    @Resource
    private CustomService customService;
    @Resource
    private TreeNode treeNode;

    @GetMapping("/go")
    public void go() {
        System.out.println(treeNode);
    }

    public static void testLoader2() {
        String path = "C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test\\ValidateResultDTO.java";
        String javaSource = "";
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            javaSource += lines.collect(Collectors.joining(" "));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(javaSource);

    }

    public static void main(String[] args) throws Exception {
        // test();
        // test1();
        // test2();
        // test3();

        // testLoader2();

        String className = "com.internet.excel.model.ContactCompanyExportModel";
        String rootPath = System.getProperties().getProperty("user.dir") + "\\template_dir";

        String filePath = rootPath;
        String[] split = className.split("\\.");
        int length = split.length;
        String fileName = "";
        for (int i = 0; i < length; i++) {
            if (i == length - 1) {
                fileName = split[i] + ".java";
            } else {
                filePath = filePath + "\\" + split[i];
            }
        }

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 创建文件
        File target = new File(filePath + "\\" + fileName);
        if (!target.exists()) {
            boolean newFile = target.createNewFile();
        }

        System.out.println("done");

        // Set<Class<?>> classes = loadClasses("C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test\\targert");
        // System.out.println(classes);
        //
        // classes.forEach(clazz -> {
        // System.out.println(clazz);
        // try {
        // Object t = clazz.newInstance();
        // Method setMessage = clazz.getDeclaredMethod("setMessage", String.class);
        // Method getMessage = clazz.getDeclaredMethod("getMessage");
        // Object invoke = getMessage.invoke(t);
        // System.out.println("name: " + invoke);
        // Object testName = setMessage.invoke(t, "testName");
        // Object invoke2 = getMessage.invoke(t);
        // System.out.println("name: " + invoke2);
        // } catch (InstantiationException e) {
        // e.printStackTrace();
        // } catch (IllegalAccessException e) {
        // e.printStackTrace();
        // } catch (NoSuchMethodException e) {
        // e.printStackTrace();
        // } catch (InvocationTargetException e) {
        // e.printStackTrace();
        // }
        // });
    }

    // @GetMapping("/test")
    public static void test() {
        try {
            String path = "C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test";
            // 动态编译
            JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
            int status = javac.run(null, null, null, "-d", "C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test\\targert",
                path + "\\ValidateResultDTO.java");
            if (status != 0) {
                System.out.println("没有编译成功！");
            }

            // 动态执行
            Class clz = Class.forName(
                "C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test\\targert\\com\\test\\com.test.ValidateResultDTO");
            // 返回与带有给定字符串名的类
            // 或接口相关联的 Class 对象。
            Object o = clz.newInstance();
            Method method = clz.getDeclaredMethod("getMessage");// 返回一个 Method 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明方法
            method.setAccessible(true);
            String result = (String)method.invoke(o);// 静态方法第一个参数可为null,第二个参数为实际传参
            System.out.println(result);

        } catch (Exception e) {
            LOGGER.error("test", e);
        }
    }

    public static Set<Class<?>> loadClasses(String rootClassPath) throws Exception {
        Set<Class<?>> classSet = new HashSet<>();
        // 设置class文件所在根路径
        File clazzPath = new File(rootClassPath);

        // 记录加载.class文件的数量
        int clazzCount = 0;

        if (clazzPath.exists() && clazzPath.isDirectory()) {
            // 获取路径长度
            int clazzPathLen = clazzPath.getAbsolutePath().length() + 1;

            Stack<File> stack = new Stack<>();
            stack.push(clazzPath);

            // 遍历类路径
            while (!stack.isEmpty()) {
                File path = stack.pop();
                File[] classFiles = path.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        // 只加载class文件
                        return pathname.isDirectory() || pathname.getName().endsWith(".class");
                    }
                });
                if (classFiles == null) {
                    break;
                }
                for (File subFile : classFiles) {
                    if (subFile.isDirectory()) {
                        stack.push(subFile);
                    } else {
                        if (clazzCount++ == 0) {
                            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                            boolean accessible = method.isAccessible();
                            try {
                                if (!accessible) {
                                    method.setAccessible(true);
                                }
                                // 设置类加载器
                                URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
                                // 将当前类路径加入到类加载器中
                                method.invoke(classLoader, clazzPath.toURI().toURL());
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                method.setAccessible(accessible);
                            }
                        }
                        // 文件名称
                        String className = subFile.getAbsolutePath();
                        className = className.substring(clazzPathLen, className.length() - 6);
                        // 将/替换成. 得到全路径类名
                        className = className.replace(File.separatorChar, '.');
                        // 加载Class类
                        Class<?> aClass = Class.forName(className);
                        classSet.add(aClass);
                        System.out.println("读取应用程序类文件[class={" + className + "}]");
                    }
                }
            }
        }
        return classSet;
    }

    public static void test1() {
        File file = new File("C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test\\targert\\com\\test\\");

        try {
            // Convert File to a URL
            URL url = file.toURI().toURL(); // file:/c:/myclasses/
            URL[] urls = new URL[] {url};

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);

            // Load in the class; MyClass.class should be located in
            // the directory file:/c:/myclasses/com/mycompany
            Class cls = cl.loadClass("com.test.ValidateResultDTO");
            Method getMessage = cls.getDeclaredMethod("getMessage");
            Method setMessage = cls.getMethod("setMessage", String.class);

            Object o = cls.newInstance();
            Object invoke = getMessage.invoke(o);
            Object testMsg = setMessage.invoke(o, "testMsg");
            Object invoke2 = getMessage.invoke(o);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void test2() throws Exception {
        File file = new File("C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test\\targert\\com\\test");
        URL[] urls = {file.toURI().toURL()};
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class<?> a = urlClassLoader
            .loadClass("C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test\\targert\\com\\test\\ValidateResultDTO");
        System.out.println(a);
    }

    private static void toCacheFile(String sourceCode, String className) {
        String rootPath = System.getProperties().getProperty("user.dir");
        String[] split = className.split("//.");
        if (split.length == 1) {
            // 直接创建文件
        } else {
            // 创建目录
        }
        // FileOutputStream fileOutputStream = new FileOutputStream();
    }

    // public void test() throws Exception {
    // // 记录加载.class文件的数量
    // int clazzCount = 0;
    //
    // if (clazzCount++ == 0) {
    // Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
    // boolean accessible = method.isAccessible();
    // try {
    // if (!accessible) {
    // method.setAccessible(true);
    // }
    // // 设置类加载器
    // URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
    // // 将当前类路径加入到类加载器中
    // method.invoke(classLoader, clazzPath.toURI().toURL());
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // method.setAccessible(accessible);
    // }
    // }
    // // 文件名称
    // String className = subFile.getAbsolutePath();
    // className = className.substring(clazzPathLen, className.length() - 6);
    // //将/替换成. 得到全路径类名
    // className = className.replace(File.separatorChar, '.');
    // // 加载Class类
    // Class<?> aClass = Class.forName(className);
    // classSet.add(aClass);
    // System.out.println("读取应用程序类文件[class={" + className + "}]");
    // }

    public static void test3() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("package testcompile;\n");
        sb.append("public class HelloWorld implements inlinecompiler.InlineCompiler.DoStuff {\n");
        sb.append("    public void doStuff() {\n");
        sb.append("        System.out.println(\"Hello world\");\n");
        sb.append("    }\n");
        sb.append("}\n");

        File helloWorldJava = new File("testcompile/HelloWorld.java");
        if (helloWorldJava.getParentFile().exists() || helloWorldJava.getParentFile().mkdirs()) {

            try {
                Writer writer = null;
                try {
                    writer = new FileWriter(helloWorldJava);
                    writer.write(sb.toString());
                    writer.flush();
                } finally {
                    try {
                        writer.close();
                    } catch (Exception e) {
                    }
                }

                /**
                 * Compilation Requirements
                 *********************************************************************************************/
                DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

                // This sets up the class path that the compiler will use.
                // I've added the .jar file that contains the DoStuff interface within in it...
                List<String> optionList = new ArrayList<String>();
                optionList.add("-classpath");
                optionList.add(System.getProperty("java.class.path") + File.pathSeparator + "dist/InlineCompiler.jar");

                Iterable<? extends JavaFileObject> compilationUnit =
                    fileManager.getJavaFileObjectsFromFiles(Arrays.asList(helloWorldJava));
                JavaCompiler.CompilationTask task =
                    compiler.getTask(null, fileManager, diagnostics, optionList, null, compilationUnit);
                /*********************************************************************************************
                 * Compilation Requirements
                 **/
                if (task.call()) {
                    /**
                     * Load and execute
                     *************************************************************************************************/
                    System.out.println("Yipe");
                    // Create a new custom class loader, pointing to the directory that contains the compiled
                    // classes, this should point to the top of the package structure!
                    URLClassLoader classLoader = new URLClassLoader(new URL[] {new File("./").toURI().toURL()});
                    // Load the class from the classloader by name....
                    Class<?> loadedClass = classLoader.loadClass("testcompile.HelloWorld");
                    // Create a new instance...
                    Object obj = loadedClass.newInstance();
                    // Santity check
                    if (obj instanceof DoStuff) {
                        // Cast to the DoStuff interface
                        DoStuff stuffToDo = (DoStuff)obj;
                        // Run it baby
                        stuffToDo.doStuff();
                    }
                    /*************************************************************************************************
                     * Load and execute
                     **/
                } else {
                    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                        System.out.format("Error on line %d in %s%n", diagnostic.getLineNumber(),
                            diagnostic.getSource().toUri());
                    }
                }
                fileManager.close();
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    @PostMapping("/user")
    public void user(@RequestParam("key") String key, @RequestBody User user2) {
        System.out.println(user2 + ": " + key);
        user2 = null;
        System.out.println(user2);
    }

    @GetMapping("/starter")
    public void testCustomService() {
        String[] split = customService.split(",");
        System.out.println(split);
    }

    /**
     * 可行，从指定目录加载class 文件
     *
     * @throws Exception
     */
    @GetMapping("/loader")
    public void testLoader() throws Exception {
        Set<Class<?>> classes = loadClasses("C:\\Users\\weiguo.liu\\Desktop\\java\\com\\test\\targert");
        System.out.println(classes);

        classes.forEach(clazz -> {
            System.out.println(clazz);
            try {
                Object t = clazz.newInstance();
                Method setMessage = clazz.getDeclaredMethod("setMessage", String.class);
                Method getMessage = clazz.getDeclaredMethod("getMessage");
                Object invoke = getMessage.invoke(t);
                System.out.println("name: " + invoke);
                Object testName = setMessage.invoke(t, "testName");
                Object invoke2 = getMessage.invoke(t);
                System.out.println("name: " + invoke2);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/test4")
    public void test44() throws Exception {
        test4();
    }

    public void test4() throws Exception {
        String path =
            "D:\\IDEAProjects\\online.base.trace.service\\trace-biz\\src\\test\\java\\com\\internet\\excel\\model"
                + "\\ContactCompanyExportModel.java";
        String javaSource = "";
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            javaSource += lines.collect(Collectors.joining(" "));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(javaSource);

        String classPath = "ContactCompanyExportModel.java";
        String className = "com.internet.excel.model.ContactCompanyExportModel";
        ClassLoader classLoader = TestController.class.getClassLoader();
        JavaStringCompiler<ClassLoader> stringCompiler = new JavaStringCompiler<>(classLoader);
        Map<String, byte[]> results = stringCompiler.compile(classPath, javaSource);
        Class<?> tClass = stringCompiler.loadClass(className, results);
        System.out.println(tClass);
        Object t = tClass.newInstance();
        Method setCompanyName = tClass.getDeclaredMethod("setCompanyName", String.class);
        Method getCompanyName = tClass.getDeclaredMethod("getCompanyName");
        Object invoke = getCompanyName.invoke(t);
        System.out.println("name: " + invoke);
        Object testName = setCompanyName.invoke(t, "testName");
        Object invoke2 = getCompanyName.invoke(t);
        System.out.println("name: " + invoke2);
    }

    /**
     * 直接从源码编译获取class对象，可用
     *
     * @throws Exception
     */
    @GetMapping("/test5")
    public void compileWithInMemory() throws Exception {
        String sourceCode = "package com.internet.purchasemanager.param;\n" + "\n" + "/**\n" + " * @author weiguo.liu\n"
            + " * @date 2021/1/7\n" + " * @description 3000 专用\n" + " */\n"
            + "public class BaseTaskConsumerLogExtDTO {\n" + "    private transient boolean fromMq = false;\n"
            + "    /**\n" + "     * 下面三个参数是3000使用，其他场景不需要传\n" + "     */\n" + "    private transient String msgId;\n"
            + "    private transient String taskNo;\n" + "    /**\n"
            + "     * AGREEMENT_MAIN_ORDER((byte)1, \"协议主单\"),\n" + "     * AGREEMENT_SUB_ORDER((byte)2, \"协议子单\"),\n"
            + "     * ORDER_MAIN_ORDER((byte)3, \"采购主单\"),\n" + "     * ORDER_SUB_ORDER((byte)4, \"采购子单\"),\n"
            + "     * RECEIPT_MAIN_ORDER((byte)5, \"收货主单\"),\n" + "     * RECEIPT_SUB_ORDER((byte)6, \"收货子单\"),\n"
            + "     * RETURN_GOODS_MAIN_ORDER((byte)7, \"退货退款主单\"),\n"
            + "     * RETURN_GOODS_SUB_ORDER((byte)8, \"退货退款子单\");\n" + "     */\n"
            + "    private transient Byte sceneType;\n" + "    private transient Long orderId;\n" + "\n"
            + "    public boolean isFromMq() {\n" + "        return fromMq;\n" + "    }\n" + "\n"
            + "    public void setFromMq(boolean fromMq) {\n" + "        this.fromMq = fromMq;\n" + "    }\n" + "\n"
            + "    public String getMsgId() {\n" + "        return msgId;\n" + "    }\n" + "\n"
            + "    public void setMsgId(String msgId) {\n" + "        this.msgId = msgId;\n" + "    }\n" + "\n"
            + "    public String getTaskNo() {\n" + "        return taskNo;\n" + "    }\n" + "\n"
            + "    public void setTaskNo(String taskNo) {\n" + "        this.taskNo = taskNo;\n" + "    }\n" + "\n"
            + "    public Byte getSceneType() {\n" + "        return sceneType;\n" + "    }\n" + "\n"
            + "    public void setSceneType(Byte sceneType) {\n" + "        this.sceneType = sceneType;\n" + "    }\n"
            + "\n" + "    public Long getOrderId() {\n" + "        return orderId;\n" + "    }\n" + "\n"
            + "    public void setOrderId(Long orderId) {\n" + "        this.orderId = orderId;\n" + "    }\n" + "}\n";

        Class<?> helloClass = InMemoryJavaCompiler.newInstance()
            .compile("com.internet.purchasemanager.param.BaseTaskConsumerLogExtDTO", sourceCode);
        Object instance = helloClass.newInstance();

        Method setItemTitle = helloClass.getDeclaredMethod("setTaskNo", String.class);
        setItemTitle.invoke(instance, "测试品名");

        Method getItemTitle = helloClass.getDeclaredMethod("getTaskNo");
        Object itemTitle = getItemTitle.invoke(instance);
        System.out.println(itemTitle);
    }

    public static interface DoStuff {

        public void doStuff();
    }

}
