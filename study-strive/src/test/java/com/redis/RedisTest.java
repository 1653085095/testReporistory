package com.redis;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.common.key.RedisKey;
import com.common.service.LoggerService;
import com.common.service.RedisService;
import com.web.entity.SysBall;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private LoggerService loggerService;
	@Test
	public void redisHmset() {
		SysBall sb = new SysBall();
		sb.setId(1);
		sb.setNum("123");
		boolean hmset = redisService.hmset(RedisKey.sys_ball+sb.getId(), sb);
		loggerService.info(hmset);
		SysBall hmget = redisService.hmget(RedisKey.sys_ball+sb.getId(),SysBall.class);
		loggerService.info(hmget);
	}
	
	@Test
	public void redisHmget() {
		Map<String, Object> hmget = redisService.hmget(RedisKey.sys_ball+1);
		System.out.println(hmget);
	}
	
	@Test
	public void redisSet() {
		SysBall sb = new SysBall();
		sb.setId(6);
		sb.setNum("666");
		boolean hmset = redisService.set(RedisKey.sys_ball+sb.getId(), sb);
		loggerService.info("status:{}", hmset);
		Map<String, Object> data = redisService.get(RedisKey.sys_ball+sb.getId());
		loggerService.info(data);
		SysBall sysBall = redisService.get(RedisKey.sys_ball+sb.getId(), SysBall.class);
		loggerService.info(sysBall);
	}
}
