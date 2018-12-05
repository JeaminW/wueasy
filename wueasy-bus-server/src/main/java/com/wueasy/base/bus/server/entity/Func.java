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
import java.util.List;

/**
 * @Description: 功能号
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:14:04
 */
public class Func implements Serializable
{
    private static final long serialVersionUID = 3645804230359183186L;

    /**
     * 功能编号
     */
    private String funcNo;
    
    /**
     * 类型 c:执行class,s:执行service,m:执行mapper
     */
    private String type;
    
    /**
     * 是否启用分页功能，1：启用
     */
    private String isPage;
    
    
    /**
     * 资源实现类
     */
    private String resourceClass;
    
    /**
     * 资源实现类方法
     */
    private String resourceMethod;
    
    /**
     * 描述
     */
    private String desc;
    
    /**
     * 流量控制
     */
    private int qps;
    
    /**
     * 命名空间
     */
    private String namespace;
    
    private List<FuncParam> funcParamList;
    
    public String getFuncNo()
    {
        return funcNo;
    }

    public void setFuncNo(String funcNo)
    {
        this.funcNo = funcNo;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public List<FuncParam> getFuncParamList()
    {
        return funcParamList;
    }


    public void setFuncParamList(List<FuncParam> funcParamList)
    {
        this.funcParamList = funcParamList;
    }
    
    
    public String getType()
    {
        return type;
    }


    public void setType(String type)
    {
        this.type = type;
    }


	public String getIsPage() {
		return isPage;
	}


	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}


	public String getResourceClass() {
		return resourceClass;
	}

	public void setResourceClass(String resourceClass) {
		this.resourceClass = resourceClass;
	}


	public String getResourceMethod() {
		return resourceMethod;
	}


	public void setResourceMethod(String resourceMethod) {
		this.resourceMethod = resourceMethod;
	}

	public int getQps() {
		return qps;
	}

	public void setQps(int qps) {
		this.qps = qps;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
