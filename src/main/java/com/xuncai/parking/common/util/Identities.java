package com.xuncai.parking.common.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * UUID生成类
 *
 * @author
 * @create 2019-07-22 14:32
 **/

public final class Identities {

    private Identities() {
        throw new AssertionError();
    }

    private static SecureRandom random = new SecureRandom();

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuid2() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static long randomLong() {
        long randomVal = random.nextLong();
        return randomVal == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(randomVal);
    }

    public static Object uuidObj() {
        return UUID.randomUUID();
    }

    /**
     * 生成length位（0-9）的数字随机码
     *
     * @param length
     * @return
     */
    public static String genRandomDigitCode(int length) {
        StringBuilder buf = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int code = random.nextInt(10);
            buf.append(code);
        }
        return buf.toString();
    }

}
