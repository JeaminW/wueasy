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
package com.wueasy.auto.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wueasy.auto.entity.TableEntity;

/**
 * @Description: table表结构查询
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年1月30日 下午8:52:50
 */
public interface TableMapper {
	
	/**
	 * @Description: 查询table表结构信息
	 * @author: fallsea
	 * @date: 2018年1月30日 下午8:53:12
	 * @param tableName
	 * @return
	 */
	List<TableEntity> query(@Param("tableName")String tableName);
}