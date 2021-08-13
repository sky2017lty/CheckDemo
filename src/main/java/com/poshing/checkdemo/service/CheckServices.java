package com.poshing.checkdemo.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author litianyi
 */
public interface CheckServices {

    /**
     * 获取check
     * @param request
     * @return
     */
    String checkData(HttpServletRequest request);

    String addFeedingCheck(HttpServletRequest request);

    String cutting(HttpServletRequest request);

    String getAllLog(HttpServletRequest request);

    String addFeedingCheckGroup(HttpServletRequest request);
}
