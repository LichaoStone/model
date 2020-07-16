package com.xuncai.parking.common.util.constant;

import java.util.HashMap;
import java.util.Map;

public  class HttpConstant {

    public static final Map<Integer,Object> CODE_INFO = new HashMap<Integer,Object>();
    public static final Map<Integer,Object> RET_INFO = new HashMap<Integer,Object>();


    /**
    *@Description: 系统状态码
    *@Param:
    *@return:
    *@Author:
    *@date: 2020/2/18/018
    */
    public static  class CODE{
        /**成功**/
        public static final Integer  SUCCESS=200;
        /**失败**/
        public static final Integer  FAIL=500;
    }

    /**
     *@Description: 业务逻辑码
     *@Param:
     *@return:
     *@Author: liuzhaokun
     *@date: 2020/2/18/018
     */
    public static class RET{
        /**成功**/
        public static final Integer  SUCCESS=200;
        /**失败**/
        public static final Integer  FAIL=500;

        /**用户名不能为空**/
        public static final Integer  LOGIN_NAME_NULL=101;
        /**用户密码不能为空**/
        public static final Integer  LOGIN_PWD_NULL=102;
        /**账号被锁定**/
        public static final Integer  LOGIN_USER_LOCK=103;
        /**用户名和账号错误**/
        public static final Integer  LOGIN_INFO_ERROR=104;
        /** 添加用户成功**/
        public static final Integer  ADD_USER_SUCCESS =200;
        /** 相同用户名**/
        public static final Integer  ADD_DUPLICATE_NAME =201;
        /** 添加用户失败**/
        public static final Integer  ADD_USER_ERROR =202;
        /** 最多添加100个账号**/
        public static final Integer  ADD_USER_TOTAL_ERROR =203;
        /** 系统不存在当前用户**/
        public static final Integer  USER_NOT_EXITS =204;
        /** 密码修改失败**/
        public static final Integer  USER_PWD_UPDATE_ERROR =205;
        /** 用户状态更新失败  **/
        public static final Integer  USER_STATUS_UPDATE_ERROR =206;
        /**未认证，请在前端系统进行认证**/
        public static final Integer  JWT_TOKEN_INVALID =207;
    }


    public static class MESSAGES {
        //用户名不能为空
        public static final String LOGIN_NAME_NULL_INFO="用户名不能为空";
        //密码不能为空
        public static final String LOGIN_PWD_NULL_INFO="密码不能为空";
        //账号被锁定
        public static final String LOGIN_USER_LOCK_INFO="密码不能为空";
        //用户名和账号错误
        public static final String LOGIN_INFO_ERROR_INFO="用户名和账号错误";
        //添加用户成功
        public static final String ADD_USER_SUCCESS_INFO="添加用户成功";
        //添加用户失败
        public static final String ADD_USER_ERROR_INFO="添加用户失败";
        //相同用户名
        public static final String ADD_DUPLICATE_NAME_INFO="用户名或手机号码重复";
        //最多添加100个账号
        public static final String ADD_USER_TOTAL_ERROR_INFO="最多添加100个账号";
        //系统不存在当前用户
        public static final String USER_NOT_EXITS_INFO="系统不存在当前用户";
        //密码修改失败
        public static final String USER_PWD_UPDATE_ERROR_INFO="密码修改失败";
        //密码修改成功
        public static final String USER_PWD_UPDATE_SUCCESS_INFO="密码修改成功";
        //用户状态更新失败
        public static final String USER_STATUS_UPDATE_ERROR_INFO="用户状态更新失败";
        //启用账号成功
        public static final String USER_STATUS_ENABLE_SUCCESS_INFO="启用账号成功";
        //禁用账号成功
        public static final String USER_STATUS_UNENABLE_SUCCESS_INFO="禁用账号成功";
    }
    
    /**系统信息设置**/
    public static class SETTING_RET{
    	//名称为空
    	public static final Integer  SETTING_NAME_NULL=601;
    	//内容为空
    	public static final Integer  SETTING_CONTENT_NULL=602;
    }
    public static class SETTING_MESSAGES {
    	//设置信息名称为空
    	public static final String SETTING_NAME_NULL_INFO = "名称为空";
    	//设置信息内容为空
    	public static final String SETTING_CONTENT_NULL_INFO = "名称为空";
    }

    /**系统信息设置**/
    public static class ROLE_RET{
        //名称为空
        public static final Integer  ADD_DUPLICATE_ROLE_NAME = 301;
        //添加角色失败
        public static final Integer  ADD_ROLE_ERROR = 302;
        //修改角色失败
        public static final Integer  MODIFY_ROLE_ERROR = 303;
        //更新角色信息失败
        public static final Integer  DELETE_ROLE_ERROR = 304;
        //该角色下存下用户信息
        public static final Integer  USER_ROLE_EXITS =305 ;

    }


    public static class ROLE_MESSAGES {
        //设置信息名称为空
        public static final String ADD_DUPLICATE_ROLE_NAME_INFO = "角色名称已存在";
        //添加角色成功
        public static final String ADD_ROLE_SUCCESS_INFO = "添加角色成功";
        //添加角色失败
        public static final String ADD_ROLE_ERROR_INFO = "添加角色失败";
        //修改角色成功
        public static final String MODIFY_ROLE_SUCCESS_INFO = "修改角色成功";
        //修改角色失败
        public static final String MODIFY_ROLE_ERROR_INFO = "修改角色失败";
        //删除角色成功
        public static final String DELETE_ROLE_SUCCESS_INFO = "删除角色成功";
        //删除角色失败
        public static final String DELETE_ROLE_ERROR_INFO = "删除角色失败";
        //当前角色下存在后台用户，无法删除
        public static final String USER_ROLE_EXITS_INFO = "当前角色下存在后台用户，无法删除";
        //启用角色成功
        public static final String USER_STATUS_ENABLE_SUCCESS_INFO="启用角色成功";
        //禁用角色成功
        public static final String USER_STATUS_UNENABLE_SUCCESS_INFO="禁用角色成功";
        //设置角色权限信息成功
        public static final String ADD_ROLE_PERMISSIONS_SUCCESS_INFO="设置角色权限信息成功";
    }
    
}
