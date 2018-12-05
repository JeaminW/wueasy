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
package com.wueasy.base.bus.server.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.wueasy.base.bus.server.pojo.BusServerConfig;
import com.wueasy.base.util.SystemHelper;

/**
 * @Description: motan服务端配置
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:15:16
 */
@Configuration
public class MotanServerConfig
{
    /**
     * @Description: bus服务端配置
     * @author: fallsea
     * @date: 2018年8月22日 下午11:11:12
     * @return
     */
	@Bean(name = "wueasyBusServerConfig")
    @ConfigurationProperties(prefix = "wueasy.bus.server")
    public BusServerConfig BusServerConfig() {
        return new BusServerConfig();
    }
	
	/**
     * @Description: 客户端协议配置
     * @author: fallsea
     * @date: 2017年10月22日 下午9:02:22
     * @return
     */
    @Bean(name = "wueasyServerMotan")
    public ProtocolConfigBean protocolServerBasicConfig(@Qualifier("wueasyBusServerConfig") BusServerConfig serverConfig) {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setName("motan");
        config.setMinWorkerThread(serverConfig.getMinWorkerThread());
        config.setMaxWorkerThread(serverConfig.getMaxWorkerThread());
        config.setMaxContentLength(serverConfig.getMaxContentLength());
        config.setMaxServerConnection(serverConfig.getMaxServerConnection());
        config.setFilter("statistic");
        return config;
    }
    
    /**
     * @Description: 服务端配置
     * @author: fallsea
     * @date: 2017年10月22日 下午9:15:27
     * @return
     */
    @Bean
    public BasicServiceConfigBean baseServiceConfig(@Qualifier("wueasyBusServerConfig") BusServerConfig serverConfig) {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setExport("wueasyServerMotan:"+serverConfig.getPort());
        config.setGroup(serverConfig.getGroup());
        config.setApplication(serverConfig.getApplication());
        config.setModule(serverConfig.getModule());
        config.setHost(SystemHelper.getLocalIP());
        config.setRegistry("wueasyRegistryConfig");
        config.setCheck(false);
        return config;
    }
    
}
