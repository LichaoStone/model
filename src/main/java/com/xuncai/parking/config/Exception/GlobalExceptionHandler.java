package com.xuncai.parking.config.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	 Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 @ExceptionHandler(Exception.class) // 所有的异常都是Exception子类
     public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) { // 出现异常之后会跳转到此方法
         ModelAndView mav = new ModelAndView("error"); // 设置跳转路径
         logger.error("异常：",e);
         mav.addObject("exception", e); // 将异常对象传递过
         mav.addObject("url", request.getRequestURL()); // 获得请求的路径
         mav.setViewName("error");
         return mav;
     }
}
