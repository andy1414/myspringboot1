package com.my.springboot.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class UrlConnectionUtil {
	
	public interface HandleConnection{
		void Handle(URLConnection conn);
	}
	
	public static String post(String urlString,String param,HandleConnection handler){
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);

			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.addRequestProperty("Content-Type", "application/" + "POST");
			conn.setReadTimeout(1500000);
			conn.setReadTimeout(1500000);
			
			if (param != null) {
				conn.setRequestProperty("Content-Length", Integer.toString(param.length()));
				if(handler!=null){
					handler.Handle(conn);				
				}
				conn.getOutputStream().write(param.getBytes("UTF-8"));
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			for (String line = null; (line = reader.readLine()) != null;) {
				sb.append(line + "\n");
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		try {
			result = URLDecoder.decode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String post(String urlString, String param) {
		return post(urlString,param,null);
	}
	
	public static String getUrl(String urlString) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);

			URLConnection conn = url.openConnection();

			conn.setReadTimeout(15000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			for (String line = null; (line = reader.readLine()) != null;) {
				sb.append(line + "\n");
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		try {
			result = URLDecoder.decode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}
}
