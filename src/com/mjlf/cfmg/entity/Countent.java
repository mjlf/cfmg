package com.mjlf.cfmg.entity;

import java.util.Date;

public class Countent {
	private long id;
	private long userId;
	private long messageId;
	private String info;
	private Date writeTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	@Override
	public String toString() {
		return "Countent [id=" + id + ", userId=" + userId + ", messageId=" + messageId + ", info=" + info
				+ ", writeTime=" + writeTime + "]";
	}
	
}	
