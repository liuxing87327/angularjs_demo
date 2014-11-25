package com.dooioo.employee.controller;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 功能说明：首页
 * 作者：liuxing(2014-11-25 02:03)
 */
@Controller
@RequestMapping(value="/employee")
public class EmployeeController {

    private static long version = DateTime.now().getMillis();

    /**
     * 默认所有angular的URL请求全部路由到这个方法
     * @return
     */
    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("version", version);
        return "index";
    }

}