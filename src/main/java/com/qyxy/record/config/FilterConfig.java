package com.qyxy.record.config;

import com.google.common.collect.Lists;
import com.qyxy.record.filter.LoginFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author luxiaoyong
 * @date 2018/2/25
 */
@Configuration
public class FilterConfig {

    @Value("${question.expireTime}")
    private String questionExpireTime;

    @Bean
    public FilterRegistrationBean addLoginFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        LoginFilter loginFilter = new LoginFilter();
        if (StringUtils.isNotBlank(questionExpireTime)){
            loginFilter.setExpireTime(Integer.parseInt(questionExpireTime));
        }
        filterRegistrationBean.setFilter(loginFilter);
        filterRegistrationBean.setUrlPatterns(Lists.newArrayList("/admin/*"));
        return filterRegistrationBean;
    }
}
