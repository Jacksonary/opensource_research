package com.zhaogang.xxljob.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

/**
 * @author weiguo.liu
 * @date 2020/10/13
 * @description
 */
@Component
public class SampleTestJob {

    @XxlJob("hello_handler")
    public ReturnT<String> demoJobHandler(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return ReturnT.SUCCESS;
    }

}
