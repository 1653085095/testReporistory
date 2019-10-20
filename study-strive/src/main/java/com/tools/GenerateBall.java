package com.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class GenerateBall {
	private GenerateBall() {};
	
	public static ArrayList<Integer> randomRedBall() {
		HashSet<Integer> set = new HashSet<>();
		while(set.size()!=6) {
			set.add(randomNum(1,33));
		}
		ArrayList<Integer> list = new ArrayList<>(set);
		Collections.sort(list);
		return list;
	}
	
	public static int randomBlueBall() {
		return randomNum(1,16);
	}
	
	/**
	 * Description：生成指定范围随机数
	 */
	public static int randomNum(int begin ,int end){
		return (int)(Math.random()*((end+1)-begin)+begin);
	}
	
	
	/**
	 * Description：随机生成日期
	 */
	public static String randomDate(String beginDate, String endDate) {  
        try {  
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");  
            Date start = format.parse(beginDate);// 构造开始日期  
            Date end = format.parse(endDate);// 构造结束日期  
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。  
            if (start.getTime() >= end.getTime()) {  
                return null;  
            }  
            long date = randomDateUtil(start.getTime(), end.getTime()) ;  
            return format.format(new Date(date)) ;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
	
	/**
	 * Description：生成时间工具
	 */
	private static long randomDateUtil(long begin, long end) {  
        long rtn = begin + (long) (Math.random() * (end - begin));  
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值  
        if (rtn == begin || rtn == end) {  
            return randomDateUtil(begin, end);  
        }  
        return rtn;  
    }
}
