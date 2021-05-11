package com.zhaogang.rocketmq;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author weiguo.liu
 * @date 2020/10/22
 * @description 
 * @formatter:off 
 * 1. 关于消费的幂等 RocketMQ无法避免消息重复（Exactly-Once），所以如果业务对消费重复非常敏感，务必要在业务层面进行去重处理。
 * 可以借助关系数据库进行去重。首先需要确定消息的唯一键，可以是msgId，也可以是消息内容中的唯一标识字段，例如订单Id等。
 * 
 * 在消费之前判断唯一键是否在关系数据库中存在。如果不存在则插入，并消费，否则跳过。
 * （实际过程要考虑原子性问题，判断是否存在可以尝试插入，如果报主键冲突，则插入失败，直接跳过）
 * msgId一定是全局唯一标识符，但是实际使用中，可能会存在相同的消息有两个不同msgId的情况（消费者主动重发、因客户端重投机制导致的重复等），
 * 这种情况就需要使业务字段进行重复消费。
 * @formatter:on 
 */
public class Consumer {

    // 1. broker 收到数据后会主动往消费者推,这种消费模式一般实时性较高
    private static final DefaultMQPushConsumer PUSH_CONSUMER;
    // 2. 消费者从broker主动拉，主动权由应用控制。一旦获取了批量消息，应用就会启动消费过程。
    // 定义时不要再用DefaultMQPullConsumer（即将被废弃）
    private static final DefaultLitePullConsumer PULL_CONSUMER;

    // 实例化消费者
    static {
        // Consumer组名，多个Consumer如果属于一个应用，订阅同样的消息，且消费逻辑一致，则应该将它们归为同一组
        // 要注意的是，消费者组的消费者实例必须订阅完全相同的Topic
        PUSH_CONSUMER = new DefaultMQPushConsumer("consumer_unique_group");
        // 设置NameServer的地址
        PUSH_CONSUMER.setNamesrvAddr("localhost:9876");

        /**
         * 1. 下面是PushConsumer的一些默认参数，可缺省
         */
        // 消费模型支持集群消费和广播消费两种，默认是集群消息
        // 1. 集群消费模式下,相同Consumer Group的每个Consumer实例平均分摊消息
        // 2. 广播消费模式下，相同Consumer Group的每个Consumer实例都接收全量的消息
        PUSH_CONSUMER.setMessageModel(MessageModel.CLUSTERING);
        // consumer启动后，默认从上次消费的位置开始消费，这包含两种情况：
        // 一种是上次消费的位置未过期，则消费从上次中止的位置进行；
        // 一种是上次消费位置已经过期，则从当前队列第一条消息开始消费
        PUSH_CONSUMER.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 只有当consumeFromWhere值为CONSUME_FROM_TIMESTAMP时才起作用,默认半小时前
//        CONSUMER.setConsumeTimestamp();
        // Rebalance算法实现策略
        PUSH_CONSUMER.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragely());
        // 消费线程池的最小线程数
        PUSH_CONSUMER.setConsumeThreadMin(10);
        // 消费线程池的最大线程数
        PUSH_CONSUMER.setConsumeThreadMax(20);
        // 单队列并行消费允许的最大跨度
        PUSH_CONSUMER.setConsumeConcurrentlyMaxSpan(2000);
        // 拉消息本地队列缓存消息最大数
        PUSH_CONSUMER.setPullThresholdForQueue(1000);
        // 拉消息间隔，由于是长轮询，所以为0，但是如果应用为了流控，也可以设置大于0的值，单位毫秒
        PUSH_CONSUMER.setPullInterval(0);
        // 批量消费，一次消费多少条消息
        PUSH_CONSUMER.setConsumeMessageBatchMaxSize(1);
        // 批量消费，一次最大拉多少条消息
        PUSH_CONSUMER.setPullBatchSize(32);


