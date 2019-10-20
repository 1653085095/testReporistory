package com.common.service;


public interface LoggerService {
	public void info(Object message);
	public void info(String format, Object... arguments);
	public void aopUseInfo(String className, String format, Object... arguments);
}
