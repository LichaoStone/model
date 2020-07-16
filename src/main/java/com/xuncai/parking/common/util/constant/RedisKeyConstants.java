package com.xuncai.parking.common.util.constant;

/**
 * RedisKeyConstants
 *
 * @author
 * @date 2018/10/20 13:44
 * @email 571002217@qq.com
 * @description redis baseKey管理常量
 */
public class RedisKeyConstants {
    /**
     * 后台管理验证码key
     */
    public final static String MANAGE_SYS_CAPTCHA = "MANAGE:SYS:CAPTCHA:";

    /**
     * 后台管理用户token key
     */
    public final static String MANAGE_SYS_USER_TOKEN = "MANAGE:SYS:USER:TOKEN:";


    // token缓存前缀
    public static final String TOKEN_CACHE_PREFIX = "febs.cache.token.";
    // 存储在线用户的 zset前缀
    public static final String ACTIVE_USERS_ZSET_PREFIX = "febs.user.active";

}
