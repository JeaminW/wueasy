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

import org.apache.commons.text.RandomStringGenerator;

/**
 * @Description: 随机数工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:48:20
 */
public class RandomHelper
{
	/**
	 * @Description: 生成随机数字
	 * @author: fallsea
	 * @date: 2017年10月22日 下午8:48:24
	 * @param count
	 * @return
	 */
    public static String randomNumeric(int count)
    {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', '9').build();
        return generator.generate(count);
    }
    
    /**
     * @Description: 生成随机字母
     * @author: fallsea
     * @date: 2017年10月22日 下午8:48:35
     * @param count
     * @return
     */
    public static String randomAlphabetic(int count)
    {
    	char [][] pairs = {{'a','z'},{'A','Z'}};
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).build();
        return generator.generate(count);
    }
    
    /**
     * @Description: 生成随机字符串
     * @author: fallsea
     * @date: 2017年10月22日 下午8:48:41
     * @param count
     * @return
     */
    public static String randomStr(int count)
    {
    	char [][] pairs = {{'a','z'},{'0','9'},{'A','Z'}};
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).build();
        return generator.generate(count);
    }
    
}
