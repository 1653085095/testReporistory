package com.test;

import java.util.HashMap;
import java.util.Map;

import com.tools.json.JsonTool;

public class Test {
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<>();
		map.put("id",1);
		System.out.println(JsonTool.toEntity(map, Object.class).getClass());
	}
}
