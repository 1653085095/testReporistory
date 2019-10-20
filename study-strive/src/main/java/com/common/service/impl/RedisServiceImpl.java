package com.common.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.common.service.RedisService;
import com.tools.json.JsonTool;
/**
 * description redis服务
 */
@Service
public class RedisServiceImpl implements RedisService{
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * @param <T>
	 * @param key
	 * @param t 实体类
	 * @return
	 */
	public boolean hmset(String key, Object obj) {
	    try {
	        redisTemplate.opsForHash().putAll(key, JsonTool.toMap(obj));
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * @param key
	 * @param map 
	 * @return
	 */
	public boolean hmset(String key, Map<String, Object> map) {
	    try {
	        redisTemplate.opsForHash().putAll(key, map);
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * @param key
	 * @return
	 */
	public Map<String, Object> hmget(String key) {
		return JsonTool.toMap(redisTemplate.opsForHash().entries(key));
	}
	
	/**
	 * @param key
	 * @param clazz 需要转成某个实体的class对象
	 * @return
	 */
	public <T> T hmget(String key, Class<T> clazz) {
		return JsonTool.toEntity(redisTemplate.opsForHash().entries(key), clazz);
	}
	
	/**
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean set(String key, Object value) {
	    try {
	    	redisTemplate.opsForValue().set(key, value);
	        return true;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * @param key
	 * @param value
	 * @param time 失效时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return
	 */
	public boolean set(String key, Object value, int time) {
	    try {
	    	redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
	        return true;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * @param key 键
	 * @return map
	 */
	public Map<String,Object> get(String key) {
		try {
			return JsonTool.toMap(redisTemplate.opsForValue().get(key));
		} catch (Exception e) {
			e.printStackTrace();
	        return null;
		}
	}
	
	/**
	 * @param key 键
	 * @return map
	 */
	public <T> T get(String key, Class<T> clazz) {
		try {
			return JsonTool.toEntity(redisTemplate.opsForValue().get(key), clazz);
		} catch (Exception e) {
			e.printStackTrace();
	        return null;
		}

	}
	
	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key, int time){
		try {
			if(time>0)
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	/**
     * 序列化
     * @param object
     * @return
     */
    public byte[] serializeObj(Object object) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException("序列化失败!", e);
        }
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public Object deserializeObj(byte[] bytes) {
        if (bytes == null){
            return null;
        }
        try(ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败!", e);
        }
    }
    
    /**
     * Author:梁博钧
     * Description:InputStream转字节数组
     * 2019年5月13日 下午1:56:01
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] inputStreamToByte(InputStream inputStream) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        }catch (IOException e){
        	throw new RuntimeException("InputStream转字节数组失败!", e);
        }finally {
			try {
				if(inputStream != null)
					inputStream.close();
			} catch (IOException e) {}
		}
    }
}
