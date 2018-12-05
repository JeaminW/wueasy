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
package com.wueasy.cache.constants;
/**
 * @Description: 
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年4月5日 下午9:23:15
 */
public class CacheConstants {

	/**系统参数缓存节点路径**/
	public static final String PARAMETER_CACHE_NODE_PATH = "/wueasy/cache/parameter";
	
	/**zk监听缓存数据更新节点路径**/
	public static final String LISTENER_CACHE_UPDATE_NODE_PATH = "/wueasy/cache/update";
	
	/**zk模板任务新增节点路径**/
	public static final String TEMPLATE_TASK_NODE_PATH = "/wueasy/task/template/publish/plan";
	
	/**zk文章发布队列节点路径**/
	public static final String ARTICLE_PUBLISH_QUEUE_NODE_PATH = "/wueasy/task/template/publish/article";
	
}
