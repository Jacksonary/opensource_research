package com.zhaogang.other.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.zhaogang.other.excel.export.model.DemoModel;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.Modifier;

/**
 * @author weiguo.liu
 * @date 2021/1/26
 * @description 文档：http://www.javassist.org/tutorial/tutorial2.html
 *              https://ljd1996.github.io/2020/04/23/Javassist%E7%94%A8%E6%B3%95/
 */
public class ClazzUtil {
    public static Class<?> generateClazz() throws Exception {
        ClassPool pool = new ClassPool();
        // 这里必须放入需要的类，否则通过 pool.get(class) 时会出现 NotFoundException
        // pool.insertClassPath(new ClassClassPath(String.class));
        pool.insertClassPath(new ClassClassPath(ClazzUtil.class));

        // classPool.appendClassPath(new LoaderClassPath(classLoader));
        //// 指定spring的类加载器去加载
        // CtClass ctClass = classPool.get("org.springframework.boot.loader.LaunchedURLClassLoader");
        // CtConstructor[] ctConstructors = ctClass.getDeclaredConstructors();
        // ctConstructors[0].insertAfter("cn.alfredzhang.agent.springservlet.Agent.modifyClass(this);");
        // ctClass.toClass();
        // 1. 创建一个空类
        CtClass cc = pool.makeClass("com.zhaogang.other.util.ExcelTest");

        // 2. 新增一个name字段
        // private String name;
        CtField param = new CtField(pool.get(String.class.getName()), "name", cc);
        // 访问级别是 private
        param.setModifiers(Modifier.PRIVATE);
        // 初始值是 "xiaoming"
        // cc.addField(param, CtField.Initializer.constant("xiaoming"));

        // 3. 生成 getter、setter 方法
        cc.addMethod(CtNewMethod.setter("setName", param));
        cc.addMethod(CtNewMethod.getter("getName", param));

        // 4. 添加无参的构造函数
        CtConstructor cons = new CtConstructor(new CtClass[] {}, cc);
        // cons.setBody("{name = \"xiaohong\";}");
        cc.addConstructor(cons);

        // // 5. 添加有参的构造函数
        // cons = new CtConstructor(new CtClass[] {pool.get("java.lang.String")}, cc);
        // // $0=this / $1,$2,$3... 代表方法参数
        // cons.setBody("{$0.name = $1;}");
        // cc.addConstructor(cons);
        //
        // // 6. 创建一个名为printName方法，无参数，无返回值，输出name值
        // CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[] {}, cc);
        // ctMethod.setModifiers(Modifier.PUBLIC);
        // ctMethod.setBody("{System.out.println(name);}");
        // cc.addMethod(ctMethod);

        // // 这里会将这个创建的类对象编译为.class文件
        // cc.writeFile("C:\\Users\\weiguo.liu\\Desktop\\excel");
        // // 冻结类，使其不能被修改
        // cc.freeze();
        // cc.isFrozen();
        // cc.defrost();
        //
        // // 删除类不必要的属性，以减少内存占用。调用该方法后，许多方法无法将无法正常使用，慎用；
        // cc.prune();
        // // 将该class从ClassPool中删除
        // cc.detach();
        // 通过类加载器加载该CtClass
        Class<?> aClass = cc.toClass();
        return aClass;
    }

    private static Class<?> createPerson() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        // 1. 创建一个空类
        CtClass cc = pool.makeClass("clazz.demo.Person");

        // 2. 新增一个字段 private String name = "hearing";
        // CtField param = new CtField(pool.get("java.lang.String"), "name", cc);
        // param.setModifiers(Modifier.PRIVATE);
        // cc.addField(param, CtField.Initializer.constant("hearing"));
        //
        // // 3. 生成 getter、setter 方法
        // cc.addMethod(CtNewMethod.setter("setName", param));
        // cc.addMethod(CtNewMethod.getter("getName", param));
        addProperty(cc, "name");

        // 4. 添加无参的构造函数
        // CtConstructor cons = new CtConstructor(new CtClass[] {}, cc);
        // cons.setBody("{name = \"hearing\";}");
        // cc.addConstructor(cons);

