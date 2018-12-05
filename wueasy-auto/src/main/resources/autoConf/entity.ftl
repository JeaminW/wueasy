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
package ${entityPackage};

import java.io.Serializable;
<#-- 导入包 -->
<#if typeSet?exists && (typeSet?size>0)>
	<#list typeSet as type>
import ${type};
	</#list>
</#if>

public class ${tableCamelCaseName} implements Serializable {

<#if tableList?exists && (tableList?size>0)>
	private static final long serialVersionUID = 1L;
	
  <#list tableList as table>
  	<#if (table.columnComment?? && table.columnComment!="")>
  	/**
	 * ${table.columnComment}
	 */
  	</#if>
	private ${table.type} ${table.camelCaseColumnName};
	
  </#list>
  
  <#list tableList as table>
  
<#-- get方法 -->
  	<#if (table.columnComment?? && table.columnComment!="")>
  	/**
	 * ${table.columnComment}
	 */
  	</#if>
	public ${table.type} get${table.camelCaseColumnName?cap_first}() {
		return ${table.camelCaseColumnName};
	}
	
<#-- set方法 -->
	<#if (table.columnComment?? && table.columnComment!="")>
  	/**
	 * ${table.columnComment}
	 */
  	</#if>
	public void set${table.camelCaseColumnName?cap_first}(${table.type} ${table.camelCaseColumnName}) {
		this.${table.camelCaseColumnName} = ${table.camelCaseColumnName};
	}
  </#list>
</#if>

}