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
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: String工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:50:48
 */
public class StringHelper {
	
	private static Logger logger = LoggerFactory.getLogger(StringHelper.class);
	
	/**
	 * @Description: 判断一个字符序列不为空（“”）和不空。
	 * @author: fallsea
	 * @date: 2017年10月22日 下午8:51:03
	 * @param cs 字符串
	 * @return 不为空，false 为空
	 */
    public static boolean isNotEmpty(final CharSequence cs)
    {
        return StringUtils.isNotEmpty(cs);
    }
    
    /**
     * @Description: 判断一个字符序列为空（“”）或空。
     * @author: fallsea
     * @date: 2017年10月22日 下午8:51:15
     * @param cs 字符串
     * @return 为空，false 不为空
     */
    public static boolean isEmpty(final CharSequence cs)
    {
        return StringUtils.isEmpty(cs);
    }
    
    /**
     * @Description: 判断一个字符序列不为空（“”）和不空。
     * @author: fallsea
     * @date: 2018年3月3日 下午12:28:41
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(final Object obj)
    {
        return !isEmpty(obj);
    }
    
    /**
     * @Description: 判断一个字符序列为空（“”）或空。
     * @author: fallsea
     * @date: 2018年3月3日 下午12:28:11
     * @param obj
     * @return
     */
    public static boolean isEmpty(final Object obj)
    {
    	if(null == obj){
    		return true;
    	}
    	if(obj instanceof String){
    		return isEmpty(obj.toString());
    	}
    	return false;
    }
    
    /**
     * @Description: 判断一个字符序列为空（“”），空或只有空白。
     * @author: fallsea
     * @date: 2018年3月3日 下午12:12:44
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs)
    {
    	return StringUtils.isBlank(cs);
    }
    
    /**
     * @Description: 判断一个字符序列不为空（“”），不空，不只有空白。
     * @author: fallsea
     * @date: 2018年3月3日 下午12:13:27
     * @param cs
     * @return
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }
    
    /**
     * @Description: 判断一个字符序列为空（“”），空或只有空白。
     * @author: fallsea
     * @date: 2018年3月3日 下午12:27:05
     * @param obj
     * @return
     */
    public static boolean isBlank(final Object obj)
    {
    	if(null == obj){
    		return true;
    	}
    	if(obj instanceof String){
    		return isBlank(obj.toString());
    	}
    	return false;
    }
    
    /**
     * @Description: 判断一个字符序列不为空（“”），不空，不只有空白。
     * @author: fallsea
     * @date: 2018年3月3日 下午12:26:50
     * @param obj
     * @return
     */
    public static boolean isNotBlank(final Object obj) {
        return !isBlank(obj);
    }
    
    /**
     * @Description: 验证是否邮箱地址
     * @author: fallsea
     * @date: 2017年10月22日 下午8:51:34
     * @param str
     * @return
     */
    public static boolean isEmail(String str)
    {
        String regex="^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return match(regex, str);
    }
    
    /**
     * @Description: 验证是否手机号码
     * @author: fallsea
     * @date: 2017年10月22日 下午8:52:14
     * @param str
     * @return
     */
    public static boolean isMobile(String str)
    {
        String regex="^(13|14|15|17|18)[0-9]{9}$";
        return match(regex, str);
    }
    
    /**
     * @Description: 验证是否字母和数字组成
     * @author: fallsea
     * @date: 2017年10月22日 下午8:52:21
     * @param str
     * @return
     */
    public static boolean isAlphaNumeric(String str)
    {
        String regex="^[A-Za-z0-9]+$";
        return match(regex, str);
    }
    
    /**
     * @Description: 验证是否数字
     * @author: fallsea
     * @date: 2017年10月22日 下午8:52:27
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        String regex="^[0-9]*$";
        return match(regex, str);
    }
    
    /**
     * @Description: 验证是否浮点数
     * @author: fallsea
     * @date: 2017年10月22日 下午8:52:34
     * @param str
     * @return
     */
    public static boolean isNumberFloat(String str)
    {
        String regex="^(-?\\d+)(\\.\\d+)?$";
        return match(regex, str);
    }
    
    
    /**
     * @Description: 验证是否邮编
     * @author: fallsea
     * @date: 2017年10月22日 下午8:52:41
     * @param str
     * @return
     */
    public static boolean isPostalCode(String str)
    {
        String regex="^[1-9]\\d{5}$";
        return match(regex, str);
    }
    
