package com.mjlf.cfmg.entity;

import java.util.Date;

public class ChatMessage {
	
	private long id;
	private Date messagetime;
	private long fromUserId;
	private long toUserId;
	private String data;
	private int state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getMessagetime() {
		return messagetime;
	}
	public void setMessagetime(Date messagetime) {
		this.messagetime = messagetime;
	}
	public long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}
	public long getToUserId() {
		return toUserId;
	}
	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "ChatMessage [id=" + id + ", messagetime=" + messagetime + ", fromUserId=" + fromUserId + ", toUserId=" + toUserId
				+ ", data=" + data + "]";
	}
	
}
