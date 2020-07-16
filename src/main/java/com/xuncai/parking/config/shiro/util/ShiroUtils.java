package com.xuncai.parking.config.shiro.util;

import com.xuncai.parking.bean.system.user.vo.UserVO;
import com.xuncai.parking.common.util.BeanUtils;
import com.xuncai.parking.common.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;


/**
 * shiro 工具类
 *	
 * @author fuce
 */
public class ShiroUtils {

    private ShiroUtils(){}
    
    /**
     * 获取shiro subject
     * @return
     * @author fuce
     * @Date 2019年11月21日 上午10:00:55
     */
    public static Subject getSubjct()
    {
        return SecurityUtils.getSubject();
    }
    
    /**
     * 获取登录session
     * @return
     * @author fuce
     * @Date 2019年11月21日 上午10:00:41
     */
    public static Session getSession()
    {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 退出登录
     * @author fuce
     * @Date 2019年11月21日 上午10:00:24
     */
    public static void logout()
    {
        getSubjct().logout();
    }

    /**
     * 获取登录用户model
     * @return
     * @author fuce
     * @Date 2019年11月21日 上午10:00:10
     */
    public static UserVO getUser()
    {
    	UserVO user = null;
        Object obj = getSubjct().getPrincipal();
        if (!StringUtils.isEmpty(obj)){
            user = new UserVO();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    /**
     * set用户
     * @param user
     * @author fuce
     * @Date 2019年11月21日 上午9:59:52
     */
    public static void setUser(UserVO user)
    {
        Subject subject = getSubjct();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    /**
     * 获取登录用户id
     * @return
     * @author fuce
     * @Date 2019年11月21日 上午9:58:55
     */
    public static String getUserId()
    {
        UserVO tsysUser = getUser();
        if (tsysUser == null || tsysUser.getUserKey() == null){
            throw new RuntimeException("用户不存在！");
        }
        return tsysUser.getUserKey().trim();
    }

    /**
     * 获取登录用户name
     * @return
     * @author fuce
     * @Date 2019年11月21日 上午9:58:48
     */
    public static String getLoginName()
    {
        UserVO tsysUser = getUser();
        if (tsysUser == null){
            throw new RuntimeException("用户不存在！");
        }
        return tsysUser.getLoginName();
    }

    /**
     * 获取登录用户ip
     * @return
     * @author fuce
     * @Date 2019年11月21日 上午9:58:26
     */
    public static String getIp()
    {
        return getSubjct().getSession().getHost();
    }
    
    /**
     * 获取登录用户sessionid
     * @return
     * @author fuce
     * @Date 2019年11月21日 上午9:58:37
     */
    public static String getSessionId()
    {
        return String.valueOf(getSubjct().getSession().getId());
    }
}
