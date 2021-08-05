package com.poshing.checkdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poshing.checkdemo.dao.UserDao;
import com.poshing.checkdemo.entity.User;
import com.poshing.checkdemo.service.LoginServices;
import com.poshing.checkdemo.utils.JsonUtils;
import com.poshing.checkdemo.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author litianyi
 */
@Service
public class LoginServicesImpl implements LoginServices {

    @Resource
    private UserDao userDao;

    /**
     * 检查是否登陆
     * @param username
     * @param password
     * @return
     */
    @Override
    public String checkLogin(String username, String password) {
        User one = userDao.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", password));
        if (one == null) {
            return JsonUtils.getInstance().formatLayerJson(200, "找不到用户", null);
        } else {
            return JsonUtils.getInstance().formatLayerJson(0, "登陆成功", 1, JSON.toJSONString(one));
        }

    }

    /**
     * 注册用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public String register(String username, String password) {
        User one = userDao.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", password));
        if (one == null) {
            User user = new User();
            user.setUuid(UUIDUtils.getUuid());
            user.setUsername(username);
            user.setPassword(password);
            int flag = userDao.insert(user);
            if (1 == flag) {
                return JsonUtils.getInstance().formatLayerJson(0, "success", null);
            } else {
                return JsonUtils.getInstance().formatLayerJson(200, "failed", null);
            }
        } else {
            return JsonUtils.getInstance().formatLayerJson(200, "failed", null);
        }
    }
}
