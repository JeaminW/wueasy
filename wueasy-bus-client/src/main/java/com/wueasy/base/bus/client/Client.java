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
package com.wueasy.base.bus.client;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.weibo.api.motan.exception.MotanBizException;
import com.weibo.api.motan.exception.MotanFrameworkException;
import com.weibo.api.motan.exception.MotanServiceException;
import com.weibo.api.motan.rpc.ResponseFuture;
import com.wueasy.base.bus.client.service.ClientService;
import com.wueasy.base.bus.client.util.RouteHelper;
import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Result;
import com.wueasy.base.exception.InvokeException;
import com.wueasy.base.util.SpringHelper;
import com.wueasy.base.util.StringHelper;
import com.wueasy.base.util.SystemHelper;
import com.wueasy.cache.util.ParameterHelper;


/**
 * @Description: 客户端工具
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:04:21
 */
public class Client
{
    /**
     * 默认调用客户端服务名称
     */
	private static final String DEFAULT_SERVICE_NAME = "ClientService";
	
    private String serviceName = "";
    
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    
    
    public Client()
    {
    	
    }
    
    public Client(String serviceName)
    {
    	this.serviceName = serviceName;
    }
    
    /**
     * @Description: 调用功能号
     * @author: fallsea
     * @date: 2017年10月22日 下午9:04:29
     * @param funcNo 功能号id
     * @param paramMap 业务参数
     * @return
     */
    public Result invoke(String funcNo,DataMap paramMap)
    {
        return this.invoke(funcNo, "", paramMap);
    }
    
    /**
     * @Description: 调用功能号
     * @author: fallsea
     * @date: 2017年10月22日 下午9:04:41
     * @param funcNo 功能编号
     * @param version 版本号,默认1.0
     * @param paramMap 业务参数
     * @return
     */
    public Result invoke(String funcNo,String version,DataMap paramMap)
    {
        return this.invoke(funcNo, version, paramMap, null);
    }
    
    /**
     * @Description: 调用功能号
     * @author: fallsea
     * @date: 2017年10月22日 下午9:04:58
     * @param funcNo 功能编号
     * @param paramMap 业务参数
     * @param systemParamMap 系统参数
     * @return
     */
    public Result invoke(String funcNo,DataMap paramMap,DataMap systemParamMap)
    {
        return this.invoke(funcNo, "", paramMap, systemParamMap);
    }
    
    /**
     * @Description: 调用功能号
     * @author: fallsea
     * @date: 2017年10月22日 下午9:05:13
     * @param funcNo 功能编号
     * @param version 版本号,默认1.0
     * @param paramMap 业务参数
     * @param systemParamMap 系统参数
     * @return
     */
    public Result invoke(String funcNo,String version,DataMap paramMap,DataMap systemParamMap)
    {
    	ClientService clientService = getClientService(funcNo);
    	
        try
        {
        	cleanHtml(funcNo,paramMap);
        	handleSystemParamMap(systemParamMap);
            return clientService.invoke(funcNo, version, paramMap, systemParamMap);
            
        }
        catch(MotanBizException e)
        {
            logger.error("",e);
            return new Result(-1009,"调用功能号业务异常!");
        }
        catch(MotanServiceException e)
        {
            logger.error("",e);
            return new Result(-1010,"调用功能号服务异常!");
        }
        catch(MotanFrameworkException e)
        {
            logger.error("",e);
            return new Result(-1011,"调用功能号框架异常!");
        }
        catch(Exception e)
        {
            logger.error("",e);
            return new Result(-1000,"系统出现异常!");
        }
        finally
        {
        	paramMap = null;
        	systemParamMap = null;
        }
    }
    
    /**
     * @Description: 调用功能号异步接口
     * @author: fallsea
     * @date: 2017年10月22日 下午9:05:36
     * @param funcNo 功能编号
     * @param paramMap 业务参数
     * @return
     * @throws InvokeException
     */
    public ResponseFuture invokeAsync(String funcNo,DataMap paramMap) throws InvokeException
    {
        return this.invokeAsync(funcNo, "", paramMap);
    }
    
    /**
     * @Description: 调用功能号异步接口
     * @author: fallsea
     * @date: 2017年10月22日 下午9:05:53
     * @param funcNo 功能编号
     * @param version 版本号,默认1.0
     * @param paramMap 业务参数
     * @return
     * @throws InvokeException
     */
    public ResponseFuture invokeAsync(String funcNo,String version,DataMap paramMap) throws InvokeException
    {
        return this.invokeAsync(funcNo, version, paramMap, null);
    }
    
