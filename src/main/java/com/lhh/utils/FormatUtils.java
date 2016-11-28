package com.lhh.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class FormatUtils {

	/**
	 * object转化成string，空不转化,去掉首尾空格
	 * @param ob
	 * @return
	 */
	public static String formatToStringTrim(Object ob){
		if(ob == null )return null;
		if( ob instanceof byte[]){
			return new String((byte[])ob);
		}
		return ob.toString().trim();
	}
	public static String formatToStringTrim(Object ob,String charsetName){
		if(ob == null )return null;
		if( ob instanceof byte[]){
			try {
				return new String((byte[])ob,charsetName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ob.toString().trim();
	}
	public static Date formatToDate(Object ob){
		if(ob == null || StringUtils.isBlank(ob.toString()))return null;
		try {
			return (Date)ob;
		} catch (Exception e) {
			System.out.println(ob);
			e.printStackTrace();
		}
		return null;
	}
	public static Integer formatToInteger(Object ob){
		if(ob == null || StringUtils.isBlank(ob.toString()) || !StringUtils.isNumeric(ob.toString()))return null;
		try {
			return Integer.parseInt(ob.toString().trim());
		} catch (Exception e) {
			System.out.println(ob);
			e.printStackTrace();
		}
		return null;
	}
	
	
}
