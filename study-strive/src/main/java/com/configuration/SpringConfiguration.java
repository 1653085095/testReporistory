package com.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@Configuration
@MapperScan("com.web.mapper")
@ComponentScan(basePackages= {"com.web.service","com.common.service"})
//@ImportResource("classpath:/spring/**/*.xml")
public class SpringConfiguration {
	
}
