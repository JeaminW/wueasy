<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 
<![CDATA[
   wueasy - A Java Distributed Rapid Development Platform.
   Copyright (C) 2017-2018 wueasy.com
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as published
   by the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.
]]>
-->
<mapper namespace="${mapperPackage}.${tableCamelCaseName}Mapper">
  <resultMap id="BaseResultMap" type="${entityPackage}.${tableCamelCaseName}">
  	<#if tableList?exists && (tableList?size>0)>
        <#list tableList as table>
            <#if (table.columnKey?? && table.columnKey=="PRI")>
	 <id column="${table.columnName}" property="${table.camelCaseColumnName}" />
  			<#else>
	 <result column="${table.columnName}" property="${table.camelCaseColumnName}" />
  			</#if>
  		</#list>
  	</#if>
  </resultMap>
  
  <sql id="Base_Column_List">
  	<#if tableList?exists && (tableList?size>0)>
    	<#assign fs_index = 0 />
        <#list tableList as table>
	<#if fs_index ==1>,</#if>${table.columnName}
	<#assign fs_index = 1 />
		</#list>
    </#if>
  </sql>
  
  
  <select id="select" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${tableName} where 
    <#if tableList?exists && (tableList?size>0)>
    	<#assign fs_index = 0 />
        <#list tableList as table>
        	<#if (table.columnKey?? && table.columnKey=="PRI")>
	<#if fs_index ==1>and</#if> ${table.columnName} = <#if table.typeHandler??> ${r'#{'}${table.camelCaseColumnName}${r',typeHandler='}${table.typeHandler}${r'}'} <#else>${r'#{'}${table.camelCaseColumnName}${r'}'}</#if>
	<#assign fs_index = 1 />
        	</#if>
        </#list>
  	</#if>
  </select>
  
  <select id="query" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${tableName} where 1=1 
    <#if tableList?exists && (tableList?size>0)>
      <#list tableList as table>
      <#if (table.type?? && table.type=="String")>
      <if test="${table.camelCaseColumnName} != null and ${table.camelCaseColumnName} != ''">
      <#else>
      <if test="${table.camelCaseColumnName} != null">
      </#if>
        and ${table.columnName} = <#if table.typeHandler??> ${r'#{'}${table.camelCaseColumnName}${r',typeHandler='}${table.typeHandler}${r'}'} <#else>${r'#{'}${table.camelCaseColumnName}${r'}'}</#if>
      </if>
      </#list>
    </#if>

<#-- 排序处理 -->
    <#if tableList?exists && (tableList?size>0)>
    	<#assign fs_index = 0 />
        <#list tableList as table>
        	<#if (table.columnKey?? && table.columnKey=="PRI")>
	  <#if fs_index ==0>order by</#if><#if fs_index ==1>,</#if> ${table.columnName} desc
		<#assign fs_index = 1 />
  			</#if>
        </#list>
    </#if>
  </select>
  
  <delete id="delete">
    delete from ${tableName}
    <#if tableList?exists && (tableList?size>0)>
    where 
    	<#assign fs_index = 0 />
        <#list tableList as table>
        	<#if (table.columnKey?? && table.columnKey=="PRI")>
	 <#if fs_index ==1>and</#if> ${table.columnName} = <#if table.typeHandler??> ${r'#{'}${table.camelCaseColumnName}${r',typeHandler='}${table.typeHandler}${r'}'} <#else>${r'#{'}${table.camelCaseColumnName}${r'}'}</#if>
	<#assign fs_index = 1 />
  			</#if>
        </#list>
    </#if>
  </delete>
  
  
  <delete id="deleteMultiple">
    delete from ${tableName}
    <#if tableList?exists && (tableList?size>0)>
    where 
    	<#assign fs_index = 0 />
        <#list tableList as table>
        	<#if (table.columnKey?? && table.columnKey=="PRI")>
    <bind name="${table.camelCaseColumnName}s" value="_parameter.get('${table.camelCaseColumnName}').split(',')" />
    <#if fs_index ==1>and</#if>
    <foreach collection="${table.camelCaseColumnName}s" item="item" index="index" open="" close="" separator="OR">
      ${table.columnName} = <#if table.typeHandler??>${r'#{item'}${r',typeHandler='}${table.typeHandler}${r'}'} <#else>${r'#{item}'}</#if>
    </foreach>
	<#assign fs_index = 1 />
  			</#if>
        </#list>
    </#if>
  </delete>
  
  
  <#if tableAutoName?? >
  	<insert id="insert" useGeneratedKeys="true" keyProperty="${tableAutoName}">
  <#else>
  	<insert id="insert">
  </#if>
    insert into ${tableName} (
    <#if tableList?exists && (tableList?size>0)>
    	<#assign fs_index = 0 />
        <#list tableList as table>
	<#if fs_index ==1>,</#if>${table.columnName}
	<#assign fs_index = 1 />
		</#list>
    </#if>
    )
    values (
    <#if tableList?exists && (tableList?size>0)>
    	<#assign fs_index = 0 />
        <#list tableList as table>
	<#if fs_index ==1>,</#if> <#if table.typeHandler??> ${r'#{'}${table.camelCaseColumnName}${r',typeHandler='}${table.typeHandler}${r'}'} <#else>${r'#{'}${table.camelCaseColumnName}${r'}'}</#if>
	<#assign fs_index = 1 />
		</#list>
    </#if>
    )
  </insert>
  
  <update id="update">
    update ${tableName}
    set 
      <#if tableList?exists && (tableList?size>0)>
      	<#assign fs_index = 0 />
        <#list tableList as table>
        	<#if (table.columnKey?? && table.columnKey!="PRI")>
	<#if fs_index ==1>,</#if> ${table.columnName} = <#if table.typeHandler??> ${r'#{'}${table.camelCaseColumnName}${r',typeHandler='}${table.typeHandler}${r'}'} <#else>${r'#{'}${table.camelCaseColumnName}${r'}'}</#if>
	<#assign fs_index = 1 />
  			</#if>
        </#list>
      </#if>
    where
      <#if tableList?exists && (tableList?size>0)>
      	<#assign fs_index = 0 />
        <#list tableList as table>
        	<#if (table.columnKey?? && table.columnKey=="PRI")>
  <#if fs_index ==1>and</#if> ${table.columnName} = <#if table.typeHandler??> ${r'#{'}${table.camelCaseColumnName}${r',typeHandler='}${table.typeHandler}${r'}'} <#else>${r'#{'}${table.camelCaseColumnName}${r'}'}</#if>
  <#assign fs_index = 1 />
  			</#if>
        </#list>
      </#if>
  </update>
  
  
</mapper>