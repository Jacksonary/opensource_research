package com.zhaogang.other.serializer.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.zhaogang.other.drools.dto.Person;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @author weiguo.liu
 * @date 2021/3/31
 * @description
 *
 * @formatter:off
 * 出现过问题：
 * 2021-03-30 19:50:35.919 [WARN] [main-EventThread] [ProtostuffUtils.java:deser 50] -  >> deserialize error -> ProtostuffIOUtil.mergeFrom occur error
 * java.lang.RuntimeException: Reading from a byte array threw an IOException (should never happen).
 *         at io.protostuff.IOUtil.mergeFrom(IOUtil.java:54)
 *         at io.protostuff.ProtostuffIOUtil.mergeFrom(ProtostuffIOUtil.java:104)
 *         at com.internet.trace.util.ProtostuffUtils.deser(ProtostuffUtils.java:48)
 *         at com.internet.trace.factory.CustomerWatcher.getExcelTemplateDO(CustomerWatcher.java:91)
 *         at com.internet.trace.factory.CustomerWatcher.fsyncChangeTemplate(CustomerWatcher.java:105)
 *         at com.internet.trace.factory.CustomerWatcher.lambda$fsyncListTemplate$39(CustomerWatcher.java:80)
 *         at com.internet.trace.factory.CustomerWatcher$$Lambda$29/331686650.accept(Unknown Source)
 *         at java.util.ArrayList.forEach(ArrayList.java:1249)
 *         at com.internet.trace.factory.CustomerWatcher.fsyncListTemplate(CustomerWatcher.java:76)
 *         at com.internet.trace.factory.CustomerWatcher.process(CustomerWatcher.java:56)
 *         at org.apache.curator.framework.imps.NamespaceWatcher.process(NamespaceWatcher.java:77)
 *         at org.apache.zookeeper.ClientCnxn$EventThread.processEvent(ClientCnxn.java:519)
 *         at org.apache.zookeeper.ClientCnxn$EventThread.run(ClientCnxn.java:495)
 * Caused by: io.protostuff.ProtobufException: Protocol message tag had invalid wire type.
 *         at io.protostuff.ProtobufException.invalidWireType(ProtobufException.java:118)
 *         at io.protostuff.ByteArrayInput.skipField(ByteArrayInput.java:194)
 *         at io.protostuff.ByteArrayInput.skipMessage(ByteArrayInput.java:207)
 *         at io.protostuff.ByteArrayInput.skipField(ByteArrayInput.java:185)
 *         at io.protostuff.ByteArrayInput.handleUnknownField(ByteArrayInput.java:217)
 *         at io.protostuff.runtime.RuntimeSchema.mergeFrom(RuntimeSchema.java:462)
 *         at io.protostuff.IOUtil.mergeFrom(IOUtil.java:45)
 *         ... 12 more
 * @formatter:on
 */
public class ProtostuffUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtostuffUtils.class);

    /** 定义为全局重量，不用每次都创建 */
    private static final LinkedBuffer LINKED_BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    private static final ConcurrentMap<Class<?>, Schema<?>> SCHEMA_CONTAINER = new ConcurrentHashMap<>(16);

    public static <T> byte[] ser(T data) {
        if (data == null) {
            LOGGER.warn(">> illegal param -> data is null");
            return null;
        }
        // 运行时加载，可以缓起来
        Schema<T> schema = (Schema<T>)getSchema(data.getClass());
        try {
            return ProtostuffIOUtil.toByteArray(data, schema, LINKED_BUFFER);
        } catch (Exception e) {
            LOGGER.warn(">> serialize error -> ProtostuffIOUtil.toByteArray occur error, param: {}",
                JSON.toJSONString(data));
            e.printStackTrace();
            return null;
        } finally {
            // 记得每次都要定理保证每次可以是空的容器
            LINKED_BUFFER.clear();
        }
    }

    public static <T> T deser(byte[] source, Class<T> clazz) {
        if (source == null || clazz == null) {
            LOGGER.warn(">> illegal param -> source or class is null");
            return null;
        }
        Schema<T> schema = getSchema(clazz);
        T obj = schema.newMessage();
        try {
            ProtostuffIOUtil.mergeFrom(source, obj, schema);
        } catch (Exception e) {
            LOGGER.warn(">> deserialize error -> ProtostuffIOUtil.mergeFrom occur error");
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    private static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema schema = SCHEMA_CONTAINER.get(clazz);
        if (schema != null) {
            return schema;
        }

        RuntimeSchema<T> newSchema = RuntimeSchema.createFrom(clazz);
        SCHEMA_CONTAINER.put(clazz, newSchema);
        return newSchema;
    }

    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(getData(i));
        }

        // json
        long s1 = System.currentTimeMillis();
        String s = JSON.toJSONString(list);
        long s2 = System.currentTimeMillis();

        // protostuff
        byte[] ser = ser(list);
        long s3 = System.currentTimeMillis();

        System.out
            .println(MessageFormat.format(">> {0} -> time took: {1}, space took: {2}", "json", s2 - s1, s.length()));
        System.out.println(
            MessageFormat.format(">> {0} -> time took: {1}, space took: {2}", "protostuff", s3 - s2, ser.length));
    }

    private static Person getData(int i) {
        return new Person("name: " + i, i);
    }

}
