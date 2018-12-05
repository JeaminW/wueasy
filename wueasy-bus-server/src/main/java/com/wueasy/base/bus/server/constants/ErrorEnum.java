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
package com.wueasy.base.bus.server.constants;
/**
 * @Description: 错误码枚举
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年4月20日 下午9:40:21
 */
public enum ErrorEnum {
	
	ERROR(-1000,"系统出现异常"),
	
	FUNC_NO_NOT_BLANK(-1001,"功能号为空"),
	
	FUNC_NO_NOT_EMPTY(-1002,"功能号不存在"),
	
	FUNC_NO_RESOURCE_NOT_EMPTY(-1003,"功能号不存在"),
	
	FUNC_NO_MAPPER_NOT_EMPTY(-1004,"功能号不存在"),
	
	FUNC_NO_ERROR_EXAMPLE(-1005,"操作异常"),
	
	FUNC_NO_CONFIG_ERROR(-1006,"功能号服务配置错误"),
	
	FUNC_NO_SERVICE_NOT_EMPTY(-1007,"功能号服务不存在"),
	
	FUNC_NO_ERROR_ILLEGAL_ACCESS(-1012,"获取实例失败"),
	
	FUNC_NO_ERROR_NO_SUCH_METHOD(-1013,"获取实例失败"),
	
	FUNC_NO_ERROR_SQL(-1014,"数据库异常"),
	
	FUNC_PARAM_BREACH(-1015,"参数违反约束"),
	
	SYSTEM_BUSY(-1016,"系统繁忙,请稍后..."),
	
	FUNC_NO_ERROR_CUSTOM(-2001,"自定义业务异常");
	
	
	private int code;
    
    private String desc;
  
    public int getCode() {  
        return code;  
    }  
  
    public String getDesc() {  
        return desc;  
    }
      
    private ErrorEnum(int code,String desc){  
        this.code = code;  
        this.desc = desc;   
    }  
}
