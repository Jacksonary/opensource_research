package com.zhaogang.other.drools;

import com.zhaogang.other.drools.dto.Person;
import javax.annotation.Resource;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Component;

import com.zhaogang.other.drools.dto.Student;

/**
 * @author weiguo.liu
 * @date 2021/4/1
 * @description 基于配置文件的加载，适用于springboot
 */
@Component
public class ResourceLoader {

    @Resource
    private KieContainer kieContainer;
    // session 可以每次创建，代价不大
    // @Resource
    // private KieSession kieSession;

    public void validate() {
        KieSession kieSession = loadRules();

        kieSession.insert(new Student(1, "spec-16160201", "sp-stu"));
//        kieSession.insert(new Person("per", 21));
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    /**
     * 从配置文件加载规则
     *
     * KieBase就是一个知识仓库，包含了若干的规则、流程、方法等，
     * 在Drools中主要就是规则和方法，KieBase本身并不包含运行时的数据之类的，如果需要执行规则KieBase中的规则的话，就需要根据KieBase创建KieSession。
     *
     * 创建KieBase是一个成本非常高的事情, KieBase会建立知识（规则、流程）仓库，而创建KieSession则是一个成本非常低的事情，所以KieBase会建立缓存，而KieSession则不必。
     */
    private KieSession loadRules() {
        // 获取kmodule.xml中配置中名称为person的session，默认为有状态的，这里的kSessionName就是kModule中的 ksession name 属性
        KieSession kieSession = kieContainer.newKieSession("person");
        StatelessKieSession person = kieContainer.newStatelessKieSession("person");

        return kieContainer.newKieSession();
    }

}
