package com.poshing.checkdemo.aspect;

import com.poshing.checkdemo.utils.JsonUtils;
import com.poshing.checkdemo.utils.Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author litianyi
 */
@Aspect
@Component
public class LoginAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAspect.class);

    @Pointcut("execution(* com.poshing.checkdemo.controller.*.*(..)) && !execution(* com.poshing.checkdemo.controller.LoginController.checkLogin(..))")
    public void checkLogin() {
    }

    @Around("checkLogin()")
    public Object beforeAdvice(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug("checklogin");
        boolean isLogin = verification("username");
        if (isLogin) {
            //继续执行被拦截的方法
            return pjp.proceed();
        } else {
            //构建JSON
            return JsonUtils.getInstance().formatLayerJson(201, "未登录");
        }
    }

    public boolean verification(String sname) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return Utils.getSession(request, sname) != null;
    }

}
