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
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wueasy.auto.WueasyApplicationAuto;
import com.wueasy.auto.entity.TableEntity;
import com.wueasy.auto.mapper.TableMapper;
import com.wueasy.auto.util.AutoCodeUtil;

/**
 * @Description: 
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年11月1日 下午5:25:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=WueasyApplicationAuto.class)
public class ApplicationTests {

	@Autowired
    private TableMapper tableMapper;
	
	
	
	/**
	 * @Description: 
	 * @author: fallsea
	 * @date: 2018年1月30日 下午10:28:44
	 */
	@Test
    public void testObj() {
		String tableName = "sys_log";
		String entityPackage = "com.wueasy.admin.entity";
		String mapperPackage = "com.wueasy.admin.mapper";
		
		List<TableEntity> list = tableMapper.query(tableName);
		
		AutoCodeUtil.autoEntity(tableName, list,entityPackage, "/src/test/java");
		
		AutoCodeUtil.autoMapperJava(tableName, list, entityPackage, mapperPackage, "/src/test/java");
		
		AutoCodeUtil.autoMapperXml(tableName, list,entityPackage,mapperPackage,"mybatis.mapper.wueasy.sys", "/src/test/resources");
		
    }
	
	
}
