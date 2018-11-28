package com.dwyanewede.cn.shiro;

import com.dwyanewede.cn.dao.UserDao;
import com.dwyanewede.cn.dao.impl.RedisSessionDao;
import com.dwyanewede.cn.dao.impl.UserDaoImpl;
import com.dwyanewede.cn.pojo.User;
import com.dwyanewede.cn.session.CustomSessionManager;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: MyRealm
 * @Description: java类作用描述
 * @Author: 尚先生
 * @CreateDate: 2018/11/27 12:27
 * @Version: 1.0
 */
public class MyRealm extends AuthorizingRealm {

    private final static Logger logger = LoggerFactory.getLogger(MyRealm.class);

    private UserDaoImpl userDao;

    private RedisSessionDao redisSessionDao;

    private CustomSessionManager sessionManager;

    public Set<String> permissions = new HashSet<>();

    {
        permissions.add("user:delete");
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        // 根据用户名获取角色，再根据角色获取对应的权限
        // 此处省略与数据库交互的代码，所有结果从 roles 和 permissions 中获取
        Set<String> roles = userDao.queryRolesByUserName(username);
        if (roles == null || roles.size() == 0) {
            return null;
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        // 查询用户对应权限，此处直接查本地缓存，可以后续扩展，参照上述查询用户角色的方式即可
        simpleAuthorizationInfo.setStringPermissions(permissions);
        logger.debug("返回授权信息");
        return simpleAuthorizationInfo;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获得用户名
        String username = (String) token.getPrincipal();
        // 校验用户信息
        User user = userDao.getUserByUserName(username);
        String password = user.getPassword();
        if (user == null || password == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, "realm");
        // md5加密后 更加安全考虑  加盐;源码校验实现见文件末尾注释方法
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("jiadeyan"));
        logger.debug("返回认证信息");
        return simpleAuthenticationInfo;
    }

  /*  public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123","jiadeyan");
        System.out.println(md5Hash.toString());
    }*/


    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public void setRedisSessionDao(RedisSessionDao redisSessionDao) {
        this.redisSessionDao = redisSessionDao;
    }

    public void setSessionManager(CustomSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}




  // 源码校验实现，可参考
  /*  @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object tokenHashedCredentials = hashProvidedCredentials(token, info);
        Object accountCredentials = getCredentials(info);
        return equals(tokenHashedCredentials, accountCredentials);
    }*/
