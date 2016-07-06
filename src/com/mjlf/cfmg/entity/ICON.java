package com.mjlf.cfmg.entity;

public class ICON {
	private long id;//图片id
	private long characterId; //会议室id
	private String pictureName; //图片名称
	private long type;//类型
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCharacterId() {
		return characterId;
	}
	public void setCharacterId(long characterId) {
		this.characterId = characterId;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "ICON [id=" + id + ", libraryId=" + characterId + ", pictureName=" + pictureName + ", type=" + type + "]";
	}
	
}
