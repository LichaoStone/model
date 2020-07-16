package com.xuncai.parking;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SuppressWarnings("ALL")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication(exclude= {DruidDataSourceAutoConfigure.class, ElasticsearchDataAutoConfiguration.class})
//@EnableElasticsearchRepositories(basePackages = "com.haikan.iptv.elasticsearch.repository")
//@EnableCustomElasticsearchRepositories(basePackages = "com.haikan.iptv.elasticsearch.repository")
@EnableAsync
public class ParkingApplication extends SpringBootServletInitializer {
    @Override
    //修改启动类，继承 SpringBootServletInitializer并重写 configure方法
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        //注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(ParkingApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ParkingApplication.class, args);
    }
}
