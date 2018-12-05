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
package com.wueasy.base.bus.server.log;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.wueasy.base.bus.server.service.SysLogService;
import com.wueasy.base.bus.server.util.SysLogUtil;
import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Result;
import com.wueasy.base.util.JsonHelper;
import com.wueasy.base.util.SpringHelper;

/**
 * @Description: 
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年5月2日 上午9:01:22
 */
@Component
public class LogTask {

	
	/**
	 * @Description: 新增日志输出
	 * @author: fallsea
	 * @date: 2017年11月2日 下午9:49:33
	 * @param funcNo 功能号
	 * @param funcName 功能名称
	 * @param version 版本
	 * @param datetime 执行时间
	 * @param ip 来源ip
	 * @param userId 当前操作用户
	 * @param paramMap 入参
	 * @param result 执行结果
	 */
	@Async
	public void add(String funcNo,String funcName,String version,String requestId,Long datetime,String ip,Long userId,DataMap paramMap,Result result,DataMap systemInfoMap) {
		if(SysLogUtil.getNotWriteLog().isEmpty() || !SysLogUtil.getNotWriteLog().contains(funcNo)) {
			SpringHelper.getBean(SysLogService.class).add(funcNo, funcName, version,requestId, datetime,ip, userId, SysLogUtil.logConvert(paramMap), SysLogUtil.logConvert(result), result.getErrorNo(), result.getErrorInfo(),JsonHelper.toJSONString(systemInfoMap));
		}
	}
	
	/**
	 * @Description: 新增日志输出
	 * @author: fallsea
	 * @date: 2018年4月30日 上午9:24:37
	 * @param funcNo 功能号
	 * @param funcName 功能名称
	 * @param version 版本
	 * @param datetime 执行时间
	 * @param ip 来源ip
	 * @param userId 当前操作用户
	 * @param paramMap 入参
	 * @param result 执行结果
	 * @param throwable 异常
	 */
	@Async
	public void add(String funcNo,String funcName,String version,String requestId,Long datetime,String ip,Long userId,DataMap paramMap,Result result,DataMap systemInfoMap,Throwable throwable) {
		if(SysLogUtil.getNotWriteLog().isEmpty() || !SysLogUtil.getNotWriteLog().contains(funcNo)) {
			SpringHelper.getBean(SysLogService.class).add(funcNo, funcName, version,requestId, datetime,ip, userId, SysLogUtil.logConvert(paramMap), null == throwable ? SysLogUtil.logConvert(result) : SysLogUtil.getTrace(throwable) , result.getErrorNo(), result.getErrorInfo(),JsonHelper.toJSONString(systemInfoMap));
		}
	}
}
