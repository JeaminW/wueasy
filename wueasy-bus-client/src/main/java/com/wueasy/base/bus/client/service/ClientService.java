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
package com.wueasy.base.bus.client.service;

import com.weibo.api.motan.rpc.ResponseFuture;
import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Result;

/**
 * @Description: 客户端接口
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:02:55
 */
public interface ClientService
{
    
	/**
	 * @Description: 调用功能号
	 * @author: fallsea
	 * @date: 2017年10月22日 下午9:03:11
	 * @param funcNo 功能编号
	 * @param version 版本号,默认1.0
	 * @param paramMap 业务参数
	 * @param systemParamMap 系统参数
	 * @return
	 */
    Result invoke(String funcNo,String version,DataMap paramMap,DataMap systemParamMap);
    
    /**
     * @Description: 调用功能号异步接口
     * @author: fallsea
     * @date: 2017年10月22日 下午9:03:35
     * @param funcNo 功能编号
     * @param version 版本号,默认1.0
     * @param paramMap 业务参数
     * @param systemParamMap 系统参数
     * @return
     */
    ResponseFuture invokeAsync(String funcNo, String version, DataMap paramMap,DataMap systemParamMap);
    
}
