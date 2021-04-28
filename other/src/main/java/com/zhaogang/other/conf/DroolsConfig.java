package com.zhaogang.other.conf;

import java.io.IOException;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author weiguo.liu
 * @date 2021/4/1
 * @description drools 的配置文件
 * 
 *              与spring集成详细参考下面
 *              https://docs.jboss.org/drools/release/7.51.0.Final/drools-docs/html_single/index.html#_ch.kie.spring
 */
@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";

    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        for (Resource file : getRuleFiles()) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
        }
        return kieFileSystem;
    }

    private Resource[] getRuleFiles() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
    }

    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer() throws IOException {
        KieRepository kieRepository = getKieServices().getRepository();

        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();

        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());

        // 无效
        // KieServices kieServices = getKieServices();
        // KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        // return kieClasspathContainer;
    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession kieSession() throws IOException {
        return kieContainer().newKieSession();
    }

    /**
     * 这个Bean对应的xml配置为 <bean id="kiePostProcessor" class="org.kie.spring.KModuleBeanFactoryPostProcessor"/>
     *
     * drools与spring集成的核心，不配置将不生效
     */
    @Bean(name = "kiePostProcessor")
    @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }

    /**
     * 和kiePostProcessor() 2选1 如果使用 kie-spring PostProcessor 相关注解必须配置这个Bean，否则不需要
     *
     * 对应于 <bean id="kiePostProcessor" class="org.kie.spring.annotations.KModuleAnnotationPostProcessor"/>
     */
    // @Bean(name = "kiePostProcessor")
    // public KModuleAnnotationPostProcessor kModuleAnnotationPostProcessor() {
    // return new KModuleAnnotationPostProcessor();
    // }
}
