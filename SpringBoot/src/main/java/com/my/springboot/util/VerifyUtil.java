/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class VerifyUtil {
	public static String getSign(List<String> list) {
		StringBuffer sb = new StringBuffer("");

		for (int signString = 0; signString < list.size(); ++signString) {
			sb.append((String) list.get(signString));
		}

		String arg2 = toMD5(sb.toString());
		return arg2;
	}

	public static String toMD5(String str) {
		try {
			MessageDigest e = MessageDigest.getInstance("MD5");
			e.update(str.getBytes());
			byte[] byteDigest = e.digest();
			StringBuffer buf = new StringBuffer("");
			boolean i = false;

			for (int offset = 0; offset < byteDigest.length; ++offset) {
				int arg6 = byteDigest[offset];
				if (arg6 < 0) {
					arg6 += 256;
				}

				if (arg6 < 16) {
					buf.append("0");
				}

				buf.append(Integer.toHexString(arg6));
			}

			return buf.toString();
		} catch (NoSuchAlgorithmException arg5) {
			arg5.printStackTrace();
			return null;
		}
	}

	public static final String toMD5_Char(String s) {
		char[] hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
				'f' };

		try {
			byte[] e = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(e);
			byte[] md = mdInst.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;

			for (int i = 0; i < j; ++i) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 15];
				str[k++] = hexDigits[byte0 & 15];
			}

			return new String(str);
		} catch (Exception arg9) {
			arg9.printStackTrace();
			return null;
		}
	}
}