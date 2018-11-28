package com.dwyanewede.cn.dao.impl;

import com.dwyanewede.cn.dao.UserDao;
import com.dwyanewede.cn.pojo.User;
import com.dwyanewede.cn.utils.JdbcManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @ClassName: UserDaoImpl
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 15:54
 * @Version: 1.0
 */
public class UserDaoImpl implements UserDao {

    private JdbcManager jdbcManager;

    @Override
    public User getUserByUserName(String userName) {
        return jdbcManager.getUserByUserName(userName);
    }

    @Override
    public Set<String> queryRolesByUserName(String userName) {
        return jdbcManager.queryRolesByUserName(userName);

    }

    public void setJdbcManager(JdbcManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }
}
