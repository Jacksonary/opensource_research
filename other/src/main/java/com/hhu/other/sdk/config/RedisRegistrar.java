package com.hhu.other.sdk.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.hhu.other.redis.opt.service.impl.CacheServiceImpl;
import com.hhu.other.redis.redisson.annotation.EnableDistributedLockAspect;

/**
 * 方式1：
 * 通过实现 ImportBeanDefinitionRegistrar 来进行注入
 * 方式2：
 * 通过 Selector 的方式进行注解的选择性加载
 * @author jacks
 * @date 2022/2/23
 */
public class RedisRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 手动将第三方的 Bean 注入容器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean distributeAspectDefinition =
            registry.containsBeanDefinition(EnableDistributedLockAspect.class.getName());
        if (!distributeAspectDefinition) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(EnableDistributedLockAspect.class);
            registry.registerBeanDefinition("enableDistributedLockAspect", beanDefinition);
        }

        boolean cacheDefinition = registry.containsBeanDefinition(CacheServiceImpl.class.getName());
        if (!cacheDefinition) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(CacheServiceImpl.class);
            registry.registerBeanDefinition("cacheService", beanDefinition);
        }
    }
}
