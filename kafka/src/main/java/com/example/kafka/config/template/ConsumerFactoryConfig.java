package com.example.kafka.config.template;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.RangeAssignor;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.RetryingBatchErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.ExponentialBackOff;

/**
 * 消费者的基础配置，可以自定义指定对应的配置
 *
 * @author jacks
 * @date 2022/2/28
 */
@Configuration
public class ConsumerFactoryConfig {
    /**
     * 设置死信队列的规则为 原 Topic+.DLT 后缀，这里的 partition 默认是和原始消息一样， 但 kafka 集群设置的自动创建 topic 的 partition 可能不一样，所以这里可以对原始
     * partition 取余 重新分配，原始partition % server自动创建的partition数
     */
    private static final BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> DEFAULT_DESTINATION_RESOLVER =
        (cr, e) -> new TopicPartition(cr.topic() + ".DLT", cr.partition());

    @Value("${momenta.kafka.bootstrap-servers:localhost:9092}")
    private String brokers;
    @Value("${momenta.kafka.consumer.auto-offset-reset:latest}")
    private String autoOffsetReset;
    @Value("${momenta.kafka.consumer.enable-auto-commit:false}")
    private Boolean autoCommit;
    @Value("${momenta.kafka.consumer.auto-commit-interval:10000}")
    private Integer autoCommitInterval;
    @Value("${momenta.kafka.consumer.metadata-max-age-ms:300000}")
    private Integer metaDataAge;
    @Value("${momenta.kafka.consumer.fetch.min.bytes:1}")
    private Integer fetchMinBytes;
    @Value("${momenta.kafka.consumer.fetch.max.wait.ms:500}")
    private Integer fetchMaxWait;
    @Value("${momenta.kafka.consumer.max-poll-records:200}")
    private Integer pollMax;
    @Value("${momenta.kafka.consumer.max.poll.interval.ms:300000}")
    private Integer maxPollInterval;
    @Value("${momenta.kafka.consumer.currency:3}")
    private Integer concurrentThread;
    @Value("${momenta.kafka.consumer.enableDlq:false}")
    private Boolean enableDLQ;

    /**
     * 单线程消费配置，默认使用这个就可以了
     */
    @Bean("singleListenFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, Serializable>> singleListenFactory(
        @Autowired ConsumerFactory<Integer, Serializable> consumerFactory,
        @Autowired KafkaTemplate<Integer, Serializable> kafkaTemplate) {
        return getFactory(1, Boolean.FALSE, enableDLQ, consumerFactory, kafkaTemplate, AckMode.BATCH);
    }

    /**
     * 通过注解指定这个 factory 进行客户端的批量消费，默认每次 200 条消息消费
     */
    @Bean("batchListenFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, Serializable>> batchListenFactory(
        @Autowired ConsumerFactory<Integer, Serializable> consumerFactory,
        @Autowired KafkaTemplate<Integer, Serializable> kafkaTemplate) {
        return getFactory(1, Boolean.TRUE, enableDLQ, consumerFactory, kafkaTemplate, AckMode.BATCH);
    }

    /**
     * 通过注解指定这个 factory 进行客户端的并发消费，默认创建 3 个消费端进行消费
     */
    @Bean("concurrentListenFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, Serializable>> concurrentListenFactory(
        @Autowired ConsumerFactory<Integer, Serializable> consumerFactory,
        @Autowired KafkaTemplate<Integer, Serializable> kafkaTemplate) {
        return getFactory(concurrentThread, Boolean.FALSE, enableDLQ, consumerFactory, kafkaTemplate, AckMode.BATCH);
    }

    /**
     * @formatter:off
     * 权衡使用，消息频繁的场景对服务端有一定的性能要求
     * 通过注解指定这个 factory 进行客户端的并发批量消费，默认 3 客户端 200 批量消费
     * @formatter:on
     */
    @Bean("concurrentBatchListenFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, Serializable>>
        concurrentBatchListenFactory(@Autowired ConsumerFactory<Integer, Serializable> consumerFactory,
            @Autowired KafkaTemplate<Integer, Serializable> kafkaTemplate) {
        return getFactory(concurrentThread, Boolean.TRUE, enableDLQ, consumerFactory, kafkaTemplate, AckMode.BATCH);
    }

    /*********************************************** 手动提交 **********************************************************/
    @Bean("manualSingleListenFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, Serializable>> manualSingleListenFactory(
        @Autowired ConsumerFactory<Integer, Serializable> consumerFactory,
        @Autowired KafkaTemplate<Integer, Serializable> kafkaTemplate) {
        return getFactory(1, Boolean.FALSE, enableDLQ, consumerFactory, kafkaTemplate, AckMode.MANUAL_IMMEDIATE);
    }

    @Bean
    public ConsumerFactory<Integer, Serializable> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>(32);
        // broker 地址
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);

        // 消费者在读取一个没有偏移量的分区或者偏移量无效的情况下，读取设置 | spring.kafka.consumer.auto-offset-reset
        // 允许的值：latest-读取最新的(默认)，earliest-读取最早的，none-如果没有为使用者的组找到偏移量，则consumer抛出异常
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

