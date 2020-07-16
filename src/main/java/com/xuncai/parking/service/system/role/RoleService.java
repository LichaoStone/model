package com.xuncai.parking.service.system.role;

import com.xuncai.parking.bean.system.permission.vo.PermissionVO;
import com.xuncai.parking.bean.system.role.query.RoleQuery;
import com.xuncai.parking.bean.system.role.vo.RoleVO;
import com.xuncai.parking.common.util.bean.Result;

import java.util.List;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.service.system.role
 * @ClassName: RoleService
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/22 22:03
 * @Version: 1.0
 */
public interface RoleService {
    /**
     * 分页获得角色列表
     * @param query
     * @return
     */
    Result getRolePageByQuery(RoleQuery query);
    /**
     * 获得角色列表
     * @param query
     * @return
     */
    List<RoleVO> getRoleListByQuery(RoleQuery query);
    /**
     * 保存用户角色
     * @param roleVo
     * @return
     */
    Result saveRole(RoleQuery roleVo);
    /**
     * 删除用户角色
     * @param roleId
     * @return
     */
    Result deleteRoleById(String roleId);

    /**
     * 保存角色权限
     * @param roleVo
     * @return
     */
    Result saveRolePermission(RoleQuery roleVo);


    /**
     * 根据用户逐渐获得用户权限
     * @param roleKey
     * @return
     */
    List<PermissionVO> getPermissionBeanVosByRoleKey(String roleKey);

    /**
     * 获取所有权限
     * @return
     */
    List<PermissionVO> getPermissions();

    /**
     * 获取角色对应的权限列表IDs
     * @return
     */
    List<String> getPermissionsIdByRoldId(String roleKey);

    /**
     * 判定拥有该角色的用户数
     * @param query
     * @return
     */
    Result checkRoleUser(RoleQuery query);

    /**
     * 更新角色状态
     * @param roleQuery
     * @return
     */
    Result updateRoleStatus(RoleQuery roleQuery);

    /**
     * 更新角色状态
     * @param roleQuery
     * @return
     */
    Result getRoleByKey(RoleQuery roleQuery);
}
