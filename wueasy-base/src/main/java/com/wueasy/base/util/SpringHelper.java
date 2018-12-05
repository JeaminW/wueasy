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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: spirng工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:49:41
 */
@Component
public class SpringHelper implements ApplicationContextAware
{

    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        if(SpringHelper.applicationContext == null) {
            SpringHelper.applicationContext = applicationContext;
        }
    }
    
    /**
     * @Description: 获取spring上下文
     * @author: fallsea
     * @date: 2017年10月22日 下午8:49:52
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    /**
     * @Description: 通过name获取bean
     * @author: fallsea
     * @date: 2017年10月22日 下午8:50:05
     * @param name 名称
     * @return
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
    
    /**
     * @Description: 通过class获取Bean
     * @author: fallsea
     * @date: 2017年10月22日 下午8:50:16
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
    
    /**
     * @Description: 通过name,以及Clazz返回指定的Bean
     * @author: fallsea
     * @date: 2017年10月22日 下午8:50:26
     * @param name 名称
     * @param clazz
     * @return
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
    
}
