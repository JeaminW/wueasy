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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wueasy.base.id.SnowflakeIdWorker;

/**
 * @Description: 数据库工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:43:11
 */
public class DBHelper
{
    
    private static Logger logger = LoggerFactory.getLogger(DBHelper.class);
    
    private static volatile Set<Long> WORKER_ID_SET;
    
    private static volatile SnowflakeIdWorker idWorker = null;
    
    /**
     * 分布式id节点
     */
    public static final String SNOWFLAKE_ID_PATH = ZKHelper.ROOT_PATH + "/SnowflakeId";
    
    
    private static volatile long WORKER_ID ;
    
    static
    {
        WORKER_ID_SET = new HashSet<Long>();
        for (long i = 0; i < 1024; i++)
        {
            WORKER_ID_SET.add(i);
        }
        
        //判断节点是否存在，不存在则创建
        if(!ZKHelper.exists(SNOWFLAKE_ID_PATH))
        {
            // 创建一个目录节点
            ZKHelper.createPersistent(SNOWFLAKE_ID_PATH);
        }
        
        WORKER_ID = getWorkerId();
        
        long workerId = WORKER_ID/32;
        long datacenterId = WORKER_ID%32;
        
        idWorker = new SnowflakeIdWorker(workerId, datacenterId);
        
        
        
    }
    
    /**
     * @Description: 获取id
     * @author: fallsea
     * @date: 2017年10月22日 下午8:43:21
     * @return
     */
    public static long getId()
    {
        return idWorker.nextId();
    }
    
    /**
     * @Description: 获取id
     * @author: fallsea
     * @date: 2017年10月22日 下午8:43:28
     * @return
     */
    public static String getIdStr()
    {
        return String.valueOf(idWorker.nextId());
    }
    
    
    /**
     * @Description: 获取id
     * @author: fallsea
     * @date: 2017年10月22日 下午8:43:38
     * @return
     */
    public static BigDecimal getIdByBigDecimal()
    {
        return new BigDecimal(getId());
    }
    
    /**
     * @Description: 获取workerId(0-1023的值)
     * @author: fallsea
     * @date: 2017年10月22日 下午8:43:52
     * @return
     */
    private static Long getWorkerId()
    {
        
        //查询子节点列表
        List<String> list = ZKHelper.getChildren(SNOWFLAKE_ID_PATH);
        if(null != list && !list.isEmpty())
        {
            if(list.size()==1024)
            {
                logger.warn("WorkerId已使用完");
                return -1L;
            }
            for (String str : list)
            {
                WORKER_ID_SET.remove(Long.valueOf(str));
            }
        }
        
        long workerId=-1L;
        
        if(!WORKER_ID_SET.isEmpty())
        {
            Long[] workerIds=WORKER_ID_SET.toArray(new Long[]{});
            workerId=workerIds[new Random().nextInt(WORKER_ID_SET.size())];
            
            //判断系欸但是否存在，如果不存在，则创建
            if(!ZKHelper.exists(SNOWFLAKE_ID_PATH+"/"+workerId))
            {
                ZKHelper.createEphemeral(SNOWFLAKE_ID_PATH+"/"+workerId);
            }
            else
            {
                return getWorkerId();
            }
        }
        return workerId;
    }
    
}
