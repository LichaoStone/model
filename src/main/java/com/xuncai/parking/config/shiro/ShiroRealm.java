package com.xuncai.parking.config.shiro;

import com.xuncai.parking.bean.system.permission.vo.PermissionVO;
import com.xuncai.parking.bean.system.user.vo.UserVO;
import com.xuncai.parking.common.util.FebsUtil;
import com.xuncai.parking.common.util.HttpContextUtil;
import com.xuncai.parking.common.util.IPUtil;
import com.xuncai.parking.common.util.bean.ServiceResult;
import com.xuncai.parking.common.util.constant.Constants;
import com.xuncai.parking.common.util.constant.RedisKeyConstants;
import com.xuncai.parking.config.redis.RedisService;
import com.xuncai.parking.config.shiro.util.JWTUtil;
import com.xuncai.parking.service.system.role.RoleService;
import com.xuncai.parking.service.system.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义实现 ShiroRealm，包含认证和授权两大模块
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private RedisService redisService;
    @Resource
    private UserService  userService;
    @Autowired
    private RoleService  roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     *  授权模块，获取用户角色和权限
     *
     * @param token token
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        // String username = JWTUtil.getUsername(token.toString());
        log.info("-----------------------------获取用户权限开始--------------------------------");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // add Permission Resourcese
        Subject subject = SecurityUtils.getSubject();
        String loginName = subject.getPrincipal().toString();

        ServiceResult<UserVO> result = userService.getUserByLoginName(loginName);
        String roleKey = result.getData().getRoleKey();
        List<PermissionVO> permissionList = roleService.getPermissionBeanVosByRoleKey(roleKey);

        List<String> urlList = new ArrayList<String>();
        urlList.addAll(Constants.PERMISSION_LIST);

        if (permissionList != null && permissionList.size() > 0) {
            /**
             * 获得用户权限URL
             */
            permissionList.stream()
                    .forEach(permission -> {
                        if (StringUtils.isNotBlank(permission.getUrl())) {
                            urlList.add(permission.getUrl());
                        }
                    });
        }
        urlList.addAll(Constants.PERMISSION_LIST);

        info.addStringPermissions(urlList);
        log.info("-----------------------------获取用户权限结束--------------------------------");

        return info;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("-----------------------------用户认证开始--------------------------------");

        // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
        String token = (String) authenticationToken.getCredentials();

        // 从 redis里获取这个 token
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddr(request);

        String encryptToken = FebsUtil.encryptToken(token);
        String encryptTokenInRedis = null;
        try {
            encryptTokenInRedis = redisService.get(RedisKeyConstants.TOKEN_CACHE_PREFIX + encryptToken + "." + ip);
        } catch (Exception ignore) {
            log.error("redis获取数据失败:",ignore);
        }

        // 如果找不到，说明已经失效
        //if (StringUtils.isBlank(encryptTokenInRedis))
        //    throw new AuthenticationException("token已经过期");

        String username = JWTUtil.getUsername(token);

        if (StringUtils.isBlank(username)){
            throw new AuthenticationException("无效token");
        }

        // 通过用户名查询用户信息
        UserVO user = (UserVO)userService.getUserByLoginName(username).getData();

        if (user == null){
            throw new AuthenticationException("用户名或密码错误");
        }

        if (!JWTUtil.verify(
                token,
                username,
                user.getPwd())){
            throw new AuthenticationException("token校验不通过");
        }

        log.info("-----------------------------用户认证结束--------------------------------");
        return new SimpleAuthenticationInfo(token,token, "febs_shiro_realm");
    }
}
