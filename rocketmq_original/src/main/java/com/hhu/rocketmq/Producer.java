package com.hhu.rocketmq;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author weiguo.liu
 * @date 2020/10/22
 * @description
 */
public class Producer {

    private static final DefaultMQProducer PRODUCER;
    private static final TransactionMQProducer TRANSACTION_PRODUCER;

    /**
     * 初始化发发送者
     */
    static {
        /**
         * 生产者组的定义：
         * 同一类Producer的集合，这类Producer发送同一类消息且发送逻辑一致。如果发送的是事务消息且原始生产者在发送之后崩溃，则Broker服务器会联系同一生产者组的
         * 其他生产者实例以提交或回溯消费
         */
        // 实例化消息生产者Producer,group 由数字、字母、下划线、横杠（-）、竖线（|）或百分号组成；不能为空；长度不能超过255
        PRODUCER = new DefaultMQProducer("producer_unique_group");
        // 设置NameServer的地址
        PRODUCER.setNamesrvAddr("localhost:9876");

        // 在发送消息时，自动创建服务器不存在的topic，需要指定Key，该Key可用于配置发送消息所在topic的默认路由
        // 默认值：TBW102,测试或者demo使用，生产环境下不建议打开自动创建配置
        PRODUCER.setCreateTopicKey("TBW102");
        // 创建topic时默认的队列数量
        PRODUCER.setDefaultTopicQueueNums(4);
        // 发送消息时的超时时间,不建议修改该值，该值应该与broker配置中的sendTimeout一致，发送超时，可临时修改该值，建议解决超时问题，提高broker集群的Tps
        PRODUCER.setSendMsgTimeout(3000);
        // 压缩消息体阈值。大于4K的消息体将默认进行压缩
        PRODUCER.setCompressMsgBodyOverHowmuch(1024*4);
        // 同步模式下，在返回发送失败之前，内部尝试重新发送消息的最大次数,默认2(即：默认情况下一条消息最多会被投递3次)
        // 注意：在极端情况下，这可能会导致消息的重复
        PRODUCER.setRetryTimesWhenSendFailed(2);
        // 同步模式下，消息保存失败时是否重试其他broker,默认false
        // 注意：此配置关闭时，非投递时产生异常情况下，会忽略retryTimesWhenSendFailed配置
        PRODUCER.setRetryAnotherBrokerWhenNotStoreOK(false);
        // 消息的最大大小(字节)。当消息题的字节数超过maxMessageSize就发送失败，默认4K
        PRODUCER.setMaxMessageSize(1024 * 1024 * 4);
    }

