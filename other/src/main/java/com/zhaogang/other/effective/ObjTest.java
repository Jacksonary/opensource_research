package com.zhaogang.other.effective;

import com.zhaogang.other.bean.Clazz;
import com.zhaogang.other.bean.Student;

/**quantifier
 * 形容词
 * @formatter:off 
 * Java 之父 Object，它是对万事万物的高度抽象，对应着哲学上的3大问：
 * 1. 我是谁 - getClass()
 * 2. 我从哪里来 - constructor生产，clone()进行繁殖
 * 3. 我要到哪里去 - finalize()
 * @formatter:on 
 * @author jacks
 * @date 2021/12/23
 */
public class ObjTest {
    public static void main(String[] args) {
        Object obj = new Object();
        Student student = new Student();
        student.setId(1);
        student.setName("name");
        student.setAge(20);
        student.setClazz(getClazz(1));

        Student clone = student.clone();
        // Student clone = student;
        System.out.println(clone);
        student.setId(2);
        student.setName("nameChange");
        clone.setClazz(getClazz(2));
        System.out.println(clone);
    }

    private static Clazz getClazz(int i) {
        Clazz re = new Clazz();
        re.setCode("code" + i);
        re.setName("name" + i);
        re.setClassProperty("property" + i);

        return re;
    }

}
