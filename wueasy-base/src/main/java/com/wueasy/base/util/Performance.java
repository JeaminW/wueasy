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

/**
 * @Description: 间隔的时间
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:47:41
 */
public class Performance
{
    /**
     * 开始时间
     */
    private long startTime = 0;
    
    /**
     * @Description: 初始化时间
     * @author: fallsea
     * @date: 2017年10月22日 下午8:48:01
     */
    public Performance()
    {
        startTime = System.currentTimeMillis();
    }
    
    /**
     * @Description: 返回间隔的时间(毫秒)
     * @author: fallsea
     * @date: 2017年10月22日 下午8:47:51
     * @return
     */
    public long getTime()
    {
        return System.currentTimeMillis() - startTime;
    }
}
