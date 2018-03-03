package com.qyxy.record.controller;

import com.qyxy.record.common.Result;
import com.qyxy.record.filter.LoginFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *
 * @author luxiaoyong
 * @date 2018/2/25
 */
@RestController
@RequestMapping(LoginController.LOGIN_REQUEST_PATH)
public class LoginController {

    public static final String LOGIN_REQUEST_PATH = "/login";

    @Value("${question.question}")
    private String question;

    @Value("${question.answer}")
    private String answer;

    @RequestMapping(value = "getQuestion", method = RequestMethod.GET)
    public Result getQuestion(HttpServletRequest request){
        boolean cookieValid = LoginFilter.validCookie(request.getCookies());
        if (cookieValid){
            return Result.successResult();
        }
        return Result.successResult(question);
    }

    @RequestMapping(value = "answer", method = RequestMethod.POST)
    public Result answer(String answer, HttpServletResponse response){
        if (StringUtils.isNotBlank(answer) && this.answer.equals(answer.trim())){
            String key = UUID.randomUUID().toString();
            String value = UUID.randomUUID().toString();
            LoginFilter.addValidData(key, value);
            Cookie cookie1 = new Cookie(LoginFilter.VALID_COOKIE_NAME_KEY, key);
            Cookie cookie2 = new Cookie(LoginFilter.VALID_COOKIE_VALUE_KEY, value);

            cookie1.setMaxAge(-1);
            cookie2.setMaxAge(-1);

            cookie1.setPath("/");
            cookie2.setPath("/");

            cookie1.setHttpOnly(true);
            cookie2.setHttpOnly(true);
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            return Result.successResult();
        }
        return Result.badRequestResult(question);
    }
}