    /**
     * @Description: 验证是否日期格式
     * @author: fallsea
     * @date: 2017年10月22日 下午8:52:48
     * @param str
     * @return
     */
    public static boolean isDate(String str)
    {
        String regex="^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-9]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
        return match(regex, str);
    }
    
    /**
     * @Description: 验证是否时间
     * @author: fallsea
     * @date: 2017年10月22日 下午8:57:55
     * @param str
     * @return
     */
    public static boolean isTime(String str)
    {
        String regex="^((20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d)$";
        return match(regex, str);
    }
    
    /**
     * @Description: 是否为日期+时间型字符串
     * @author: fallsea
     * @date: 2017年10月22日 下午8:57:45
     * @param str
     * @return
     */
    public static boolean isDateTime(String str)
    {
        String regex="^(((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d)$";
        return match(regex, str);
    }
    
    /**
     * @Description: 验证身份证号
     * @author: fallsea
     * @date: 2017年10月22日 下午8:52:55
     * @param str
     * @return
     */
    public static boolean isCardID(String str)
    {
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4","3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7","9", "10", "5", "8", "4", "2" };
        String ai = "";
        if(StringHelper.isBlank(str)){
            return false;
        }
        str=str.toUpperCase();
        // ================ 号码的长度 15位或18位 ================
        if (str.length() != 15 && str.length() != 18) {
            return false;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (str.length() == 18) {
        	ai = str.substring(0, 17);
        } else if (str.length() == 15) {
        	ai = str.substring(0, 6) + "19" + str.substring(6, 15);
        }
        if (!StringHelper.isNumeric(ai)) {
            return false;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = ai.substring(6, 10);// 年份
        String strMonth = ai.substring(10, 12);// 月份
        String strDay = ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                            strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        } catch (ParseException e) {
            return false;
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            return false;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        /*Hashtable<?, ?> h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            return false;
        }*/
        // ==============================================

        // ================ 判断最后一位的值 ================
        int totalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
        	totalmulAiWi = totalmulAiWi
                    + Integer.parseInt(String.valueOf(ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = totalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        ai = ai + strVerifyCode;

        if (str.length() == 18) {
            if (ai.equals(str) == false) {
                return false;
            }
        } 
        // =====================(end)=====================
        return true;
    }
    
    /**
     * @Description: 验证是否url
     * @author: fallsea
     * @date: 2017年10月22日 下午8:53:05
     * @param str
     * @return
     */
    public static boolean isUrl(String str)
    {
        String regex="http(s)?://[^\\s]+";
        return match(regex, str);
    }
    
    /**
     * @Description: 验证银行卡号是否正确
     * @author: fallsea
     * @date: 2017年10月22日 下午8:53:14
     * @param str
     *   	  校验过程： 
     *       1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。 
     *       2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。 
     *       3、将奇数位总和加上偶数位总和，结果应该可以被10整除。     
     * @return
     */
    public static boolean isBankCode(String str)
    {
        if(StringHelper.isBlank(str) ||  str.length() < 15 || str.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(str.substring(0, str.length() - 1));  
        if(bit == 'N'){  
            return false;  
        }  
        return str.charAt(str.length() - 1) == bit;
    }
    
    /**
     * @Description: 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @author: fallsea
     * @date: 2017年10月22日 下午8:53:33
     * @param nonCheckCodeBankCard
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeBankCard){  
        if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0  
                || !nonCheckCodeBankCard.matches("\\d+")) {  
            //如果传的不是数据返回N  
            return 'N';  
        }  
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();  
        int luhmSum = 0;  
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {  
            int k = chs[i] - '0';  
            if(j % 2 == 0) {  
                k *= 2;  
                k = k / 10 + k % 10;  
            }  
            luhmSum += k;             
        }  
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');  
    }  
    
    /**
     * @Description: 正则表达式验证
     * @author: fallsea
     * @date: 2017年10月22日 下午8:53:41
     * @param regex 正则
     * @param str 值
     * @return true 成功 false 失败
     */
    private static boolean match(String regex, String str) 
    {
        if(isEmpty(str))
        {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    
    /**
     * @Description: 获取字符串字节长度
     * @author: fallsea
     * @date: 2017年10月22日 下午8:53:57
     * @param str 字符串
     * @return 字节长度
     */
    public static int length(String str)
    {
        if(isEmpty(str))
        {
            return 0;
        }
        return str.getBytes().length;
    }
    
    /**
     * @Description: 获取uuid
     * @author: fallsea
     * @date: 2017年10月22日 下午8:54:13
     * @return
     */
    public static String getUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /** 
     * @Description: 转换为下划线 
     * @author: fallsea
     * @date: 2018年1月30日 下午9:25:08
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {  
        StringBuilder result = new StringBuilder();  
        if (camelCaseName != null && camelCaseName.length() > 0) {  
            result.append(camelCaseName.substring(0, 1).toLowerCase());  
            for (int i = 1; i < camelCaseName.length(); i++) {  
                char ch = camelCaseName.charAt(i);  
                if (Character.isUpperCase(ch)) {  
                    result.append("_");  
                    result.append(Character.toLowerCase(ch));  
                } else {  
                    result.append(ch);  
                }  
            }  
        }  
        return result.toString();  
    }  
  
    /**
     * @Description: 转换为驼峰 
     * @author: fallsea
     * @date: 2018年1月30日 下午9:25:18
     * @param underscoreName
     * @return
     */
    public static String camelCaseName(String underscoreName) {  
        StringBuilder result = new StringBuilder();  
        if (underscoreName != null && underscoreName.length() > 0) {  
            boolean flag = false;  
            for (int i = 0; i < underscoreName.length(); i++) {  
                char ch = underscoreName.charAt(i);  
                if ("_".charAt(0) == ch) {  
                    flag = true;  
                } else {  
                    if (flag) {  
                        result.append(Character.toUpperCase(ch));  
                        flag = false;  
                    } else {  
                        result.append(ch);  
                    }  
                }  
            }  
        }  
        return result.toString();  
    }
    
    /**
     * @Description: 转换为驼峰 首字母大写
     * @author: fallsea
     * @date: 2018年2月1日 下午9:00:13
     * @param underscoreName
     * @return
     */
    public static String camelCaseName2(String underscoreName) {  
    	String tableCamelCaseName = camelCaseName(underscoreName);
		tableCamelCaseName = tableCamelCaseName.substring(0, 1).toUpperCase() + tableCamelCaseName.substring(1);
		return tableCamelCaseName;
    }
    
    /**配置安全策略文件**/
    private static volatile Policy POLICY = null;
    
    static {
    	try {
			POLICY = Policy.getInstance(FileHelper.getURL("/antisamy-myspace.xml"));
		} catch (PolicyException e) {
			logger.error("获取策略文件失败!",e);
		}
    }
    
    /**
     * @Description: 清理html内容
     * @author: fallsea
     * @date: 2018年4月17日 上午12:01:42
     * @param html
     * @return
     */
    public static String cleanHTML(String html){
    	if(StringHelper.isEmpty(html)){
    		return "";
    	}
    	try {
			AntiSamy as = new AntiSamy();
			CleanResults cr = as.scan(html, POLICY);
			//获取安全的 HTML 输出
			return cr.getCleanHTML();
		} catch (PolicyException e) {
			logger.error("获取策略文件失败!",e);
		} catch (ScanException e) {
			logger.error("清理html失败",e);
		}
    	return "";
    }
    
    /**
     * @Description: 验证用户名
     * @author: fallsea
     * @date: 2018年5月1日 下午9:21:37
     * @param str
     * @return
     */
    public static boolean isAccountNo(String str)
    {
        String regex="^[A-Za-z]{1}[A-Za-z0-9_]{1,20}$";
        return match(regex, str);
    }
  
}
