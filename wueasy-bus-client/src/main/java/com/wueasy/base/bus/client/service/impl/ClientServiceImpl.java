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
package com.wueasy.base.bus.client.service.impl;

import org.springframework.stereotype.Service;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.weibo.api.motan.rpc.ResponseFuture;
import com.wueasy.base.bus.FunctionAsync;
import com.wueasy.base.bus.client.service.ClientService;
import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Result;

/**
 * @Description: 客户端接口实现
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:04:03
 */
@Service("ClientService")
public class ClientServiceImpl implements ClientService
{
    
    @MotanReferer(basicReferer = "wueasyClientBasicConfig")
    private FunctionAsync function;


    @Override
    public Result invoke(String funcNo, String version, DataMap paramMap, DataMap systemParamMap)
    {
        return function.invoke(funcNo, version, paramMap, systemParamMap);
    }


    @Override
    public ResponseFuture invokeAsync(String funcNo, String version, DataMap paramMap,DataMap systemParamMap)
    {
        return function.invokeAsync(funcNo, version, paramMap, systemParamMap);
    }
    
}
