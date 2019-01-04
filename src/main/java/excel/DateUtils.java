/*
* @(#)DateUtils.java
*
* Copyright (c) 2008 onest Inc.
* All Rights Reserved.
*
* 作用：日期处理单�?
* 注意事项:
* Create by haicao Jun 4, 2008 v 1.0
* LastUpdate by haicao Jun 4, 2008  v 1.0
*/


package excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DateUtils {
	public static SimpleDateFormat yyyymmddformatter =new SimpleDateFormat("yyyy-MM-dd HH");
	public static SimpleDateFormat yyyymmddhhmmssformatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) throws Exception{   
	    /*  
    	Calendar cal = Calendar.getInstance();   
    	Calendar cal1 = Calendar.getInstance();   
    	cal.set(2005,03,01);  
    	cal1.set(2004,07,28);   
    	System.out.println(getMonthDiff(cal, cal1));   
    	*/
//    	System.out.println("111::"+getDateTimeFromString("2009-09-18 11:49:13"));
    	System.out.println(getDate("2010-03-12 0:0:00"));
    	System.out.println(getBetweenDayNumber("2010-03-10", "2010-03-12"));
    	System.out.println(getBetweenMiNumber("2010-03-10 11:26:20", "2010-03-10 11:36:02"));
    	
    	//System.out.println(yyyyformatter.format(new Date());
    	
    }
	
	/**
	 * 得到今天的yyyyMMdd格式的字符串.估计调用较多,故写成static的formatter
	 * @return String
	 */
	public static String getYmdOfToday(){		
		return yyyymmddformatter.format(new Date());
		
	}
	
	public static String getYmdhmsOfToday(){		
		return yyyymmddhhmmssformatter.format(new Date());
		
	}
	
	/**
	 * 得到日期字符串对应的日期
	 * @return Date
	 */
	public static Date getDate(String s){
		try {
			return yyyymmddhhmmssformatter.parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 得到今天的开始时�?(带日�?)
	 * @return
	 */
	public static Date getTodayStart(){
		try {
			return yyyymmddhhmmssformatter.parse(getYmdOfToday()+" 00:00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 得到今天的结束时�?(带日�?)
	 * @return
	 */	
	public static Date getTodayEnd(){
		try {
			return yyyymmddhhmmssformatter.parse(getYmdOfToday()+" 23:59:59");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 把日期对像转换为字串 200x-x-x格式
	 * @return
	 */	
	public static String getYmd(Date value){
		return yyyymmddformatter.format(value);
	}
	
	/**
	 * 把日期对像转换为字串 200x-x-x格式 �?:�?:�? 格式
	 * @return
	 */	
	public static String getYmdhms(Date value){
		return yyyymmddhhmmssformatter.format(value);
	}	
	
	/**
	 * 取得某日期的�?始时�?
	 * @return Date
	 */
	public static Date getStartDate(Date value){
		try {
			return yyyymmddhhmmssformatter.parse(getYmd(value)+" 00:00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 取得某日期的结束时间
	 * @return Date
	 */
	public static Date getEndDate(Date value){
		try {
			return yyyymmddhhmmssformatter.parse(getYmd(value)+" 23:59:59");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 取两个日期间的日�?
	 * by haicao
	 */
    public static int getBetweenDayNumber(String dateA, String dateB) {
        long dayNumber = 0;
        long DAY = 24L * 60L * 60L * 1000L;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	 Date d1 = df.parse(dateA);
	         Date d2 = df.parse(dateB);
	         dayNumber = (d2.getTime() - d1.getTime()) / DAY;
	         System.out.println(dayNumber);
        } catch (Exception e) {
         e.printStackTrace();
        }
        return (int) dayNumber;
     }
      
    /*
	 * 取两个日期间的分钟数�?
	 * by xyu
	 */
    public static int getBetweenMiNumber(String dateA, String dateB) {
        long miNumber = 0;
        long MI =  60L * 1000L ;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	 Date d1 = df.parse(dateA);
	         Date d2 = df.parse(dateB);
	         miNumber = (d2.getTime() - d1.getTime()) / MI;
        } catch (Exception e) {
         e.printStackTrace();
        }
        return (int) miNumber;
     }
     
   /*
    * 计算两个日期的月�?
    * Create by haicao
    * 2008-6-27
    */
   public static int getMonthDiff(Calendar cal, Calendar cal1){   
    	  int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));   
    	  int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));   
    	  return y * 12 + m;   
   }
   
   /*
    * 计算两个日期的月�?
    * 超过�?天也加一�?
    * �?2007-1-1 2007-2-2 返回2
    * Create by haicao
    * 2008-6-27
    */
   public static int getMonthDiff(String dateStr1, String dateStr2) {  
	   int m = 0;
	   int y = 0;

	   	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	   	try {
		    Date d1 = df.parse(dateStr1);
	        Date d2 = df.parse(dateStr2);
	    	m = (d1.getMonth() - d2.getMonth());   
	    	y = (d1.getYear() - d2.getYear());
	    	if (d1.getDate() - d2.getDate() > 0)
	    		m ++;
	   	} catch (Exception e) {
	   		
	   	}
    	return y * 12 + m;   
   }  
   
   /*
    * 给某个日期加上指定的天数
    * Create by haicao
    * 2008-6-27
    */
	public static Date addDate(Date value,int day){
		Calendar cal=Calendar.getInstance();
		cal.setTime(value);
		cal.add(Calendar.DAY_OF_YEAR,day);
		return cal.getTime();
	}
	
	   /*
	    * 计算start 超过 end的天�?
	    * Create by haicao
	    * 2008-6-27
	    */
	public static long moreDay(Date start,Date end){
		Long startM=getStartDate(start).getTime();
		Long endM=getStartDate(end).getTime();
		long result = (startM-endM) / (24 * 60 * 60*1000); 
		return result;
	}
	
	public static Date getStartDate(String dateStr){
		try{
			return yyyymmddhhmmssformatter.parse(dateStr+" 00:00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}		
	}
	public static Date getEndDate(String dateStr){
		try{
			return yyyymmddhhmmssformatter.parse(dateStr+" 23:59:59");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}		
	}
	public static String getMoneyFirstDay(Date theDate){
		Calendar cal=Calendar.getInstance();
		cal.setTime(theDate);
		cal.set(Calendar.DAY_OF_MONTH,1);
		return yyyymmddformatter.format(cal.getTime());			
	}	
	/**
	 * 获取昨天的日�?
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Date date = new Date();
		return new Date(date.getTime() - 1000 * 60 * 60 * 24);
	}

	/**
	 * 获取�?周之前日�?
	 * 
	 * @param format
	 * @return
	 */
	public static String getOneWeekAgoStr(String format) {
		long current = new java.util.Date().getTime();
		long aweek = 7 * 24 * 60 * 60 * 1000;
		long oneweekago = current - aweek;
		return new SimpleDateFormat(format).format(new java.util.Date(
				oneweekago));
	}

	/**
	 * 获取3天之前日�?
	 * 
	 * @param format
	 * @return
	 */
	public static String getThreeDayAgoStr(String format) {
		long current = new java.util.Date().getTime();
		long threeday = 3 * 24 * 60 * 60 * 1000;
		long threedayago = current - threeday;
		return new SimpleDateFormat(format).format(new java.util.Date(
				threedayago));
	}

	/**
	 * 获取给定天之前日�?
	 * 
	 * @param format
	 * @return
	 */
	public static String getGivenDayAgoStr(int beforeDays, String format) {
		Calendar c = Calendar.getInstance(); // 当时的日期和时间
		int d = c.get(Calendar.DAY_OF_MONTH); // 取出“日”数
		int d2 = d - beforeDays;
		c.set(Calendar.DAY_OF_MONTH, d2); // 将�?�日”数设置回去
		//
		// long current = new java.util.Date().getTime();
		// long beforetimes = beforeDays * 24 * 60 * 60 * 1000;
		// long times = current - beforetimes;
		return new SimpleDateFormat(format).format(c.getTime());
	}

	/**
	 * 取当前日�?
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		return new SimpleDateFormat(format).format(new java.util.Date());
	}

	/**
	 * 获取给定天后日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getGivenDayAfter(int afterDays, String format) {
		Calendar c = Calendar.getInstance(); // 当时的日期和时间
		int d = c.get(Calendar.DAY_OF_MONTH); // 取出“日”数
		int d2 = d + afterDays;
		c.set(Calendar.DAY_OF_MONTH, d2); // 将�?�日”数设置回去
		//
		// long current = new java.util.Date().getTime();
		// long beforetimes = beforeDays * 24 * 60 * 60 * 1000;
		// long times = current - beforetimes;
		return new SimpleDateFormat(format).format(c.getTime());
	}

	/**
	 * 取当前日�?
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format, Locale l) {
		return new SimpleDateFormat(format, l).format(new java.util.Date());
	}

	public static String format(java.util.Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static String format(java.util.Date date, String format, Locale l) {
		return new SimpleDateFormat(format, l).format(date);
	}

	/**
	 * 
	 * @param dateStr
	 *            "yyyy-MM-dd"
	 * @return
	 */
	public static java.util.Date buildDate(String dateStr)
			throws Exception {
		
		try {
			
			int year = intValue(dateStr.substring(1, 4)) + 100;
			int month = intValue(dateStr.substring(5, 7)) - 1;
			if (month == 0) {
				month = 12;
				year = year - 1;
			}
			int day = intValue(dateStr.substring(8, 10));
			
			GregorianCalendar calendar = new GregorianCalendar(year + 1900,
					month, day);
			return calendar.getTime();
			
		} catch (Exception e) {
			throw new Exception("时间格式不正�?");
		}
	}

	/**
	 * 
	 * @param timeStr
	 *            "mm:ss"
	 * @return
	 */
	public static java.util.Date buildTime(String timeStr) {
		int hour = intValue(timeStr.substring(0, 2));
		int minute = intValue(timeStr.substring(3, 5));
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), hour, minute, 0);
		return calendar.getTime();
	}

	static int intValue(String str) {
		return new Integer(str).intValue();
	}

	/**
	 * 比较时间，精确到小时
	 * 
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	public static int compare(String dateStr1, String dateStr2) {
		int result = -1;
		System.out.println("a: " + dateStr1);
		String a = dateStr1.substring(0, 4) + dateStr1.substring(5, 7)
				+ dateStr1.substring(8, 10) + dateStr1.substring(11, 13);
		System.out.println("a: " + a);
		String b = dateStr2.substring(0, 4) + dateStr2.substring(5, 7)
				+ dateStr2.substring(8, 10) + dateStr2.substring(11, 13);
		System.out.println("b: " + dateStr2);
		System.out.println("b: " + b);
		if (intValue(a) < intValue(b)) {
			result = 1;
		}
		if (intValue(a) == intValue(b)) {
			result = 0;
		}
		
		System.out.println("result: " + result);
		return result;
	}
	
	/**
	 * 比较时间
	 * 
	 * date dateStr
	 * -1:date早于date2 date�?
	 * 0:date等于date2  相等
	 * 1:date晚于date2 date�?
	 * @param date
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static int compare(Date date, Date date2) {
		
		int result = -1;
		
		if (date.before(date2)) {
			result = 1;
		} else if (date.equals(date2)) {
			result = 0;
		} else if (date.after(date2)) {
			result = -1;
		}
		
		return result;
	}
	
	/**
	 * 取得两个时间的最大�??(比较晚的时间)
	 * 
	 * @param date
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getMaxDate(Date date, Date date2){
		
		Date maxDate = date2;
		
		if (date != null) {
			int result = compare(date, date2);
			
			if (result == 1) {
				maxDate = date;
			} else if (result == -1) {
				maxDate = date2;
			}
		}
		
		return maxDate;
	}
	
	/**
	 * 取得两个时间的最小�??(比较早的时间)
	 * 
	 * @param date
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getMinDate(Date date, Date date2){
		
		Date minDate = date2;
		
		if (date != null) {
			int result = compare(date, date2);
			
			if (result == 1) {
				minDate = date2;
			} else if (result == -1) {
				minDate = date;
			}
		}
		
		return minDate;
	}

	public static String getUsedTime(long millis) {
		int hour = (int) millis / (1000 * 60 * 60);
		int minute = (int) (millis - (hour * 1000 * 60 * 60)) / (1000 * 60);
		int second = (int) ((millis - (hour * 1000 * 60 * 60) - (minute * 1000 * 60)) / 1000);
		return hour + "小时" + minute + "分" + second + "秒";
	}
    
	public static String getDayTime(double minutes){
		int day = (int)minutes /(60*24);
		int hour = (int)(minutes-(day*60*24))/ 60;
		int minute = (int)(minutes-(day*60*24)-(hour*60));
		return  day+":"+hour+":"+minute;
	}
	
	/**
	 * 
	 * @param timeStr
	 * @return
	 */
	public static Calendar getCalendar(String timeStr) {
		// 判断时间格式是否合法、取小时和分�?
		if (timeStr == null || timeStr.length() != 5) {
			throw new IllegalArgumentException("时间格式错误!");
		}
		String hourStr = timeStr.substring(0, 2);
		String minuteStr = timeStr.substring(3, 5);
		if (hourStr.substring(0, 1).equals("0")) {
			hourStr = hourStr.substring(1, 2);
			System.out.println("hourstr is:" + hourStr);
		}
		if (minuteStr.substring(0, 1).equals("0")) {
			minuteStr = minuteStr.substring(1, 2);
			System.out.println("timeStr is:" + minuteStr);
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, intValue(hourStr));
		cal.set(Calendar.MINUTE, intValue(minuteStr));
		return cal;
	}
	
	public static String dateFormat(String pattern, Date date) {
		String str = "";
		if (date != null) {
			if (pattern == null || pattern.equals("")) {
				return new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(date);
			} else {
				return new SimpleDateFormat(pattern).format(date);
			}
		}
		return str;
	} 
	
	public static Date dateFormat(String pattern, String date) throws ParseException {
		if (pattern == null || pattern.equals("")) {
			return new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").parse(date);
		} else {
			return new SimpleDateFormat(pattern).parse(date);
		}
	}
	
	static public Date getDateTimeFromString(String dateStr) throws ParseException {
		return dateFormat(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	static public String formatDateTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	/**
	 * 新增加静态方法只得到年份
	 * modify add/by jcxu
	 */

}



