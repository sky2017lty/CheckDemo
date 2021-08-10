package com.poshing.checkdemo.controller;

import com.poshing.checkdemo.service.CheckServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author litianyi
 */
@Controller
public class CheckController {

    @Resource
    private CheckServices checkServices;

    @GetMapping("/checkData")
    @ResponseBody
    public String checkData(HttpServletRequest request) {
        return checkServices.checkData(request);
    }

    @GetMapping("/addFeedingCheck")
    @ResponseBody
    public String addFeedingCheck(HttpServletRequest request) {
        return checkServices.addFeedingCheck(request);
    }

    @GetMapping("/cutting")
    @ResponseBody
    public String cutting(HttpServletRequest request) {
        return checkServices.cutting(request);
    }

    @GetMapping("/getAllLog")
    @ResponseBody
    public String getAllLog(HttpServletRequest request) {
        return checkServices.getAllLog(request);
    }
}