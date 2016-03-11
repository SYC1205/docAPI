package com.e104.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className DateUtil
 * @purpose 日期處理工具程式
 * @author jason.hsiao
 * @createTime 2013-06-25
 */
public abstract class DateUtil {
	
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_3 = "yyyy-MM-dd";	
		
	/**
	 * @method getDateTimeForLog
	 * @author jason.hsiao
	 * @createTime 2013-06-25
	 * @purpose 取得目前時間[yyyy-MM-dd HH:mm:ss.SSS]並回傳字串做log紀錄
	 * @return String
	 */
	public static String getDateTimeForLog(){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_1);
		Date current = new Date();		
		return sdf.format(current);
	}
	
	/**
	 * @method getTimeDifference
	 * @author jason.hsiao
	 * @createTime 2013-06-25
	 * @purpose 計算傳時間差,回格式[h:m:s:ms]
	 * @param startDate
	 * @param endDate
	 * @return String 
	 */
	public static String getTimeDifference(Date startDate,Date endDate){
		long timeDifference = endDate.getTime() - startDate.getTime();
		
		long day = timeDifference / (24 * 60 * 60 * 1000);
        long hour = (timeDifference / (60 * 60 * 1000) - day * 24);
        long min = ((timeDifference / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeDifference / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (timeDifference - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000); 

        return hour + ":" + min + ":" + s + ":" + ms;
	}

}

