package com.hhu.other.rpc;

import com.hhu.other.cat.rpc.CatFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jacks
 * @date 2022/3/21
 */
@FeignClient(name = "pavo-meta-data-app", url = "http://localhost:7200", contextId = "delphinusClient",
    configuration = {CatFeignConfiguration.class})
public interface DelphinusClient {
    @GetMapping(path = "/ping", produces = {"text/plain"})
    String root();
}
