package com.test.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    /**
     * 页面访问入口，仅支持一级
     *
     * @param value 页面名字
     * @return modelAndView
     */
    @RequestMapping("/web/{value}")
    public String html(@PathVariable("value") String value) {
        return value;
    }
}
