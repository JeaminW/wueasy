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
package com.wueasy.base.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 分页bean
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:28:53
 */
public class Page<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**第几页**/
    private int pageNum;
    
    /**每页数量**/
    private int pageSize;
    
    /**总数**/
    private long total;
    
    /**总分页数**/
    private int pages;
    
    private List<T> list;
    
    public Page(){
    	
    }
    
    public Page(int pageNum, int pageSize, List<T> list){
        if (list == null || list.isEmpty()){
            return;
        }

        //总记录条数
        this.pages = list.size();
        //每页显示多少条记录
        this.pageSize = pageSize;
        //获取总页数
        this.total = this.pages / this.pageSize;
        if (this.pages % this.pageSize != 0) {
            this.total += 1;
        }

        //当前第几页数据
        this.pageNum = this.total < (long)pageNum ? (int)this.total : pageNum;

        //起始索引
        int fromIndex = this.pageSize * (this.pageNum - 1);
        //结束索引
        int toIndex =this.pageSize * this.pageNum > this.pages ?  this.pages :  this.pageSize * this.pageNum;

        this.list = list.subList(fromIndex, toIndex);
    }

    public int getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public int getPages()
    {
        return pages;
    }

    public void setPages(int pages)
    {
        this.pages = pages;
    }

    public List<T> getList()
    {
        return list;
    }

    public void setList(List<T> list)
    {
        this.list = list;
    }
    
}
