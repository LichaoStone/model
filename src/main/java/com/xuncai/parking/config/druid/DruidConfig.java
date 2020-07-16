package com.xuncai.parking.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

	//private static String IP = "127.0.0.1" ;
	private static String LOGIN_USER_NAME = "admin" ;
	private static String LOGIN_PASSWORD = "admin" ;

    @SuppressWarnings("rawtypes")
	@Bean
    public ServletRegistrationBean druidServlet() {
        @SuppressWarnings("unchecked")

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        // IP白名单
        //servletRegistrationBean.addInitParameter("allow", IP);
        // IP黑名单(共同存在时，deny优先于allow)
        //servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", LOGIN_USER_NAME);
        servletRegistrationBean.addInitParameter("loginPassword", LOGIN_PASSWORD);
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @SuppressWarnings("rawtypes")
	@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        @SuppressWarnings("unchecked")
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.json,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
