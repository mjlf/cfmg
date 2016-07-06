package com.mjlf.cfmg.entity;

import java.sql.Time;

/**
 * 会议室类
 * @author mjlf
 *
 *会议室编号  地址 容纳人数 开发时段 管理人  是否投影   是否视频会议
 */
public class Library {
	protected long id;					//会议室id
	protected String address;			//地址
	protected int people;				//容纳人数
	protected Time startTimeAtAM;		//上午放开始时间
	protected Time endTimeAtAM;			//上午开放结束时间
	protected Time startTimeAtPM;		//下午开放开始时间
	protected Time endTimeAtPM;			//下午开放结束时间
	protected long adminId;				//管理员id
	protected String isProjection = "1";		//是否投影
	protected String videoConferencing = "0";	//是否视频会议
	protected String disc;						//会议室描述
	protected String bookAndKnow;				//预定须知
	protected String adminName;
	protected int startnum;
	protected int endnum;
	protected Time checkStartTime;
	protected int checkadmin;
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public int getCheckadmin() {
		return checkadmin;
	}
	public void setCheckadmin(int checkadmin) {
		this.checkadmin = checkadmin;
	}
	public int getCheckamdin() {
		return checkadmin;
	}
	public void setCheckamdin(int checkamdin) {
		this.checkadmin = checkamdin;
	}
	public int getStartnum() {
		return startnum;
	}
	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}
	public int getEndnum() {
		return endnum;
	}
	public void setEndnum(int endnum) {
		this.endnum = endnum;
	}
	public Time getCheckStartTime() {
		return checkStartTime;
	}
	public void setCheckStartTime(Time checkStartTime) {
		this.checkStartTime = checkStartTime;
	}
	public Time getCheckEndTime() {
		return checkEndTime;
	}
	public void setCheckEndTime(Time checkEndTime) {
		this.checkEndTime = checkEndTime;
	}
	protected Time checkEndTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		this.people = people;
	}
	public Time getStartTimeAtAM() {
		return startTimeAtAM;
	}
	public void setStartTimeAtAM(Time startTimeAtAM) {
		this.startTimeAtAM = startTimeAtAM;
	}
	public Time getEndTimeAtAM() {
		return endTimeAtAM;
	}
	public void setEndTimeAtAM(Time endTimeAtAM) {
		this.endTimeAtAM = endTimeAtAM;
	}
	public Time getStartTimeAtPM() {
		return startTimeAtPM;
	}
	public void setStartTimeAtPM(Time startTimeAtPM) {
		this.startTimeAtPM = startTimeAtPM;
	}
	public Time getEndTimeAtPM() {
		return endTimeAtPM;
	}
	public void setEndTimeAtPM(Time endTimeAtPM) {
		this.endTimeAtPM = endTimeAtPM;
	}
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	public String getIsProjection() {
		return isProjection;
	}
	public void setIsProjection(String isProjection) {
		this.isProjection = isProjection;
	}
	public String getVideoConferencing() {
		return videoConferencing;
	}
	public void setVideoConferencing(String videoConferencing) {
		this.videoConferencing = videoConferencing;
	}
	public String getDisc() {
		return disc;
	}
	public void setDisc(String disc) {
		this.disc = disc;
	}
	public String getBookAndKnow() {
		return bookAndKnow;
	}
	public void setBookAndKnow(String bookAndKnow) {
		this.bookAndKnow = bookAndKnow;
	}
	@Override
	public String toString() {
		return "Library [id=" + id + ", address=" + address + ", people=" + people + ", startTimeAtAM=" + startTimeAtAM
				+ ", endTimeAtAM=" + endTimeAtAM + ", startTimeAtPM=" + startTimeAtPM + ", endTimeAtPM=" + endTimeAtPM
				+ ", adminId=" + adminId + ", isProjection=" + isProjection + ", videoConferencing=" + videoConferencing
				+ ", disc=" + disc + ", bookAndKnow=" + bookAndKnow + ", startnum=" + startnum + ", endnum=" + endnum
				+ ", checkStartTime=" + checkStartTime + ", checkadmin=" + checkadmin + ", checkEndTime=" + checkEndTime
				+ "]";
	}
	
}
