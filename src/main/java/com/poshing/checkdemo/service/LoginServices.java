package com.poshing.checkdemo.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author litianyi
 */
public interface LoginServices {

    /**
     * 登录校验
     * @param request
     * @return
     */
    String checkLogin(HttpServletRequest request);

    /**
     * 注册
     * @param request
     * @return
     */
    String register(HttpServletRequest request);

    /**
     * 修改密码
     * @param request
     * @return
     */
    String updatePass(HttpServletRequest request);

    /**
     * 检查session
     * @param request
     * @return
     */
    String checkSession(HttpServletRequest request);

    /**
     * 删除session
     * @param request
     * @return
     */
    String delSession(HttpServletRequest request);

    /**
     * 获取用户用于管理
     * @param request
     * @return
     */
    String getUser(HttpServletRequest request);

    /**
     * 增加用户
     * @param request
     * @return
     */
    String addUser(HttpServletRequest request);

    /**
     * 删除用户
     * @param request
     * @return
     */
    String deleteUser(HttpServletRequest request);
}
