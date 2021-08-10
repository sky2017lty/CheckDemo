package com.poshing.checkdemo.utils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author litianyi
 */
public class Utils {

    private Utils() {
    }

    /**
     * 判断是否修改数据库成功
     *
     * @param flag
     * @return
     */
    public static String returnJson(int flag) {
        if (1 == flag) {
            return JsonUtils.getInstance().formatLayerJson(0, "success");
        } else {
            return JsonUtils.getInstance().formatLayerJson(200, "failed");
        }
    }

    /**
     * 存入session
     *
     * @param request
     * @param svalue
     * @return
     */
    public static void setSession(HttpServletRequest request, String sname, String svalue) {
        request.getSession().setAttribute(sname, svalue);
    }

    /**
     * 获取session
     *
     * @param request
     * @param svalue
     * @return
     */
    public static String getSession(HttpServletRequest request, String svalue) {
        return (String) request.getSession().getAttribute(svalue);
    }

    /**
     * 移除session
     *
     * @param request
     * @param svalue
     * @return
     */
    public static void delSession(HttpServletRequest request, String svalue) {
        request.getSession().removeAttribute(svalue);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDate() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(dNow);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTime() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        return ft.format(dNow);
    }

    /**
     * 判断对象是否为null,不允许空白串
     *
     * @param object 目标对象类型
     * @return
     */
    public static boolean isNull(Object object) {
        if (null == object) {
            return true;
        }
        if ((object instanceof String)) {
            return "".equals(((String) object).trim());
        }
        return false;
    }
}
