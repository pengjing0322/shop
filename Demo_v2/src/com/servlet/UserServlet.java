package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.PageInfo;
import com.beans.User;
import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.proxy.DaoProxy;
import com.util.PageUtil;

public class UserServlet extends HttpServlet {
	
	DaoProxy tp=new DaoProxy();
	UserDao userDao=(UserDao) tp.binding(new UserDaoImpl());
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag=request.getParameter("flag");
		//登陆
		if("login".equals(flag)) {
			this.login(request, response);
		}
		//注册
		if("register".equals(flag)) {
			this.register(request, response);
		}
		//查询所有
		if("queryAllUser".equals(flag)) {
			this.queryAllUser(request, response);
		}
		//查询某个
		if("queryUserById".equals(flag)) {
			this.queryUserById(request, response);
		}
		//删除某个
		if("deleteUserById".equals(flag)) {
			this.deleteUserById(request, response);
		}
		//修改
		if("updateUserById".equals(flag)) {
			this.updateUserById(request, response);
		}
		if("zhuxiao".equals(flag)) {
			this.zhuxiao(request, response);
		}
	}

	
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		//接受用户名和密码
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String cookie=request.getParameter("cookie");
		System.out.println("====="+cookie);
		
		if(cookie!=null) {
			Cookie cName=new Cookie("userName",userName);
			Cookie cPwd=new Cookie("password",password);
			cName.setMaxAge(10);  //保存一周
			cPwd.setMaxAge(15);
			response.addCookie(cName);
			response.addCookie(cPwd);
		}
		//用来调用持久层或者业务层
		User user=userDao.login(userName, password);
		//判断返回值，来进行控制
		if(user!=null) {
			request.getSession().setAttribute("user", user);
			//登陆成功，进入主页面
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}else {
			request.setAttribute("msg", "对不起，用户名和密码错误，请重新登陆");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
	}
	
	public void queryUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String idStr=request.getParameter("id");
		
		User user=userDao.queryUserById(Integer.parseInt(idStr));
		
		request.setAttribute("pageIndex", request.getParameter("pageIndex"));
		request.setAttribute("user", user);
		request.getRequestDispatcher("update.jsp").forward(request, response);
	}
	
	public void queryAllUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int pageIndex=1;
		String pageIndexStr=request.getParameter("pageIndex");
		if(pageIndexStr!=null) {
			pageIndex=Integer.parseInt(pageIndexStr);
		}
		//调用持久层
		int rowCount=userDao.getRowCount();
		
		PageInfo pageInfo=PageUtil.getPageInfo(PageUtil.PAGESIZE, pageIndex, rowCount);
		
		List<User> userList=userDao.queryAllUser(pageInfo);
		
		request.setAttribute("userList", userList);
		request.setAttribute("pageInfo", pageInfo);
		request.getRequestDispatcher("query.jsp").forward(request, response);
		
	}
	
	public void updateUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//接受用户名和密码
		String idStr=request.getParameter("id");
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		
		//调用持久层
		if(userDao.updateUserById(Integer.parseInt(idStr), userName, password)) {
			request.setAttribute("msg", "修改成功！");
			//调用查询方法
			this.queryAllUser(request, response);
			
		}else {
			request.setAttribute("msg", "修改失败！");
			User user=userDao.queryUserById(Integer.parseInt(idStr));
			request.setAttribute("user", user);
			request.getRequestDispatcher("update.jsp").forward(request, response);
			/*
			//调用回显
			this.queryUserById(request, response);
			*/
		}
	}
	
	public void deleteUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//接受用户名和密码
		String idStr=request.getParameter("id");
		
		//调用持久层
		if(userDao.deleteUserById(Integer.parseInt(idStr))) {
			request.setAttribute("msg", "删除成功！");
		}else {
			request.setAttribute("msg", "删除失败！");
		}
		//调用查询所有
		this.queryAllUser(request, response);
		
	} 
	
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//接受用户名和密码
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		
		//调用持久层
		//通过返回值进行判断
		if(userDao.register(userName, password)) {
			//提示？
			request.setAttribute("msg", "注册成功！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			//提示？
			request.setAttribute("msg", "注册失败！");
			request.getRequestDispatcher("reg.jsp").forward(request, response);
		}
	}
	
	public void zhuxiao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().removeAttribute("user");
		response.sendRedirect(request.getContextPath()+"/login.jsp?f=2");
	}
	
	
}
