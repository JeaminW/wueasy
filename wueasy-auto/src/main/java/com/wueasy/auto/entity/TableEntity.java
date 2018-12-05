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
package com.wueasy.auto.entity;

import com.wueasy.base.util.StringHelper;

/**
 * @Description: 
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年1月30日 下午8:50:52
 */
public class TableEntity {
	
	private String columnName;
	
	private String dataType;
	
	private String columnType;
	
	private String columnComment;
	
	private String columnKey;
	
	private String extra;

	public String getColumnName() {
		return columnName;
	}
	
	/**
	 * 
	 * @Description: 驼峰命名
	 * @author: fallsea
	 * @date: 2018年1月30日 下午9:59:24
	 * @return
	 */
	public String getCamelCaseColumnName() {
		return StringHelper.camelCaseName(columnName);
	}
	
	/**
	 * @Description: 获取数据库转换类型
	 * @author: fallsea
	 * @date: 2018年9月16日 上午9:13:45
	 * @return
	 */
	public String getTypeHandler(){
		
		String typeHandler = null;
		switch (getType()) {
			case "Long":
				typeHandler = "LongTypeHandler";
				break;
			case "Integer":
				typeHandler = "IntegerTypeHandler";
				break;
			case "Double":
				typeHandler = "DoubleTypeHandler";
				break;
			case "Float":
				typeHandler = "FloatTypeHandler";
				break;
			default:
				break;
		}
		return typeHandler;
	}

	/**
	 * @Description: 类型
	 * @author: fallsea
	 * @date: 2018年2月1日 下午9:15:28
	 * @return
	 */
	public String getType(){
		String type = "String";
		
		switch (getDataType()) {
			case "datetime":
			case "date":
				type = "Date";
				break;
			case "bigint":
				type = "Long";
				break;
			case "int":
			case "tinyint":
				type = "Integer";
				break;
			case "double":
				type = "Double";
				break;
			case "float":
				type = "Float";
				break;
			default:
				type =  "String";
				break;
		}
		return type;
	}
	
	/**
	 * 
	 * @Description: 
	 * @author: fallsea
	 * @date: 2018年1月30日 下午9:59:20
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	
}
