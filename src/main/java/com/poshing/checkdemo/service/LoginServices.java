package com.poshing.checkdemo.service;

/**
 * @author litianyi
 */
public interface LoginServices {

    String checkLogin(String username, String password);

    String register(String username, String password);
}
