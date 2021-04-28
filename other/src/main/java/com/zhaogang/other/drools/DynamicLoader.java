package com.zhaogang.other.drools;

import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import com.zhaogang.other.drools.dto.Person;

/**
 * @author weiguo.liu
 * @date 2021/4/1
 * @description 动态加载规则，可以直接执行
 */
public class DynamicLoader {

    public static void main(String[] args) {
        Person tests = new Person("Tests", 57);
        validate(tests);
        System.out.println(tests.getAge());
    }

    public static void validate(Person person) {
        KieSession statefulKieSession = loadRules();
        statefulKieSession.insert(person);
        statefulKieSession.fireAllRules();
        statefulKieSession.dispose();
    }

    /**
     * 动态加载规则 规则以字符串的形式加载到规则引擎
     */
    public static KieSession loadRules() {
        //  @formatter:off
        String myRule = "import com.zhaogang.other.drools.dto.Person\n" +
            "\n" +
            "dialect  \"mvel\"\n" +
            "\n" +
            "rule \"age\"\n" +
            "    when\n" +
            "        $person : Person(age<16 || age>50)\n" +
            "    then\n" +
            "$person.setAge(500);\n" +
            "        System.out.println(\"这个人的年龄不符合要求！（动态加载）\");\n" +
            "end\n";

        // @formatter:on
        KieHelper helper = new KieHelper();
        helper.addContent(myRule, ResourceType.DRL);

        return helper.build().newKieSession();
    }
}
