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

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.wueasy.base.config.ZKConfig;

/**
 * @Description: ZK工具类，zk实例
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:54:35
 */
public class ZKHelper
{
    
    private static volatile ZkClient zkClient;
    
    /**
     * 根节点
     */
    public static final String ROOT_PATH = "/wueasy";
    
    
    static
    {
        ZKConfig zkConfig=SpringHelper.getBean("wueasyZkConfig", ZKConfig.class);
        if(zkClient == null)
        {
            zkClient = new ZkClient(zkConfig.getServers(),zkConfig.getSessionTimeout(),zkConfig.getConnectionTimeout());
            
            zkClient.subscribeStateChanges(new IZkStateListener()
            {
                
                @Override
                public void handleStateChanged(KeeperState state) throws Exception
                {
                    if(KeeperState.SyncConnected == state)
                    {
                        //连接成功
                        
                    }
                }
                
                @Override
                public void handleSessionEstablishmentError(Throwable error) throws Exception
                {
                    
                }
                
                @Override
                public void handleNewSession() throws Exception
                {
                    
                }
            });
            
        }
    }
    
    /**
     * @Description: 获取zk实例
     * @author: fallsea
     * @date: 2017年10月22日 下午8:54:42
     * @return
     */
    public static ZkClient getInstance()
    {
        return zkClient;
    }
    
    /**
     * @Description: 查询子节点列表
     * @author: fallsea
     * @date: 2017年10月22日 下午8:54:52
     * @param path 节点路径
     * @return
     */
    public static List<String> getChildren(String path)
    {
        return zkClient.getChildren(path);
    }
    
    /**
     * @Description:判断节点是否存在 
     * @author: fallsea
     * @date: 2017年10月22日 下午8:55:08
     * @param path
     * @return true 存在
     */
    public static boolean exists(String path)
    {
        return zkClient.exists(path);
    }
    
    /**
     * @Description: 创建临时节点
     * @author: fallsea
     * @date: 2017年10月22日 下午8:55:19
     * @param path
     */
    public static void createEphemeral(String path)
    {
        zkClient.createEphemeral(path);
    }
    
    /**
     * @Description: 创建永久节点
     * @author: fallsea
     * @date: 2017年10月22日 下午8:55:26
     * @param path
     */
    public static void createPersistent(String path)
    {
        ////创建持久化节点，true表示如果父节点不存在则创建父节点 
        zkClient.createPersistent(path, true);
    }
}
