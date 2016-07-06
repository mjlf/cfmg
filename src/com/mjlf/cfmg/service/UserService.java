package com.mjlf.cfmg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjlf.cfmg.daoImp.UserDaoImp;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.entity.User;

@Service
public class UserService {
	@Autowired
	private UserDaoImp daoImp;

	public void add(User user){
		daoImp.add(user);
	}
	public void update(User user){
		daoImp.update(user);
	}
	public void delete(long id){
		daoImp.delete(id);
	}
	public User load(long id){
		return daoImp.load(id);
	}
	public List<User> load(){
		return daoImp.load();
	}
	public PageValue getPageValue(PageValue pageValue){
		return daoImp.pageLoad(pageValue);
	}
	public PageValue getPageValueisAdmin(PageValue pageValue){
		return daoImp.pageLoadIsadmin(pageValue);
	}
	public PageValue getPageValueNotisAdmin(PageValue pageValue){
		return daoImp.pageLoadNotIsAdmin(pageValue);
	}
	public boolean isContainUsername(String username){
		return daoImp.isContainUsername(username);
	}
	public boolean isContainPhone(String phone){
		return daoImp.isContainPhone(phone);
	}
	public boolean isContainEmail(String email){
		return daoImp.isContainEmail(email);
	}
	public boolean login(User user){
		return daoImp.login(user);
	}
	public User findbyName(String username){
		return daoImp.findbyName(username);
	}
}	
