package com.xuncai.parking.mapper.system.user;


import com.xuncai.parking.bean.system.user.query.UserQuery;
import com.xuncai.parking.bean.system.user.vo.UserVO;

import java.util.List;
import java.util.Set;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.mapper.system.user
 * @ClassName: UserSqlMapper
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/22 20:36
 * @Version: 1.0
 */
public interface UserSqlMapper {
    /**
     * 获取用户列表数据
     * @param userQuery
     * @return
     */
    List<UserVO> getUserList(UserQuery userQuery);
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
     * 插入对象
     * @return
     */
    public int insertUser(UserQuery bean);
    /**
     * 编辑对象
     * @param bean
     * @return
     */
    public int updateUser(UserQuery bean);
    /**
     * 删除对象
     * @param bean
     * @return
     */
    public int deleteUser(UserQuery bean);
    /**
     * 只修改需要编辑的字段
     * @param bean
     * @return
     */
    public int updateUserBySelective(UserQuery bean);
    /**
     * 根据Id获得对象
     * @param key
     * @return
     */
    public UserVO getUserVoById(String key);

    /**
     * 根据Id获得对象
     * @param userKey
     * @return
     */
    public UserVO getUserVoByKey(String userKey);

    /**
     * 获得对象总数
     * @param query
     * @return
     */

    public long getUserCountByQuery(UserQuery query);
    /**
     * 获得对象列表
     * @param query
     * @return
     */
    public List<UserVO> getUserListByQuery(UserQuery query);

    /**
     * 检查用户登录名
     * @param user
     * @return
     */
    public List<UserVO> checkUserLoginName(UserQuery user);
    /**
     * 更新用户状态
     * @param bean
     * @return
     */
    public int updateUserStatus(UserQuery bean);
}
