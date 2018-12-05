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
package com.wueasy.base.exception;

/**
 * @Description: 自定义异常
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:31:15
 */
public class InvokeException extends RuntimeException
{
    private static final long serialVersionUID = -8449990964438816749L;
    
    
    private int errorCode;
    
    public int getErrorCode()
    {
        return errorCode;
    }
    
    public InvokeException(int errorCode)
    {
        super();
        this.errorCode = errorCode;
    }
    
    
    public InvokeException(int errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }
    
    public InvokeException(int errorCode, String message, Throwable cause)
    {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
