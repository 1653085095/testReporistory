package com.common.annotation;


import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.common.service.LoggerService;
import com.common.service.RedisService;
import com.tools.json.JsonTool;
import com.tools.spring.SpelParser;
/**
 * description 组件，切面类
 */
@Component
@Aspect
public class AopAspect {
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private LoggerService loggerService;
	
	/**
	 * description 环绕通知，指定哪个注解生效, 优雅点的方式
	 * @param pjp 切入点
	 * @return
	 */
	@Around("@annotation(redisCacheHmset)")
	public Object doArount(ProceedingJoinPoint pjp, RedisCacheHmset redisCacheHmset) throws Throwable {
		String id = getId(redisCacheHmset.id(),pjp);
		String key = redisCacheHmset.key();
		boolean isLog = redisCacheHmset.isLog();
		String classAndMethodName = new StringBuilder().append(pjp.getSourceLocation().getWithinType().getName()).append('.').append(pjp.getSignature().getName()).toString();
		Map<String, Object> hmget = redisService.hmget(key+id);
		if(!hmget.isEmpty()) {
			if(isLog)loggerService.aopUseInfo(classAndMethodName, "从redis加载数据 data:{}", hmget);
			return JsonTool.toEntity(hmget, ((MethodSignature)pjp.getSignature()).getMethod().getReturnType());
		}
		Object mr = null;
		try {
			mr = pjp.proceed();//执行切入点方法
		} catch (Exception e) {
			if(isLog)loggerService.aopUseInfo(classAndMethodName, "从数据库加载数据发生异常:{}", e);
		}
		if(isLog)loggerService.aopUseInfo(classAndMethodName, "从数据库加载数据 data:{}", mr.toString());
		if(mr != null) {
			boolean hmsetSuccess = redisService.hmset(key+id, mr);
			if(isLog)loggerService.aopUseInfo(classAndMethodName, "status:{} 从数据库加载数据存入redis:{}", hmsetSuccess , mr.toString());
			
		}
		return mr;
	}
	/*
	@Around("@annotation(com.annotation.RedisCache)")
	public Object doArount(ProceedingJoinPoint pjp, RedisCache RedisCache) throws Throwable {
		//通过方法的数字签名拿到方法
		Method method = ((MethodSignature)pjp.getSignature()).getMethod();
		//拿到注解的实例
		RedisCache redisCache = method.getAnnotation(RedisCache.class);
		logger.info("=======1========"+redisCache.key());
		Object proceed = pjp.proceed();
		logger.info("=======2========");
		pjp.getSourceLocation().getWithinType().getName();
		pjp.getSignature().getName();
		return proceed;
	}
	*/
	/**
	 * descripton spring el 解析
	 */
	private String getId(String id, ProceedingJoinPoint pjp) {
		//通过连接点得到形参名字
		Method method = ((MethodSignature)pjp.getSignature()).getMethod();
		String[] paramNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
		return SpelParser.getId(id, paramNames, pjp.getArgs());
	}
	
}
