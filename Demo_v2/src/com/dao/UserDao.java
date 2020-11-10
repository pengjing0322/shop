package com.dao;

import java.util.List;

import com.beans.PageInfo;
import com.beans.User;

public interface UserDao {

	public boolean deleteUserById(int id);
	public User login(String userName,String password);
	public List<User> queryAllUser(PageInfo pageInfo);
	public User queryUserById(int id);
	public boolean register(String userName,String password);
	public boolean updateUserById(int id,String userName,String password);
	
	public int getRowCount();
}
