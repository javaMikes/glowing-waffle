package com.cloudmusic.util;

public class RandomString {
	
	
	 
	private static String string = "abcdefghijklmnopqrstuvwxyz0123456789";   
	 
	/**
	 * 获取随机字符串
	 * @param length 传入想要获取字符串的长度
	 * @return
	 */
	public static String getRandomString(int length){
	    StringBuffer sb = new StringBuffer();
	    int len = string.length();
	    for (int i = 0; i < length; i++) {
	        sb.append(string.charAt(getRandom(len-1)));
	    }
	    return sb.toString();
	}
	
	private static int getRandom(int count) 
	{
	   return (int) Math.round(Math.random() * (count));
	}
}
