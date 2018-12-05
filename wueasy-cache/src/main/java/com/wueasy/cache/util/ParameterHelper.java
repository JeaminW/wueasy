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
package com.wueasy.cache.util;

import java.util.Map;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wueasy.base.util.StringHelper;
import com.wueasy.base.util.ZKHelper;
import com.wueasy.cache.constants.CacheConstants;

/**
 * @Description: 系统参数获取工具
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年4月11日 下午9:33:53
 */
public class ParameterHelper {
	
	private static Logger logger = LoggerFactory.getLogger(ParameterHelper.class);

	private static volatile Map<String,String> parameter = null;
	
	/**
	 * @Description: 获取系统参数值
	 * @author: fallsea
	 * @date: 2018年4月11日 下午10:17:41
	 * @param name 名称（类型编号.属性名称）
	 * @return
	 */
    public static String getString(String name)
    {
        return getString(name,"");
    }
    
    /**
     * @Description: 获取系统参数值
     * @author: fallsea
     * @date: 2018年4月11日 下午10:17:52
     * @param name 名称（类型编号.属性名称）
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(String name,String defaultValue)
    {
        String value=getAttribute(name);
        if(StringHelper.isNotEmpty(value)){
            return value;
        }
        return defaultValue;
    }
    
    /**
     * @Description: 获取系统参数值
     * @author: fallsea
     * @date: 2018年4月11日 下午10:18:06
     * @param name 名称（类型编号.属性名称）
     * @return
     */
    public static int getInt(String name)
    {
        return getInt(name, 0);
    }
    
    /**
     * @Description: 获取系统参数值
     * @author: fallsea
     * @date: 2018年4月11日 下午10:18:17
     * @param name 名称（类型编号.属性名称）
     * @param defaultValue 默认值
     * @return
     */
    public static int getInt(String name,int defaultValue)
    {
        String value=getAttribute(name);
        if(StringHelper.isEmpty(value)){
            logger.debug("系统参数key[" + name + "]未配置，默认返回： "+defaultValue);
            return defaultValue;
        }
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex)
        {
            logger.warn("系统参数key[" + name + "]配置错误，默认返回： "+defaultValue);
        }
        return defaultValue;
    }
    
    /**
     * @Description: 获取属性值
     * @author: fallsea
     * @date: 2018年4月11日 下午10:18:35
     * @param name
     * @return
     */
    private static String getAttribute(String name)
    {
    	String value = "";
    	if(null!=parameter && !parameter.isEmpty()){
    		value = parameter.get(name);
    	}
        return value;
    }
	
	static {
		//初始化从zk中获取数据
		
		ZkClient zkClient = ZKHelper.getInstance();
		
		if(!ZKHelper.exists(CacheConstants.PARAMETER_CACHE_NODE_PATH)){
			zkClient.createPersistent(CacheConstants.PARAMETER_CACHE_NODE_PATH,true);
		}
		
		String jsonStr = zkClient.readData(CacheConstants.PARAMETER_CACHE_NODE_PATH);
		if(StringHelper.isNotEmpty(jsonStr)){
			parameter = JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>(){});
		}
		
		zkClient.subscribeDataChanges(CacheConstants.PARAMETER_CACHE_NODE_PATH, new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				parameter = JSON.parseObject(data.toString(), new TypeReference<Map<String, String>>(){});
			}
		});
		
	}
	
	
}
