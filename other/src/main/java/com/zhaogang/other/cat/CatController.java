package com.zhaogang.other.cat;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.zhaogang.other.dao.mapper.nebula_dev.CoreDriverDOMapper;
import com.zhaogang.other.dao.model.nebula_dev.CoreDriverDO;
import com.zhaogang.other.rpc.DelphinusClient;

/**
 * @author jacks
 * @date 2022/3/17
 */
@RestController
@RequestMapping("/cat")
public class CatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatController.class);
    @Resource
    private CoreDriverDOMapper coreDriverDOMapper;
    @Resource
    private DelphinusClient delphinusClient;

    /**
     * 手动埋点测试
     * 
     * @return
     */
    @GetMapping("/test_origin")
    public String testOrigin() {
        Transaction t = Cat.newTransaction("URL", "pageName");

        try {
            Cat.logEvent("URL.Server", "serverIp", Event.SUCCESS, "ip=${serverIp}");
            // Cat.logMetricForCount("metric.key");
            // Cat.logMetricForDuration("metric.key", 5);

            LOGGER.info(">> do process...");
            TimeUnit.SECONDS.sleep(3);

            t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            t.setStatus(e);
            Cat.logError(e);
        } finally {
            t.complete();
        }
        return "ok";
    }

    /**
     * 记录 cat event
     */
    @GetMapping("/test_events")
    public String testEvent() throws InterruptedException {
        Cat.logEvent("URL.Server", "serverIp", Event.SUCCESS, "ip=${serverIp}");

        LOGGER.info(">> do process...");
        TimeUnit.SECONDS.sleep(3);

        return "ok";
    }

    /**
     * 记录一个带有错误堆栈信息的 Error
     */
    @GetMapping("/test_errors")
    public String testError() {
        Transaction t = Cat.newTransaction("URL", "pageName");

        try {
            LOGGER.info(">> do process, throw exception...");
            throw new RuntimeException("自定义的异常，系统错误");
        } catch (Throwable e) {
            // 如果 e 是一个 Error, type 会被设置为 Error。
            // 如果 e 是一个 RuntimeException, type 会被设置为 RuntimeException。
            // 其他情况下，type 会被设置为 Exception。
            // 同时错误堆栈信息会被收集并写入 data 属性中
            Cat.logError(e);
        } finally {
            t.complete();
        }
        return "ok";
    }

    /**
     * 记录业务指标的总和或平均值 每秒会聚合 metric。 举例来说，如果你在同一秒调用 count 三次（相同的 name），我们会累加他们的值，并且一次性上报给服务端。 在 duration
     * 的情况下，我们用平均值来取代累加值。
     */
    @GetMapping("/test_metrics")
    public String testMetrics() {
        // # Counter
        Cat.logMetricForCount("other.metric");
        // Cat.logMetricForCount("metric.key", 3);

        // # Duration
        // Cat.logMetricForDuration("metric.key", 5);
        LOGGER.info(">> do process...");
        return "ok";
    }

    /**
     * 注：这里只有抓 error 级别的日志，并且需要将 e 放入才生效
     */
    @GetMapping("/test_logs")
    public String testLogs() {
        LOGGER.warn(">> illegal param");
        LOGGER.error(">> occur an error", new RuntimeException("throw an error manually"));
        return "ok";
    }

    @GetMapping("/test_exception")
    public String testExceptions() {
        int i = 8 / 0;
        return "ok";
    }

    @GetMapping("/test_url")
    public String testFilter() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        return "ok";
    }

    @GetMapping("/test_url2")
    public String testFilter2() {
        return "ok";
    }

    @GetMapping("/test_sql")
    public String testSQL() {
        CoreDriverDO coreDriverDO = coreDriverDOMapper.selectByPrimaryKey(144);
        System.out.println(coreDriverDO);
        return "ok";
    }

    @GetMapping("/test_rpc")
    public String testRpc() {
        return delphinusClient.root();
    }
}
