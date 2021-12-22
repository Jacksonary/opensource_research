package com.zhaogang.other.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhaogang.other.bean.User;

/**
 * @author jacks
 * @date 2021/12/22
 */
public class JsonTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonTest.class);

    public static void main(String[] args) {
        User user = new User();
        user.setId(23L);
        user.setName("test");
        LOGGER.info("user: {}", user.toString());
        System.out.println(Math.pow(2, 7));
    }
}
