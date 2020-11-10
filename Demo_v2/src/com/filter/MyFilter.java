package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MyFilter implements Filter{

	//过滤器销毁的时候执行
	@Override
	public void destroy() {
		
	}

	//拦截到的时候执行
	@Override
	public void doFilter(ServletRequest request, ServletResponse resposne, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("我进入myfilter中了.....");
		HttpServletRequest req=(HttpServletRequest)request;
		String uri=req.getRequestURI();
		uri=uri.toUpperCase();
		if(!uri.endsWith(".JPG")) {
			chain.doFilter(request, resposne);  //放行
		}
		System.out.println("我出去myfilter中了.....");
	}
	
	//初始化执行
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
