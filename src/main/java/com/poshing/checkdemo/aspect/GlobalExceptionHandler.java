package com.poshing.checkdemo.aspect;

import com.poshing.checkdemo.utils.JsonUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author litianyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return JsonUtils.getInstance().formatLayerJson(500, e.getCause().getMessage());
    }
}
