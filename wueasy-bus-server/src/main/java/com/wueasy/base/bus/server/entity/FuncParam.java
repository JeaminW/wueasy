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
package com.wueasy.base.bus.server.entity;

import java.io.Serializable;

/**
 * @Description: 功能号参数
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:14:35
 */
public class FuncParam implements Serializable
{
    
    private static final long serialVersionUID = -3579539693082262480L;

    /**
     * 参数id
     */
    private String id;
    
    /**
     * 参数名称
     */
    private String name;
    
    /**
     * 是否非空
     */
    private String notEmpty;
    
    /**
     * 验证类型
     */
    private String verifyType;
    
    /**
     * 转换类型
     */
    private String convertType;
    
    /**
     * 最大长度，0 不限制
     */
    private int maxLength;
    
    /**
     * 最小长度，0不限制
     */
    private int minLength;
    
    
    /**
     * value 值
     */
    private String value;
    
    /**
     * 默认值
     */
    private String defaultValue;
    

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

   
    public String getNotEmpty()
    {
        return notEmpty;
    }

    public void setNotEmpty(String notEmpty)
    {
        this.notEmpty = notEmpty;
    }

    public String getVerifyType()
    {
        return verifyType;
    }

    public void setVerifyType(String verifyType)
    {
        this.verifyType = verifyType;
    }

    public int getMaxLength()
    {
        return maxLength;
    }

    public void setMaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }

    public int getMinLength()
    {
        return minLength;
    }

    public void setMinLength(int minLength)
    {
        this.minLength = minLength;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

	public String getConvertType() {
		return convertType;
	}

	public void setConvertType(String convertType) {
		this.convertType = convertType;
	}
    
}
