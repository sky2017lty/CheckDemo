package com.poshing.checkdemo.controller;

import com.poshing.checkdemo.service.LoginServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author LiTianyi
 */
@Controller
public class LoginController {

    @Resource
    private LoginServices loginServices;

    @PostMapping("/checkLogin")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return loginServices.checkLogin(username, password);
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return loginServices.register(username, password);
    }

}
