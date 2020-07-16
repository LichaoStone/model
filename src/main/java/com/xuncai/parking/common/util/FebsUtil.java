package com.xuncai.parking.common.util;

import com.xuncai.parking.common.util.constant.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

/**
 * FEBS工具类
 */
@Slf4j
public class FebsUtil {


//    /**
//     * 获取当前操作用户
//     *
//     * @return 用户信息
//     */
//    public static User getCurrentUser() {
//        String token = (String) SecurityUtils.getSubject().getPrincipal();
//        String username = JWTUtil.getUsername(token);
//        UserService userService = SpringContextUtil.getBean(UserService.class);
//        CacheService cacheService = SpringContextUtil.getBean(CacheService.class);
//
//        return selectCacheByTemplate(() -> cacheService.getUser(username), () -> userService.findByName(username));
//    }

    /**
     * token 加密
     *
     * @param token token
     * @return 加密后的 token
     */
    public static String encryptToken(String token) {
        try {
            EncryptUtil encryptUtil = new EncryptUtil(RedisKeyConstants.TOKEN_CACHE_PREFIX);
            return encryptUtil.encrypt(token);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * token 解密
     *
     * @param encryptToken 加密后的 token
     * @return 解密后的 token
     */
    public static String decryptToken(String encryptToken) {
        try {
            EncryptUtil encryptUtil = new EncryptUtil(RedisKeyConstants.TOKEN_CACHE_PREFIX);
            return encryptUtil.decrypt(encryptToken);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value))
            return value;
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0)
            return value;
        StringBuilder result = new StringBuilder();
        IntStream.range(0, arr.length).forEach(i -> {
            if (i != arr.length - 1)
                result.append(arr[i]).append("_");
            else
                result.append(arr[i]);
        });
        return StringUtils.lowerCase(result.toString());
    }

}
