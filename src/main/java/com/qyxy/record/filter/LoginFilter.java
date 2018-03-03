package com.qyxy.record.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import lombok.Setter;
import org.apache.catalina.servlet4preview.http.HttpFilter;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 *
 * @author luxiaoyong
 * @date 2018/2/25
 */
public class LoginFilter extends HttpFilter {

    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    public static final String VALID_COOKIE_NAME_KEY = "QYXY_LOVE_EVERYDAY_NAME";
    public static final String VALID_COOKIE_VALUE_KEY = "QYXY_LOVE_EVERYDAY_VALUE";

    private static volatile Cache<String, String> validDataCache;

    @Setter
    private int expireTime = 180;

    @Override
    public void init() throws ServletException {
        super.init();
        validDataCache = CacheBuilder.newBuilder().expireAfterWrite(expireTime, TimeUnit.SECONDS).build();
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 校验cookie
        if (!validCookie(request.getCookies())){
            response.setStatus(HttpStatus.OK.value());
            Map<String, String> redirectData = Maps.newHashMap();
            redirectData.put("code", "302");
            redirectData.put("message", "会话过期");
            response.getOutputStream().write(JSON.toJSONString(redirectData).getBytes());
            return;
        }

        // 正常操作
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    public static boolean validCookie(Cookie[] cookies){
        if (ArrayUtils.isEmpty(cookies)){
            return false;
        }

        Optional<Cookie> cookieNameOptional = Stream.of(cookies).parallel().filter(cookie -> VALID_COOKIE_NAME_KEY.equals(cookie.getName())).findAny();
        Optional<Cookie> cookieValueOptional = Stream.of(cookies).parallel().filter(cookie -> VALID_COOKIE_VALUE_KEY.equals(cookie.getName())).findAny();
        if (BooleanUtils.isNotTrue(cookieNameOptional.isPresent()) || BooleanUtils.isNotTrue(cookieValueOptional.isPresent())){
            return false;
        }

        String name = cookieNameOptional.get().getValue();
        String value = cookieValueOptional.get().getValue();

        String validValue = validDataCache.getIfPresent(name);

        if (StringUtils.isBlank(validValue)){
            return false;
        }
        return BooleanUtils.isTrue(validValue.equals(value));
    }

    public static void addValidData(String key, String value){
        validDataCache.put(key, value);
    }
}
