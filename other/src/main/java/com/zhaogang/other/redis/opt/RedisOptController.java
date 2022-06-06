package com.zhaogang.other.redis.opt;

import com.zhaogang.other.util.DateUtils;
import com.zhaogang.other.util.ISO8601DateTimeUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.zhaogang.other.redis.enums.FailureMeasureDimensionEnum;
import com.zhaogang.other.redis.opt.service.CacheService;

/**
 * @author jacks
 * @date 2022/2/22
 */
@RestController
@RequestMapping("/redis_opt")
public class RedisOptController {

    private static final String HEART_BEAT_COUNT_CACHE_KAY = "mtbf:%s:%s:heartbeat:%s";

    @Resource
    private CacheService cacheService;

    @GetMapping("/scan")
    public void test() {
        // 这里注意 utc 时间，计算前天的数据 | xxx: 项目中日期的处理真是离谱到家了，待改进
        LocalDateTime nowOfSH = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        LocalDateTime fromDateTime = nowOfSH.plusDays(-3).withHour(16).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime toDateTime = nowOfSH.plusDays(-2).withHour(15).withMinute(59).withSecond(59).withNano(59);
        String from = ISO8601DateTimeUtils.format(fromDateTime);
        String to = ISO8601DateTimeUtils.format(toDateTime);

        Date fromDate = Date.from(fromDateTime.atZone(ZoneOffset.UTC).toInstant());
        Date toDate = Date.from(toDateTime.atZone(ZoneOffset.UTC).toInstant());

        String targetTimeKey = DateUtils.getStrDate(toDate);
        String preDayKey = DateUtils.getStrDate(DateUtils.getOffsetDate(toDate, -1));

        // 计算当前指标
        for (FailureMeasureDimensionEnum dimensionEnum : FailureMeasureDimensionEnum.values()) {
            String format =
                String.format(HEART_BEAT_COUNT_CACHE_KAY, targetTimeKey, dimensionEnum.name().toLowerCase(), "*");
            Set<String> keys = cacheService.scan(format,
                5000);
            if (CollectionUtils.isEmpty(keys)) {
                continue;
            }
            List<List<String>> partition = Lists.partition(new ArrayList<>(keys), 1000);

            for (int i = 0; i < partition.size(); i++) {
                List<String> p = partition.get(i);

                List<Integer> heartBeatTimeList = cacheService.multiGetObject(p, Integer.class);
            }
        }
    }

}
