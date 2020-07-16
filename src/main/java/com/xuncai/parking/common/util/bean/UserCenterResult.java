package com.xuncai.parking.common.util.bean;

import java.io.Serializable;

/**
 * @Auther:hicon
 * @Date: 2019/11/29/029
 * @Description: com.haikan.iptv.common.util.bean
 * @version: 1.0
 */
public class UserCenterResult implements Serializable {
    private static final long serialVersionUID = -4349001142601542462L;

    private boolean result;
    private String code;
    private String message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
