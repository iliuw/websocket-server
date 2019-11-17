package com.robby.app.wsserver.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域处理
 * Created @ 2019/11/17
 * @author liuwei
 */
@Component
public class HttpRequestInterceptor implements HandlerInterceptor {
    @Value("${sysconf.cors-allow.headers}")
    String headers;
    @Value("${sysconf.cors-allow.origin}")
    String origin;
    @Value("${sysconf.cors-allow.methods}")
    String methods;
    @Value("${sysconf.cors-allow.power}")
    String power;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", origin);
        String header = String.format("%s,%s", headers, headers.toLowerCase());
        response.setHeader("Access-Control-Allow-Headers", header);
        response.setHeader("Access-Control-Allow-Methods", methods);
        response.setHeader("X-Powered-By", power);

        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)) {
            response.setStatus(200);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