    static {
        TRANSACTION_PRODUCER = new TransactionMQProducer("transaction_unique_group");
        // 设置NameServer的地址
        PRODUCER.setNamesrvAddr("localhost:9876");

        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("client-transaction-msg-check-thread");
                    return thread;
                });
        TRANSACTION_PRODUCER.setExecutorService(executorService);

        // 设置本地事务管理器
        TransactionListener transactionListener = new TransactionListenerImpl();
        TRANSACTION_PRODUCER.setTransactionListener(transactionListener);
    }

    public static void main(String[] args) throws Exception {
        // 启动Producer实例
        PRODUCER.start();

//        syncProduce();
//        asyncProduce();
//        onewayProduce();
        orderedProduce();
//        batchProduce();

        // 启动事务Producer实例
//        TRANSACTION_PRODUCER.start();
//        transactionProduce();
    }


    /**
     * @formatter:off 
     * 1. 第一种消息发送方式：同步发送，可靠，使用的比较广泛，比如：重要的消息通知，短信通知 
     * 1.2 延迟消息也是普通消息，只是多了一个延迟，消费逻辑不变
     * （电商里，提交了一个订单就可以发送一个延时消息，1h后去检查这个订单的状态，如果还是未付款就取消订单释放库存）
     * @formatter:on 
     */
    private static void syncProduce() throws Exception {
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("SYNC_TOPIC" /* Topic */,
                    "TagA" /* Tag */,
                    String.valueOf(i) /* key 建议设置，方便回溯,由于是哈希索引，请务必保证key尽可能唯一，这样可以避免潜在的哈希冲突 */,
                    ("Sync message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );

            // 设置消息延迟级别为3，即10s后发送消息，只支持几个预定义的延时
            // mq源码的store模块中的 MessageStoreConfig 类中的messageDelayLevel变量定义了延迟级别
            // 默认支持 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h，只需要给序号即可（2表示5s，3表示10s）
//            msg.setDelayTimeLevel(3);

            // 发送消息到一个Broker
            // send方法本身支持内部重试，重试逻辑为：
            // 1. 至多重试2次（同步发送为2次，异步发送为0次）。
            // 2. 如果发送失败，则轮转到下一个Broker。这个方法的总耗时时间不超过sendMsgTimeout设置的值，默认10s。
            // 3. 如果本身向broker发送消息产生超时异常，就不会再重试
            SendResult sendResult = PRODUCER.send(msg);
            // 消息发送成功或者失败要打印消息日志，务必要打印SendResult和key字段。
            // send消息方法只要不抛异常，就代表发送成功。发送成功会有多个状态
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        PRODUCER.shutdown();
    }

    /**
     * 2. 第二种消息发送方式：异步发送，也是可靠的，通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应
     */
    private static void asyncProduce() throws Exception {
        PRODUCER.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;
        // 根据消息数量实例化倒计时计算器
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("ASYNC_TOPIC",
                    "TagA",
                    String.valueOf(i),
                    ("async message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // SendCallback接收异步返回结果的回调
            PRODUCER.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        // 等待5s
        countDownLatch.await(5, TimeUnit.SECONDS);
        // 如果不再发送消息，关闭Producer实例。
        PRODUCER.shutdown();
    }

    /**
     * 3. 第三种消息发送方式：单向发送（oneway）,没有ack，不可靠,主要用在不特别关心发送结果的场景，例如日志发送
     */
    private static void onewayProduce() throws Exception {
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("ONEWAY_TOPIC" /* Topic */,
                    "TagA" /* Tag */,
                    ("Oneway message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送单向消息，没有任何返回结果
            PRODUCER.sendOneway(msg);
        }
        // 如果不再发送消息，关闭Producer实例。
        PRODUCER.shutdown();
    }

    /**
     * @formatter:off 
     * 4. 第四种消息发送方式：顺序消息（分区、队列有序）
     * 在默认的情况下,消息发送会采取Round Robin轮询方式把消息发送到不同的queue(分区队列)； 
     * 而消费消息的时候从多个queue上拉取消息，这种情况发送和消费是不能保证顺序。
     * 
     * 有序消息的实现， 
     * 控制发送的顺序消息只依次发送到同一个queue中， 
     * 消费的时候只从这个queue上依次拉取，则就保证了顺序。 
     * 当发送和消费参与的queue只有一个，则是全局有序（但由于是全局公用一个队列，并发性能会变差）；
     * 如果多个queue参与，则为分区有序，即相对每个queue，消息都是有序的。
     * @formatter:on 
     */
    private static void orderedProduce() throws Exception {
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("ORDERED_TOPIC" /* Topic */,
                    "TagA" /* Tag */,
                    ("Ordered message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );

            // 这里调用的方法
            SendResult sendResult = PRODUCER.send(msg, (mqs, msg1, arg) -> {
                // 根据计数器（实际通常是业务的id）选择发送queue，这个arg参数就是主函数中传入的i
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }, i);

            System.out.printf("SendResult status:%s, queueId:%d, body:%s%n",
                    sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(),
                    new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET));

        }
        // 如果不再发送消息，关闭Producer实例。
        PRODUCER.shutdown();
    }

    /**
     * @formatter:off 
     * 5. 批量消息，合理的批量发送会提升mq的性能
     * 
     * 批量发送的限制： 
     * 1. 批量消息应该有相同的topic、waitStoreMsgOK 
     * 2. 批量消息不能是延时消息 
     * 3. 一批消息的总大小不应超过4MB
     * @formatter:on 
     */
    private static void batchProduce() {
        String topic = "BATCH_TOPIC";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "OrderID001", "Batch message 0".getBytes()));
        messages.add(new Message(topic, "TagB", "OrderID002", "Batch message 1".getBytes()));
        messages.add(new Message(topic, "TagA", "OrderID003", "Batch message 2".getBytes()));

        //把大的消息分裂成若干个小的消息
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            try {
                List<Message> listItem = splitter.next();
                PRODUCER.send(listItem);
            } catch (Exception e) {
                e.printStackTrace();
                //处理error
            }
        }

        // 如果不再发送消息，关闭Producer实例。
        PRODUCER.shutdown();
    }

    /**
     * @formatter:off
     * 6. 事务消息 事务消息有三种状态：提交、回滚、中间
     * TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
     * TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
     * TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
     *
     * 事务消息的限制：
     * 1. 事务消息不支持延时消息和批量消息。
     * 2. 为了避免单个消息被检查太多次而导致半队列消息累积，默认将单个消息的检查次数限制为 15 次，但是用户可以通过 Broker 配置文件
     * 的 transactionCheckMax参数来修改此限制。如果已经检查某条消息超过 N 次的话（ N = transactionCheckMax ） 则 Broker 将
     * 丢弃此消息，并在默认情况下同时打印错误日志。用户可以通过重写 AbstractTransactionalMessageCheckListener 类来修改这个行为。
     * 3. 事务消息将在 Broker 配置文件中的参数 transactionTimeout 这样的特定时间长度之后被检查。当发送事务消息时，用户还可以通过
     * 设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制，该参数优先于 transactionTimeout 参数。
     * 4. 事务性消息可能不止一次被检查或消费。
     * 5. 提交给用户的目标主题消息可能会失败，目前这依日志的记录而定。它的高可用性通过 RocketMQ 本身的高可用性机制来保证，如果希望确
     * 保事务消息不丢失、并且事务完整性得到保证，建议使用同步的双重写入机制。
     * 6. 事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。与其他类型的消息不同，事务消息允许反向查询、MQ服务器能通过它们的生
     * 产者 ID 查询到消费者。
     * @formatter:on
     */
    private static void transactionProduce() throws Exception {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message msg =
                        new Message("TRANSACTION_TOPIC", tags[i % tags.length], "KEY" + i,
                                ("transaction message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 这里可以额外放一些属性，需要时可以使用
                msg.putUserProperty("k" + i, "v" + i);
                msg.putUserProperty("kk" + i, "vv" + i);

                SendResult sendResult = TRANSACTION_PRODUCER.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);
                Thread.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
//        for (int i = 0; i < 100000; i++) {
//            Thread.sleep(1000);
//        }
        TRANSACTION_PRODUCER.shutdown();
    }

}
