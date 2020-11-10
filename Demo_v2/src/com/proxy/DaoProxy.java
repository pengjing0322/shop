package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DaoProxy implements InvocationHandler{
	
	Object obj;
	
	//方法：接受被代理对象，动态的产生当前的代理对象
	public Object binding(Object obj) {
		this.obj=obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		long start=System.currentTimeMillis();
		Object result=method.invoke(obj, args); 
		long end=System.currentTimeMillis();
		System.out.println(method.getName()+"所用时:"+(end-start)+"ms");
		return result;
	}
}
