package com.zhaogang.other.cat;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.dianping.cat.servlet.CatFilter;

/**
 * 和 springBoot 的集成监控 url
 * 
 * @author jacks
 * @date 2022/3/17
 */
@Configuration
public class CatFilterConfig {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    /**
     * 拦截 springboot 里的 restUrl 进行自动打点
     * 由于涉及微服务的调用监控，所以这里直接过滤器 CatContextServletFilter
     * 若不涉及 feignClient 的调用可以直接使用官方提供的 CatFilter
     */
//    @Bean
//    public FilterRegistrationBean catFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        CatFilter filter = new CatFilter();
//        registration.setFilter(filter);
//        registration.addUrlPatterns("/*");
//        registration.setName("cat-filter");
//        registration.setOrder(1);
//        return registration;
//    }

    /**
     * 拦截 mybatis
     * @param mysqlDataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory mysqlSessionFactory(DataSource mysqlDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mysqlDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:com/zhaogang/other/dao/mapper/nebula_dev/*.xml"));
        sqlSessionFactoryBean.setPlugins(new CatMybatisInterceptor(jdbcUrl));
        return sqlSessionFactoryBean.getObject();
    }
}
