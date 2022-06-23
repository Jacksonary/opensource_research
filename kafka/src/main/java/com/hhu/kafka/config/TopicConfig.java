package com.hhu.kafka.config;

import com.hhu.kafka.constant.TopicConstant;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * 进行 Topic 的基础配置
 * 
 * @author jacks
 * @date 2022/2/25
 */
@Configuration
public class TopicConfig {
    private static final String COMPRESSION_TYPE_CONFIG = "localConfig";

    /**
     * 若配置了 KafkaAdmin 对象，它可以自动向 broker 自动新增 topic, 后面就可以像下面使用 NewTopic 的 Bean 注入新建的 Topic
     */
//    @Bean
//    public KafkaAdmin admin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, ProducerFactoryConfig.LOCAL_BROKER);
//        return new KafkaAdmin(configs);
//    }

    /**
     * 若 topic 不存在就创建
     */
    @Bean
    public NewTopic nameTopic() {
        return TopicBuilder.name(TopicConstant.NAME_TOPIC).partitions(1).replicas(1).compact().build();
    }

    @Bean
    public NewTopic nameTopic2() {
        return TopicBuilder.name(TopicConstant.NAME_TOPIC2).partitions(1).replicas(1).compact().build();
    }
    @Bean
    public NewTopic nameTopic3() {
        return TopicBuilder.name(TopicConstant.NAME_TOPIC3).partitions(1).replicas(1).compact().build();
    }
    @Bean
    public NewTopic nameTopic4() {
        return TopicBuilder.name(TopicConstant.NAME_TOPIC4).partitions(2).replicas(1).compact().build();
    }

//    @Bean
//    public NewTopic topic2() {
//        return TopicBuilder.name("thing2").partitions(10).replicas(3)
//            .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd").build();
//    }
//
//    @Bean
//    public NewTopic topic3() {
//        return TopicBuilder.name("thing3").assignReplicas(0, Arrays.asList(0, 1)).assignReplicas(1, Arrays.asList(1, 2))
//            .assignReplicas(2, Arrays.asList(2, 0)).config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd").build();
//    }
}
