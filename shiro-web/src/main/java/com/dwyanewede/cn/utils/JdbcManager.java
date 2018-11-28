package com.dwyanewede.cn.utils;

import com.dwyanewede.cn.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: JdbcUtils
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 15:51
 * @Version: 1.0
 */
@Component
public class JdbcManager {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected static final String QUERY_USER_BY_USERNAME = "select username,password from users where username = ?";

    protected static final String QUERY_ROLES_BY_USERNAME = "select role_name from user_roles where username = ?";


    /*public User getUserByUserName(String userName) {
        User user = jdbcTemplate.query(QUERY_USER_BY_USERNAME, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }, new String[]{userName});

        return user;
    }*/
    public User getUserByUserName(String userName) {
        List<User> userList = jdbcTemplate.query(QUERY_USER_BY_USERNAME, new String[]{userName}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        return userList.get(0);
    }

    public Set<String> queryRolesByUserName(String userName) {
        List list = jdbcTemplate.query(QUERY_ROLES_BY_USERNAME, new String[]{userName}, new RowMapper<String>() {
            @Nullable
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");
            }
        });
        HashSet<String> set = new HashSet<>();
        set.add((String) list.get(0));
        return set;
    }
}
