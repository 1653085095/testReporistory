package com.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.common.service.LoggerService;
import com.web.entity.SysBall;
import com.web.service.SysBallService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopTest {
	
	@Autowired
	private SysBallService sysBallService;
	@Autowired
	private LoggerService loggerService;
	
	@Test
	public void redisCache() {
		SysBall byId = sysBallService.getById("1");
		loggerService.info(byId);
	}
	
}
