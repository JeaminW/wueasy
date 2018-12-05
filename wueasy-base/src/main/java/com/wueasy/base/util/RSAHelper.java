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

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wueasy.base.config.RsaConfig;
import com.wueasy.base.exception.InvokeException;

/**
 * @Description: rsa加解密工具
 * @Copyright: 2018 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.0
 * @date: 2018年1月23日 下午10:05:52
 */
public class RSAHelper {
	
	private static Logger logger = LoggerFactory.getLogger(RSAHelper.class);

	public static final String KEY_ALGORITHM = "RSA";
	
	private static volatile String privateKey;

	private static volatile String publicKey;
	
	static
	{
		RsaConfig rsaConfig = SpringHelper.getBean("wueasyRsaConfig", RsaConfig.class);
		publicKey = rsaConfig.getPublicKey();
		privateKey = rsaConfig.getPrivateKey();
	}
	
	/**
	 * @Description: 解密
	 * @author: fallsea
	 * @date: 2018年1月23日 下午10:34:44
	 * @param data
	 * @return
	 */
	public static String decrypt(String data){
		return decrypt(data,privateKey);
	}
	
	/**
	 * @Description: 解密
	 * @author: fallsea
	 * @date: 2018年1月23日 下午10:27:15
	 * @param data 
	 * @param key
	 * @return
	 */
	public static String decrypt(String data, String key){
		if(StringHelper.isBlank(data) || StringHelper.isBlank(key)){
			throw new InvokeException(-1,"数据为空!");
		}
		try {
			// 对密钥解密
			byte[] keyBytes =Base64Helper.decodeByte(key);
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(cipher.doFinal(Base64Helper.decodeByte(data)));
		} catch (Exception e) {
			logger.error("解密失败",e);
			throw new InvokeException(-1,"解密失败");
		}
	}


	/**
	 * @Description: 加密
	 * @author: fallsea
	 * @date: 2018年1月23日 下午10:35:15
	 * @param data
	 * @return
	 */
	public static String encrypt(String data){
		return encrypt(data, publicKey);
	}
	/**
	 * @Description: 加密
	 * @author: fallsea
	 * @date: 2018年1月23日 下午10:28:27
	 * @param data
	 * @param key
	 * @return
	 */
	public static String encrypt(String data, String key){
		
		try {
			// 对公钥解密
			byte[] keyBytes = Base64Helper.decodeByte(key);
			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);
			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return new String(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			logger.error("加密失败",e);
			throw new InvokeException(-1,"加密失败");
		}
	}
}
