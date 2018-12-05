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
package com.wueasy.base.bus.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.wueasy.base.bus.server.constants.ServerConstants;
import com.wueasy.base.bus.server.entity.Func;
import com.wueasy.base.bus.server.entity.FuncParam;
import com.wueasy.base.entity.DataMap;
import com.wueasy.base.util.DBHelper;
import com.wueasy.base.util.DateHelper;
import com.wueasy.base.util.FileHelper;
import com.wueasy.base.util.StringHelper;

/**
 * @Description: 功能号工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:12:44
 */
public class FunctionHelper
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionHelper.class);
    
    private static volatile Map<String, Func> FUNC_MAP = new HashMap<String, Func>();
    
    /**
     * 命名空间对应的qps
     */
    private static volatile Map<String, Integer> NAMESPACE_QPS_MAP = new HashMap<String, Integer>();
    
    public static void init()
    {
        
    }
    
    static
    {
        loadConfig();
    }
    
    /**
     * @Description: 获取功能号信息
     * @author: fallsea
     * @date: 2017年10月22日 下午9:12:51
     * @param funcNo
     * @return
     */
    public static Func getFunc(String funcNo)
    {
        return FUNC_MAP.get(funcNo);
    }
    
    /**
     * @Description: 获取命名空间qps限流配置
     * @author: fallsea
     * @date: 2018年8月28日 下午10:09:47
     * @param namespace
     * @return
     */
    public static int getNamespaceQps(String namespace){
    	Integer qps = NAMESPACE_QPS_MAP.get(namespace);
    	if(null==qps){
    		qps = 0 ;
    	}
    	return qps;
    }
    
    /**
     * @Description: 初始化数据
     * @author: fallsea
     * @date: 2017年10月22日 下午9:12:56
     */
    private static void loadConfig()
    {
        
        Map<String,Func> tempJarConfig = null;//存储jar中的所有资源文件
        Map<String,Func> tempFileConfig = null;//存储classpath下的所有资源文件
        
        //初始化qps规则
    	List<FlowRule> rules = new ArrayList<FlowRule>();
    	List<FlowRule> tempJarRules = null;
    	List<FlowRule> tempFileRules = null;
        try
        {
            ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
            
            //将加载多个绝对匹配的所有Resource  
            //将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分  
            //然后进行遍历模式匹配  
            Resource[] resources = (Resource[]) resolver.getResources("classpath*:/functions/**.xml");
            
            tempJarConfig = new HashMap<String,Func>();
            tempFileConfig = new HashMap<String,Func>();
            tempJarRules = new ArrayList<FlowRule>();
            tempFileRules = new ArrayList<FlowRule>();
            if ( resources != null )
            {
                for (Resource resource : resources)
                {
                    URL url = resource.getURL();
                    try
                    {
                        if ( FileHelper.PROTOCOL_JAR.equals(url.getProtocol()) )//读取jar包中的消息资源文件
                        {
                            readConfig(url,tempJarConfig,tempJarRules);
                        }
                        else if ( FileHelper.PROTOCOL_FILE.equals(url.getProtocol()) )//读取本地classpath的消息资源文件
                        {
                            readConfig(url,tempFileConfig,tempFileRules);
                        }
                    }
                    catch (Exception e)
                    {
                        LOGGER.error("", e);
                    }
                }
                
                FUNC_MAP.putAll(tempJarConfig);
                FUNC_MAP.putAll(tempFileConfig);
                
                rules.addAll(tempJarRules);
                rules.addAll(tempFileRules);
            }
            
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.error("", e);
        }
        finally
        {
            if ( tempJarConfig != null )
            {
                tempJarConfig.clear();
            }
            
            if ( tempFileConfig != null )
            {
                tempFileConfig.clear();
            }
        }
        
        FlowRuleManager.loadRules(rules);
        
    }
    
    /**
     * @Description: 读取文件中配置项
     * @author: fallsea
     * @date: 2017年10月22日 下午9:13:08
     * @param url
     * @param items
     */
    @SuppressWarnings("unchecked")
    private static void readConfig(URL url,Map<String, Func> items,List<FlowRule> rules)
    {
    	
    	//命名空间，默认为文件名称 
    	String namespace = url.getFile().substring(url.getFile().lastIndexOf('/')+1,url.getFile().length()-4);
    	
        Document document = null;
        InputStream ins = null;
        try
        {
            ins = url.openStream();
            if ( ins != null )
            {
                SAXReader reader = new SAXReader();
                document = reader.read(ins);
            }
            if ( document != null )
            {
            	
                Element systemElement = document.getRootElement();
                String namespaceQpsStr= systemElement.attributeValue("qps");
                
                int namespaceQps = 0 ;//默认为0，不限制
                if(StringHelper.isNotEmpty(namespaceQpsStr)){
                	try {
                		namespaceQps = Integer.parseInt(namespaceQpsStr);
					} catch (NumberFormatException e) {
						LOGGER.error("qps转换异常",e);
					}
                }
                NAMESPACE_QPS_MAP.put(namespace, namespaceQps);
                
                if(namespaceQps>0){
                	FlowRule rule = new FlowRule();
                    rule.setResource(namespace);
                    rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
                    rule.setCount(namespaceQps);
                    rules.add(rule);
                }
                
                List<Element> catList = systemElement.elements("function");
                for (Element catElement : catList)
                {
                    String funcNo=catElement.attributeValue("id");
                    
                    //功能号版本
                    String version = catElement.attributeValue("version");
                    if(StringHelper.isBlank(version))
                    {
                        version = ServerConstants.FUNC_DEFAULT_VERSION;
                    }
                    
                    String type=catElement.attributeValue("type");//类型
                    
                    if(StringHelper.isNotBlank(funcNo))
                    {
                        //功能号+版本号
                        funcNo = funcNo + "_" + version;
                        Element elementResource= catElement.element("resource");
                        Element elementDesc= catElement.element("desc");
                        
                        Func func=new Func();
                        func.setFuncNo(funcNo);
                        if(ServerConstants.FUNC_TYPE_C.equalsIgnoreCase(type) || ServerConstants.FUNC_TYPE_S.equalsIgnoreCase(type) || ServerConstants.FUNC_TYPE_M.equalsIgnoreCase(type))
                        {
                            func.setType(type);
                        }
                        else
                        {
                            func.setType(ServerConstants.FUNC_TYPE_C);
                        }
                        
                        String isPage = catElement.attributeValue("isPage");
                        if(StringHelper.isNotBlank(isPage)){
                        	func.setIsPage(isPage);
                        }
                        
                        //限流配置
                        String qps = catElement.attributeValue("qps");
                        if(StringHelper.isNotBlank(qps)){
                        	try {
                        		func.setQps(Integer.valueOf(qps));
							} catch (NumberFormatException e) {
								LOGGER.error("qps转换异常",e);
							}
                        }
                        
                        if(func.getQps()>0){
                        	FlowRule rule = new FlowRule();
                            rule.setResource(funcNo);
                            rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
                            rule.setCount(func.getQps());
                            rules.add(rule);
                        }
                        
                        if(null!=elementResource)
                        {
                        	//资源信息处理
                        	String resource = elementResource.getTextTrim();
                        	
                        	if(StringHelper.isNotEmpty(resource)){
                        		if(ServerConstants.FUNC_TYPE_C.equalsIgnoreCase(type)){//class实现类
                        			func.setResourceClass(resource);
                        		}else if(ServerConstants.FUNC_TYPE_M.equalsIgnoreCase(type) || ServerConstants.FUNC_TYPE_S.equalsIgnoreCase(type)){//service服务类、mapper实现类
                        			int index=resource.lastIndexOf(".");
                        	        if(index==-1)
                        	        {
                        	            continue;
                        	        }
                        	        String resourceClass=resource.substring(0,index);
                        	        String methodName=resource.substring(index+1);
                        	        func.setResourceClass(resourceClass);
                        	        func.setResourceMethod(methodName);
                        		}
                        	}else{
                        		continue;
                        	}
                        }
                        if(null!=elementDesc)
                        {
                            func.setDesc(elementDesc.getTextTrim());
                        }
                        
                        func.setNamespace(namespace);
                        
                        /****************读取参数配置--开始--********************/
                        Element paramsElement= catElement.element("params");
                        if(null!=paramsElement)
                        {
                            List<Element> paramElementList = paramsElement.elements("param");
                            List<FuncParam> funcParamList = new ArrayList<FuncParam>();
                            
                            for (Element element : paramElementList)
                            {
                                String id=element.attributeValue("id");
                               
                                if(StringHelper.isNotBlank(id))
                                {
                                    FuncParam funcParam=new FuncParam();
                                    funcParam.setId(id);
                                    funcParam.setName(element.attributeValue("name"));
                                    funcParam.setNotEmpty(element.attributeValue("notEmpty"));
                                    
                                    String value = element.attributeValue("value");
                                    /*if(StringHelper.isNotBlank(value) && (value.startsWith("#") || value.startsWith("$")))
                                    {
                                        value = value.toLowerCase();
                                    }*/
                                    funcParam.setValue(value);
                                    
                                    String defaultValue = element.attributeValue("defaultValue");
                                    /*if(StringHelper.isNotBlank(defaultValue) && (defaultValue.startsWith("#") || defaultValue.startsWith("$")))
                                    {
                                        value = defaultValue.toLowerCase();
                                    }*/
                                    funcParam.setDefaultValue(defaultValue);
                                    
                                    
                                    String verifyType=element.attributeValue("verifyType");
                                    if(StringHelper.isNotBlank(verifyType))
                                    {
                                        verifyType=verifyType.toUpperCase();
                                    }
                                    funcParam.setVerifyType(verifyType);
                                    
                                    String convertType=element.attributeValue("convertType");
                                    if(StringHelper.isNotBlank(convertType))
                                    {
                                    	convertType=convertType.toUpperCase();
                                    }
                                    funcParam.setConvertType(convertType);
                                    
                                    String maxLength=element.attributeValue("maxLength");
                                    if(StringHelper.isNotBlank(maxLength))
                                    {
                                        int length=0;
                                        try
                                        {
                                            length=Integer.parseInt(maxLength);
                                        }
                                        catch (NumberFormatException e)
                                        {
                                            LOGGER.error("",e);
                                        }
                                        funcParam.setMaxLength(length);
                                    }
                                    String minLength=element.attributeValue("minLength");
                                    if(StringHelper.isNotBlank(minLength))
                                    {
                                        int length=0;
                                        try
                                        {
                                            length=Integer.parseInt(minLength);
                                        }
                                        catch (NumberFormatException e)
                                        {
                                            LOGGER.error("",e);
                                        }
                                        funcParam.setMinLength(length);
                                    }
                                    funcParamList.add(funcParam);
                                }
                                
                            }
                            
                            func.setFuncParamList(funcParamList);
                            
                        }
                        /****************读取参数配置--结束--********************/
                        
                        items.put(funcNo, func);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("读取xml文件[" + url.getPath() + "]出错，返回null", ex);
        }
        finally
        {
            if ( ins != null )
            {
                try
                {
                    ins.close();
                }
                catch (IOException e)
                {
                    LOGGER.error("", e);
                }
            }
        }
    }
    
    
    /**
     * @Description: 处理自定义规则系统参数
     * @author: fallsea
     * @date: 2017年10月22日 下午9:13:19
     * @param paramValue
     * @param paramMap
     * @param systemParamMap
     * @return
     */
    public static Object getParamValue(String paramValue,DataMap paramMap,DataMap systemParamMap)
    {
        Object value = null;
        if(StringHelper.isNotBlank(paramValue))
        {
            
            if(paramValue.startsWith("$"))
            {
                switch (paramValue)
                {
                    case "$id()":
                        value = DBHelper.getId();
                        break;
                    case "$ip()":
                        value = systemParamMap.getString(ServerConstants.SYS_PARAM_IP);
                        break;
                    case "$date()":
                    case "$datetime()":
                        value = new Date();
                        break;
                    case "$timestamp()":
                        value = System.currentTimeMillis();   
                        break;
                    case "$dateStr()":
                        value = DateHelper.formatDate(new Date(), DateHelper.PATTERN_DATE);
                        break;
                    case "$datetimeStr()":
                        value = DateHelper.formatDate(new Date(), DateHelper.PATTERN_DATE_TIME);
                        break;
                    case "$uuid()":
                        value = StringHelper.getUUID();
                        break;
                    case "$userId()":
                        value = systemParamMap.getLong(ServerConstants.SYS_PARAM_USER_ID);
                        break;
                    case "$userName()":
                        value = systemParamMap.getString(ServerConstants.SYS_PARAM_USER_NAME);
                        break;
                    case "$loginNo()":
                        value = systemParamMap.getString(ServerConstants.SYS_PARAM_LOGIN_NO);
                        break;
                    case "$isSystem()":
                        value = systemParamMap.getString(ServerConstants.SYS_PARAM_IS_SYSTEM);
                        break;
                    case "$authData()":
                        value = systemParamMap.getString(ServerConstants.SYS_PARAM_AUTH_DATA);
                        break;
                }
            }
            else if(paramValue.startsWith("#"))
            {
                String name = paramValue.substring(1);
                if(StringHelper.isNotBlank(name))
                {
                    value = paramMap.get(name);
                }
            }
            else
            {
                value = paramValue ;
            }
        }
        return value;
    }
    
    
    /**
     * @Description: 转换参数值
     * @author: fallsea
     * @date: 2018年3月19日 下午8:54:19
     * @param convertType
     * @param value
     * @return
     */
    public static Object convertParamValue(String convertType,Object value){
    	
		if(StringHelper.isNotBlank(value))
		{
		    
			switch (convertType) {
				case "DATETIME":
					if(value instanceof Date){
						return value;
					}
					value = DateHelper.parseString(value.toString(), DateHelper.PATTERN_DATE_TIME);
					break;
				case "DATE":
					value = DateHelper.parseString(value.toString(), DateHelper.PATTERN_DATE);
					break;
			}
		}
		return value;
    }
    
}
