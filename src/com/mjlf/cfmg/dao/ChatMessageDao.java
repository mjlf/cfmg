package com.mjlf.cfmg.dao;

import java.sql.Time;
import java.util.List;

import com.mjlf.cfmg.entity.ChatMessage;

public interface ChatMessageDao {
	public void add(ChatMessage chatMessage);
	public void clear();
	public List<ChatMessage> load();
	public List<ChatMessage> loadFrom(long fromUserId);
	public List<ChatMessage> loadTo(long toUserId);
	public List<ChatMessage> load(long fromUserId, long toUserId);
	public List<ChatMessage> loadByTime(Time startTime, Time endTime, long fromUserId, long toUserId);
	public void delete(Time startTime, Time endTime, long fromUserId, long toUserId);
	List<ChatMessage> load(long fromUserId, long toUserId, int state);
	List<ChatMessage> notread(long toUserId, int state);
	void upload(ChatMessage chatMessage);
}
