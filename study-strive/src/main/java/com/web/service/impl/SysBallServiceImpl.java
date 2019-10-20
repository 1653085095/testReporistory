package com.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.annotation.RedisCacheHmset;
import com.common.key.RedisKey;
import com.web.entity.SysBall;
import com.web.mapper.SysBallMapper;
import com.web.service.SysBallService;
@Service
public class SysBallServiceImpl implements SysBallService{
	
	@Autowired
	private SysBallMapper sysBallMapper;
	
	@RedisCacheHmset(key=RedisKey.sys_ball, id="#id")
	public SysBall getById(String id) {
		return sysBallMapper.getById(id);
	}
}
