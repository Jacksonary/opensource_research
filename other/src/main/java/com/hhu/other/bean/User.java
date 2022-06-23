package com.hhu.other.bean;

import com.alibaba.fastjson.JSON;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author weiguo.liu
 * @date 2020/11/28
 * @description
 */
@Component
@Scope("prototype")
//@Scope("singleton")
public class User {

    private Long id;
    private String name;

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
        LOGGER.info("user construct, user:{}", JSON.toJSONString(this));
    }

    /**
     * 在构造方法被调用后立刻执行
     */
    @PostConstruct
    public void init() {
        LOGGER.info("user init, user:{}", JSON.toJSONString(this));
    }

    @PreDestroy
    public void destroy() {
        LOGGER.info("user destroy, user:{}", JSON.toJSONString(this));
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        System.out.println(user);

        user = null;
    }
}
