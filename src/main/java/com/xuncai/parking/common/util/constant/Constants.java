package com.xuncai.parking.common.util.constant;

import java.util.Arrays;
import java.util.List;

public class Constants {


    /**
     * 用户状态
     * @author mayi
     *
     */
    public static class USER {
        /**
         * 用户状态 禁用
         */
        public static final String STATUS_FORBIDDEN = "0" ;

        /**
         * 用户状态 启用
         */
        public static final String STATUS_ENABLE = "1" ;

        /**
         * 权限类型 菜单
         */
        public static final String PERMISSION_TYPE_MENU = "menu" ;
        /**
         * 权限类型 操作
         */
        public static final String PERMISSION_TYPE_OPERATION = "operation" ;
        /**
         * 推流权限标识
         */
        public static final String PERMISSION_KEY = "media:activity:push_url" ;
        /**
         * 用户密码添加字符
         */
        public static final String PASSWORD_ADD_WORD = "_xuncai" ;
        /**
         * 用户登陆密码错误
         */
        public static final String PASSWORD_ERROR_CODE = "4005" ;
        /**
         * redis存储key
         */
        public static final String REDIS_PASSWORD_ERROR_KEY = "integral:platform:user:" ;
        /**
         * 用户登陆次数
         */
        public static final int USER_LOGIN_TIMES = 5 ;

        /**用户名不能为空**/
        public static final String LOGIN_NAME_NULL="1001";
        /**用户密码不能为空**/
        public static final String LOGIN_PWD_NULL="1002";


    }

    /**
     *用户角色状态
     */
    public static  class ROLE{
        /**禁用**/
        public static final String ROLE_FORBIDDEN = "0";
        /**启用**/
        public static final String  ROLE_ENABLE = "1";
    }

    /*********************公共权限**********************/
    public final static List<String> PERMISSION_LIST = Arrays.asList(
            "/local_opinion/get_localOpinion_by_key",
            "/local_opinion/update_localOpinion"
    );

    public final static String UPLOAD_IMAGE_PATH = "images/agreement/editor/";
}
