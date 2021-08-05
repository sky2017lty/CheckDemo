package com.poshing.checkdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.poshing.checkdemo.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author litianyi
 * @since 2021-08-04 14:36:27
 */
public interface UserDao extends BaseMapper<User> {

}