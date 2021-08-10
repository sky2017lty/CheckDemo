package com.poshing.checkdemo.controller;

import com.alibaba.fastjson.JSONObject;
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
        return loginServices.checkLogin(request);
    }

    @PostMapping("/getUser")
    @ResponseBody
    public String getUser(HttpServletRequest request) {
        return loginServices.getUser(request);
    }

    @PostMapping("/addUser")
    @ResponseBody
    public String addUser(HttpServletRequest request) {
        return loginServices.addUser(request);
    }

    @PostMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(HttpServletRequest request) {
        return loginServices.deleteUser(request);
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(HttpServletRequest request) {
        return loginServices.register(request);
    }

    @PostMapping("/updatePass")
    @ResponseBody
    public String updatePass(HttpServletRequest request) {
        return loginServices.updatePass(request);
    }

    @PostMapping("/checkSession")
    @ResponseBody
    public String checkSession(HttpServletRequest request) {
        return loginServices.checkSession(request);
    }

    @PostMapping("/delSession")
    @ResponseBody
    public String delSession(HttpServletRequest request) {
        return loginServices.delSession(request);
    }
}
