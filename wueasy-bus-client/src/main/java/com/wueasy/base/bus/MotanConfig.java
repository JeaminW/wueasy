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
package com.wueasy.base.bus;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import com.wueasy.base.config.ZKConfig;

/**
 * @Description: motan 通用配置
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:02:08
 */
@Configuration
public class MotanConfig
{
	/**
	 * @Description: 声明Annotation用来指定需要解析的包名
	 * @author: fallsea
	 * @date: 2017年10月22日 下午9:02:13
	 * @return
	 */
    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("com.wueasy.base.bus");
        return motanAnnotationBean;
    }
    
    /**
     * @Description: 注册中心配置
     * @author: fallsea
     * @date: 2017年10月22日 下午9:02:28
     * @return
     */
    @Bean(name = "wueasyRegistryConfig")
    public RegistryConfigBean registryConfig(@Qualifier("wueasyZkConfig") ZKConfig zkConfig) {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress(zkConfig.getServers());
        config.setRequestTimeout(zkConfig.getSessionTimeout());
        config.setConnectTimeout(zkConfig.getConnectionTimeout());
        return config;
    }

}
