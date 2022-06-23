package com.hhu.dubbo.provider.config;

import com.hhu.dubbo.provider.service.impl.ProviderServiceImpl;
import com.hhu.dubbo.service.ProviderService;
import java.util.concurrent.CountDownLatch;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

/**
 * @author weiguo.liu
 * @date 2020/11/17
 * @description 通过api的方式暴露服务，不依赖于spring
 */
public class exportApiConfig {

    public static void main(String[] args) throws InterruptedException {
//        // 服务实现
//        ProviderService xxxService = new ProviderServiceImpl();
//
//// 当前应用配置
//        ApplicationConfig application = new ApplicationConfig();
//        application.setName("provider");
//
//// 连接注册中心配置
//        RegistryConfig registry = new RegistryConfig();
//        registry.setAddress("127.0.0.1:2181");
//        registry.setUsername("aaa");
//        registry.setPassword("bbb");
//
//// 服务提供者协议配置
//        ProtocolConfig protocol = new ProtocolConfig();
//        protocol.setName("dubbo");
//        protocol.setPort(12345);
//        protocol.setThreads(200);
//
//// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
//
//// 服务提供者暴露服务配置
//        ServiceConfig<ProviderService> service = new ServiceConfig<>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
//        service.setApplication(application);
//        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
//        service.setProtocol(protocol); // 多个协议可以用setProtocols()
//        service.setInterface(ProviderService.class);
//        service.setRef(xxxService);
//        service.setVersion("1.0.0");
//
//// 暴露及注册服务
//        service.export();

        ServiceConfig<ProviderService> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig("first-dubbo-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://" + "127.0.0.1:2181"));
        service.setInterface(ProviderService.class);
        service.setRef(new ProviderServiceImpl());
        service.setVersion("1.0");
        service.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