        PULL_CONSUMER = new DefaultLitePullConsumer();
        /**
         * 2. PullConsumer 的默认配置
         */
        PULL_CONSUMER.setConsumerGroup("consumer_pull_group");

    }

    // 定义有序消费的内容
    private static Map<Integer, List<String>> orderedMsgContainer = new HashMap<>(16);
    // 用于计算消费消息的数量，定位日志使用
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
//        consume();
        orderedConsume();
        // 启动消费者实例
        PUSH_CONSUMER.start();
        System.out.print("Consumer Started.");
    }

    /**
     * 1. 普通消费消息方式 此时从多个queue上拉取消息，这种情况发送和消费是不能保证顺序。
     *
     * @throws Exception
     */
    private static void consume() throws Exception {
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息,
        // subExpression 表示是tag（用于过滤消息），可以传多个以 || 分割，如tag1 || tag2
        // 这里给入的过滤tag是仅支持一个消息只能有一个tag，复杂场景可以使用SQL92标准的sql语句进行扩展（仅限push模式的消费者）
        PUSH_CONSUMER.subscribe("SYNC_TOPIC", "tga1||tag2|| TagA");
        PUSH_CONSUMER.subscribe("ASYNC_TOPIC", "TagA");
        PUSH_CONSUMER.subscribe("ONEWAY_TOPIC", "TagA");
        PUSH_CONSUMER.subscribe("ORDERED_TOPIC", "TagA");
        PUSH_CONSUMER.subscribe("BATCH_TOPIC", "TagA || TagB");
        PUSH_CONSUMER.subscribe("TRANSACTION_TOPIC", "TagA || TagB || TagC || TagD || TagE");

        // 注册回调实现类来处理从broker拉取回来的消息
        PUSH_CONSUMER.registerMessageListener((MessageListenerConcurrently) (msgs, consumeConcurrentlyContext) -> {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            // List<MessageExt> msgs是一个List，批量消息这个List中有多个，否则只有一个
            msgs.forEach(msg -> {
                try {
                    // RocketMQ无法避免消息重复（Exactly-Once），所以如果业务对消费重复非常敏感，务必要在业务层面进行去重处理
                    // 首先需要确定消息的唯一键，可以是msgId，也可以是消息内容中的唯一标识字段，例如订单Id等。在消费之前判断唯一键是否在关系数据库中存在
                    // msgId一定是全局唯一标识符，但是实际使用中，可能会存在相同的消息有两个不同msgId的情况（消费者主动重发、因客户端重投机制导致的重复等），这种情况就需要使业务字段进行重复消费
                    // 输出内容和延迟时间 = 当前时间 - 存储时间
                    System.out.printf("[%d ms later] -> %s %n", System.currentTimeMillis() - msg.getStoreTimestamp(),
                            new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
            // 标记该消息已经被成功消费
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

    }

    /**
     * @formatter:off
     * 2. 分区顺序消息消费，带事务方式（应用可控制Offset什么时候提交）
     * 需要配合顺序消息发送方式才有意义 这里的有序是分区有序，即一个queue里面的顺序是有序的。
     * 顺序消费消息 消费的时候只从这个queue上依次拉取，则就保证了顺序。
     *
     * 分区有序的典型Demo就是订单：一个订单的流程大概是：创建->付款->推送->完成，这个流程中的4个步骤会统一带上订单id（OrderId）
     * 对于消息队列模型而言，这4个步骤定义为一组有序消息，对于同一个订单发出的4个消息，期望4个消息被有序消费
     * 所以我们要做的就是将一组消息向同一个队列中发，消费时从这个队列中依次拉取
     * @formatter:on
     */
    private static void orderedConsume() throws MQClientException {
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        PUSH_CONSUMER.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        PUSH_CONSUMER.subscribe("ORDERED_TOPIC", "TagA || TagC || TagD");

        PUSH_CONSUMER.registerMessageListener(new MessageListenerOrderly() {
            final Random random = new Random();

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);

                for (MessageExt msg : msgs) {
                    String bodyMsg = new String(msg.getBody());
                    // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + ", queueId="
                        + msg.getQueueId() + ", content:" + bodyMsg);

                    // 这里直接按队列分区，debug到此可以查看map里面的数据是否有序
                    List<String> content = orderedMsgContainer.get(msg.getQueueId());
                    if (content == null) {
                        content = new ArrayList<>(16);
                        content.add(bodyMsg);
                        orderedMsgContainer.put(msg.getQueueId(), content);
                    } else {
                        content.add(bodyMsg);
                    }
                }

                try {
                    //模拟业务逻辑处理中...
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 用于
                int i = count.incrementAndGet();
                if (i == 100) {
                    System.out.println(">> 100 consumer msg! check your orderedMsgContainer order!");
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

    }
}
