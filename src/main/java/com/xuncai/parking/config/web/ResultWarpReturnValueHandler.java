/**
* @Title: ResultWarpReturnValueHandler.java
* @Package com.haikan.iptv.config.web
* @Description: TODO
* @author mayi
* @date 2020年2月21日
* @version V1.0
*/
package com.xuncai.parking.config.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuncai.parking.common.util.bean.BaseResult;
import com.xuncai.parking.common.util.bean.Result;

/**
* @ClassName: ResultWarpReturnValueHandler
* @Description: 返回参数封装
* @author
* @date 2020年2月21日
*
*/
public class ResultWarpReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ResultWarpReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
    	BaseResult baseResult = new BaseResult() ;
    	baseResult.setCode(200);
    	ObjectMapper objectMapper = new ObjectMapper();
    	//过滤序列化异常
    	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	Result result = objectMapper.convertValue(returnValue, Result.class);
    	//执行异常
    	if (result.getCode().intValue() == 500) {
    		baseResult.setCode(500);
		}
    	baseResult.setResults(returnValue);
        delegate.handleReturnValue(baseResult, returnType, mavContainer, webRequest);
    }
}
