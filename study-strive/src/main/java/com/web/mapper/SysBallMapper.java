package com.web.mapper;

import com.web.entity.SysBall;
public interface SysBallMapper {
	public int insert(SysBall sysBall);
	public SysBall getById(String id);
}
