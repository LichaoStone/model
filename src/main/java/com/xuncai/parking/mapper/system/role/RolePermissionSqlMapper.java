package com.xuncai.parking.mapper.system.role;

import com.xuncai.parking.bean.system.role.query.RolePermissionQuery;
import com.xuncai.parking.bean.system.role.vo.RolePermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.mapper.system.role
 * @ClassName: RolePermissionSqlMapper
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/23 5:00
 * @Version: 1.0
 */
public interface RolePermissionSqlMapper {
    /**
     * 插入对象
     * @param bean
     * @return
     */
    public int insertRolePermissionRelationship(RolePermissionQuery bean);
    /**
     * 编辑对象
     * @param bean
     * @return
     */
    public int updateRolePermissionRelationship(RolePermissionQuery bean);
    /**
     * 删除对象
     * @param bean
     * @return
     */
    public int deleteRolePermissionRelationship(RolePermissionQuery bean);

    /**
     *
     * @param roleKey
     * @return
     */
    public int deletePermissionByRoleId(String roleKey);
    /**
     * 只修改需要编辑的字段
     * @param bean
     * @return
     */
    public int updateRolePermissionRelationshipBySelective(RolePermissionQuery bean);
    /**
     * 根据Id获得对象
     * @param key
     * @return
     */
    public RolePermissionVO getRolePermissionRelationshipVoById(String key);

    /**
     * 获得对象总数
     * @param query
     * @return
     */

    public long getRolePermissionRelationshipCountByQuery(RolePermissionQuery query);
    /**
     * 获得对象列表
     * @param query
     * @return
     */
    public List<RolePermissionVO> getRolePermissionRelationshipListByQuery(RolePermissionQuery query);
    /**
     *
     * @Title: deleteRelationshipByRoleKey
     * @Description: 根据角色Key删除角色权限
     * @param: @param roleKey
     * @param: @return
     * @return: int
     * @throws
     * @author: banbu
     */
    public int deleteRelationshipByRoleKey(String roleKey);
    /**
     *
     * @Title: batchInsert
     * @Description: 批量插入权限
     * @param: @param relationshipVos
     * @param: @return
     * @return: int
     * @throws
     * @author: banbu
     */
    public int batchInsert(@Param("relationshipList") List<RolePermissionVO> relationshipList);
}
