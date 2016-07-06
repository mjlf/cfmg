package com.mjlf.cfmg.utils;

import org.junit.Test;

public class RandomString {
	public static String base = "QWERTYUIOPASDFGHJKLZXCVBNM!@#$%^&()12[]34567890-=";
	public static String BASE = "1234567890";
	public static String getPassword(){
		String password = "";
		int num = (int) (Math.random()*10);
		while(num < 6){
			num = (int)(Math.random()*10);
		}
		char[] str = base.toCharArray();
		for(int i = 0; i < num; i++){
			int index = (int)(Math.random()*str.length);
			password += str[index];
		}
		return password;
	}
	public static String getSignPassword(){
		String password = "";
		int num = 6;
		char[] str = BASE.toCharArray();
		for(int i = 0; i < num; i++){
			int index = (int)(Math.random()*str.length);
			password += str[index];
		}
		return password;
	}
}
