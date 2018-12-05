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
package com.wueasy.base.bus.server.pojo;
/**
 * @Description: 
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年8月22日 下午10:35:04
 */
public class BusServerConfig {
	
	/**
	 * 最小工作pool线程数
	 */
    private Integer minWorkerThread;
    /**
     * 最大工作pool线程数
     */
    private Integer maxWorkerThread;
    /**
     * 请求响应包的最大长度限制
     */
    private Integer maxContentLength;
    /**
     * server支持的最大连接数
     */
    private Integer maxServerConnection;
    
    /**
     * 端口
     */
    private Integer port;
    
    /**
     * 分组
     */
    private String group;
    
    /**
     * 应用名称
     */
    private String application;

    /**
     * 模块名称
     */
    private String module;

	public Integer getMinWorkerThread() {
		return minWorkerThread;
	}

	public void setMinWorkerThread(Integer minWorkerThread) {
		this.minWorkerThread = minWorkerThread;
	}

	public Integer getMaxWorkerThread() {
		return maxWorkerThread;
	}

	public void setMaxWorkerThread(Integer maxWorkerThread) {
		this.maxWorkerThread = maxWorkerThread;
	}

	public Integer getMaxContentLength() {
		return maxContentLength;
	}

	public void setMaxContentLength(Integer maxContentLength) {
		this.maxContentLength = maxContentLength;
	}

	public Integer getMaxServerConnection() {
		return maxServerConnection;
	}

	public void setMaxServerConnection(Integer maxServerConnection) {
		this.maxServerConnection = maxServerConnection;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
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
