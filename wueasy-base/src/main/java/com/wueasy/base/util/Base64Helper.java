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

import org.springframework.util.Base64Utils;

/**
 * @Description: bas64工具
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:36:19
 */
public class Base64Helper
{
	
	private Base64Helper() {
		
	}
	
	/**
	 * @Description: 对给定的字符串进行base64解码操作
	 * @author: fallsea
	 * @date: 2017年10月22日 下午8:36:40
	 * @param str
	 * @return
	 */
    public static String decode(String str) {
        if(StringHelper.isBlank(str))
        {
            return "";
        }
        return new String(Base64Utils.decodeFromString(str));
    }
    
    /**
     * @Description: 对给定的字符串进行base64解码操作
     * @author: fallsea
     * @date: 2017年10月22日 下午8:36:49
     * @param str
     * @return
     */
    public static byte[] decodeByte(String str) {
        if(StringHelper.isBlank(str))
        {
            return null;
        }
        return Base64Utils.decode(str.getBytes());
    }
    
    /**
     * @Description: 对给定的字符串进行base64加密操作
     * @author: fallsea
     * @date: 2017年10月22日 下午8:36:58
     * @param str
     * @return
     */
    public static String encode(String str) {
        if(StringHelper.isBlank(str))
        {
            return "";
        }
        return Base64Utils.encodeToString(str.getBytes());
    }
    
    /**
     * @Description: 对给定的byte数组进行base64加密操作
     * @author: fallsea
     * @date: 2017年10月22日 下午8:37:05
     * @param binaryData
     * @return
     */
    public static String encode(byte[] binaryData) {
        if(null==binaryData)
        {
            return "";
        }
        return Base64Utils.encodeToString(binaryData);
    }
    
}
