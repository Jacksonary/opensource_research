package com.hhu.dubbo.consumer;

import com.hhu.dubbo.service.ProviderService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

/**
 * @author weiguo.liu
 * @date 2020/11/17
 * @description
 */
public class ApiConsumerTest {

    public static void main(String[] args) {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("hhu-consumer-app");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("127.0.0.1:2181");
//        registry.setUsername("aaa");
//        registry.setPassword("bbb");

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        ReferenceConfig<ProviderService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        // 多个注册中心可以用setRegistries()
        reference.setRegistry(registry);
        reference.setInterface(ProviderService.class);
        reference.setVersion("1.0");

        // 和本地bean一样使用xxxService
        // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
        ProviderService providerService = reference.get();

        System.out.println(providerService.test());

//        ReferenceConfig<ProviderService> reference = new ReferenceConfig<>();
//        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
//        reference.setRegistry(new RegistryConfig("zookeeper://" + "127.0.0.1:2181"));
        //
//        reference.setUrl("dubbo://172.21.64.1:20882/com.hhu.dubbo.service.ProviderService");
//        reference.setInterface(ProviderService.class);
//        ProviderService service = reference.get();
//        String message = service.test();
//        System.out.println(message);
    }
}
