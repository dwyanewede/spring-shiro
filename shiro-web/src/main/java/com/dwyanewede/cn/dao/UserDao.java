package com.dwyanewede.cn.dao;

import com.dwyanewede.cn.pojo.User;

import java.util.Set;

public interface UserDao {

    User getUserByUserName(String userName);

    Set<String> queryRolesByUserName(String userName);
}