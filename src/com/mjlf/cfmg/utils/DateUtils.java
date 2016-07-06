package com.mjlf.cfmg.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static Date ParseDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parseDate = null;
		try {
			parseDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parseDate;
	}
	
	public static Date ParseDateTime(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parseDate = null;
		try {
			parseDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parseDate;
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static long getTime(Time time){
		int hour = time.getHours();
		int min = time.getMinutes();
		int sec = time.getSeconds();
		Date date = new Date(0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		cal.add(Calendar.MINUTE, min);
		cal.add(Calendar.SECOND, sec);
		date = cal.getTime();
		return date.getTime();
	}
	
	
	public static long getLength(Date end, Date start){
		long startTime = start.getTime();
		long endTime = end.getTime();
		
		long length = (endTime - startTime)/(1000*60);
		return length;
	}
}
