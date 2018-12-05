/*
 * wueasy - A Java Distributed Rapid Development Platform.
 * Copyright (C) 2017-2018 wueasy.com

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.wueasy.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 日期工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:37:16
 */
public class DateHelper
{
    
    private static final Logger LOGGER=LoggerFactory.getLogger(DateHelper.class);
    
    /**
     * 日期格式yyyy-MM-dd
     */
    public static final String PATTERN_DATE="yyyy-MM-dd";
    
    /**
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     */
    public static final String PATTERN_DATE_TIME="yyyy-MM-dd HH:mm:ss";
    
    /**
     * @Description: 日期格式化
     * @author: fallsea
     * @date: 2017年10月22日 下午8:37:25
     * @param date 日期
     * @return 输出格式为 yyyy-MM-dd HH:mm:ss 的字符串
     */
    public static String formatDate(Date date)
    {
        return formatDate(date, PATTERN_DATE_TIME);
    }
    
    /**
     * @Description: 日期格式化
     * @author: fallsea
     * @date: 2017年10月22日 下午8:37:53
     * @param date 日期
     * @param pattern 格式化类型
     * @return
     */
    public static String formatDate(Date date, String pattern)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
    
    
    /**
     * @Description: 解析日期
     * @author: fallsea
     * @date: 2017年10月22日 下午8:38:08
     * @param dateStr 日期字符串
     * @return 默认 yyyy-MM-dd HH:mm:ss 格式解析
     */
    public static Date parseString(String dateStr)
    {
        return parseString(dateStr,PATTERN_DATE_TIME);
    }
    
    /**
     * @Description: 解析日期字符串
     * @author: fallsea
     * @date: 2017年10月22日 下午8:38:36
     * @param dateStr 日期字符串
     * @param pattern 格式化类型
     * @return
     */
    public static Date parseString(String dateStr, String pattern)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try
        {
            if (!StringHelper.isBlank(dateStr))
            {
                return dateFormat.parse(dateStr);
            }
        }
        catch (ParseException ex)
        {
            LOGGER.error(dateStr+"转换成日期失败，可能是不符合格式："+pattern, ex);
        }
        return null;
    }
    
    /**
     * @Description: 获取指定日期的中文星期数
     * @author: fallsea
     * @date: 2017年10月22日 下午8:38:49
     * @param date 指定日期
     * @return 星期数，如：星期一
     */
    public static String getWeekStr(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(7);
        --week;
        String weekStr = "";
        switch (week)
        {
            case 0:
                weekStr = "星期日";
                break;
            case 1:
                weekStr = "星期一";
                break;
            case 2:
                weekStr = "星期二";
                break;
            case 3:
                weekStr = "星期三";
                break;
            case 4:
                weekStr = "星期四";
                break;
            case 5:
                weekStr = "星期五";
                break;
            case 6:
                weekStr = "星期六";
                break;
            default :
            	break;
        }
        return weekStr;
    }
    
    /**
     * @Description: 间隔时间
     * @author: fallsea
     * @date: 2017年10月22日 下午8:39:04
     * @param date1
     * @param date2
     * @return 毫秒数
     */
    public static long getDateMiliDispersion(Date date1, Date date2)
    {
        if ((date1 == null) || (date2 == null))
        {
            return 0L;
        }
        
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        
        return time1 - time2;
    }
    
    /**
     * @Description: 间隔天数
     * @author: fallsea
     * @date: 2017年10月22日 下午8:39:19
     * @param date1
     * @param date2
     * @return 天数
     */
    public static int getDateDiff(Date date1, Date date2)
    {
        if ((date1 == null) || (date2 == null))
        {
            return 0;
        }
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        
        long diff = time1 - time2;
        
        Long longValue = new Long(diff / 86400000L);
        return longValue.intValue();
    }
    
    /**
     * @Description: 获取指定日期之前多少天的日期
     * @author: fallsea
     * @date: 2017年10月22日 下午8:39:31
     * @param date 指定日期
     * @param day 天数
     * @return 日期
     */
    public static Date getDataDiff(Date date, int day)
    {
        if (date == null)
        {
            return null;
        }
        long time = date.getTime();
        time -= 86400000L * day;
        return new Date(time);
    }
    
    /**
     * @Description: 获取当前周
     * @author: fallsea
     * @date: 2017年10月22日 下午8:39:48
     * @return
     */
    public static int getCurrentWeek()
    {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(7);
        --week;
        if (week == 0)
        {
            week = 7;
        }
        return week;
    }
    
    
    /**
     * @Description: 获取中文当前周
     * @author: fallsea
     * @date: 2017年10月22日 下午8:40:01
     * @return
     */
    public static String getCurrentWeekStr()
    {
        return getWeekStr(new Date());
    }
    
    
    /**
     * @Description: 获取本年
     * @author: fallsea
     * @date: 2017年10月22日 下午8:40:08
     * @return
     */
    public static int getCurrentYear()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(1);
    }
    
    
    /**
     * @Description: 获取本月
     * @author: fallsea
     * @date: 2017年10月22日 下午8:40:13
     * @return
     */
    public static int getCurrentMonth()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(2) + 1;
    }
    
    
    /**
     * @Description: 获取本月的当前日期数
     * @author: fallsea
     * @date: 2017年10月22日 下午8:40:20
     * @return
     */
    public static int getCurrentDay()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(5);
    }
    
    /**
     * @Description: 当前时间与指定时间的差
     * @author: fallsea
     * @date: 2017年10月22日 下午8:40:27
     * @param str 秒数
     * @return 时间差，单位：秒
     */
    public static int getUnixTime(String str)
    {
        if ((str == null) || ("".equals(str)))
        {
            return 0;
        }
        try
        {
            long utime = Long.parseLong(str) * 1000L;
            Date date1 = new Date(utime);
            
            Date date = new Date();
            
            long nowtime = (date.getTime() - date1.getTime()) / 1000L;
            return (int) nowtime;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return 0;
    }
    
    /**
     * @Description: 去除日期字串中原“-”和“:”
     * @author: fallsea
     * @date: 2017年10月22日 下午8:40:43
     * @param dateTime 日期字符串
     * @return
     */
    public static String formatString(String dateTime)
    {
        if ((dateTime != null) && (dateTime.length() >= 8))
        {
            String formatDateTime = dateTime.replaceAll("-", "");
            formatDateTime = formatDateTime.replaceAll(":", "");
            String date = formatDateTime.substring(0, 8);
            return date;
        }
        
        return "";
    }
    
    /**
     * @Description: 当前时间与指定时间的差
     * @author: fallsea
     * @date: 2017年10月22日 下午8:40:54
     * @param str yyyy-MM-dd HH:mm:ss 格式的日期
     * @return 时间差，单位：秒
     */
    public static int getTimesper(String str)
    {
        if ((str == null) || ("".equals(str)))
        {
            return 0;
        }
        try
        {
            Date date1 = new Date(Long.parseLong(str));
            Date date = new Date();
            long nowtime = (date.getTime() - date1.getTime()) / 1000L;
            return (int) nowtime;
        }
        catch (Exception e)
        {
            LOGGER.error("日期转换出错", e);
        }
        return 0;
    }
    
    /**
     * @Description: 获取16位日期时间，yyyyMMddHHmmss
     * @author: fallsea
     * @date: 2017年10月22日 下午8:41:12
     * @param date 日期
     * @return
     */
    public static String formatDateTime(Date date)
    {
        return formatDate(date,"yyyyMMddHHmmss");
    }
    
}
