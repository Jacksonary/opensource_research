package com.hhu.other.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * @author jacks
 * @date 2022/4/2
 */
public class ServletUtils {

    private static final String UNKNOWN = "unknown";
    private static final String X_FORWARDED_FOR = "x-forwarded-for";
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * 获取客户端真实 ip
     * @param request
     * @return
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }

        String ip = request.getHeader(X_FORWARDED_FOR);
        System.out.println(">> ServletUtils, X_FORWARDED_FOR: " + ip);

        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
            System.out.println(">> ServletUtils, PROXY_CLIENT_IP: " + ip);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
            System.out.println(">> ServletUtils, WL_Proxy_Client_IP: " + ip);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println(">> ServletUtils, RemoteAddr: " + ip);
        }
        return ip;
    }
}
