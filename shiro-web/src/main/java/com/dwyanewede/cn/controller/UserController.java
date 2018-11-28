package com.dwyanewede.cn.controller;

import com.dwyanewede.cn.pojo.User;
import com.dwyanewede.cn.shiro.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: UserController
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 14:56
 * @Version: 1.0
 */
@Controller
@ResponseBody
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(MyRealm.class);


    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            usernamePasswordToken.setRememberMe(user.isRememberMe());
            subject.login(usernamePasswordToken);
            // 校验角色，shiro 每次权限校验都会执行自定义 Realm 中的 doGet方法
            subject.checkRole("super");
            subject.checkPermission("user:delete");
            logger.debug("username [{}], 权限校验完成，允许登录",new Object[]{user.getUsername()} );
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
        return "login succefful";
    }
    @RequiresRoles("admin")
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return "testRole success";
    }

    @RequiresRoles("super")
    @RequestMapping(value = "/testSuper",method = RequestMethod.GET)
    @ResponseBody
    public String testSuper(){
        return "testRole1 success";
    }

    @RequestMapping(value = "/testPerms",method = RequestMethod.GET)
    @ResponseBody
    public String testPerms(){
        return "testPerms success";
    }
}
