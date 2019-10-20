package com.common.service;

import java.io.InputStream;
import java.util.Map;

public interface RedisService {
	public boolean hmset(String key, Object obj) ;
	public boolean hmset(String key, Map<String, Object> map);
	
	public Map<String, Object> hmget(String key);
	public <T> T hmget(String key, Class<T> clazz);
	
	public boolean set(String key, Object value);
	public boolean set(String key, Object value, int time);
	
	public Map<String,Object> get(String key);
	public <T> T get(String key, Class<T> clazz);
	
	public byte[] serializeObj(Object object);
	public Object deserializeObj(byte[] bytes);
	public byte[] inputStreamToByte(InputStream inputStream);
}
