package com.poshing.checkdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poshing.checkdemo.dao.UserDao;
import com.poshing.checkdemo.entity.User;
import com.poshing.checkdemo.service.LoginServices;
import com.poshing.checkdemo.utils.JsonUtils;
import com.poshing.checkdemo.utils.UUIDUtils;
import com.poshing.checkdemo.utils.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author litianyi
 */
@Service
public class LoginServicesImpl implements LoginServices {

    //管理员
    private static final String ADMIN = "admin";

    @Resource
    private UserDao userDao;

    /**
     * 登录校验
     *
     * @param request
     * @return
     */
    @Override
    public String checkLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (ADMIN.equals(username) && ADMIN.equals(password)) {
            Utils.setSession(request, "username", ADMIN);
            Utils.setSession(request, "name", ADMIN);
            return JsonUtils.getInstance().formatLayerJson(1, "管理员登陆成功");
        }
        User one = userDao.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", password));
        if (one == null) {
            return JsonUtils.getInstance().formatLayerJson(200, "找不到用户");
        } else {
            Utils.setSession(request, "username", username);
            Utils.setSession(request, "name", one.getName());
            return JsonUtils.getInstance().formatLayerJson(0, "登陆成功", 1, JSON.toJSONString(one));
        }

    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @Override
    public String register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User one = userDao.selectOne(new QueryWrapper<User>().eq("username", username));
        if (one == null) {
            User user = new User();
            user.setUuid(UUIDUtils.getUuid());
            user.setUsername(username);
            user.setPassword(password);
            int flag = userDao.insert(user);
            return Utils.returnJson(flag);
        } else {
            return JsonUtils.getInstance().formatLayerJson(200, "failed");
        }
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @Override
    public String updatePass(HttpServletRequest request) {
        String username = request.getParameter("username");
        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");
        User one = userDao.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", oldPassword));
        if (one == null) {
            return JsonUtils.getInstance().formatLayerJson(200, "用户名或密码错误");
        } else {
            one.setPassword(newPassword);
            int flag = userDao.updateById(one);
            return Utils.returnJson(flag);
        }
    }

    @Override
    public String checkSession(HttpServletRequest request) {
        String sname = request.getParameter("sname");
        String s = Utils.getSession(request, "username");
        if (sname.equals(s)) {
            return JsonUtils.getInstance().formatLayerJson(0, "success");
        } else {
            return JsonUtils.getInstance().formatLayerJson(200, "failed");
        }
    }

    @Override
    public String delSession(HttpServletRequest request) {
        Utils.delSession(request, "username");
        return JsonUtils.getInstance().formatLayerJson(0, "success");
    }

    @Override
    public String getUser(HttpServletRequest request) {
        List<User> selectList = userDao.selectList(new QueryWrapper<User>());
        JSONArray jsonArray = new JSONArray();
        for (User user : selectList) {
            JSONObject json = new JSONObject();
            json.put("uuid", user.getUuid());
            json.put("username", user.getUsername());
            json.put("name", user.getName());
            jsonArray.add(json);
        }
        return JsonUtils.getInstance().formatLayerJson(0, "success", jsonArray);
    }

    @Override
    public String addUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        if (Utils.isNull(username) || Utils.isNull(name)) {
            return JsonUtils.getInstance().formatLayerJson(200, "用户名或姓名为空");
        } else {
            User user = new User();
            user.setUuid(UUIDUtils.getUuid());
            user.setUsername(username);
            user.setPassword("123456");
            user.setName(name);
            int flag = userDao.insert(user);
            return Utils.returnJson(flag);
        }
    }

    @Override
    public String deleteUser(HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        User user = new User();
        user.setUuid(uuid);
        int flag = userDao.deleteById(user);
        return Utils.returnJson(flag);
    }


}