        // 5. 添加有参的构造函数
        // cons = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, cc);
        // // $0=this / $1,$2,$3... 代表方法参数
        // cons.setBody("{$0.name = $1;}");
        // cc.addConstructor(cons);

        // 6. 创建一个名为printName方法，无参数，无返回值，输出name值
        // CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, cc);
        // ctMethod.setModifiers(Modifier.PUBLIC);
        // ctMethod.setBody("{System.out.println(name);}");
        // cc.addMethod(ctMethod);

        // 这里会将这个创建的类对象编译为.class文件
        // cc.writeFile("C:\\Users\\weiguo.liu\\Desktop\\excel/");

        return cc.toClass();
    }

    public static void main(String[] args) throws Exception {
        // DemoModel demoModel = new DemoModel();
        // demoModel.setName("nameTest");
        // demoModel.setId("idTest");
        // demoModel.setAge("ageTest");
        // String source = JSON.toJSONString(demoModel);

        // Class<?> aClass = createPerson();
        // // 反转对象
        // Object o = JSON.parseObject(source, aClass);

        Map<Integer, List<Integer>> mergeCon = new HashMap<>();
        mergeCon.put(2, Arrays.asList(new Integer[] {1, 2}));
        jsonMap(mergeCon);
    }

    private static void jsonMap(Map<Integer, List<Integer>> mergeCon) {
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("name");
        fields.add("age");

        List<String> container = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoModel demoModel = new DemoModel();
            demoModel.setName("nameTest" + i / 2);
            demoModel.setId("idTest" + i / 2);
            demoModel.setAge("ageTest" + i / 2);
            container.add(JSON.toJSONString(demoModel));
        }

        int rowNum = container.size();
        int columnNum = fields.size();
        String[][] content = new String[rowNum][columnNum];
        for (int i = 0; i < rowNum; i++) {
            String jsonStr = container.get(i);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            for (int j = 0; j < columnNum; j++) {
                content[i][j] = jsonObject.getString(fields.get(j));
            }
        }

        System.out.println(content);

        Set<CellRangeAddress> mergeCellContainer = new HashSet<>();
        for (int i = 0; i < columnNum; i++) {
            if (!mergeCon.containsKey(i)) {
                continue;
            }

            List<Integer> columnIndexes = mergeCon.get(i);
            for (int j = 1; j < rowNum; j++) {
                boolean mergeFlag = false;
                // 比较上行对应列
                String cur = content[j][i];
                if (!cur.equals(content[j - 1][i])) {
                    System.out.println("========= dif with pre ======");
                    continue;
                }

                // 比较上行其他指定列
                for (int k = 0; k < columnIndexes.size(); k++) {
                    // 比较上行对应列
                    String cur1 = content[j][columnIndexes.get(k)];
                    if (!cur1.equals(content[j - 1][columnIndexes.get(k)])) {
                        System.out.println("========= dif with pre ======");
                        break;
                    }
                    mergeFlag = k == columnIndexes.size() - 1;
                }

                if (mergeFlag) {
                    mergeCellContainer.add(new CellRangeAddress(j - 1, j, i, i));
                }
            }
        }

        System.out.println(mergeCellContainer);
    }

    private static void addProperty(CtClass cc, String fieldName) throws Exception {
        if (StringUtils.isNullOrEmpty(fieldName)) {
            throw new IllegalArgumentException("filedName must not empty");
        }

        // 2. 新增一个字段 private String name = "hearing";
        CtField param = new CtField(cc.getClassPool().get(String.class.getName()), fieldName, cc);
        param.setModifiers(Modifier.PRIVATE);
        cc.addField(param, CtField.Initializer.constant(""));

        // 3. 生成 getter、setter 方法
        String upperCaseFiled = String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
        cc.addMethod(CtNewMethod.setter("set" + upperCaseFiled, param));
        cc.addMethod(CtNewMethod.getter("get" + upperCaseFiled, param));
    }

}