        // 是否自动提交偏移，默认true (当为 true 时，会自动根据 autoCommitInterval 进行提交)。推荐将偏移量自己控制，可以有效避免重复读、漏读
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        // 自动提交间隔，默认5秒，当且仅当 autoCommit 为 true 时才有意义
        // 从开始消费一条数据到业务结束，必须在5秒内完成，否则会造成提前提交偏移量，如果出现事务失败，将会漏掉该条消费
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);

        // 把分区分配给消费者的策略。注意：无论分区个数变化或者消费者个数变化，都会触发再分配
        // RangeAssignor：默认。采用大部分分区都分配给消费者群组里的群主(即消费者0)的策略。
        // RoundRobinAssignor：采用所有消费者平均分配分区策略
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, Collections.singletonList(RangeAssignor.class));

        // 消费的时候，会缓存kafka的元数据。默认5分钟
        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, metaDataAge);

        // 最小拉取多大的数据，默认值1，就是立即发送。达不到这个数据就等待。
        // 注意：这里不是根据消费数据条数，而是数据大小，这样设计主要避免每个数据之间大小差距过大
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinBytes);

        // consumer最多等待多久消费一次数据，默认500ms
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWait);
        // 消费者组管理时轮询调用之间的最大延迟。这为使用者在获取更多记录之前空闲的时间设置了上限。如果在此超时过期之前没有调用poll()，
        // 则认为使用者失败，组将重新平衡，以便将分区重新分配给另一个成员默认 5min
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);

        // 控制每次poll方法返回的消息的最多数量，默认500
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, pollMax);

        // k-v 反序列化配置，注：这里必须设置为 ErrorHandlingDeserializer，然后配置实际处理的反序列化（DefaultKafkaConsumerFactory 级别配置）
        // 注：当反序列化器无法反序列化消息时，Spring将无法处理该问题，因为它发生在poll()返回之前。为了解决这个问题，ErrorHandlingDeserializer已经引入了。该处理器将委托给实际的反序列化（键或值）。如果委托未能反序列化记录内容，则在包含原因和原始字节的标头中ErrorHandlingDeserializer返回一个null值和一个DeserializationException。
        // 当使用记录级时MessageListener，如果中ConsumerRecord包含DeserializationException键或值的标头，ErrorHandler则会使用failed调用容器的ConsumerRecord。记录不会传递给侦听器
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, IntegerDeserializer.class);
        // 这里需要指定信任反序列化的目标类，否则会失败，默认只信任 [java.util, java.lang]
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        return props;
    }

    private ConcurrentKafkaListenerContainerFactory<Integer, Serializable> getFactory(int concurrency, Boolean isBatch,
        Boolean enableDLQ, ConsumerFactory<Integer, Serializable> consumerFactory,
        KafkaTemplate<Integer, Serializable> kafkaTemplate, AckMode ackMode) {
        ConcurrentKafkaListenerContainerFactory<Integer, Serializable> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setPollTimeout(3000);

        // 设置 1 个消费者端进行消费
        factory.setConcurrency(concurrency);
        // 设置是否开启批量消费
        factory.setBatchListener(isBatch);
        // 手动提交设置 ACK 模式，默认为 BATCH，listener 处理完由 spring 自动提交
        factory.getContainerProperties().setAckMode(ackMode);

        if (enableDLQ) {
            if (isBatch) {
                // 若开启了批量消费的开关这个必须设置
                factory.setBatchErrorHandler(getDefaultBatchHandler(kafkaTemplate));
            } else {
                // 设置重试和死信队列
                factory.setErrorHandler(getDefaultErrorHandler(kafkaTemplate));
            }
        }
        return factory;
    }

    private ErrorHandler getDefaultErrorHandler(KafkaTemplate<Integer, Serializable> kafkaTemplate) {
        // 设置重试和死信队列
        return new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(
            Collections.singletonMap(Object.class, kafkaTemplate), DEFAULT_DESTINATION_RESOLVER), getDefaultBackOff());
    }

    private BatchErrorHandler getDefaultBatchHandler(KafkaTemplate<Integer, Serializable> kafkaTemplate) {
        // RetryingBatchErrorHandler 会将批量的信息丢进 DLQ，丢之前先会根据 BackOff 进行重试
        return new RetryingBatchErrorHandler(getDefaultBackOff(), new DeadLetterPublishingRecoverer(
            Collections.singletonMap(Object.class, kafkaTemplate), DEFAULT_DESTINATION_RESOLVER));
    }

    private BackOff getDefaultBackOff() {
        // 设置重试时间的方式为指数增长，基数为 2000 ms,指数为2
        ExponentialBackOff backOff = new ExponentialBackOff(2 * 1000L, 2);
        // 上限为 1 h，超过这个值直接设置为 1 h 重试一次
        backOff.setMaxInterval(60 * 60 * 1000L);
        // 重试 10 次，设置临界条件
        // backOff.setMaxElapsedTime(Double.valueOf(Math.pow(2, 10) * 1000).longValue());
        backOff.setMaxElapsedTime(Double.valueOf(Math.pow(2, 5) * 1000).longValue());
        return backOff;
    }
}
