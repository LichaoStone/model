package com.xuncai.parking.controller;

import com.xuncai.parking.common.util.bean.BaseResult;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {
    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public BaseResult handleShiroEx(ShiroException e) {
        logger.error("异常{}",e);
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(4001);
        return baseResult;
    }

    // 捕捉全局的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResult handleGlobalEx(Exception e) {
        logger.error("异常{}",e);
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(500);
        return baseResult;
    }

}
