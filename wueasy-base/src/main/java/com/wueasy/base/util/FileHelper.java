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

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @Description: 文件工具类
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2017年10月22日 下午8:44:10
 */
public class FileHelper
{
    
    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);
    
    public static final String PROTOCOL_JAR = "jar";
    
    public static final String PROTOCOL_FILE = "file";
    
    /**默认编码**/
    public static final String DEFAULT_ENCODING = "UTF-8";
    
    
    /**
     * @Description: 规范化路径，合并其中的多个分隔符为一个,并转化为本地系统路径格式
     * @author: fallsea
     * @date: 2017年10月22日 下午8:44:19
     * @param filepath 文件路径
     * @return
     */
    public static String normalize(String filepath)
    {
        return FilenameUtils.normalize(filepath);
    }
    
    /**
     * @Description: 获取文件mime类型
     * @author: fallsea
     * @date: 2017年10月22日 下午8:44:28
     * @param file
     * @return mime类型
     */
    public static String getMimeType(File file)
    {
        if(null==file || !file.isFile())
        {
            return "";
        }
        try
        {
            return new Tika().detect(file);
        }
        catch (IOException e)
        {
            return "";
        }
    }
    
    
    /**
     * @Description: 获取文件路径
     * @author: fallsea
     * @date: 2017年10月22日 下午8:44:44
     * @param path 文件路径
     * @return
     */
    public static URL getURL(String path)
    {
        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        
        try
        {
            //将加载多个绝对匹配的所有Resource  
            //将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分  
            //然后进行遍历模式匹配  
            Resource[] resources = (Resource[]) resolver.getResources("classpath*:"+path);
            
            if ( resources != null )
            {
                URL url = null;
                
                for (Resource resource : resources)
                {
                    if(url == null)
                    {
                        url = resource.getURL();
                    }
                    if ( FileHelper.PROTOCOL_FILE.equals(url.getProtocol()) )//读取本地classpath的消息资源文件
                    {
                        url = resource.getURL();
                        break;
                    }
                    
                }
                return url;
            }
        }
        catch (IOException e)
        {
        	logger.error("",e);
        }
        return null;
    }
    
    /**
     * @Description: 创建文件夹和文件
     * @author: fallsea
     * @date: 2018年4月21日 上午11:03:53
     * @param filePathName
     * @return
     */
    public static boolean createNewFile(String filePathName)
    {
      String filePath = getFullPath(filePathName);

      if (!exists(filePath))
      {
        if (!createDirectory(filePath))
        {
          return false;
        }
      }

      try
      {
        File file = new File(filePathName);
        return file.createNewFile();
      }
      catch (IOException ex)
      {
        logger.error("创建文件出错", ex);
      }return false;
    }
    
    /**
     * @Description: 从完整文件名获取完整路径，这是前缀+路径
     * @author: fallsea
     * @date: 2018年4月21日 上午11:03:43
     * @param filename
     * @return
     */
    public static String getFullPath(String filename)
    {
      return FilenameUtils.getFullPath(filename);
    }
    
    /**
     * @Description: 判断文件是否存在
     * @author: fallsea
     * @date: 2018年4月21日 上午11:03:02
     * @param filePath 文件路径
     * @return
     */
    public static boolean exists(String filePath)
    {
      File file = new File(filePath);
      return file.exists();
    }
    
    /**
     * @Description: 创建目录
     * @author: fallsea
     * @date: 2018年4月21日 上午11:02:51
     * @param directory
     * @return
     */
    public static boolean createDirectory(String directory)
    {
      try
      {
        FileUtils.forceMkdir(new File(directory));
        return true;
      }
      catch (IOException ex)
      {
        logger.error("创建目录出错", ex);
      }
      return false;
    }
    
    /**
     * @Description: 文件写入内容
     * @author: fallsea
     * @date: 2018年4月21日 上午11:02:24
     * @param filePath 文件路径
     * @param data 内容
     * @return
     */
    public static boolean writeToFile(String filePath, byte[] data)
    {
      try
      {
        FileUtils.writeByteArrayToFile(new File(filePath), data);
        return true;
      }
      catch (IOException ex)
      {
        logger.error("写文件出错", ex);
      }
      return false;
    }

    /**
     * @Description: 文件写入内容
     * @author: fallsea
     * @date: 2018年4月21日 上午11:01:54
     * @param filePath 文件路径
     * @param data 内容
     * @return
     */
    public static boolean writeToFile(String filePath, String data)
    {
      return writeToFile(filePath, data, DEFAULT_ENCODING);
    }

    /**
     * @Description: 文件写入内容
     * @author: fallsea
     * @date: 2018年4月21日 上午10:59:24
     * @param filePath 文件路径
     * @param data 内容
     * @param encoding 编码
     * @return
     */
    public static boolean writeToFile(String filePath, String data, String encoding)
    {
      try
      {
        if (StringHelper.isEmpty(encoding))
        {
        	encoding = DEFAULT_ENCODING;
        }
        FileUtils.writeStringToFile(new File(filePath), data, encoding);
        return true;
      }
      catch (IOException ex)
      {
        logger.error("写文件出错", ex);
      }
      return false;
    }

}
