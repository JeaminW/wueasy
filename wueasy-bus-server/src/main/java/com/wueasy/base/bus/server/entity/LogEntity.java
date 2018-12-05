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

/**
 * @Description: log配置
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年1月23日 下午7:39:19
 */
public class LogEntity
{
    private String sensitiveParam;
    
    private String notWriteFuncNo;
    
    private int maxValueLength = 256;
    
    
    private int writeDb = 1;

	public String getSensitiveParam() {
		return sensitiveParam;
	}

	public void setSensitiveParam(String sensitiveParam) {
		this.sensitiveParam = sensitiveParam;
	}

	public String getNotWriteFuncNo() {
		return notWriteFuncNo;
	}

	public void setNotWriteFuncNo(String notWriteFuncNo) {
		this.notWriteFuncNo = notWriteFuncNo;
	}

	public int getMaxValueLength() {
		if(maxValueLength <= 0){
			maxValueLength = 256;
		}
		return maxValueLength;
	}

	public void setMaxValueLength(int maxValueLength) {
		this.maxValueLength = maxValueLength;
	}

	public int getWriteDb() {
		return writeDb;
	}

	public void setWriteDb(int writeDb) {
		this.writeDb = writeDb;
	}
    
}
