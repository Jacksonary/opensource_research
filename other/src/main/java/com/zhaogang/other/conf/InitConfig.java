package com.zhaogang.other.conf;

import com.zhaogang.other.bean.Clazz;
import com.zhaogang.other.bean.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weiguo.liu
 * @date 2020/10/10
 * @description
 */
@Configuration
public class InitConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitConfig.class);



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
