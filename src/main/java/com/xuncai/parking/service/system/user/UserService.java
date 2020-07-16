package com.xuncai.parking.service.system.user;

import com.xuncai.parking.bean.system.user.query.UserQuery;
import com.xuncai.parking.bean.system.user.vo.UserVO;
import com.xuncai.parking.common.util.bean.Result;
import com.xuncai.parking.common.util.bean.ServiceResult;

import java.util.Set;


/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.service.system.user
 * @ClassName: UserService
 * @Author: zangt
 * @Description: 用户管理Service层
 * @Date: 2020/6/22 20:29
 * @Version: 1.0
 */
public interface UserService {
    /**
     * 获取用户所有角色
     * @param userQuery
     * @return
     */
    Set<String> getUserRoles(UserQuery userQuery);

    /**
     * 获取用户所有权限
     * @param userQuery
     * @return
     */
    Set<String> getUserPermission(UserQuery userQuery);


    /**
     * 获取用户列表页数据
     * @param userQuery
     * @return
     */
    Result getUserList(UserQuery userQuery);


    /**
     *
     * @Title: getUserByLoginName
     * @Description: 根据登录名获得用户信息
     * @param: @param loginName
     * @param: @return
     * @return: ServiceResult<UserVo>
     * @throws
     * @author: banbu
     */
    ServiceResult<UserVO> getUserByLoginName(String loginName);

    /**
     * 查询用户
     * @param query
     * @return
     */
    Result getUserPageResult(UserQuery query);

    /**
     * 查询用户详情
     * @param query
     * @return
     */
    Result getUserInfoResult(UserQuery query);

    /**
     * 保存用户
     * @return
     */
    Result saveUserVo(UserQuery user);

    /**
     * 检查登录用户
     * @param userQuery
     * @return
     */
    ServiceResult<UserVO> checkLoginUser(UserQuery userQuery);

    /**
     * 查询总的账号数
     * @return
     */
    Result  getUserCountByQuery(UserQuery query);
    /**
     * 更新用户密码
     * @param userKey
     * @return
     */
    Result updateUserPwd(String userKey, String loginName);
    /**
     * 更新用户状态
     * @param userQuery
     * @return
     */
    Result updateUserStatus(UserQuery userQuery);

}
