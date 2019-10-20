package com.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.entity.SysBall;

@RestController
@RequestMapping("/redis")
public class SysBallController {

	@RequestMapping("/cache")
	public Object redisCache(HttpServletRequest request, SysBall sysBall) {
		
		return "";
	}
	
}
