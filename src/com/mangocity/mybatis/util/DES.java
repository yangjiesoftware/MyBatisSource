package com.mangocity.mybatis.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * 
* @ClassName: DES 
* @Description: TODO(des加密) 
* @author Syungen
* @date 2015年8月25日 下午2:50:36 
*
 */
public class DES {
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	public static String encryptDES(String encryptString, String encryptKey)
	{
		try
		{
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));
	
			return Base64.encode(encryptedData);
		}
		catch( Exception e )
		{
			return null;
		}
	}

	@SuppressWarnings("static-access")
	public static String decryptDES(String decryptString, String decryptKey)
	{
		try
		{
			byte[] byteMi = new Base64().decode(decryptString);
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte decryptedData[] = cipher.doFinal(byteMi);
	
			return new String(decryptedData,"utf-8");
		}
		catch( Exception e )
		{
			return null;
		}
	}
}
