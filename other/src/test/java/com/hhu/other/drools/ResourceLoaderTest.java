package com.hhu.other.drools;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.hhu.other.OtherApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author weiguo.liu
 * @date 2021/4/1
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OtherApplication.class})
//@ImportResource("classpath:META-INF/*.xml")
public class ResourceLoaderTest {

    @Resource
    private ResourceLoader resourceLoader;

    @Test
    public void validateTest() {
        resourceLoader.validate();
    }
}