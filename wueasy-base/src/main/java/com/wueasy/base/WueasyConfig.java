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
package com.wueasy.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wueasy.base.config.RsaConfig;
import com.wueasy.base.config.ZKConfig;

/**
 * @Description: 基础配置中心
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年1月23日 下午10:22:47
 */
@Configuration
public class WueasyConfig {
	
	@Bean(name = "wueasyRsaConfig")
    @ConfigurationProperties(prefix = "wueasy.crypt.rsa")
    public RsaConfig RsaConfig() {
        return new RsaConfig();
    }
	
	@Bean(name = "wueasyZkConfig")
    @ConfigurationProperties(prefix = "wueasy.zk")
    public ZKConfig zkConfig() {
        return new ZKConfig();
    }
}
