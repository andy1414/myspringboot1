package com.my.springboot.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.springboot.redis.JedisConnector_Cache;
import com.my.springboot.redis.RedisProperties;

@Controller
@RequestMapping(value = "/demo")
public class DemoController {
	@Autowired
	private JedisConnector_Cache jedisConnector_Cache;
	@Autowired
	private RedisProperties redisProperties;
	@RequestMapping("/test")
	public String welcome(Map<String, Object> model, HttpServletRequest request) {
		String k=request.getParameter("k");
		String v = request.getParameter("v");
		System.out.println(redisProperties.getCacheidle());
		jedisConnector_Cache.set(k,v);
		return "index";
	}
}
