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
package com.wueasy.base.bus.server;

import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Result;
import com.wueasy.base.util.StringHelper;

/**
 * @Description: 基础接口
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:09:45
 */
public abstract class BaseFunction
{
    private DataMap paramMap;
    
    private DataMap systemParamMap;
    
    /**
     * @Description: 抽象方法，由子类复写
     * @author: fallsea
     * @date: 2017年10月22日 下午9:09:50
     * @return
     */
    public abstract Result execute();
    
    
    /**
     * @Description: 获取参数值
     * @author: fallsea
     * @date: 2017年10月22日 下午9:09:59
     * @param fieldName 属性
     * @return
     */
    public String getStrParameter(String fieldName)
    {
        return paramMap.getString(fieldName);
    }
    
    /**
     * @Description: 获取参数值
     * @author: fallsea
     * @date: 2017年10月22日 下午9:10:09
     * @param fieldName 属性
     * @param defaultValue 默认值
     * @return
     */
    public String getStrParameter(String fieldName,String defaultValue)
    {
    	String value = getStrParameter(fieldName);
        return StringHelper.isBlank(value) ? defaultValue : value;
    }
    
    /**
     * @Description: 获取参数值
     * @author: fallsea
     * @date: 2017年10月22日 下午9:10:21
     * @param fieldName 属性
     * @return
     */
    public int getIntParameter(String fieldName)
    {
        return paramMap.getInt(fieldName);
    }
    
    /**
     * @Description: 获取参数值
     * @author: fallsea
     * @date: 2017年10月22日 下午9:10:36
     * @param fieldName 属性
     * @param defaultValue 默认值
     * @return
     */
    public int getIntParameter(String fieldName,int defaultValue)
    {
        return null == getObjectParameter(fieldName) ? defaultValue : getIntParameter(fieldName);
    }
    
    /**
     * @Description: 获取参数值
     * @author: fallsea
     * @date: 2017年10月22日 下午9:11:22
     * @param fieldName
     * @return
     */
    public Object getObjectParameter(String fieldName)
    {
        return paramMap.get(fieldName);
    }
    
    /**
     * @Description: 获取map结果集
     * @author: fallsea
     * @date: 2017年10月22日 下午9:12:12
     * @return
     */
    public DataMap getParamMap()
    {
        return this.paramMap;
    }


	public void setParamMap(DataMap paramMap) {
		this.paramMap = paramMap;
	}


	public DataMap getSystemParamMap() {
		return systemParamMap;
	}


	public void setSystemParamMap(DataMap systemParamMap) {
		this.systemParamMap = systemParamMap;
	}
    
    
    
}
