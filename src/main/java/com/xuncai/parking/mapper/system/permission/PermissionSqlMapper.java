package com.xuncai.parking.mapper.system.permission;

import com.xuncai.parking.bean.system.permission.query.PermissionQuery;
import com.xuncai.parking.bean.system.permission.vo.PermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.mapper.system.permission
 * @ClassName: PermissionSqlMapper
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/23 4:58
 * @Version: 1.0
 */
public interface PermissionSqlMapper {
    /**
     * 插入对象
     * @param bean
     * @return
     */
    public int insertPermission(PermissionQuery bean);
    /**
     * 编辑对象
     * @param bean
     * @return
     */
    public int updatePermission(PermissionQuery bean);
    /**
     * 删除对象
     * @param bean
     * @return
     */
    public int deletePermission(PermissionQuery bean);
    /**
     * 只修改需要编辑的字段
     * @param bean
     * @return
     */
    public int updatePermissionBySelective(PermissionQuery bean);
    /**
     * 根据Id获得对象
     * @param key
     * @return
     */
    public PermissionVO getPermissionVoById(String key);

    /**
     * 获得对象总数
     * @param query
     * @return
     */

    public long getPermissionCountByQuery(PermissionQuery query);
    /**
     * 获得对象列表
     * @param query
     * @return
     */
    public List<PermissionVO> getPermissionListByQuery(PermissionQuery query);
    /**
     * 根据角色Id删除角色权限
     * @param roleId
     * @return
     */
    public int deletePermissionByRoleId(@Param("roleId") Integer roleId);
    /**
     * 批量插入角色权限
     * @param list
     * @return
     */
    public int batchInsertPermission(@Param("list") List<PermissionQuery> list);

    /**
     * 根据查询条件获得权限
     * @param query
     * @return
     */
    public List<PermissionVO> getListByQuery(PermissionQuery query);

    /**
     * 根据查询条件获得角色所拥有的权限
     * @param query
     * @return
     */
    public List<String> getRolePerssionIdListByRoleId(PermissionQuery query);
}
