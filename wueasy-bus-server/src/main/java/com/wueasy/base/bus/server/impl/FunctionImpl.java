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
package com.wueasy.base.bus.server.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.wueasy.base.bus.Function;
import com.wueasy.base.bus.server.BaseFunction;
import com.wueasy.base.bus.server.constants.ErrorEnum;
import com.wueasy.base.bus.server.constants.ServerConstants;
import com.wueasy.base.bus.server.entity.Func;
import com.wueasy.base.bus.server.entity.FuncParam;
import com.wueasy.base.bus.server.log.LogTask;
import com.wueasy.base.bus.server.util.FunctionHelper;
import com.wueasy.base.bus.server.util.SysLogUtil;
import com.wueasy.base.entity.DataMap;
import com.wueasy.base.entity.Result;
import com.wueasy.base.exception.InvokeException;
import com.wueasy.base.util.PageHelper;
import com.wueasy.base.util.Performance;
import com.wueasy.base.util.SpringHelper;
import com.wueasy.base.util.StringHelper;
import com.wueasy.base.util.SystemHelper;

/**
 * @Description: 功能号接口入口
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午9:13:30
 */
@MotanService
public class FunctionImpl implements Function
{
    private static Logger logger = LoggerFactory.getLogger("wueasy.function");
    
    @Autowired
    private LogTask logTask;
    
    @Override
    public Result invoke(String funcno, String version, DataMap paramMap)
    {
        return this.invoke(funcno, version, paramMap, null);
    }
    
    public Result invoke(String funcNo, DataMap paramMap)
    {
        return this.invoke(funcNo,paramMap,null);
    }
    
    @Override
    public Result invoke(String funcNo, DataMap paramMap, DataMap systemParamMap)
    {
        return this.invoke(funcNo, ServerConstants.FUNC_DEFAULT_VERSION, paramMap, systemParamMap);
    }

