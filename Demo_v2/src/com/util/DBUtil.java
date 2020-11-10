package com.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

public class DBUtil {

	private static String className;
	private static String url;
	private static String userName;
	private static String password;
	static {
		try {
			Properties prop=new Properties();
			InputStream in=DBUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties"); //这个相当于项目中地址
			prop.load(in);
			
			className=prop.getProperty("className");
			url=prop.getProperty("url");
			userName=prop.getProperty("userName");
			password=prop.getProperty("password");
			
			Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取连接
	 * @return
	 */
	public static Connection getConn() {
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭资源
	 * @param rs
	 * @param stm
	 * @param conn
	 */
	public static void close(ResultSet rs,Statement stm,Connection conn) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		if(stm!=null) {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close(); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 使用jdbc完成万能修改，返回影响的条数
	 * @param sql  要执行的sql语句
	 * @param args 占位符的参数
	 * @return
	 */
	public static int update(String sql,Object ... args) {
		Connection conn=null;
		PreparedStatement stm=null;
		int result=0;
		try {
			conn=DBUtil.getConn();
			stm=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++) {
				stm.setObject(i+1, args[i]);
			}
			result=stm.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(null, stm, conn);
		}
		return result;
	}
	
	public static <T> List<T> getList(Class<T> clazz,String sql,Object ...objects ){
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		T entity=null;
		List<T> list=new ArrayList<T>();
		try {
			conn=DBUtil.getConn();
			stm=conn.prepareStatement(sql);
			for(int i=0;i<objects.length;i++) {
				stm.setObject(i+1, objects[i]);
			}
			rs=stm.executeQuery();
			
			
			ResultSetMetaData data=rs.getMetaData();
			
			while(rs.next()) {
				entity=clazz.newInstance();  //user=new User();
				
				for(int i=0;i<data.getColumnCount();i++) {
					String columnName=data.getColumnName(i+1);
					Object columnValue=rs.getObject(columnName);
					
					Field f=clazz.getDeclaredField(columnName);
					f.setAccessible(true);
					String fieldType=f.getType().getSimpleName();
					String columnType=data.getColumnTypeName(i+1);
					if(fieldType.equalsIgnoreCase("String") && (columnType.equalsIgnoreCase("TIMESTAMP") || columnType.equalsIgnoreCase("DATETIME"))) {
						if(columnValue!=null) {
							f.set(entity, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)columnValue));  //user.setXXX("xxx")
						}
					}else {
						f.set(entity, columnValue);
					}
				}
				list.add(entity);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, stm, conn);
		}
		return list;
	}
}
