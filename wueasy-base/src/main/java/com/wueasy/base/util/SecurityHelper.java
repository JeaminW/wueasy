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

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description: 加密工具
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:48:51
 */
public class SecurityHelper
{
    
	/**
	 * @Description: md5加密字符串
	 * @author: fallsea
	 * @date: 2017年10月22日 下午8:49:09
	 * @param str
	 * @return
	 */
    public static String getMD5Str(String str)
    {
        return DigestUtils.md5Hex(str);
    }
    
    /**
     * @Description: SHA512加密字符串
     * @author: fallsea
     * @date: 2017年10月22日 下午8:49:17
     * @param str
     * @return
     */
    public static String getSHA512Str(String str)
    {
        return DigestUtils.sha512Hex(str);
    }
    
    /**
     * @Description: SHA256加密字符串
     * @author: fallsea
     * @date: 2017年10月22日 下午8:49:29
     * @param str
     * @return
     */
    public static String getSHA256Str(String str)
    {
        return DigestUtils.sha256Hex(str);
    }
    
}
