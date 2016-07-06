package com.mjlf.cfmg.dao;

import java.util.List;

import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.entity.User;

/**
 * �û�Dao�ӿ�
 * @author mjlf
 *
 */
public interface UserDao {
	public void add(User user);
	public void delete(long id);
	public boolean login(User user);
	public void update(User user);
	public User load(long id);
	public List<User> load();
	public PageValue pageLoad(PageValue pageValue);
	public boolean isContainUsername(String username);
	public boolean isContainPhone(String phone);
	public boolean isContainEmail(String email);
	public User findbyName(String name);
	PageValue pageLoadNotIsAdmin(PageValue pageValue);
	PageValue pageLoadIsadmin(PageValue pageValue);
}
