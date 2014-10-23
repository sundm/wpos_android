package com.zc.app.sebc.util;

import java.util.Calendar;
import java.util.Date;

public final class AndroidUtil {

	static Date date = null;
	final static Calendar calendar = Calendar.getInstance();
	
	public static String getCurTime() {
		
		long time = System.currentTimeMillis();
		calendar.setTimeInMillis(time);
		
		int ihour;
		String hour;
		String minute;
		String second;
		
		ihour = calendar.get(Calendar.HOUR);
		if(calendar.get(Calendar.AM_PM) == 0) {
			
		} else {
			if(ihour < 12) {
				ihour += 12;
			}
		}
		
		hour = Integer.toString(ihour);
		minute = Integer.toString(calendar.get(Calendar.MINUTE));
		second = Integer.toString(calendar.get(Calendar.SECOND));
		
		if(hour.length() < 2) {
			hour = "0".concat(hour);
		}
		
		if(minute.length() < 2) {
			minute = "0".concat(minute);
		}
		
		if(second.length() < 2) {
			second = "0".concat(second);
		}
		
		return hour + minute + second;
	}
	
	public static String getCurDate() {
		
		date = new Date();
		
		String year;
		String month;
		String day;
		
		calendar.setTime(date);
		
		year = Integer.toString(calendar.get(Calendar.YEAR)).substring(2, 4);
		month = Integer.toString(calendar.get(Calendar.MONTH)+1);
		day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		
		if(month.length() < 2) {
			month = "0".concat(month);
		}
		
		if(day.length() < 2) {
			day = "0".concat(day);
		}
		
		return year + month + day;
	}
}