    /**
     * @Description: 调用功能号异步接口
     * @author: fallsea
     * @date: 2017年10月22日 下午9:06:16
     * @param funcNo 功能编号
     * @param paramMap 业务参数
     * @param systemParamMap 系统参数
     * @return
     * @throws InvokeException
     */
    public ResponseFuture invokeAsync(String funcNo,DataMap paramMap,DataMap systemParamMap) throws InvokeException
    {
        return this.invokeAsync(funcNo, "", paramMap, systemParamMap);
    }
    
    /**
     * @Description: 调用功能号异步接口
     * @author: fallsea
     * @date: 2017年10月22日 下午9:06:31
     * @param funcNo 功能编号
     * @param version 版本号,默认1.0
     * @param paramMap 业务参数
     * @param systemParamMap 系统参数
     * @return
     * @throws InvokeException
     */
    public ResponseFuture invokeAsync(String funcNo, String version, DataMap paramMap,DataMap systemParamMap) throws InvokeException
    {
    	
    	ClientService clientService = getClientService(funcNo);
        try
        {
        	cleanHtml(funcNo,paramMap);
        	handleSystemParamMap(systemParamMap);
            return clientService.invokeAsync(funcNo, version, paramMap, systemParamMap);
            
        }
        catch(MotanBizException e)
        {
            logger.error("",e);
            throw new InvokeException(-1009, "调用功能号业务异常!");
        }
        catch(MotanServiceException e)
        {
            logger.error("",e);
            throw new InvokeException(-1010, "调用功能号服务异常!");
        }
        catch(MotanFrameworkException e)
        {
            logger.error("",e);
            throw new InvokeException(-1011, "调用功能号框架异常!");
        }
        catch(Exception e)
        {
            logger.error("",e);
            throw new InvokeException(-1000, "系统出现异常!");
        }
        finally
        {
        	paramMap = null;
        	systemParamMap = null;
        }
    }
    
    /**
     * @Description: 处理系统参数信息
     * @author: fallsea
     * @date: 2018年5月5日 下午2:18:54
     * @param systemParamMap
     */
    private void handleSystemParamMap(DataMap systemParamMap){
    	
    	if(null==systemParamMap){
    		systemParamMap = new DataMap();
    	}
    	systemParamMap.set(SystemHelper.ATTRIBUTE_SERVER_IP,SystemHelper.getServerIp());
    	systemParamMap.set(SystemHelper.ATTRIBUTE_SERVER_NAME,SystemHelper.getServerName());
    	systemParamMap.set(SystemHelper.ATTRIBUTE_SERVER_PID,SystemHelper.getServerPid());
    	systemParamMap.set(SystemHelper.ATTRIBUTE_APPLICATION_NAME,SystemHelper.getApplicationName());
    }
    
    
    /**
     * @Description: 获取service服务
     * @author: fallsea
     * @date: 2018年4月19日 下午8:08:08
     * @param funcNo
     * @return
     */
    private ClientService getClientService(String funcNo){
    	
    	if(StringHelper.isEmpty(this.serviceName)){
    		
    		//判断是否配置路由信息，如果配置，需要从路由中获取
    		if(!RouteHelper.isEmpty()){
    			//获取功能号最前面的6位字母
    			Pattern p = Pattern.compile("([a-zA-Z]{1,6})");
    			Matcher m = p.matcher(funcNo);
    			m.find();
    			String key = m.group(1);
    			if(StringHelper.isNotEmpty(key)){
    				this.serviceName = RouteHelper.getClientId(key);
    			}
    		}
    	}
    	
    	//最终如果还是空，则默认
    	if(StringHelper.isEmpty(this.serviceName)){
    		this.serviceName = DEFAULT_SERVICE_NAME;
    	}
    	
    	return SpringHelper.getBean(this.serviceName, ClientService.class);
    }
    
    
    /**
     * @Description: 清理html标签
     * @author: fallsea
     * @date: 2018年4月17日 上午12:04:59
     * @param funcNo
     * @param paramMap
     */
    private void cleanHtml(String funcNo,DataMap paramMap){
    	if(null!=paramMap && !paramMap.isEmpty()){
    		
    		//判断是否功能号字段是否需要清理
    		
    		String securityXssValue = ParameterHelper.getString("securityNotXss."+funcNo);
    		
    		List<String> result = null;
    		
    		if(StringHelper.isNotEmpty(securityXssValue)){
    			result = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(securityXssValue); 
    		}
    		
    		for (Map.Entry<String,Object> data : paramMap.entrySet()) {
    			String key = data.getKey();
    			
    			if(null!=result && result.contains(key)){//不为空，并且存在，跳过循环
    				continue;
    			}
    			
    			Object value = data.getValue();
    			if(null!=value && value instanceof String){
    				paramMap.set(key, StringHelper.cleanHTML(value.toString()));
    			}
    		}
    		
    		
    	}
    }
    
}
