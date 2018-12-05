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

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wueasy.base.util.StringHelper;

/**
 * @Description: 数据集合，继承自HashMap方便对数据存取
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:28:30
 */
public class DataMap extends HashMap<String, Object>
{
    
    private static final long serialVersionUID = 5657127379998521108L;
    
    private static final Logger LOGGER=LoggerFactory.getLogger(DataMap.class);
    
    public void set(String name, String value) 
    {
        put(name, value);
    }

    public void set(String name, Integer value)
    {
        put(name, value);
    }

    public void set(String name, Boolean value) 
    {
        put(name, value);
    }

    public void set(String name, Long value) 
    {
        put(name, value);
    }

    public void set(String name, Float value) 
    {
        put(name, value);
    }

    public void set(String name, Double value) 
    {
        put(name, value);
    }

    public void set(String name, Object value) 
    {
        put(name, value);
    }
    
    public void set(String name, Date value) 
    {
        put(name, value);
    }

    public String getString(String name) 
    {
        return getString(name,"");
    }
    
    public String getString(String name,String defaultValue) 
    {
    	if(StringHelper.isBlank(defaultValue)){
        	defaultValue = "";
        }
        if (StringHelper.isBlank(name))
        {
            return defaultValue;
        }
        
        Object obj = this.get(name);
        return (obj == null) ? defaultValue : obj.toString();
    }

    public Integer getInt(String name) 
    {
    	return getInt(name, null);
    }
    
    public Integer getInt(String name,Integer defaultValue) 
    {
        if (StringHelper.isBlank(name))
        {
            return defaultValue;
        }

        if (containsKey(name) == false)
        {
            return defaultValue;
        }
        Integer value = null;
        Object obj = this.get(name);
        if (obj == null)
        {
            return defaultValue;
        }

        if (!(obj instanceof Integer)) 
        {
            try 
            {
                value = Integer.parseInt(obj.toString());
            }
            catch (NumberFormatException ex) 
            {
                LOGGER.error(name+"对应的值不是int类型", ex);
                value = defaultValue;
            }
        }
        else 
        {
            value = (Integer) obj;
        }
        return value;
    }


    public Long getLong(String name) 
    {
        if (StringHelper.isBlank(name))
        {
            return null;
        }

        Long value = null;
        if (containsKey(name) == false)
        {
            return null;
        }

        Object obj = this.get(name);
        if (obj == null)
        {
            return null;
        }

        if (!(obj instanceof Long)) 
        {
            try 
            {
                value = Long.parseLong(obj.toString());
            }
            catch (NumberFormatException ex)
            {
                LOGGER.error(name+"对应的值不是long类型", ex);
                value = null;
            }
        }
        else 
        {
            value = (Long) obj;
        }
        return value;
    }

    public Float getFloat(String name)
    {
        if (StringHelper.isBlank(name))
        {
            return null;
        }

        Float value = null;
        if (containsKey(name) == false)
        {
            return null;
        }

        Object obj = this.get(name);
        if (obj == null)
        {
            return null;
        }

        if (!(obj instanceof Float)) 
        {
            try 
            {
                value = Float.parseFloat(obj.toString());
            }
            catch (NumberFormatException ex)
            {
                LOGGER.error(name+"对应的值不是float类型", ex);
                value = null;
            }
        } else 
        {
            value = (Float) obj;
        }
        return value;
    }

    public Double getDouble(String name) 
    {
        if (StringHelper.isBlank(name))
        {
            return null;
        }

        Double value = null;
        if (containsKey(name) == false)
        {
            return null;
        }

        Object obj = this.get(name);
        if (obj == null)
        {
            return null;
        }

        if (!(obj instanceof Double)) 
        {
            try 
            {
                value = Double.parseDouble(obj.toString());
            }
            catch (NumberFormatException ex) 
            {
                LOGGER.error(name+"对应的值不是double类型", ex);
                value = null;
            }
        }
        else 
        {
            value = (Double) obj;
        }
        return value;
    }

    public Boolean getBoolean(String name) 
    {
        if (StringHelper.isBlank(name))
        {
            return null;
        }

        if (containsKey(name) == false)
        {
            return null;
        }
        Object obj = this.get(name);
        if (obj == null)
        {
            return null;
        }

        if (obj instanceof Boolean) 
        {
            return (Boolean) obj;
        }

        Boolean value = Boolean.valueOf(obj.toString());
        return value;
    }

    public Date getDate(String name) 
    {
        if (StringHelper.isBlank(name))
        {
            return null;
        }

        if (containsKey(name) == false)
        {
            return null;
        }

        Object obj = this.get(name);
        if (obj == null)
        {
            return null;
        }

        if (!(obj instanceof Date)) 
        {
            LOGGER.error(name+"对应的值不是date类型，return null");
            return null;
        }
        else 
        {
            return (Date)obj;
        }
    }
    
    public Object getObject(String name) 
    {
        if (StringHelper.isBlank(name))
        {
            return null;
        }
        //如果该键不存在，则返回
        if (containsKey(name) == false)
        {
            return null;
        }
        return this.get(name);
    }
}
