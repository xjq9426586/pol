package socket;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	//中文转码UTF-8
	public static final SimpleDateFormat SDF_YMDHMS_DELI = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final String CUSTOMARY_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String CUSTOMARY_YMD_FORMAT="yyyy-MM-dd";
	
	public static String dateFormat() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatted = sdf.format(date);
		return formatted;
	}

	public static String dateFormatYMD() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String formatted = sdf.format(date);
		return formatted;
	}

	public static String dateFormatDay(Date date) {
		if (date==null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = sdf.format(date);
		return formatted;
	} 
	
	
	/**
	 * 获得两个时间的时间差（DeltaTime）
	 * @param Date startT, Date endT
	 * @return {int}分钟
	 */
	public static int deltaTime(Date startT, Date endT) {
		if(startT==null||endT==null)
			return 0;
		int result =0 ;
		String s=formatDateToString(endT);
		String s1=formatDateToString(startT);
		Date d1=formatDate(s);
		Date d2=formatDate(s1);
		result = (int)(Math.abs(d1.getTime() - d2.getTime())/60000);
		return result;
	}
	
	/**
	 * 获得两个时间的时间差（DeltaTime）
	 * @param Date startT, Date endT
	 * @return {Float}小时
	 */
	public static Float getDeltaTime(Date startT, Date endT) {
		if(startT==null||endT==null)
			return null;
		Float result = 0.0f;
		// 结束时间减去开始时间
		result = (float)((endT.getTime() - startT.getTime()) / 3600000.0);
		return result;
	}
	/**
	 * 获得两个时间的时间差（DeltaTime）
	 * @param String startT, String endT
	 * @return {Float}小时
	 */
	public static Float getDeltaTime(String startT, String endT) {
		if(StringUtil.isBlank(startT)||StringUtil.isBlank(endT))
			return null;
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
		Float result = 0.0f;
		try {
			// 结束时间减去开始时间
			result =(float) ((d.parse(endT).getTime() - d.parse(startT).getTime()) / 3600000.0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获得系统当前时间
	 * 
	 * @return {Date}
	 */
	public static Date getSysDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Date date = null;
		try {
			date = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getSysOnlyDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Date date = null;
		try {
			date = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当前系统时间
	 * @param format 格式化
	 * @return
	 */
	public static String getSysTime(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}
	
	/**
	 * 获得系统当前时间
	 * 
	 * @return {String}
	 */
	public static String getSysDateStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date());
	}
	public static String getSysOnlyDateStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		return df.format(new Date());
	}
	public static String getSysDateContinuous() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		return df.format(new Date());
	}
	public static String getSysTimeMs() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return df.format(new Date());
	}
	/**
	 *获取当前系统时间一年前的日期
	 **/
	public static String getDateFullYear() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
		try {
			dt = sdf.parse(sdf.format(new Date()));//获取系统当前日期，转换格式并转换成date类型
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR,-1);//日期减1年
        //rightNow.add(Calendar.MONTH,3);//日期加3个月
        //rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
        
        String reStr = sdf.format(rightNow.getTime());
		return reStr;
	}
	
	/**
	 *获取指定时间加几年的时间
	 **/
	public static Date getDateAddYear(Date year,int n) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
		try {
			dt = sdf.parse(sdf.format(year));//获取指定日期，转换格式并转换成date类型
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR,n);//日期加n年
        
        return rightNow.getTime();
	}
	
	/**
	 *获取指定时间加几月的时间
	 **/
	public static Date getDateAddMonth(Date year,int n) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
		try {
			dt = sdf.parse(sdf.format(year));//获取指定日期，转换格式并转换成date类型
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH,n);//日期加n月
        
        ;
		return rightNow.getTime();
	}
	
	/**
	 * 获取 与  给定时间  差异 的 时间
	 * 几年前，几天前，几小时前...等
	 * @param date
	 * @param year
	 * @param mouth
	 * @param day
	 * @param hour
	 * @param mimute
	 * @param second
	 * @return
	 */
	public static String getDateDiffrence(Date date,int year,int mouth,int day,int hour,int mimute,int second){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, mouth);
		c.add(Calendar.DAY_OF_YEAR, day);
		c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.MINUTE, mimute);
		c.add(Calendar.SECOND, second);
		
		String cyear=c.get(Calendar.YEAR)+"";
		String cmouth=c.get(Calendar.MONTH)+1<10?"0"+(c.get(Calendar.MONTH)+1):c.get(Calendar.MONTH)+1+"";
		String cday=c.get(Calendar.DAY_OF_MONTH)<10?"0"+c.get(Calendar.DAY_OF_MONTH):c.get(Calendar.DAY_OF_MONTH)+"";
		String chour=c.get(Calendar.HOUR_OF_DAY)<10?"0"+c.get(Calendar.HOUR_OF_DAY):c.get(Calendar.HOUR_OF_DAY)+"";
		String cmimute=c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE)+"";
		String csecond=c.get(Calendar.SECOND)<10?"0"+c.get(Calendar.SECOND):c.get(Calendar.SECOND)+"";
		
		return cyear+"-"+cmouth+"-"+cday+" "+chour+":"+cmimute+":"+csecond;
	}

	public static Date StringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getCurrentDayBeginTime() {
		String str = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ " 00:00:00";
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static Date getCurrentDayEndTime() {
		String str = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
				+ " 23:59:59";
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String d = sdf.format(date);
		return d;
	}
	
	public static Date formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	public static Date formatDate(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date newD=null;
		try {
			newD=sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newD;
	}

	public static Date formatDate(String dateStr) {
		DateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static String formatDateToString(Date date) {
		return SDF_YMDHMS_DELI.format(date);
	}
	
	public static String getBeforeDate(Integer days) {
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.DAY_OF_MONTH, -days); 
		dBefore = calendar.getTime(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(dBefore);
		return date;
	}

	
	public static String formatAllDateToString(Object dateobj,String format) {
		if (StringUtil.isBlank(dateobj)||StringUtil.isBlank(format))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String datetostr=""+dateobj;
		Date datestr=null;
		try {
			datestr=sdf.parse(datetostr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String formatted = sdf.format(datestr);
		return formatted;
	}
	
	public static Date timestampToDate(long timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		return cal.getTime();
	}
	/**
	 * 当前时间往后推1周、1个月、1季度、1年
	 * @param rang(0-当天日期，1-周，2-月，3-季，4-年)
	 * @return
	 */
	public static String dateAdvance(int rang){
		 Calendar calendar = Calendar.getInstance();
	     Date date = new Date(System.currentTimeMillis());
	     calendar.setTime(date);
	     
		switch (rang) {
		case 0:
			calendar.add(Calendar.WEEK_OF_YEAR, 0);
			break;
		case 1:
			calendar.add(Calendar.WEEK_OF_YEAR, -1);
			break;
		case 2:
			 calendar.add(Calendar.MONTH, -1);
			break;	
		case 3:
			 calendar.add(Calendar.MONTH, -3);
			break;
		case 4:
			 calendar.add(Calendar.YEAR, -1);
			break;	
		}
		date = calendar.getTime();
		
		return dateFormatDay(date);
		
	}
	
	/**
	 * 加减小时
	 * @param n 负为减正为加
	 * @return
	 */
	public static Date timeAdvance(int n){
		Calendar calendar = Calendar.getInstance();
	     Date date = new Date(System.currentTimeMillis());
	     calendar.setTime(date);
	     calendar.add(Calendar.HOUR, n);
	     date = calendar.getTime();
	     return formatDate(date,"HH:mm");
	}
	
	/**
	 * 获得两个日期有多少个工作日（DeltaTime）
	 * @param Date startT, Date endT
	 * @return {int}天数
	 */
	public static int getDutyDays(String startT, String endT) {
		int result =0 ;
		Date d1=StringToDate(startT);
		Date d2=StringToDate(endT);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		
		while (cal1.compareTo(cal2) <=0) {
			if (cal1.get(Calendar.DAY_OF_WEEK)-1 != 6 && cal1.get(Calendar.DAY_OF_WEEK)-1 != 0)result++;
			cal1.add(Calendar.DAY_OF_MONTH, 1);
			}
		
		return result;
	}
	/**
	 * 获取一个时间段日期
	 * @param startT
	 * @param endT
	 * @return
	 */
	public static List<String> getDate(String startT, String endT){
		Date d1=StringToDate(startT);
		Date d2=StringToDate(endT);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		List<String> dateList=new ArrayList<String>();
		while (cal1.compareTo(cal2) <=0) {
			if (cal1.get(Calendar.DAY_OF_WEEK)-1 != 6 && cal1.get(Calendar.DAY_OF_WEEK)-1 != 0){
				dateList.add(dateFormatDay(cal1.getTime()));
			}
			cal1.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dateList;
	}
	
	
	/**
     * 日期转星期
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取本月今天之前的所有工作日（星期一至星期五）日期
     * @return
     */
    public static List<String> getWorkDaysBeforeToday() {
    	String[] workDays = {"星期一", "星期二", "星期三", "星期四", "星期五"};
    	List<String> list = new ArrayList<String>();
    	Calendar cal = Calendar.getInstance(); // 获得一个日历
    	for (int i = 0; i < Calendar.DAY_OF_MONTH-1; i++) {//判断今天是本月的第几天弄成循环次数	,当日不计算	   			
    		cal.add(Calendar.DATE, -1);  		
    		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());//获取上一天的日期
    		String dayWeek = dateToWeek(yesterday);//获取上一天是星期几
    		for (int j = 0; j < 5; j++) {//如果上一天是星期一到星期五之间的一天，那么将上一天的日期添加到list中				
    			if(workDays[j].equals(dayWeek)){   			
    				list.add(yesterday);
    			}
			}
		}
    	return list;
    }
	

	
	
	public static void main(String[] args) {
		// Date date = StringToDate("2008-11-05");
		// System.out.println(getCurrentDayBeginTime());
		// System.out.println(getCurrentDayEndTime());
		// System.out.println(formatDateToString(new Date()));
		String date = "2018-02-01";
		String date1 = "2018-02-28";
		System.out.println(getDutyDays(date, date1));
		Calendar c=Calendar.getInstance();
		c.setTime(getSysOnlyDate());
		System.out.println(dateToWeek(date));
		System.out.println(getDate(date, date1));
//		System.out.println(getDateBefore(getSysDate(),0,0,0,0,-30,0));
		System.out.println(dateAdvance(1));
		
		System.out.println(formatAllDateToString("2016-01-07 14:26:04","yyyy-MM-dd"));
		
		System.out.println(dateFormatDay(getSysOnlyDate()));
		
		System.out.println(timeAdvance(-1));
	}

}
