package com.xuncai.parking.config.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
*@Description: token
*@Param: 
*@return: 
*@Author:
*@date: 2020/2/21/021
*/
@Data
public class JWTToken implements AuthenticationToken {
    private static final long serialVersionUID = 7132041709465628099L;

    /**JWT token**/
    private String token;
    /**超时时长**/
    private String exipreAt;

    public JWTToken(String token) {
        this.token = token;
    }

    public JWTToken(String token,String exipreAt) {
        this.token = token;
        this.exipreAt = exipreAt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
