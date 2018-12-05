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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description: json工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:44:57
 */
public class JsonHelper
{
    private static final Logger LOGGER=LoggerFactory.getLogger(JsonHelper.class);
    
    /**
     * @Description: 将对象转化为json字串 
     * @author: fallsea
     * @date: 2017年10月22日 下午8:45:04
     * @param obj
     * @return
     */
    public static String toJSONString(Object obj)
    {
        String result = "";
        try
        {
            result = JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteNonStringValueAsString);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
        return result;
    }
    
    /**
     * @Description: 将字串反序列化为对象
     * @author: fallsea
     * @date: 2017年10月22日 下午8:45:14
     * @param jsonStr
     * @return
     */
    public static Object parseObject(String jsonStr)
    {
        Object obj = null;
        try
        {
            obj = JSON.parseObject(jsonStr,Object.class);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
        return obj;
    }
    
    /**
     * @Description: json返回指定类型对象
     * @author: fallsea
     * @date: 2017年10月22日 下午8:45:23
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz)
    {
        
        T obj = null;
        try
        {
            obj = JSON.parseObject(jsonStr,clazz);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
        return obj;
    }
}
