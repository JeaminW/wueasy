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

import java.util.List;

import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Page;

/**
 * @Description: page工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:47:10
 */
public class PageHelper
{
	
	/**默认页数**/
    public static final int PAGE_NUM = 1;
    
    /**默认分页数量**/
    public static final int PAGE_SIZE = 20;
    
    /**最大每页数量**/
    public static final int MAX_PAGE_SIZE = 10000;
	
    
	/**
	 * @Description: 分页
	 * @author: fallsea
	 * @date: 2017年10月22日 下午8:47:18
	 * @param pageNum 当前页
	 * @param pageSize 每页数量
	 */
    public static void startPage(int pageNum, int pageSize)
    {
        com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
    }
    
    /**
     * @Description: 获取分页信息
     * @author: fallsea
     * @date: 2017年10月22日 下午8:47:30
     * @param list
     * @return
     */
    public static Page getPage(List<?> list)
    {
        Page page = new Page();
        if(null != list)
        {
            com.github.pagehelper.Page<?> page2=(com.github.pagehelper.Page<?>)list;
            page.setList(list);
            page.setTotal(page2.getTotal());
            page.setPageNum(page2.getPageNum());
            page.setPages(page2.getPages());
            page.setPageSize(page2.getPageSize());
        }
        return page;
    }
    
    /**
     * @Description: 获取当前页
     * @author: fallsea
     * @date: 2018年3月3日 下午12:33:35
     * @param paramMap
     * @return
     */
    public static int getPageNum(DataMap paramMap){
    	int pageNum = paramMap.getInt("pageNum",PAGE_NUM);
    	if(pageNum<1){
    		pageNum = PAGE_NUM;
    	}
    	return pageNum;
    }
    
    /**
     * @Description: 获取每页数量
     * @author: fallsea
     * @date: 2018年3月3日 下午12:33:51
     * @param paramMap
     * @return
     */
    public static int getPageSize(DataMap paramMap){
    	int pageSize = paramMap.getInt("pageSize",PAGE_SIZE);
    	if(pageSize<1){
    		pageSize = PAGE_SIZE;
    	}else if(pageSize > MAX_PAGE_SIZE){
    		pageSize = MAX_PAGE_SIZE;
    	}
    	return pageSize;
    }
    
}
