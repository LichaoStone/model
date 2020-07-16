package com.xuncai.parking.common.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
* @author
* @version 创建时间：2017年10月15日 上午10:49:56
* 
*/
@Component

public class SpringContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext = null;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("-----------------------------------context:"+applicationContext);
		if(SpringContextUtil.applicationContext == null){
			SpringContextUtil.applicationContext = applicationContext;
		}
		
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	
	public static Object getBean(String name){
		System.out.println("name:"+name+";context:"+getApplicationContext());
		return getApplicationContext().getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz){
		return getApplicationContext().getBean(clazz);
	}
}
