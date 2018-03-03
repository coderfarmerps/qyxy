package com.qyxy.record.controller;

import com.qyxy.record.aop.NotAspect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author luxiaoyong
 * @date 2018/2/25
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @NotAspect
    @RequestMapping("")
    public String index(){
        return "index";
    }
}
