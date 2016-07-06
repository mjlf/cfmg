package com.mjlf.cfmg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjlf.cfmg.daoImp.ChatMessageDaoImpl;
import com.mjlf.cfmg.entity.ChatMessage;
import com.mjlf.cfmg.entity.Library;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageDaoImpl chatMessageDaoImpl;
	
	public void add(ChatMessage chatMessage){
		chatMessageDaoImpl.add(chatMessage);
	}
	
	public void upload(ChatMessage chatMessage){
		chatMessageDaoImpl.upload(chatMessage);
	}
	
	public List<ChatMessage> load(long fromUserId, long toUserId){
		return chatMessageDaoImpl.load(fromUserId, toUserId);
	}
	
	public List<ChatMessage> notread(long toUserid, int state){
		return chatMessageDaoImpl.notread(toUserid, state);
	}
	
	public List<ChatMessage> load(long fromUserId, long toUserId, int state){
		return chatMessageDaoImpl.load(fromUserId, toUserId, state);
	}
	
	public List<ChatMessage> loadTo(long toUserid){
		return chatMessageDaoImpl.loadTo(toUserid);
	}
	
	public List<ChatMessage> loadFrom(long fromUserId){
		return chatMessageDaoImpl.loadFrom(fromUserId);
	}
	
}
