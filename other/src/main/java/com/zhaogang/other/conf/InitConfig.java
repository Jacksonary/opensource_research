package com.zhaogang.other.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhaogang.other.bean.Clazz;
import com.zhaogang.other.bean.Student;

/**
 * @author weiguo.liu
 * @date 2020/10/10
 * @description
 */
@Configuration
public class InitConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitConfig.class);

    // 这个Bean 在redisson已经配置了
    // @Bean("stringRedisTemplate")
    // public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    // RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    // redisTemplate.setConnectionFactory(redisConnectionFactory);
    // redisTemplate.setKeySerializer(new StringRedisSerializer());
    // redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
    // return redisTemplate;
    // }

    @ConditionalOnBean(value = {Student.class})
    @Bean(name = "class")
    public Clazz clazz() {
        LOGGER.info("class init ...");
        Clazz clazz = new Clazz();
        clazz.setCode("english");
        clazz.setName("language");
        clazz.setClassProperty("pazz");
        return clazz;
    }

    @Bean(name = "student")
    public Student student() {
        LOGGER.info("student init ...");
        Student student = new Student();
        student.setId(1);
        student.setName("xiaoming");
        student.setAge(20);

        return student;
    }

}
