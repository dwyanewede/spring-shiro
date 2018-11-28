package com.dwyanewede.cn.demo;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName: TestJdbcRealm
 * @Description: IniRealm 功能介绍
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 11:12
 * @Version: 1.0
 */
public class TestJdbcRealm {

    private static DruidDataSource druidDataSource =  new DruidDataSource();

    // 设置自定义查询语句
    private static String QUERY_ROLE_SQL = "select role_name from user_roles where username = ?";

    private static String QUERY_PASSWORD_SQL = "select password from test_user where username = ?";
    // 创建数据源
    static {
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/basedb");
        druidDataSource.setUsername("base");
        druidDataSource.setPassword("123456");

    }

    /**
     * 基于 JdbcRealm 的认证处理
     * @param args
     */
    public static void main(String[] args) {
        // 创建 JdbcRealm
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(druidDataSource);
        // TODO 在使用 jdbcRealm时要设置权限开关
        realm.setPermissionsLookupEnabled(true);
        // TODO 设置自定义查询语句
        realm.setAuthenticationQuery(QUERY_PASSWORD_SQL);
//        realm.setPermissionsQuery(QUERY_ROLE_SQL);
        realm.setUserRolesQuery(QUERY_ROLE_SQL);
        // 构建 securityManager 环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 设置 realm
        securityManager.setRealm(realm);
        // 设置主体环境
        SecurityUtils.setSecurityManager(securityManager);
        // 主体提交认证
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("ceshi","123");
        // 登录校验
        subject.login(token);
        // 认证校验
        subject.isAuthenticated();
        // 权限校验
        subject.checkRole("super");
//        subject.checkPermission("user:delete");
        System.out.println("认证结果 " + subject.isAuthenticated());

    }

}
