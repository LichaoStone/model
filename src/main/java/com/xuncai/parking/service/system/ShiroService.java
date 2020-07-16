package com.xuncai.parking.service.system;

import com.xuncai.parking.bean.system.user.vo.UserVO;

import java.util.Set;

/**
 * ShiroService
 *
 * @author
 * @date 2018/10/08 19:58
 * @email 571002217@qq.com
 * @description service接口类
 */
public interface ShiroService {

    /**
     * 获取用户的所有权限
     * @param userId
     * @return
     */
    Set<String> getUserPermissions(Integer userId);

    /**
     * 查询token
     * @param token
     * @return
     */
    UserVO queryByToken(String token);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    UserVO queryUser(Integer userId);

    /**
     * 续期
     * @param userId
     * @param accessToken
     */
    void refreshToken(Integer userId, String accessToken);
}
