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
package com.wueasy.base.bus.server.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Splitter;
import com.wueasy.base.bus.server.entity.LogEntity;
import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Result;
import com.wueasy.base.util.JsonHelper;
import com.wueasy.base.util.SpringHelper;
import com.wueasy.base.util.StringHelper;

/**
 * @Description: 系统日志工具
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年11月2日 下午9:42:04
 */
public class SysLogUtil {

	/**
	 * 敏感属性集合，敏感属性值加密输出到日志
	 */
	private static Set<String> sensitive = new HashSet<String>();
	
	/**
	 * 不写日志的功能号集合
	 */
	private static Set<String> NOT_WRITE_LOG = new HashSet<String>();
	
	/**
	 * 最大值长度限制
	 */
	private static volatile int MAX_VALUE_LENGTH = 256;
	
	/**
	 * 是否输入db
	 */
	public static volatile int IS_WRITE_DB = 1;
	
	static {
		
		LogEntity logEntity = SpringHelper.getBean("wueasyLogConfig", LogEntity.class);
		
		MAX_VALUE_LENGTH = logEntity.getMaxValueLength();
		
		String sensitiveParam = logEntity.getSensitiveParam();
		if(StringHelper.isNotBlank(sensitiveParam)){
			sensitive.addAll(Splitter.on(",").trimResults().omitEmptyStrings().splitToList(sensitiveParam));
		}
		
		String notWriteFuncNo = logEntity.getNotWriteFuncNo();
		if(StringHelper.isNotBlank(notWriteFuncNo)){
			NOT_WRITE_LOG.addAll(Splitter.on(",").trimResults().omitEmptyStrings().splitToList(notWriteFuncNo));
		}
		
		IS_WRITE_DB = logEntity.getWriteDb();
	}
	
	private SysLogUtil() {
		
	}
	
	
	public static Set<String> getNotWriteLog(){
		return NOT_WRITE_LOG;
	}
	
	/**
	 * @Description: 获取异常内容
	 * @author: fallsea
	 * @date: 2018年4月30日 上午9:29:21
	 * @param throwable
	 * @return
	 */
	public static String getTrace(Throwable throwable) {  
		if(throwable==null){
			return "";
		}
        StringWriter stringWriter = new StringWriter();  
        PrintWriter writer = new PrintWriter(stringWriter);  
        throwable.printStackTrace(writer);  
        StringBuffer buffer = stringWriter.getBuffer();  
        return buffer.toString();  
    }
	
	/**
	 * @Description: 转换log输出值
	 * @author: fallsea
	 * @date: 2017年11月2日 下午11:26:57
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String logConvert(Object obj) {
		
		if(null != obj) {
			
			if(obj instanceof DataMap) {// 入参，处理map参数
				DataMap paramMap = (DataMap)obj;
				if(null!=paramMap && !paramMap.isEmpty()) {
					StringBuilder sb=new StringBuilder();
					for (Map.Entry<String,Object> entry : paramMap.entrySet()) {
					    String key = entry.getKey();
					    Object value = entry.getValue();
					    if(StringHelper.isNotBlank(value)) {
					    	
					    	if(sensitive.contains(key)) {
						    	value = "******";
						    }
					    	
						    //长度控制
						    if(String.valueOf(value).length() > MAX_VALUE_LENGTH) {
						    	value = String.valueOf(value).substring(0, MAX_VALUE_LENGTH) +"...";
						    }
					    }
					    
					    sb.append(key).append(":").append(value).append(",");
					}
					sb.deleteCharAt(sb.length()-1);
					return sb.toString();
				}
			}else if(obj instanceof Map) {// 入参，处理map参数
				Map<String,Object> paramMap = (Map<String,Object>)obj;
				if(null!=paramMap && !paramMap.isEmpty()) {
					StringBuilder sb=new StringBuilder();
					for (Map.Entry<String,Object> entry : paramMap.entrySet()) {
					    String key = entry.getKey();
					    Object value = entry.getValue();
					    if(StringHelper.isNotBlank(value)) {
					    	
					    	if(sensitive.contains(key)) {
						    	value = "******";
						    }
					    	
						    //长度控制
						    if(String.valueOf(value).length() > MAX_VALUE_LENGTH) {
						    	value = String.valueOf(value).substring(0, MAX_VALUE_LENGTH) +"...";
						    }
					    }
					    
					    sb.append(key).append(":").append(value).append(",");
					}
					sb.deleteCharAt(sb.length()-1);
					return sb.toString();
				}
			}else if(obj instanceof Result) {//结果集处理
				
				Result result = (Result)obj;
				Map<String, Object> resultMap = result.getResults();
				if(null!=resultMap && !resultMap.isEmpty()) {
					
					StringBuilder sb=new StringBuilder();
					for (Map.Entry<String,Object> entry : resultMap.entrySet()) {
					    String key = entry.getKey();
					    Object object = entry.getValue();
					    String value = null;
					    if(null!=object) {
					    	value = JsonHelper.toJSONString(object);
						    //长度控制
						    if(value.length() > MAX_VALUE_LENGTH) {
						    	value = value.substring(0, MAX_VALUE_LENGTH) +"...";
						    }
					    }
					    
					    sb.append(key).append(":").append(value).append(",");
					}
					sb.deleteCharAt(sb.length()-1);
					return sb.toString();
				}
			}
		}
		return "";
	}
	
}
