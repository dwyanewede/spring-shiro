package com.dwyanewede.cn.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MyRealm
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 12:27
 * @Version: 1.0
 */
public class MyRealmCache extends AuthorizingRealm {

    public ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();
    public Set<String> roles = new HashSet<>();
    public Set<String> permissions = new HashSet<>();

    public MyRealmCache() {
        super.setName("myRealm");
//        dataMap.put("sk", "202cb962ac59075b964b07152d234b70"); // 加盐前
        dataMap.put("sk", "94017719e170ad371e68f05bb66ed84a");// 加盐后
        roles.add("super");
        roles.add("admin");
        permissions.add("user:delete");
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        // 根据用户名获取角色，再根据角色获取对应的权限
        // 此处省略与数据库交互的代码，所有结果从 roles 和 permissions 中获取
        if (roles == null || permissions == null) {
            return null;
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获得用户名
        String username = (String) token.getPrincipal();
        // 校验用户信息
        String password = (String) dataMap.get(username);
        if (password == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, "myRealm");
        // md5加密后 更加安全考虑  加盐;源码校验实现见文件末尾注释方法
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("jiadeyan"));
        return simpleAuthenticationInfo;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123","jiadeyan");
        System.out.println(md5Hash.toString());
    }
}




  // 源码校验实现，可参考
  /*  @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object tokenHashedCredentials = hashProvidedCredentials(token, info);
        Object accountCredentials = getCredentials(info);
        return equals(tokenHashedCredentials, accountCredentials);
    }*/
