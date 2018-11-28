package com.dwyanewede.cn.my;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName: TestRealm
 * @Description: 自定义 MyRealm 测试类
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 12:35
 * @Version: 1.0
 */
public class TestRealm {

    /**
     * 基于 SimpleAccountRealm 的认证处理
     * @param args
     */
    public static void main(String[] args) {
        // 自定义 MyRealm
        MyRealm realm = new MyRealm();
        // 构建 securityManager 环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 创建 HashedCredentialsMatcher 实现加密
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1);
        // 设置加密
        realm.setCredentialsMatcher(credentialsMatcher);
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
        // 授权校验
        subject.checkRole("super");
        subject.checkPermission("user:delete");
        System.out.println("认证结果 " + subject.isAuthenticated());

    }
}
