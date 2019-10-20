package com.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.common.service.LoggerService;
@Service
public class LoggerServiceImpl implements LoggerService {
	
	/**
	 * description 哪个类调用打印哪个类名字
	 */
	public void info(Object message) {
		getLogger((new Throwable()).getStackTrace()[1].getClassName()).info(message.toString());
	}
	
	/**
	 * description 哪个类调用打印哪个类名字
	 */
	public void info(String format, Object... arguments) {
		getLogger((new Throwable()).getStackTrace()[1].getClassName()).info(format, arguments);
	}
	
	/**
	 * @param calssName 类名称
	 * @param format 格式化日志
	 * @param arguments 参数
	 */
	public void aopUseInfo(String className, String format, Object... arguments) {
		getLogger(className).info(format.toString(), arguments);
	}
	
	public void error() {
		
	}
	
	/**
	 * description 获取日志
	 * @param name
	 * @return
	 */
	private Logger getLogger(String name) {
		return LoggerFactory.getLogger('('+name+')');
	}
	
}
