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
package com.wueasy.auto.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wueasy.auto.entity.TableEntity;
import com.wueasy.base.util.StringHelper;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * @Description: 自动生成工具
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年2月1日 下午8:19:55
 */
public class AutoCodeUtil {
	
	
	private static Configuration cfg = null;
	
	private static String sysPath = "";
	
	
	static
	{
		cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		
		sysPath = System.getProperty("user.dir");
	}
	
	
	/**
	 * @Description: 生成javabean
	 * @author: fallsea
	 * @date: 2018年2月1日 下午9:21:37
	 * @param tableName
	 * @param list
	 * @param entityPackage
	 * @param sourcePath
	 */
	public static void autoEntity(String tableName,List<TableEntity> list,String entityPackage,String sourcePath){
		
		cfg.setClassForTemplateLoading(AutoCodeUtil.class, "/autoConf");
		
		//构造填充数据的Map 
        Map<String,Object> map = new HashMap<String,Object>(); 
        
		if(null!=list && !list.isEmpty()){
			Set<String> typeSet = new HashSet<String>();
			for (TableEntity tableEntity : list) {
				if("datetime".equals(tableEntity.getDataType())){
					typeSet.add("java.util.Date");
				}
			}
			map.put("typeSet", typeSet);
		}
		
		String tableCamelCaseName = StringHelper.camelCaseName2(tableName);
		
        map.put("tableList",list); 
        map.put("entityPackage", entityPackage);
        map.put("tableName",tableName);
        map.put("tableCamelCaseName",tableCamelCaseName);
        
		try {
			Template t = cfg.getTemplate("entity.ftl");
			
			File file = new File(sysPath+sourcePath+getPathPackage(entityPackage));
			if(!file.exists()){
				file.mkdirs();
			}
			file = new File(sysPath+sourcePath+getPathPackage(entityPackage)+tableCamelCaseName+".java");
			t.process(map, new FileWriter(file));
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void autoMapperJava(String tableName,List<TableEntity> list,String entityPackage,String mapperPackage,String sourcePath){
		
		cfg.setClassForTemplateLoading(AutoCodeUtil.class, "/autoConf");
		
		//构造填充数据的Map 
        Map<String,Object> map = new HashMap<String,Object>(); 
		
		String tableCamelCaseName = StringHelper.camelCaseName2(tableName);
		
        map.put("tableList",list); 
        map.put("mapperPackage", mapperPackage);
        map.put("entityPackage", entityPackage);
        map.put("tableName",tableName);
        map.put("tableCamelCaseName",tableCamelCaseName);
        
		try {
			Template t = cfg.getTemplate("mapperJava.ftl");
			
			File file = new File(sysPath+sourcePath+getPathPackage(mapperPackage));
			if(!file.exists()){
				file.mkdirs();
			}
			file = new File(sysPath+sourcePath+getPathPackage(mapperPackage)+tableCamelCaseName+"Mapper.java");
			t.process(map, new FileWriter(file));
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void autoMapperXml(String tableName,List<TableEntity> list,String entityPackage,String mapperPackage,String mapperXmlPackage,String sourcePath){
		
		cfg.setClassForTemplateLoading(AutoCodeUtil.class, "/autoConf");
		
		//构造填充数据的Map 
        Map<String,Object> map = new HashMap<String,Object>(); 
		
		String tableCamelCaseName = StringHelper.camelCaseName2(tableName);
		
        map.put("tableList",list); 
        map.put("mapperPackage", mapperPackage);
        map.put("entityPackage", entityPackage);
        map.put("tableName",tableName);
        map.put("tableCamelCaseName",tableCamelCaseName);
        
        String autoName = "";//自增字段
        //处理自增长字段
        for (TableEntity tableEntity : list) {
			if("auto_increment".equals(tableEntity.getExtra())){
				autoName = tableEntity.getCamelCaseColumnName();
			}
		}
        map.put("tableAutoName",autoName);
		try {
			Template t = cfg.getTemplate("mapperXml.ftl");
			
			File file = new File(sysPath+sourcePath+getPathPackage(mapperXmlPackage));
			if(!file.exists()){
				file.mkdirs();
			}
			file = new File(sysPath+sourcePath+getPathPackage(mapperXmlPackage)+tableCamelCaseName+"Mapper.xml");
			t.process(map, new FileWriter(file));
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	
	private static String getPathPackage(String packages){
		StringBuilder sb = new StringBuilder("/");
		String[] packagess = packages.split("\\.");
		for (int i = 0; i < packagess.length; i++) {
			sb.append(packagess[i]+"/");
		}
		return sb.toString();
	}
	
	
	
	

}
