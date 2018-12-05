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
package com.wueasy.base.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wueasy.base.util.StringHelper;

/**
 * @Description: 
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年8月31日 下午6:46:50
 */
@MappedJdbcTypes({JdbcType.DOUBLE})
@MappedTypes({Object.class})
public class DoubleTypeHandler extends BaseTypeHandler<Object> {
	
	private static Logger logger = LoggerFactory.getLogger(DoubleTypeHandler.class);

	@Override
	public Object getNullableResult(ResultSet resultSet, String arg1) throws SQLException {
		return resultSet.getDouble(arg1);
	}

	@Override
	public Object getNullableResult(ResultSet resultSet, int arg1) throws SQLException {
		return resultSet.getDouble(arg1);
	}

	@Override
	public Object getNullableResult(CallableStatement callableStatement, int arg1) throws SQLException {
		 return callableStatement.getDouble(arg1);
	}

	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int arg1, Object arg2, JdbcType arg3) throws SQLException {
		Double value = null;
		if(StringHelper.isNotEmpty(arg2)){
			if(arg2 instanceof Double){
				value = (Double)arg2;
			}else if(arg2 instanceof String){
				try {
					value = Double.parseDouble(arg2.toString());
				} catch (NumberFormatException e) {
					logger.error("转换失败!返回null",e);
					value = null;
				}
			}
		}
		if(null==value) {
			preparedStatement.setNull(arg1, Types.DOUBLE);
		}else {
			preparedStatement.setDouble(arg1, value);
		}
	}
	

}
