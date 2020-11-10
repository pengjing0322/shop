package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.beans.PageInfo;
import com.beans.User;
import com.dao.UserDao;
import com.util.DBUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public boolean deleteUserById(int id) {
		if(DBUtil.update("delete from user where id=?", id)>0) {
			return true;
		}
		return false;
	}

	@Override
	public User login(String userName, String password) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		User user=null;
		try {
			conn=DBUtil.getConn();
			stm=conn.prepareStatement("select * from user where userName=? and password=?");
			stm.setString(1, userName);
			stm.setString(2, password);
			rs=stm.executeQuery();
			if(rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, stm, conn);
		}
		return user;
	}

	@Override
	public List<User> queryAllUser(PageInfo pageInfo) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		List<User> userList=new ArrayList<User>();
		User user=null;
		try {
			conn=DBUtil.getConn();
			stm=conn.prepareStatement("select * from user limit ?,?");
			stm.setInt(1, pageInfo.getBeginRow());
			stm.setInt(2, pageInfo.getPageSize());
			rs=stm.executeQuery();
			while(rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				userList.add(user);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, stm, conn);
		}
		return userList;
	}

	@Override
	public User queryUserById(int id) {
		return DBUtil.getList(User.class, "select * from user where id=?", id).get(0);
	}

	@Override
	public boolean register(String userName, String password) {
		Connection conn=null;
		PreparedStatement stm=null;
		try {
			conn=DBUtil.getConn();
			stm=conn.prepareStatement("insert into user(userName,password) values(?,?)");
			stm.setString(1, userName);
			stm.setString(2, password);
			if(stm.executeUpdate()>0) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(null, stm, conn);
		}
		return false;
	}

	@Override
	public boolean updateUserById(int id, String userName, String password) {
		if(DBUtil.update("update user set userName=?,password=? where id=?", userName,password,id)>0) {
			return true;
		}
		return false;
	}

	@Override
	public int getRowCount() {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		int rowCount=0;
		try {
			conn=DBUtil.getConn();
			stm=conn.prepareStatement("select count(*) from user");
			rs=stm.executeQuery();
			rs.next();
			rowCount=rs.getInt(1);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, stm, conn);
		}
		return rowCount;
	}

}
