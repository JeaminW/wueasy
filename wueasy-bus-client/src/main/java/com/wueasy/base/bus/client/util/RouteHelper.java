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
package com.wueasy.base.bus.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.wueasy.base.bus.client.config.ClientRouteConfig;
import com.wueasy.base.bus.client.pojo.Route;
import com.wueasy.base.util.SpringHelper;
import com.wueasy.base.util.StringHelper;

/**
 * @Description: 路由工具
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年4月19日 下午8:45:05
 */
public class RouteHelper {
	
	
	private static volatile Map<String,String> routeMap = null;

	
	static
    {
		ClientRouteConfig routeConfig =SpringHelper.getBean("wueasyBusClientRouteConfig", ClientRouteConfig.class);
		if(null!=routeConfig){
			List<Route> list = routeConfig.getRoute();
			if(null!=list && !list.isEmpty()){
				routeMap = new HashMap<String, String>();
				for (Route route : list) {
					String clientId = route.getClientId();
					String role = route.getRule();
					if(StringHelper.isNotEmpty(clientId) && StringHelper.isNotEmpty(role)){
						Splitter.on(',').trimResults().omitEmptyStrings().split(role).forEach((str) ->{
							routeMap.put(str, clientId);
						});
					}
					
				}
			}
			
		}
		
    }
	
	/**
	 * @Description: 是否为空
	 * @author: fallsea
	 * @date: 2018年4月19日 下午9:07:08
	 * @return true为空
	 */
	public static boolean isEmpty(){
		if(routeMap == null || routeMap.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: 获取客户端busid
	 * @author: fallsea
	 * @date: 2018年4月19日 下午9:08:09
	 * @param key
	 * @return
	 */
	public static String getClientId(String key){
		if(!isEmpty()){
			return routeMap.get(key);
		}
		return "";
	}
	
}
