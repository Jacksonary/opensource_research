package com.hhu.other.cat.rpc;

import com.hhu.other.util.ServletUtils;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;

import lombok.Getter;

/**
 * http协议传输，远程调用链目标端接收context的filter，
 * 通过header接收rootId、parentId、childId并放入CatContextImpl中，调用Cat.logRemoteCallServer()进行调用链关联
 * 注:若不涉及调用链，则直接使用cat-client.jar中提供的filter即可 使用方法（视项目框架而定）： 1、web项目：在web.xml中引用此filter
 * 2、Springboot项目，通过注入bean的方式注入此filter，注意启动类添加扫描的注解 @ServletComponentScan 才能生效
 * 
 * @author soar
 * @date 2019-01-10
 */
//@WebFilter(filterName = "catContextServletFilter", urlPatterns = "/*")
public class CatContextServletFilter implements Filter {
    private static final String TRANSACTION_NAME_TEMPLATE = "%s @[%s]";
    private static final Logger LOGGER = LoggerFactory.getLogger(CatContextServletFilter.class);
    private Map<RequestMappingInfo, HandlerMethod> requestMappingContainer;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        RequestMappingHandlerMapping handlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        if (CollectionUtils.isEmpty(handlerMethods)) {
            LOGGER.warn(">> unexpected result -> handlerMethods is null or empty");
            return;
        }

        // 将容器中的 requestMapping 缓存起来
        requestMappingContainer = handlerMethods;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        // 若header中有context相关属性，则生成调用链，若无，仅统计请求的Transaction
        if (null != request.getHeader(CatConstantsExt.CAT_HTTP_HEADER_ROOT_MESSAGE_ID)) {
            CatContextImpl catContext = new CatContextImpl();
            catContext.addProperty(Cat.Context.ROOT,
                request.getHeader(CatConstantsExt.CAT_HTTP_HEADER_ROOT_MESSAGE_ID));
            catContext.addProperty(Cat.Context.PARENT,
                request.getHeader(CatConstantsExt.CAT_HTTP_HEADER_PARENT_MESSAGE_ID));
            catContext.addProperty(Cat.Context.CHILD,
                request.getHeader(CatConstantsExt.CAT_HTTP_HEADER_CHILD_MESSAGE_ID));
            Cat.logRemoteCallServer(catContext);
        }

        CatHttpTransactionInfo catHttpTransactionInfo = generateTransactionName(request);
        Transaction filterTransaction =
            Cat.newTransaction(CatConstants.TYPE_URL, catHttpTransactionInfo.getTransactionName());

        try {
            // 请求方法统计
            Cat.logEvent(CatConstantsExt.Type_URL_METHOD, catHttpTransactionInfo.getMethod(), Message.SUCCESS,
                catHttpTransactionInfo.getUri());
            // 客户端分析使用
            Cat.logEvent(CatConstantsExt.Type_URL_CLIENT, ServletUtils.getRemoteAddress(request));

            filterChain.doFilter(servletRequest, servletResponse);

            filterTransaction.setSuccessStatus();
        } catch (Exception e) {
            filterTransaction.setStatus(e);
            Cat.logError(e);
            // 这里需要继续把异常抛出来，不然会在日志文件中被吞掉
            throw e;
        } finally {
            filterTransaction.complete();
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 构建事务上报的名字，便于统计（解决 restful 无法统计的情况） 重新定义 cat 事务名，主流的 restful api 无法进行准确统计，需要自定义重写，形式为 /demo/{}/props @GET
     *
     * @param request
     * @return
     */
    private CatHttpTransactionInfo generateTransactionName(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 重新定义 cat 事务名，主流的 restful api 无法进行准确统计，需要自定义重写， build uri, /demo/{}/props@GET
        RequestMappingInfo matchingCondition = null;
        for (Entry<RequestMappingInfo, HandlerMethod> o : requestMappingContainer.entrySet()) {
            matchingCondition = o.getKey().getMatchingCondition(request);
            if (matchingCondition != null) {
                break;
            }
        }

        if (matchingCondition == null) {
            return new CatHttpTransactionInfo(uri, method, String.format(TRANSACTION_NAME_TEMPLATE, uri, method));
        }

        PatternsRequestCondition patternsCondition = matchingCondition.getPatternsCondition();
        Set<String> patterns = patternsCondition.getPatterns();
        if (!CollectionUtils.isEmpty(patterns)) {
            uri = patterns.iterator().next();
        }

        // 这里可能为空，如果不写方法，可以直接取 httpServlet 中的方法
        Set<RequestMethod> methods = matchingCondition.getMethodsCondition().getMethods();
        if (!CollectionUtils.isEmpty(methods)) {
            method = methods.iterator().next().name();
        }

        return new CatHttpTransactionInfo(uri, method, String.format(TRANSACTION_NAME_TEMPLATE, uri, method));
    }

    @Getter
    private static class CatHttpTransactionInfo {
        private String uri;
        private String method;
        private String transactionName;

        public CatHttpTransactionInfo(String uri, String method, String transactionName) {
            this.uri = uri;
            this.method = method;
            this.transactionName = transactionName;
        }
    }
}