    @Override
    public Result invoke(String funcno, String version, DataMap paramMap,DataMap systemParamMap)
    {
        if(null==paramMap){
        	paramMap = new DataMap();
        }
        if(null==systemParamMap){
        	systemParamMap = new DataMap();
        }
        String v = version;
        if(StringHelper.isBlank(v))
        {
            v = ServerConstants.FUNC_DEFAULT_VERSION;
        }
        
        String funcNo = funcno + "_" + v;
        
        Performance performance = new Performance();
        
        String request_id = "";
        /*String request_id = paramMap.getString("request_id");// request_id非必传
        if ( StringHelper.isBlank(request_id) )
        {
            // 客户端不传则自己生成一个
           request_id = StringHelper.getUUID();
        }
        // 将req_id放入线程名称中打印
        Thread.currentThread().setName(request_id);*/
        
        String funcDesc = "";
        Result result = null;
        
        //异常处理
        Throwable throwable = null;
        
        Entry entry = null;
        Entry entry2 = null;
        try
        {
            if ( StringHelper.isBlank(funcNo) )
            {
                throw new InvokeException(ErrorEnum.FUNC_NO_NOT_BLANK.getCode(), ErrorEnum.FUNC_NO_NOT_BLANK.getDesc());
            }
            
            Func func = FunctionHelper.getFunc(funcNo);
            if ( null == func )
            {
                throw new InvokeException(ErrorEnum.FUNC_NO_NOT_EMPTY.getCode(), ErrorEnum.FUNC_NO_NOT_EMPTY.getDesc());
            }
            //限流控制
            
            if(FunctionHelper.getNamespaceQps(func.getNamespace())>0){//先走总线限流
            	entry2 = SphU.entry(func.getNamespace());
            }
            
            if(func.getQps() > 0){//按功能号版本限流
            	entry = SphU.entry(funcNo);
            }
            
            funcDesc = func.getDesc();//功能号描述
            
            if ( logger.isInfoEnabled() )
            {
                logger.info("[FuncNo:{}-{}] 开始 Request:{}", funcNo, funcDesc,SysLogUtil.logConvert(paramMap));
            }
            
            String type=func.getType();//功能号类型
            
            //验证参数中排序信息。
//            field：排序的字段 ， 只能是字母+数字或下划线组成
//            order：排序的方式asc,desc
            String field = paramMap.getString("field");
            if(StringHelper.isNotEmpty(field)){
            	Pattern pattern = Pattern.compile("^[A-Za-z]{1}[A-Za-z0-9_]{1,50}$");
            	Matcher matcher = pattern.matcher(field);
            	if(!matcher.matches()){
            		throw new InvokeException(ErrorEnum.FUNC_PARAM_BREACH.getCode(), ErrorEnum.FUNC_PARAM_BREACH.getDesc());
            	}
            	
            	//自动转换成驼峰
            	paramMap.set("field", underscoreName(field));
            }
            
            String order = paramMap.getString("order");
            if(StringHelper.isNotEmpty(order)){
            	if(!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)){
            		throw new InvokeException(ErrorEnum.FUNC_PARAM_BREACH.getCode(), ErrorEnum.FUNC_PARAM_BREACH.getDesc());
            	}
            }
            
            if(ServerConstants.FUNC_TYPE_C.equalsIgnoreCase(type))
            {
                result = doClass(func, paramMap,systemParamMap);
            }
            else if(ServerConstants.FUNC_TYPE_S.equalsIgnoreCase(type))
            {
                result = doService(func, paramMap,systemParamMap);
            }
            else if(ServerConstants.FUNC_TYPE_M.equalsIgnoreCase(type))
            {
                result = doDao(func, paramMap,systemParamMap);
            }
            
            if ( logger.isInfoEnabled() )
            {
                logger.info("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果:{}", funcNo, funcDesc, performance.getTime(),SysLogUtil.logConvert(result));
            }
            
            return result;
        }
        catch (InvokeException e)
        {
            result = new Result(e.getErrorCode(),e.getMessage());
            if ( logger.isWarnEnabled() )
            {
                logger.warn("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果: {}|{}", funcNo, funcDesc, performance.getTime(),e.getErrorCode(), e.getMessage());
            }
            return result;
        }
        catch (BlockException e1) {
        	result = new Result(ErrorEnum.SYSTEM_BUSY.getCode(),ErrorEnum.SYSTEM_BUSY.getDesc());
        	if ( logger.isWarnEnabled() )
            {
                logger.warn("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果：系统繁忙,请稍后...", funcNo, funcDesc, performance.getTime());
            }
        	return result;
    	}
        catch (IllegalAccessException e)
        {
        	logger.error("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果: 获取实例失败,反射异常:", funcNo, funcDesc, performance.getTime(), e);
        	throwable = e;
        	result = new Result(ErrorEnum.ERROR.getCode(),ErrorEnum.ERROR.getDesc());
        	return result;
        }
        catch (NoSuchMethodException e)
        {
        	logger.error("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果: 获取实例失败,反射异常:", funcNo, funcDesc, performance.getTime(), e);
        	throwable = e;
            result = new Result(ErrorEnum.ERROR.getCode(),ErrorEnum.ERROR.getDesc());
        	return result;
        }
        catch (InvocationTargetException e)
        {
        	Throwable t = e.getCause();
            if(t instanceof InvokeException)
            {
            	InvokeException invokeException =  (InvokeException)t;
                result = new Result(invokeException.getErrorCode(),invokeException.getMessage());
                if ( logger.isWarnEnabled() )
                {
                	logger.warn("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果: {}|{}", funcNo, funcDesc, performance.getTime(),invokeException.getErrorCode(), invokeException.getMessage());
                }
            	return result;
            }else if(t instanceof DataAccessException) {
            	logger.error("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果: 数据库异常:", funcNo, funcDesc, performance.getTime(), t);
            	throwable = t;
            	result = new Result(ErrorEnum.FUNC_NO_ERROR_SQL.getCode(), ErrorEnum.FUNC_NO_ERROR_SQL.getDesc());
            	return result;
            }
            logger.error("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果: 获取实例失败:", funcNo, funcDesc, performance.getTime(), t);
            throwable = t;
            result = new Result(ErrorEnum.ERROR.getCode(),ErrorEnum.ERROR.getDesc());
        	return result;
        }
        catch (ClassNotFoundException e)
        {
            logger.error("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果: 功能号实现类不存在:", funcNo, funcDesc, performance.getTime(), e);
            throwable = e;
            result = new Result(ErrorEnum.FUNC_NO_MAPPER_NOT_EMPTY.getCode(),ErrorEnum.FUNC_NO_MAPPER_NOT_EMPTY.getDesc());
        	return result;
        }
        catch (Exception e)
        {
            result = new Result(ErrorEnum.ERROR.getCode(),ErrorEnum.ERROR.getDesc());
            throwable = e;
            logger.error("[FuncNo:{}-{}] 结束,共花费时间:{},执行结果: ", funcNo, funcDesc, performance.getTime(), e);
            return result;
        }
        finally {
        	if(SysLogUtil.IS_WRITE_DB == 1){
        		//日志处理
        		logTask.add(funcno, funcDesc, v,request_id, performance.getTime(),systemParamMap.getString(ServerConstants.SYS_PARAM_IP), systemParamMap.getLong(ServerConstants.SYS_PARAM_USER_ID), paramMap, result,SystemHelper.getSystemInfo(systemParamMap),throwable);
        	}
        	throwable = null;
        	if (entry != null) {
     	       entry.exit();
     	       entry= null;
     	    }
        	if (entry2 != null) {
      	       entry2.exit();
      	       entry2= null;
      	    }
		}
        
        
    }
    
	private Result doDao(Func func,DataMap paramMap,DataMap systemParamMap) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException
    {
        
        try
        {
            //验证入参
        	String msg = verify(paramMap,systemParamMap,func.getFuncParamList());
            if (StringHelper.isNotEmpty(msg))
            {
                throw new InvokeException(ErrorEnum.FUNC_NO_ERROR_CUSTOM.getCode(), msg);
            }
            
            Object mapper = SpringHelper.getBean(Class.forName(func.getResourceClass()));
            if(mapper==null)
            {
            	throw new InvokeException(ErrorEnum.FUNC_NO_SERVICE_NOT_EMPTY.getCode(),ErrorEnum.FUNC_NO_SERVICE_NOT_EMPTY.getDesc());
            }
            Object obj = null;
            if("1".equals(func.getIsPage())){
            	int pageNum = PageHelper.getPageNum(paramMap);
                int pageSize = PageHelper.getPageSize(paramMap);
                
                PageHelper.startPage(pageNum, pageSize);
                obj = MethodUtils.invokeMethod(mapper,func.getResourceMethod(),paramMap);
                if(obj instanceof List){
                	obj = PageHelper.getPage((List<?>)obj);
                }
            }else{
            	obj = MethodUtils.invokeMethod(mapper,func.getResourceMethod(),paramMap);
            }
            
            Result result = null;
            if(obj == null)
            {
                result = new Result();
            }
            else
            {
                result = new Result();
                result.setResult(obj);
            }
            return result;
        }
        catch (IllegalAccessException e)
        {
            throw e;
        }
        catch (NoSuchMethodException e)
        {
            throw e;
        }
        catch (InvocationTargetException e)
        {
            throw e;
        }
        catch (ClassNotFoundException e)
        {
            throw e;
        }
    }
    
    
    /**
     * @Description: 调用service实现
     * @author: fallsea
     * @date: 2017年10月22日 下午9:13:41
     * @param func
     * @param paramMap
     * @param systemParamMap
     * @return
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     */
    private Result doService(Func func,DataMap paramMap,DataMap systemParamMap) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        try
        {
            Object obj= SpringHelper.getBean(func.getResourceClass());
            if(obj==null)
            {
                throw new InvokeException(ErrorEnum.FUNC_NO_SERVICE_NOT_EMPTY.getCode(),ErrorEnum.FUNC_NO_SERVICE_NOT_EMPTY.getDesc());
            }
            
            //验证入参
            String msg = verify(paramMap,systemParamMap,func.getFuncParamList());
            if (StringHelper.isNotEmpty(msg))
            {
                throw new InvokeException(ErrorEnum.FUNC_NO_ERROR_CUSTOM.getCode(), msg);
            }
            obj=MethodUtils.invokeMethod(obj,func.getResourceMethod(),paramMap);
            Result result = null;
            if(obj == null)
            {
                result = new Result();
            }
            else if(obj instanceof Result)
            {
                result = (Result)obj;
            }
            else
            {
                result = new Result();
                result.setResult(obj);
            }
            
            return result;
        }
        catch (IllegalAccessException e)
        {
            throw e;
        }
        catch (NoSuchMethodException e)
        {
            throw e;
        }
        catch (InvocationTargetException e)
        {
            throw e;
        }
    }
    
    /**
     * @Description: 执行功能号实现
     * @author: fallsea
     * @date: 2017年10月22日 下午9:13:47
     * @param func
     * @param paramMap
     * @param systemParamMap
     * @return
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws ClassNotFoundException 
     */
    private Result doClass(Func func,DataMap paramMap,DataMap systemParamMap) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        try
        {
            //实例化功能号
            BaseFunction baseFunction = (BaseFunction) Class.forName(func.getResourceClass()).newInstance();
            
            //验证入参
            String msg = verify(paramMap,systemParamMap,func.getFuncParamList());
            if (StringHelper.isNotEmpty(msg))
            {
                throw new InvokeException(ErrorEnum.FUNC_NO_ERROR_CUSTOM.getCode(), msg);
            }
            
            baseFunction.setParamMap(paramMap);
            baseFunction.setSystemParamMap(systemParamMap);
            return baseFunction.execute();
        }
        catch (ClassNotFoundException e)
        {
            throw e;
        }
        catch (InstantiationException e)
        {
            throw e;
        }
        catch (IllegalAccessException e)
        {
            throw e;
        }
    }
    
    /**
     * @Description: 参数验证
     * @author: fallsea
     * @date: 2017年10月22日 下午9:14:10
     * @param paramMap 业务参数
     * @param systemParamMap 系统参数
     * @return
     */
    private String verify(DataMap paramMap,DataMap systemParamMap,List<FuncParam> funcParamList)
    {
        if(null!=funcParamList && !funcParamList.isEmpty())
        {
            for (FuncParam funcParam : funcParamList)
            {
                //验证参数
                String key = funcParam.getId();
                
                //处理参数值
                Object value = FunctionHelper.getParamValue(funcParam.getValue(),paramMap,systemParamMap);
                if(null!=value)
                {
                	paramMap.put(key, value);//改变参数值
                }
                
                //处理参数默认值,当默认值不为空，参数为空，处理
                if(StringHelper.isNotBlank(funcParam.getDefaultValue()) && StringHelper.isBlank(paramMap.get(key)))
                {
                    value = FunctionHelper.getParamValue(funcParam.getDefaultValue(),paramMap,systemParamMap);
                    if(StringHelper.isNotBlank(value))
                    {
                        paramMap.put(key, value);//改变参数值
                    }
                }
                
                //验证格式
                String msg = verify(funcParam,paramMap.getObject(funcParam.getId()));
                if(StringHelper.isNotEmpty(msg))
                {
                    return msg;
                }
                
                //转换
                String convertType = funcParam.getConvertType();
                if(StringHelper.isNotBlank(convertType)){
                	if(StringHelper.isNotBlank(paramMap.get(key))){
                		value = FunctionHelper.convertParamValue(convertType, paramMap.get(key));
                		paramMap.put(key, value);//改变参数值
                	}else{
                		paramMap.remove(key);//类型不匹配移除
                	}
                }
            }
        }
        return null;
    }
    
    
    /**
     * @Description: 参数验证
     * @author: fallsea
     * @date: 2017年10月22日 下午9:14:43
     * @param value 值
     * @return true成功 false失败
     */
    private String verify(FuncParam funcParam,Object value)
    {
        String name=funcParam.getName();
        if(StringHelper.isBlank(name))
        {
            name=funcParam.getId();
        }
        if("1".equals(funcParam.getNotEmpty()) && StringHelper.isBlank(value))
        {
             return "["+name+"]不能为空";
        }
        if(funcParam.getMaxLength()>0 && StringHelper.isNotBlank(value) && StringHelper.length(value.toString())>funcParam.getMaxLength())
        {
            return "["+name+"]超出最大长度"+funcParam.getMaxLength();
        }
        
        if(funcParam.getMinLength()>0 && ((StringHelper.isNotBlank(value) && StringHelper.length(value.toString())<funcParam.getMinLength()) || StringHelper.isBlank(value)))
        {
            return "["+name+"]小于最小长度"+funcParam.getMinLength();
        }
        
        boolean b = true;
        if(StringHelper.isNotBlank(funcParam.getVerifyType()) && StringHelper.isNotBlank(value))
        {
            
            switch (funcParam.getVerifyType())
            {
                case "DATE":
                	if(!(value instanceof Date)){
                		if(!StringHelper.isDate(value.toString()))
                        {
                            b = false;
                        }
                	}
                    
                    break;
                case "DATETIME":
                	if(!(value instanceof Date)){
                		if(!StringHelper.isDateTime(value.toString()))
                		{
                			b = false;
                		}
                	}
                    break;
                case "TIME":
                	if(!(value instanceof Date)){
                		if(!StringHelper.isTime(value.toString()))
                		{
                			b = false;
                		}
                	}
                    break;
                case "ALPHANUMERIC":
                    if(!StringHelper.isAlphaNumeric(value.toString()))
                    {
                        b = false;
                    }
                    break;
                case "EMAIL":
                    if(!StringHelper.isEmail(value.toString()))
                    {
                        b = false;
                    }
                    break;
                case "NUMERIC":
                	if(value instanceof String){
                		if(!StringHelper.isNumeric(value.toString()))
                		{
                			b = false;
                		}
                	}else if(!(value instanceof Integer) && !(value instanceof Long)){
                		b = false;
                	}
                    break;
                case "NUMBERFLOAT":
                	
                	if(value instanceof String){
                		if(!StringHelper.isNumberFloat(value.toString()))
                		{
                			b = false;
                		}else if(!(value instanceof Integer) && !(value instanceof Long  && !(value instanceof Float) && !(value instanceof Double))){
                    		b = false;
                    	}
                	}
                    break;
                case "MOBILE":
                    if(!StringHelper.isMobile(value.toString()))
                    {
                        b = false;
                    }
                    break;
                case "POSTALCODE":
                    if(!StringHelper.isPostalCode(value.toString()))
                    {
                        b = false;
                    }
                    break;
                case "URL":
                    if(!StringHelper.isUrl(value.toString()))
                    {
                        b = false;
                    }
                    break;
                case "CARDID":
                    if(!StringHelper.isCardID(value.toString()))
                    {
                        b = false;
                    }
                    break;
                case "BANKCODE":
                    if(!StringHelper.isBankCode(value.toString()))
                    {
                        b = false;
                    }
                    break;
            }
            
            if(!b)
            {
                return "["+name+"]格式不正确";
            }
            
        }
        return null;
    }
    
    /**
	 * 驼峰转下划线
	 * @param str
	 * @return
	 */
	public static String underscoreName(String camelCaseName) {  
        StringBuffer result = new StringBuffer();  
        if (camelCaseName != null && camelCaseName.length() > 0) {  
            result.append(camelCaseName.substring(0, 1).toLowerCase());  
            for (int i = 1; i < camelCaseName.length(); i++) {  
                char ch = camelCaseName.charAt(i);  
                if (Character.isUpperCase(ch)) {  
                    result.append("_");  
                    result.append(Character.toLowerCase(ch));  
                } else {  
                    result.append(ch);  
                }  
            }  
        }  
        return result.toString();  
    }  

    
}
