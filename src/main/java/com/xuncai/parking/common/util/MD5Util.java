package com.xuncai.parking.common.util;


import com.xuncai.parking.common.util.constant.Constants;

import java.security.MessageDigest;
import java.util.Calendar;

public class MD5Util {
    public final static String MD5(String res) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = res.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String dd = new String(str);
            return dd;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, 2018);

    	//System.out.println(cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        System.out.println(MD5Util.MD5("123456"+Constants.USER.PASSWORD_ADD_WORD));
	}
}

