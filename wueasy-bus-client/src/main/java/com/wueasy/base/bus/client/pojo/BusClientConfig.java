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
package com.wueasy.base.bus.client.pojo;
/**
 * @Description: 客户端配置
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年8月22日 下午11:23:13
 */
public class BusClientConfig {
	
	// 服务协议
    private String name;
    
    // client最小连接数
    private Integer minClientConnection;
    // client最大连接数
    private Integer maxClientConnection;
    
    
    // 请求超时时间
    private Integer requestTimeout;
    
    /**
     * 分组
     */
    private String group;
    
    /**
     * 应用名称
     */
    protected String application;

    /**
     * 模块名称
     */
    protected String module;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMinClientConnection() {
		return minClientConnection;
	}

	public void setMinClientConnection(Integer minClientConnection) {
		this.minClientConnection = minClientConnection;
	}

	public Integer getMaxClientConnection() {
		return maxClientConnection;
	}

	public void setMaxClientConnection(Integer maxClientConnection) {
		this.maxClientConnection = maxClientConnection;
	}

	public Integer getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(Integer requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
    
    
    

}
