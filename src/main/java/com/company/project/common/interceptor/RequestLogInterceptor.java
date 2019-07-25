package com.company.project.common.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuzefan  2019/7/25 15:14
 */

@Component
public class RequestLogInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RequestLogInterceptor.class);
    private ThreadLocal<Long> startTime = new ThreadLocal();

    public RequestLogInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        this.startTime.set(System.currentTimeMillis());
        log.info("URL: {}, HTTP_METHOD: {}, IP: {}, HOST: {}", new Object[]{request.getRequestURL().toString(), request.getMethod(), this.getRemoteIp(request), request.getHeader("Host")});
        if (request.getQueryString() != null) {
            log.info("PARAMETERS: {}", request.getQueryString());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        if (response.getStatus() == 200) {
            log.info("URL: {}, RESPONSE STATUS: {}, SPEND TIME: {} ", new Object[]{request.getRequestURL().toString(), response.getStatus(), System.currentTimeMillis() - (Long)this.startTime.get() + " ms"});
        } else {
            log.info("URL: {}, RESPONSE STATUS: {}, SPEND TIME: {} ", new Object[]{request.getAttribute("javax.servlet.error.request_uri"), response.getStatus(), System.currentTimeMillis() - (Long)this.startTime.get() + " ms"});
        }

    }

    private String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
