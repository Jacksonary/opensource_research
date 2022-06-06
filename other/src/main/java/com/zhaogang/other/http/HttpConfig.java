package com.zhaogang.other.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacks
 * @date 2022/3/21
 */
@Configuration
public class HttpConfig {

    private Integer maxTotal = 200;
    private Integer defaultMaxPerRoute = 20;
    private Integer connectTimeout = 20000;
    private Integer connectionRequestTimeout = 20000;
    private Integer socketTimeout = 20000;

    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;
    }

    @Bean(name = "httpClient")
    public HttpClient getCloseableHttpClient(
        @Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {
        return HttpClientBuilder.create().setConnectionManager(httpClientConnectionManager)
            .setDefaultRequestConfig(getRequestConfig()).build();
    }

    @Bean
    public RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout)
            .setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
    }
}
