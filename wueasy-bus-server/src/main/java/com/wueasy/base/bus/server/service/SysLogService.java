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
package com.wueasy.base.bus.server.service;

import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Page;

/**
 * @Description: 系统日志服务接口
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年11月2日 下午10:11:36
 */
public interface SysLogService {

	
	/**
	 * @Description: 新增日志输出
	 * @author: fallsea
	 * @date: 2017年11月2日 下午11:50:05
	 * @param funcNo 功能号
	 * @param funcName 功能名称
	 * @param version 版本
	 * @param datetime 执行时间
	 * @param ip 来源ip
	 * @param userId 当前操作用户id
	 * @param inParam 入参
	 * @param outParam 出参
	 * @param errorNo 错误码
	 * @param errorInfo 错误消息
	 */
	void add(String funcNo,String funcName,String version,String requestId,Long datetime,String ip,Long userId,String inParam,String outParam,int errorNo,String errorInfo,String systemInfo);
	
	/**
	 * @Description: 查询分页信息
	 * @author: fallsea
	 * @date: 2017年11月3日 上午9:41:21
	 * @param paramMap
	 * @return
	 */
	Page queryPage(DataMap paramMap);
	
}
