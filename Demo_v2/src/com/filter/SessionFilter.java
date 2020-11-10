package com.filter;

import java.io.IOException;

import javax.jws.WebService;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.User;

public class SessionFilter implements Filter{

	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		String uri=req.getRequestURI();
		if(uri.contains("reg.jsp") || uri.contains("login.jsp")) {
			chain.doFilter(request, response);
		}else {
			User user=(User)req.getSession().getAttribute("user");
			if(user!=null) {
				chain.doFilter(request, response);
			}else {
				//尽量用重定向
				resp.sendRedirect(req.getContextPath()+"/login.jsp?f=1");
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
