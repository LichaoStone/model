package com.xuncai.parking.service.system.role.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuncai.parking.bean.system.permission.query.PermissionQuery;
import com.xuncai.parking.bean.system.permission.vo.PermissionVO;
import com.xuncai.parking.bean.system.role.query.RoleQuery;
import com.xuncai.parking.bean.system.role.vo.RolePermissionVO;
import com.xuncai.parking.bean.system.role.vo.RoleVO;
import com.xuncai.parking.bean.system.user.query.UserQuery;
import com.xuncai.parking.common.util.DateUtil;
import com.xuncai.parking.common.util.bean.Result;
import com.xuncai.parking.common.util.constant.Constants;
import com.xuncai.parking.common.util.constant.HttpConstant;
import com.xuncai.parking.mapper.system.permission.PermissionSqlMapper;
import com.xuncai.parking.mapper.system.role.RolePermissionSqlMapper;
import com.xuncai.parking.mapper.system.role.RoleSqlMapper;
import com.xuncai.parking.mapper.system.user.UserSqlMapper;
import com.xuncai.parking.service.system.role.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.service.system.role.impl
 * @ClassName: RoleServiceImpl
 * @Author: zangt
 * @Description:
 * @Date: 2020/6/22 22:03
 * @Version: 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleSqlMapper roleMapper ;
    @Resource
    private UserSqlMapper userSqlMapper;
    @Resource
    private PermissionSqlMapper permissionSqlMapper;
    @Resource
    private RolePermissionSqlMapper rolePermissionSqlMapper;

    @Override
    public Result getRolePageByQuery(RoleQuery query) {
        PageHelper.startPage(query.getOffset(), query.getPageSize());
        List<RoleVO> list = roleMapper.getRoleListByQuery(query);
        PageInfo<RoleVO> pageInfo = new PageInfo<>(list);
        Result result = new Result(pageInfo,list) ;
        return result;
    }

    @Override
    public List<RoleVO> getRoleListByQuery(RoleQuery query) {
        List<RoleVO> list = roleMapper.getRoleListByQuery(query);
        if (list == null) {
            list = new ArrayList<RoleVO>();
        }
        return list;
    }

    @Override
    @Transactional
    public Result saveRole(RoleQuery roleVo) {
        Result result = new Result();
        List<RoleVO> list = roleMapper.checkRoleName(roleVo);
        if (list != null && list.size() > 0) {
            result.setRet(HttpConstant.ROLE_RET.ADD_DUPLICATE_ROLE_NAME);
            result.setMsg(HttpConstant.ROLE_MESSAGES.ADD_DUPLICATE_ROLE_NAME_INFO);
            return result ;
        }
        int count = 0 ;
        // 新增或者修改标志  flag:true 新增 flag:flase 修改
//		boolean flag=false;
        if (roleVo.getRoleKey() == null) {
//			flag =true;
            roleVo.setStatus(Constants.ROLE.ROLE_ENABLE);
            count = roleMapper.insertRole(roleVo);
            result.setMsg(HttpConstant.ROLE_MESSAGES.ADD_ROLE_SUCCESS_INFO);
            return  result;
        }else {
            count = roleMapper.updateRoleBySelective(roleVo);
            result.setMsg(HttpConstant.ROLE_MESSAGES.MODIFY_ROLE_SUCCESS_INFO);
            return  result;
        }
    }

    @Override
    public Result checkRoleUser(RoleQuery query) {
        Result result = new Result();
        UserQuery userVoQuery = new UserQuery();
        userVoQuery.setRoleKey(query.getRoleKey());
        long count = userSqlMapper.getUserCountByQuery(userVoQuery);
        if (count != 0) {
            result.setRet(HttpConstant.ROLE_RET.USER_ROLE_EXITS);
            result.setMsg(HttpConstant.ROLE_MESSAGES.USER_ROLE_EXITS_INFO);
        }
        return result;
    }

    @Override
    @Transactional
    public Result deleteRoleById(String  roleKey) {
        Result result = new Result();
        RoleQuery role = new RoleQuery();
        role.setRoleKey(roleKey);
        rolePermissionSqlMapper.deletePermissionByRoleId(roleKey);
        int count = roleMapper.deleteRole(role);
        if (count > 0) {
            result.setMsg(HttpConstant.ROLE_MESSAGES.DELETE_ROLE_SUCCESS_INFO);
            return result ;
        }
        result.setRet(HttpConstant.ROLE_RET.DELETE_ROLE_ERROR);
        result.setMsg(HttpConstant.ROLE_MESSAGES.DELETE_ROLE_ERROR_INFO);
        return result;
    }

    @Override
    public Result updateRoleStatus(RoleQuery vo) {
        int cnt = roleMapper.updateRoleStatus(vo);
        Result result = new Result() ;
        // 用户禁用成功
        if(Constants.ROLE.ROLE_FORBIDDEN == vo.getStatus()){
            result.setMsg(HttpConstant.ROLE_MESSAGES.USER_STATUS_UNENABLE_SUCCESS_INFO);
        }else if(Constants.USER.STATUS_ENABLE == vo.getStatus()){
            // 用户启用成功
            result.setMsg(HttpConstant.ROLE_MESSAGES.USER_STATUS_ENABLE_SUCCESS_INFO);
        }
        return result;
    }

    @Override
    public Result getRoleByKey(RoleQuery vo) {
        RoleVO role = roleMapper.getRoleVoById(vo.getRoleKey());
        Result result = new Result() ;
        result.setData(role);
        return result;
    }

    @Override
    @Transactional
    public Result saveRolePermission(RoleQuery roleVo) {
        Result result = new Result();
        String [] keys = roleVo.getPermissions();
        RolePermissionVO vo = null ;
        List<RolePermissionVO> list = new ArrayList<RolePermissionVO>();
        if (keys.length > 0) {
            for (String key : keys) {
                if (StringUtils.isNotBlank(key)) {
                    vo = new RolePermissionVO();
                    vo.setCreateTime(DateUtil.getTimeToSec());
                    //TODO  获取用户 根据token
                    //vo.setCreator(userName);
                    vo.setPermissionKey(key);
                    vo.setRoleKey(roleVo.getRoleKey());
                    list.add(vo);
                }
            }
        }
        rolePermissionSqlMapper.deleteRelationshipByRoleKey(roleVo.getRoleKey());
        int count  = rolePermissionSqlMapper.batchInsert(list);
        result.setMsg(HttpConstant.ROLE_MESSAGES.ADD_ROLE_PERMISSIONS_SUCCESS_INFO);
        return result;
    }

    @Override
    public List<PermissionVO> getPermissionBeanVosByRoleKey(String roleKey) {
        PermissionQuery query = new PermissionQuery();
        query.setRoleKey(roleKey);
        List<PermissionVO> permissionlist = permissionSqlMapper.getListByQuery(query);
        if (permissionlist == null) {
            permissionlist = new ArrayList<PermissionVO>(0);
        }
        return permissionlist;
    }

    @Override
    public List<PermissionVO> getPermissions() {
        PermissionQuery query = new PermissionQuery();
        List<PermissionVO> permissionlist = permissionSqlMapper.getListByQuery(query);
        if (permissionlist == null) {
            permissionlist = new ArrayList<PermissionVO>(0);
        }
        return permissionlist;
    }

    @Override
    public List<String> getPermissionsIdByRoldId(String roleKey) {
        PermissionQuery query = new PermissionQuery();
        query.setRoleKey(roleKey);
        List<String> permissionlist = permissionSqlMapper.getRolePerssionIdListByRoleId(query);
        if (permissionlist == null) {
            permissionlist = new ArrayList<String>(0);
        }
        return permissionlist;
    }
}
