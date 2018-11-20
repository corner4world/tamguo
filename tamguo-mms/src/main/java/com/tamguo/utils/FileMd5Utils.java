package com.tamguo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

public class FileMd5Utils {

	/**
	 * 获取一个文件的md5值(可处理大文件)
	 * @return md5 value
	 */
	public static String getMD5(FileInputStream fileInputStream) {
		try {
			MessageDigest MD5 = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fileInputStream.read(buffer)) != -1) {
				MD5.update(buffer, 0, length);
			}
			return new String(Hex.encodeHex(MD5.digest()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fileInputStream != null){
					fileInputStream.close();
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
