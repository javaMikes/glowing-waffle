package com.cloudmusic.util;

public class RandomString {
	
	
	 
	private static String string = "abcdefghijklmnopqrstuvwxyz0123456789";   
	 
	/**
	 * ��ȡ����ַ���
	 * @param length ������Ҫ��ȡ�ַ����ĳ���
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
