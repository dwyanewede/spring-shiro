package com.dwyanewede.cn.pojo;

import java.io.Serializable;

/**
 * @ClassName: User
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 14:58
 * @Version: 1.0
 */
public class User implements Serializable {

    private String username;
    private String password;
    private boolean rememberMe;

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
