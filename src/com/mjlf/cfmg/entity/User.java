package com.mjlf.cfmg.entity;

/**
 * �û���
 * @author mjlf
 *
 */
public class User {
	private long id;
	private String username;
	private String password;
	private String nickname;
	private String email;
	private String phone;
	private String admin="0";
	private String isOnline="0";
	private String count;
	private long num;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	public String getNickname(){
		return nickname;
	}
	
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", phone=" + phone + ", admin=" + admin + ", isOnline=" + isOnline + "]";
	}
	
}
