package com.dwyanewede.cn.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName: Authorizer
 * @Description: Authenticator 授权 by SimpleAccountRealm
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 10:53
 * @Version: 1.0
 */
public class Authorizer {

    private static SimpleAccountRealm realm = new SimpleAccountRealm();
    static {
        realm.addAccount("sk","123","admin","super");
    }


    /**
     * 基于 SimpleAccountRealm 的认证处理
     * @param args
     */
    public static void main(String[] args) {
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
        // 授权校验
        subject.checkRole("admin");
        // checkRoles 校验时当前用户必须满足所有权限，才能通过
        subject.checkRoles("admin","super");
        System.out.println("授权校验成功");

    }
}
