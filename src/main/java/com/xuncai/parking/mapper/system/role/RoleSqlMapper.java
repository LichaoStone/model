package com.xuncai.parking.mapper.system.role;

import com.xuncai.parking.bean.system.role.query.RoleQuery;
import com.xuncai.parking.bean.system.role.vo.RoleVO;

import java.util.List;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.mapper.system.role
 * @ClassName: RoleSqlMapper
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/23 4:55
 * @Version: 1.0
 */
public interface RoleSqlMapper {
    /**
     * 插入对象
     * @param bean
     * @return
     */
    public int insertRole(RoleQuery bean);
    /**
     * 编辑对象
     * @param bean
     * @return
     */
    public int updateRole(RoleQuery bean);

    /**
     * 编辑对象
     * @param bean
     * @return
     */
    public int updateRoleStatus(RoleQuery bean);

    /**
     * 删除对象
     * @param bean
     * @return
     */
    public int deleteRole(RoleQuery bean);
    /**
     * 只修改需要编辑的字段
     * @param bean
     * @return
     */
    public int updateRoleBySelective(RoleQuery bean);
    /**
     * 根据Id获得对象
     * @param key
     * @return
     */
    public RoleVO getRoleVoById(String key);

    /**
     * 获得对象总数
     * @param query
     * @return
     */

    public long getRoleCountByQuery(RoleQuery query);
    /**
     * 获得对象列表
     * @param query
     * @return
     */
    public List<RoleVO> getRoleListByQuery(RoleQuery query);
    /**
     * 检查角色名称是否存在
     * @param roleVo
     * @return
     */
    public List<RoleVO> checkRoleName(RoleQuery roleVo);
}
