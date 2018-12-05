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

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.wueasy.base.entity.DataMap;

/**
 * @Description: 系统信息
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年5月3日 上午12:33:01
 */
@Component
public class SystemHelper {
	
	private static Logger logger = LoggerFactory.getLogger(SystemHelper.class);
	
	
	public static final String ATTRIBUTE_SERVER_PID = "_serverIp";
	
	public static final String ATTRIBUTE_SERVER_NAME = "_serverName";
	
	public static final String ATTRIBUTE_SERVER_IP = "_serverPid";
	
	public static final String ATTRIBUTE_APPLICATION_NAME = "_applicationName";
	
	/**服务器ip**/
	private static String SERVER_PID = "";
	
	/**服务器名称**/
	private static String SERVER_NAME = "";
	
	/**进程id**/
	private static String SERVER_IP = "";
	
	/**应用名称**/
	private static String APPLICATION_NAME = "";
	/**
	 * 本机ip
	 */
	private static String LOCAL_IP = "";
	
	
	static
	{
		try {
			InetAddress localAddress = InetAddress.getLocalHost();
			SERVER_NAME = localAddress.getHostName();
			SERVER_IP = getLocalIP();
		} catch (UnknownHostException e) {
			logger.error("",e);
		}
		String name = ManagementFactory.getRuntimeMXBean().getName();
		SERVER_PID = name.split("@")[0];
		//获取应用名称
		RelaxedPropertyResolver springPropertyResolver = new RelaxedPropertyResolver(SpringHelper.getBean(Environment.class), "spring.application.");
		APPLICATION_NAME = springPropertyResolver.getProperty("name");
		
		//获取配置ip信息
		springPropertyResolver = new RelaxedPropertyResolver(SpringHelper.getBean(Environment.class), "wueasy.");
		LOCAL_IP = springPropertyResolver.getProperty("localIp");
		
		if(StringHelper.isEmpty(LOCAL_IP)){
			LOCAL_IP = getLocalIp();
		}
	}
	
	/**
	 * @Description: 获取本机ip地址
	 * @author: fallsea
	 * @date: 2018年5月6日 下午4:30:38
	 * @return
	 */
	public static String getLocalIP() {
        return LOCAL_IP;
    }
	
	private static String getLocalIp(){
		String localIP = "127.0.0.1";
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                InetAddress ip = ni.getInetAddresses().nextElement();
                if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                    localIP = ip.getHostAddress();
                    break;
                }
            }
        } catch (Exception e) {
            try {
                localIP = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e1) {
            	logger.error("",e);
            }
        }
        return localIP;
	}

	public static String getServerIp(){
		return SERVER_IP;
	}
	
	public static String getServerPid(){
		return SERVER_PID;
	}
	
	public static String getServerName(){
		return SERVER_NAME;
	}
	
	public static String getApplicationName(){
		return APPLICATION_NAME;
	}
	
	/**
	 * @Description: 获取调用系统信息
	 * @author: fallsea
	 * @date: 2018年5月5日 下午4:05:23
	 * @param systemParamMap
	 * @return
	 */
	public static DataMap getSystemInfo(DataMap systemParamMap){
		//当前系统信息
		DataMap dataMap = new DataMap();
		dataMap.set("serverIp",SERVER_IP);
		dataMap.set("serverName",SERVER_NAME);
		dataMap.set("serverPid",SERVER_PID);
		dataMap.set("applicationName",APPLICATION_NAME);
		//上个系统信息
		
		dataMap.set("requestServerIp",systemParamMap.getString(ATTRIBUTE_SERVER_IP));
		dataMap.set("requestServerName",systemParamMap.getString(ATTRIBUTE_SERVER_NAME));
		dataMap.set("requestServerPid",systemParamMap.getString(ATTRIBUTE_SERVER_PID));
		dataMap.set("requestApplicationName",systemParamMap.getString(ATTRIBUTE_APPLICATION_NAME));
		return dataMap;
	}
}
