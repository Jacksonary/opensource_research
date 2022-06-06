package com.example.kafka.config.template;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.kafka.producer.DefaultProducerListener;

/**
 * @author jacks
 * @date 2022/2/25
 */
@Configuration
public class ProducerFactoryConfig {
    @Value("${momenta.kafka.bootstrap-servers:localhost:9092}")
    private String brokers;
    @Value("${momenta.kafka.producer.batch-size:1024}")
    private Integer producerBatchSize;
    @Value("${momenta.kafka.producer.properties.linger.ms:500}")
    private Long lingerMs;
    @Value("${momenta.kafka.producer.retries:30}")
    private Integer retries;
    @Value("${momenta.kafka.producer.acks:1}")
    private String acks;
    @Value("${momenta.kafka.producer.max.request.size:1048576}")
    private Integer maxSize;
    @Value("${momenta.kafka.producer.buffer.memory:33554432}")
    private Integer maxBufferSize;
    @Value("${momenta.kafka.producer.request.timeout.ms:10000}")
    private Integer sendTimeout;
    @Value("${momenta.kafka.producer.max.in.flight.requests.per.connection:5}")
    private Integer flightNumPerSession;
    @Value("${momenta.kafka.max.block.ms:60000}")
    private Integer maxBlockTime;
    @Value("${momenta.kafka.compression.type:none}")
    private String compressionType;
    @Value("${momenta.kafka.producer.metadata.max.age.ms:300000}")
    private Integer metaDataAge;

    @Bean
    public ProducerFactory<Integer, Serializable> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(32);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);

        // 异步消息的设置，达到下面的2个条件之一即可
        // 1. batchSize(默认16384) | spring.kafka.producer.batch-size
        // 2. 达到指定的时间(默认0) | spring.kafka.producer.properties.linger.ms
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producerBatchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);

        // 生产者发送消息失败时的重试次数(默认 INTEGER.MAX) | spring.kafka.producer.retries
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        // 生产者的 ack 方式(默认1)："all", "-1", "0", "1" | spring.kafka.producer.acks
        // acks = 0,
        // 生产者将不会等待来自服务器的任何确认，该记录将立即添加到套接字缓冲区并视为已发送。无法保证服务器已收到记录，并且重试配置将不会生效（因为客户端通常不会知道任何故障），为每条记录返回的偏移量始终设置为-1。
        // acks = 1,leader会将记录写入其本地日志，但无需等待所有副本服务器的完全确认即可做出回应，在这种情况下，如果leader在确认记录后立即失败，但在将数据复制到所有的副本服务器之前，则记录将会丢失。
        // acks = all,leader将等待完整的同步副本集以确认记录，只要至少一个同步副本服务器仍然存活，记录就不会丢失，
        // acks = -1, 同 all
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        // 生产者的超时时间(默认 30s = 30 * 1000) | spring.kafka.producer.request.timeout.ms
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, sendTimeout);

        // 生产者自己控制发送的消息体最大体积(默认 1M= 1024 * 1024) | spring.kafka.producer.max.request.size
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, maxSize);
        // 生产者本地 buffer 中可以缓存的消息总体积最大值(默认 32 * 1024 * 1024L) | spring.kafka.producer.buffer.memory
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, maxBufferSize);
        // 最大阻塞时间(默认 60 s) | spring.kafka.max.block.ms
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, maxBlockTime);
        // kafka生产者允许存在最大的kafka服务端未确认接收的消息个数最大值(默认5) | spring.kafka.producer.max.in.flight.requests.per.connection
        // 注: 如果该设为1，并且开启重试机制，则会在允许的重试次数内，阻塞其他消息发送到kafka Server端。并且为1的话，会严重影响生产者的吞吐量。仅适用于对数据有严格顺序要求的场景
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, flightNumPerSession);

        // 指定数据压缩类型(默认 none) | spring.kafka.compression.type
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);

        // 客户端在进行发送和消费的时候，会缓存kafka的元数据。默认 5 分钟 | spring.kafka.producer.metadata.max.age.ms
        props.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, metaDataAge);

        // 指定了 key 的序列化方式，那发送消息时必须指定对应的 key,否则将无法发送
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        // 指定 value 的序列化方式
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }
    /**
     * @formatter:on 
     */

    @Bean
    @ConditionalOnMissingBean
    public ProducerListener<Integer, Serializable> producerListener() {
        return new DefaultProducerListener();
    }

    @Bean
    public KafkaTemplate<Integer, Serializable>
        kafkaTemplate(@Autowired ProducerListener<Integer, Serializable> producerListener) {
        KafkaTemplate<Integer, Serializable> template = new KafkaTemplate<>(producerFactory());
        // 定制发送消息的 callback
        template.setProducerListener(producerListener);
        return template;
    }
}
