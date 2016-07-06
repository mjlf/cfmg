package com.mjlf.cfmg.entity;

import java.util.Date;

public class LibraryPeople {
	protected long id;
	protected String name;
	protected String phone;
	protected String email;
	protected String sign_inCode;
	protected int isSign_in;
	protected Date sign_inTime;
	protected String sign_inPassword;
	protected long libraryBookId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSign_inCode() {
		return sign_inCode;
	}

	public void setSign_inCode(String sign_inCode) {
		this.sign_inCode = sign_inCode;
	}

	public int getIsSign_in() {
		return isSign_in;
	}

	public void setIsSign_in(int isSign_in) {
		this.isSign_in = isSign_in;
	}

	public Date getSign_inTime() {
		return sign_inTime;
	}

	public void setSign_inTime(Date sign_inTime) {
		this.sign_inTime = sign_inTime;
	}

	public String getSign_inPassword() {
		return sign_inPassword;
	}

	public void setSign_inPassword(String sign_inPassword) {
		this.sign_inPassword = sign_inPassword;
	}

	public long getLibraryBookId() {
		return libraryBookId;
	}

	public void setLibraryBookId(long libraryBookId) {
		this.libraryBookId = libraryBookId;
	}

	@Override
	public String toString() {
		return "LibraryPeople [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", sign_inCode="
				+ sign_inCode + ", isSign_in=" + isSign_in + ", Sign_inTime=" + sign_inTime + "]";
	}
}
