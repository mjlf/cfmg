package com.mjlf.cfmg.entity;

import java.util.Date;

public class Message {
	private long id;
	private Date writeTime;
	private long userId;
	private String info;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", writeTime=" + writeTime + ", userId=" + userId + ", info=" + info + "]";
	}
}
