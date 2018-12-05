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
package com.wueasy.base.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Description: 结果集
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:29:07
 */
public class Result implements Serializable
{

    private static final long serialVersionUID = -5063527180151987941L;

    private int errorNo;
	
	private String errorInfo;
	
	private Map<String, Object> results = null;
	
	public Result()
    {
	    this.errorNo = 0;
    }
	
	/**
	 * @Description: 
	 * @author: fallsea
	 * @date: 2017年10月22日 下午8:29:29
	 * @param errorNo 错误码
	 * @param errorInfo 错误消息
	 */
    public Result(int errorNo, String errorInfo)
    {
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
    }


    public int getErrorNo()
    {
        return errorNo;
    }

    public void setErrorNo(int errorNo)
    {
        this.errorNo = errorNo;
    }

    public String getErrorInfo()
    {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo)
    {
        this.errorInfo = errorInfo;
    }

    public Map<String, Object> getResults()
    {
        return results;
    }
    
    /**
     * @Description: 获取默认集合
     * @author: fallsea
     * @date: 2017年10月22日 下午8:29:43
     * @return
     */
    @JSONField(serialize=false)
    public Object getResult()
    {
        if( null != this.results)
        {
        	return getResult("data");
        }
        return null;
    }
    
    /**
     * @Description: 获取属性集合
     * @author: fallsea
     * @date: 2017年10月22日 下午8:29:51
     * @param dsName
     * @return
     */
    @JSONField(serialize=false) 
    public Object getResult(String dsName)
    {
        if( null != getResults())
        {
            return getResults().get(dsName);
        }
        return null;
    }
    
    /**
     * @Description: 获取datamap结果集
     * @author: fallsea
     * @date: 2017年10月22日 下午8:30:03
     * @return
     */
    @JSONField(serialize=false) 
    public DataMap getDataMap()
    {
        Object obj = getResult();
        if( null != obj &&  obj instanceof DataMap )
        {
            return (DataMap)obj;
        }
        return null;
    }
    
    /**
     * @Description: 获取datamap结果集
     * @author: fallsea
     * @date: 2017年10月22日 下午8:30:17
     * @param dsName
     * @return
     */
    @JSONField(serialize=false)
    public DataMap getDataMap(String dsName)
    {
        if(null != getResults())
        {
            Object obj = getResults().get(dsName);
            if( null != obj && obj instanceof DataMap )
            {
                return (DataMap)obj;
            }
        }
        return null;
    }
    
    
    /**
     * @Description: 当一个 接口只返回一个结果集时，可调用此方法
     * @author: fallsea
     * @date: 2017年10月22日 下午8:30:28
     * @param object
     */
    public void setResult(Object object)
    {
        this.setResult("data", object);
    }
    
    /**
     * @Description: 多个结果集需使用此方法，前台根据结果集名称获取不同的数据
     * @author: fallsea
     * @date: 2017年10月22日 下午8:30:42
     * @param name
     * @param object
     */
    public void setResult(String name, Object object)
    {
        if(null == this.results)
        {
            this.results = new HashMap<String, Object>();
        }
        this.results.put(name, object);
    }
    

}
