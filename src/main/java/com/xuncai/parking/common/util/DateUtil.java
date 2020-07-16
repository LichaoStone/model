package com.xuncai.parking.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间工具类
 * @作者 栗超
 * @时间 2019年1月17日 下午1:36:06
 * @说明
 */
public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	/**
	 * 时间转换为毫秒数
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return 1526275490000
	 */
    public static String date2msec(String date){
    	long time=0L;
    	try {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
	        time = simpleDateFormat.parse(date).getTime();
		} catch (ParseException e) {
			logger.error("时间转换为毫秒数出错:",e);
		}  
		return String.valueOf(time);
    }
    
    /**
     * 毫秒转换为时间
     * @param msec 1526275490000
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String msec2date(String msec){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
        Date date2 = new Date();  
        try {
            date2.setTime(Long.parseLong(msec));  
		} catch (Exception e) {
			logger.error("毫秒转换为时间出错:",e);
		}
    	
    	return simpleDateFormat.format(date2);  
    }
    
    /**
     * 时间转换为N时N分N秒   
     * 	   86（单位秒）-->1分26秒
     * @time 
     * @return 例:1分26秒
     */
    public static String second2HourMinSecond(Integer seconds){
    	String retStr="";
    	try {
			if (seconds>60) {
				Integer second = seconds % 60;  
				Integer min = seconds / 60; 
				retStr=min+"分"+second+"秒"; 
				
				if (min>60){ 
					 min = (seconds / 60) % 60;  
					 Integer hour = (seconds / 60) / 60; 
					 retStr = hour + "小时" + min + "分" + second + "秒";  
					 
					 if (hour>24){  
		                    hour =((seconds/60)/60)%24;  
		                    Integer day =(((seconds/60)/60)/24);  
		                    retStr=day+"天"+hour+"小时"+min+"分"+second+"秒";  
		              }  
				}
			}else{
				retStr=seconds+"秒";
			}
		} catch (Exception e) {
		}
    	
		return retStr;
    }
    
    /**
     * 获取日期对应的星期几
     * @param time 2019-1-17
     * @return     星期二
     */
    public static String getWeekOfDate(String time) {
    	String[] weekDays = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};  
    	int dayForWeek = 0;

    	try {
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	Calendar c = Calendar.getInstance();
        	c.setTime(format.parse(time));
        	if(c.get(Calendar.DAY_OF_WEEK) == 1){
        		dayForWeek = 0;
        	}else{
        		dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        	}
		} catch (Exception e) {
			logger.error("获取日期对应星期几出错:",e);
		}
    	
    	return weekDays[dayForWeek];
    }
    
    /**
     * 转换时间为3月12日 12:23格式
     * @return
     */
    public static String getMMDDHHmm(String time) {
    	
    	String returnStr="";
    	if (!StringUtils.isEmpty(time)) {
        	try {
            	String[] timeArr=time.split(" ");
            	String[] YYYYMMDDArr=timeArr[0].split("-");
            	
            	returnStr=YYYYMMDDArr[1]+"月"+YYYYMMDDArr[2]+"日 "+timeArr[1];
    		} catch (Exception e) {
    			logger.error("【MM月dd日 HH:mm】时间转换错误",e);
    		}
		}
    	
		return returnStr;
	}
    
    /**
     * 获取当前时间
     * @return
     */
    public static String getTimeToSec() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
    
    
    
    /**
     * 获取当日
     * @return
     */
    public static String getTody(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}
    
    /**
     * 获取当前日期前N天或者后N天
     * @param n 天数
     * @return 2019-01-17
     */
    public static String getBeforeOrNextDay(int n) {
        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    		String day=dateFormat.format(new Date());
        	
            String sYear = day.substring(0,4);
            int year = Integer.parseInt(sYear);
            String sMonth = day.substring(4,6);
            int month = Integer.parseInt(sMonth);
            String sDay = day.substring(6,8);
            int dday = Integer.parseInt(sDay);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, dday);
            cal.add(Calendar.DATE,n);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(cal.getTime());
        } catch (Exception e) {
            throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
        }
    }

	/**
	 * 获取当年
	 * @return
	 */
	public static String getCurrYear(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(new Date());
	}


	/**
	 * 获取当月
	 * @return
	 */
	public static String getCurrMonth(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		String month=dateFormat.format(new Date());

		if(month.length()<2){
			month="0"+month;
		}
		return month;
	}
    /**
     * 获取日期对应月份的第一天
     * @return 2019-01-17
     */
    public static String getFirstDayOfMonth() {
    	try {
    		Calendar cale = null;
    		cale = Calendar.getInstance();
    		
        	// 获取当月第一天和最后一天
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    		// 获取前月的第一天
    		cale = Calendar.getInstance();
    		cale.add(Calendar.MONTH,0);
    		cale.set(Calendar.DAY_OF_MONTH, 1);
    		return format.format(cale.getTime());
		} catch (Exception e) {
			logger.error("获取日期对应月份的第一天出错",e);
		}
    	
    	return null;
	}



	public static void main(String[] args) {
	}
}
