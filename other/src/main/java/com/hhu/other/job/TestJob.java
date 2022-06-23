package com.hhu.other.job;

import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author jacks
 * @date 2022/2/21
 */
@Component
public class TestJob {
//    @Scheduled(cron ="*/2 * 13 * * Mon")
    public void sayHello() {
        System.out.println(LocalDateTime.now()
            + ": hello");
    }
}
