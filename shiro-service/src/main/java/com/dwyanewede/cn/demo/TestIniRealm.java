package com.dwyanewede.cn.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName: TestIniRealm
 * @Description: IniRealm 功能介绍
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 11:12
 * @Version: 1.0
 */
public class TestIniRealm {

    /**
     * 基于 IniRealm 的认证处理
     * @param args
     */
    public static void main(String[] args) {
        // 创建 IniRealm
        IniRealm realm = new IniRealm("classpath:test.ini");
        // 构建 securityManager 环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 设置 realm
        securityManager.setRealm(realm);
        // 设置主体环境
        SecurityUtils.setSecurityManager(securityManager);
        // 主体提交认证
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("sk","123");
        // 登录校验
        subject.login(token);
        // 认证校验
        subject.isAuthenticated();
        // 权限校验
        subject.checkRole("super");
        subject.checkPermission("user:delete");
        System.out.println("认证结果 " + subject.isAuthenticated());

    }

}
