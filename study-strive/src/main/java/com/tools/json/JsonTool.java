package com.tools.json;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class JsonTool {
	/**
	 * description Object转map
	 * @param t 实体
	 * @return
	 */
	public static Map<String, Object> toMap(Object obj) {
		return JSON.parseObject(JSON.toJSONString(obj),new TypeReference<Map<String,Object>>(){});
	}
	
	/**
	 * description map转entity
	 * @param map
	 * @param clazz 需要转成某个实体的class对象
	 * @return
	 */
	public static <T> T toEntity(Object obj, Class<T> clazz) {
		return JSON.parseObject(JSON.toJSONString(obj), clazz);
	}
	
}
