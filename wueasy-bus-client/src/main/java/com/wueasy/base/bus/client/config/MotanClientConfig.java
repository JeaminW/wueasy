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
package com.wueasy.base.bus.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.wueasy.base.bus.client.pojo.BusClientConfig;

/**
 * @Description: 客户端配置
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:02:36
 */
@Configuration
public class MotanClientConfig
{
	
	/**
     * @Description: bus客户端端配置
     * @author: fallsea
     * @date: 2018年8月22日 下午11:11:12
     * @return
     */
	@Bean(name = "wueasyBusClientConfig")
    @ConfigurationProperties(prefix = "wueasy.bus.client.base")
    public BusClientConfig BusClientConfig() {
        return new BusClientConfig();
    }
	
    /**
     * @Description: 客户端协议配置
     * @author: fallsea
     * @date: 2017年10月22日 下午9:02:22
     * @return
     */
    @Bean(name = "wueasyClientMotan")
    public ProtocolConfigBean protocolClientBasicConfig(@Qualifier("wueasyBusClientConfig") BusClientConfig clientConfig) {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setName("motan");
        config.setMinClientConnection(clientConfig.getMinClientConnection());
        config.setMaxClientConnection(clientConfig.getMaxClientConnection());
        return config;
    }
    
    /**
     * @Description: 默认客户端配置
     * @author: fallsea
     * @date: 2017年10月22日 下午9:02:46
     * @return
     */
    @Bean(name = "wueasyClientBasicConfig")
    public BasicRefererConfigBean baseRefererConfig(@Qualifier("wueasyBusClientConfig") BusClientConfig clientConfig) {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setProtocol("wueasyClientMotan");
        config.setGroup(clientConfig.getGroup());
        config.setApplication(clientConfig.getApplication());
        config.setModule(clientConfig.getModule());
        config.setCheck(false);
        config.setRequestTimeout(clientConfig.getRequestTimeout());
        config.setRegistry("wueasyRegistryConfig");
        return config;
    }

    /**
     * @Description: 客户端路由配置
     * @author: fallsea
     * @date: 2018年4月19日 下午8:42:22
     * @return
     */
    @Bean(name = "wueasyBusClientRouteConfig")
    @ConfigurationProperties(prefix = "wueasy.bus.client")
    public ClientRouteConfig ClientRouteConfig() {
    	ClientRouteConfig config = new ClientRouteConfig();
        return config;
    }
}
