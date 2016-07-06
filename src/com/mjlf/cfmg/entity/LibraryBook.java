package com.mjlf.cfmg.entity;

import java.util.Date;

/**
 * 会议室预定类
 * @author mjlf
 *
 */
public class LibraryBook {
	protected long id;
	protected long bookUserId;
	protected long libraryId;
	protected Date bookTime;
	protected Date startTime;
	protected Date endTime;
	protected int peopleNum;
	protected String startTimeStr;
	protected String endTimeStr;
	protected String length;
	protected int state;
	protected long adminUserId;
	protected String address;
	
	protected Library library;
	protected User user;
	
	protected String nameBookUser;
	protected int libraryPeopleNum;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBookUserId() {
		return bookUserId;
	}
	public void setBookUserId(long bookUserId) {
		this.bookUserId = bookUserId;
	}
	public long getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(long libraryId) {
		this.libraryId = libraryId;
	}
	public Date getBookTime() {
		return bookTime;
	}
	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}
	
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getNameBookUser() {
		return nameBookUser;
	}
	public void setNameBookUser(String nameBookUser) {
		this.nameBookUser = nameBookUser;
	}
	public int getLibraryPeopleNum() {
		return libraryPeopleNum;
	}
	public void setLibraryPeopleNum(int libraryPeopleNum) {
		this.libraryPeopleNum = libraryPeopleNum;
	}
	
	public long getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(long adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Library getLibrary() {
		return library;
	}
	public void setLibrary(Library library) {
		this.library = library;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "LibraryBook [id=" + id + ", bookUserId=" + bookUserId + ", libraryId=" + libraryId + ", bookTime="
				+ bookTime + ", startTime=" + startTime + ", endTime=" + endTime + ", peopleNum=" + peopleNum
				+ ", startTimeStr=" + startTimeStr + ", endTimeStr=" + endTimeStr + ", length=" + length + ", state="
				+ state + ", adminUserId=" + adminUserId + ", address=" + address + ", library=" + library + ", user="
				+ user + ", nameBookUser=" + nameBookUser + ", libraryPeopleNum=" + libraryPeopleNum + "]";
	}
	
}
