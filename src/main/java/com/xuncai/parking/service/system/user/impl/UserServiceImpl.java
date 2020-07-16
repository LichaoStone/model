package com.xuncai.parking.service.system.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuncai.parking.bean.system.user.query.UserQuery;
import com.xuncai.parking.bean.system.user.vo.UserVO;
import com.xuncai.parking.common.util.DateUtil;
import com.xuncai.parking.common.util.MD5Util;
import com.xuncai.parking.common.util.PkCreat;
import com.xuncai.parking.common.util.bean.Result;
import com.xuncai.parking.common.util.bean.ServiceResult;
import com.xuncai.parking.common.util.constant.Constants;
import com.xuncai.parking.common.util.constant.HttpConstant;
import com.xuncai.parking.mapper.system.user.UserSqlMapper;
import com.xuncai.parking.service.system.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @ProjectName: parking
 * @Package: com.xuncai.parking.service.system.user.impl
 * @ClassName: UserServiceImpl
 * @Author: zangt
 * @Description: 用户管理Service实现层
 * @Date: 2020/6/22 20:30
 * @Version: 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserSqlMapper userSqlMapper;

    @Override
    public Set<String> getUserRoles(UserQuery userQuery) {
        return userSqlMapper.getUserRoles(userQuery);
    }

    @Override
    public Set<String> getUserPermission(UserQuery userQuery) {
        return userSqlMapper.getUserPermission(userQuery);
    }

    @Override
    public Result getUserList(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getOffset(), userQuery.getPageSize());
        List<UserVO> list = userSqlMapper.getUserList(userQuery);
        PageInfo<UserVO> pageInfo = new PageInfo<>(list);
        Result result = new Result(pageInfo,list) ;
        return result;
    }

    @Override
    public Result getUserPageResult(UserQuery query) {
        PageHelper.startPage(query.getOffset(), query.getPageSize());
        List<UserVO> list = userSqlMapper.getUserListByQuery(query);
        PageInfo<UserVO> pageInfo = new PageInfo<>(list);
        Result result = new Result(pageInfo,list) ;
        return result;
    }

    @Override
    public Result getUserInfoResult(UserQuery query) {
        UserVO vo = userSqlMapper.getUserVoByKey(query.getUserKey());
        Result result = new Result(vo) ;
        return result;
    }

    @Override
    @Transactional
    public Result saveUserVo(UserQuery user) {

        List<UserVO> list = userSqlMapper.checkUserLoginName(user);
        if (list != null && list.size() >0) {
            Result result = new Result(
                    HttpConstant.CODE.SUCCESS,
                    HttpConstant.RET.ADD_DUPLICATE_NAME,
                    HttpConstant.MESSAGES.ADD_DUPLICATE_NAME_INFO,
                    user);
            return result;
        }
        int count = 0 ;
        String pw = PkCreat.createUserPass();
        String userKey = PkCreat.getTablePk();
        //新增用户
        if (user.getUserKey()==null || StringUtils.isEmpty(user.getUserKey())){
            user.setUserKey(userKey);
            //新建默认是启用状态
            user.setStatus(Constants.USER.STATUS_ENABLE);
            //创建时间
            user.setCreateTime(DateUtil.getTimeToSec());
            //密码
            user.setPwd(MD5Util.MD5(pw+Constants.USER.PASSWORD_ADD_WORD));
            count = userSqlMapper.insertUser(user);
        }else {
            count = userSqlMapper.updateUserBySelective(user);
        }
        if (count > 0) {
            Result result = new Result(
                    HttpConstant.CODE.SUCCESS,
                    HttpConstant.RET.ADD_USER_SUCCESS,
                    HttpConstant.MESSAGES.ADD_USER_SUCCESS_INFO,
                    user);
            return result;
        }
        //操作失败
        Result result = new Result(
                HttpConstant.CODE.SUCCESS,
                HttpConstant.RET.ADD_USER_ERROR,
                HttpConstant.MESSAGES.ADD_USER_ERROR_INFO,
                user);
        return result;
    }

    @Override
    public ServiceResult<UserVO> checkLoginUser(UserQuery userQuery) {
        ServiceResult<UserVO> serviceResult = new ServiceResult<UserVO>(false);
        UserQuery query = new UserQuery();
        query.setLoginName(userQuery.getLoginName());
        List<UserVO> list = userSqlMapper.getUserListByQuery(userQuery);
        if (list == null || list.isEmpty()) {
            serviceResult.setComment("系统不存在当前用户");
            serviceResult.setOk(false);
            serviceResult.setMsgCode("4003");
            return serviceResult ;
        }

        UserVO loginUser = list.get(0);
        if (loginUser.getStatus() == Constants.USER.STATUS_FORBIDDEN) {
            serviceResult.setComment("当前用户被禁用");
            serviceResult.setMsgCode("4004");
            serviceResult.setOk(false);
            return serviceResult ;
        }

        log.info(MD5Util.MD5(userQuery.getPwd()+Constants.USER.PASSWORD_ADD_WORD));
        log.info("user="+loginUser.getPwd());

        if (!MD5Util.MD5(userQuery.getPwd()+Constants.USER.PASSWORD_ADD_WORD).equals(loginUser.getPwd())) {
            serviceResult.setComment("用户密码不正确");
            serviceResult.setMsgCode("4005");
            serviceResult.setOk(false);
            return serviceResult ;
        }

        // RoleQuery roleQuery=new RoleQuery();
        // roleQuery.setRoleKey(loginUser.getRoleKey());


        serviceResult.setOk(true);
        serviceResult.setData(loginUser);
        return serviceResult;
    }

    @Override
    public Result getUserCountByQuery(UserQuery query) {
        query.setStatus(Constants.USER.STATUS_ENABLE);
        Result result = new Result();
        long cnt = userSqlMapper.getUserCountByQuery(query);
        // 最多添加100个有效账号
        if((int)cnt >=100){
            result.setRet(HttpConstant.RET.ADD_USER_TOTAL_ERROR);
            result.setMsg(HttpConstant.MESSAGES.ADD_USER_TOTAL_ERROR_INFO);
        }
        return result;
    }


    @Override
    public ServiceResult<UserVO> getUserByLoginName(String loginName) {
        ServiceResult<UserVO> serviceResult = new ServiceResult<UserVO>(false);
        UserQuery query = new UserQuery() ;
        query.setLoginName(loginName);
        List<UserVO> list = userSqlMapper.getUserListByQuery(query);

        if (list.size() > 0) {
            serviceResult.setData(list.get(0));
            serviceResult.setOk(true);
            return serviceResult  ;
        }
        serviceResult.setComment("没有获得用户登录信息");
        return serviceResult;
    }

    @Override
    public Result updateUserPwd(String userKey, String loginName) {
        Result result = new Result();
        UserQuery query = new UserQuery();
        query.setLoginName(loginName);
        List<UserVO> list = userSqlMapper.getUserListByQuery(query);
        if (list == null || list.isEmpty()) {
            result.setRet(HttpConstant.RET.USER_NOT_EXITS);
            result.setMsg(HttpConstant.MESSAGES.USER_NOT_EXITS_INFO);
            return result ;
        }
        String pw = PkCreat.createUserPass();
        query.setPwd(MD5Util.MD5(pw+Constants.USER.PASSWORD_ADD_WORD));
        query.setUserKey(userKey);
        int count = userSqlMapper.updateUserBySelective(query);
        if(count > 0) {
            result.setRet(HttpConstant.RET.SUCCESS);
            result.setMsg(HttpConstant.MESSAGES.USER_PWD_UPDATE_SUCCESS_INFO);
            result.setData(query);
            return result;
        }
        result.setRet(HttpConstant.RET.USER_PWD_UPDATE_ERROR);
        result.setMsg(HttpConstant.MESSAGES.USER_PWD_UPDATE_ERROR_INFO);
        return result;
    }

    @Override
    public Result updateUserStatus(UserQuery userVo) {
        int cnt = userSqlMapper.updateUserStatus(userVo);
        Result result = new Result() ;
        if(cnt > 0) {
            result.setRet(HttpConstant.RET.SUCCESS);
            // 用户禁用成功
            if(Constants.USER.STATUS_FORBIDDEN == userVo.getStatus()){
                result.setMsg(HttpConstant.MESSAGES.USER_STATUS_UNENABLE_SUCCESS_INFO);
            }else if(Constants.USER.STATUS_ENABLE == userVo.getStatus()){
                // 用户启用成功
                result.setMsg(HttpConstant.MESSAGES.USER_STATUS_ENABLE_SUCCESS_INFO);
            }
            return result;
        }
        result.setRet(HttpConstant.RET.USER_STATUS_UPDATE_ERROR);
        result.setMsg(HttpConstant.MESSAGES.USER_STATUS_UPDATE_ERROR_INFO);
        return result;
    }

}
