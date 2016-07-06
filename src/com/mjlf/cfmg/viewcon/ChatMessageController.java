package com.mjlf.cfmg.viewcon;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.ChatMessage;
import com.mjlf.cfmg.entity.User;
import com.mjlf.cfmg.service.ChatMessageService;
import com.mjlf.cfmg.service.UserService;
import com.sun.mail.handlers.message_rfc822;

@Controller
@RequestMapping("cfmg")
public class ChatMessageController {
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/toReadInfoPage.do",method=RequestMethod.GET)
	public String toReadInfoPage(HttpServletRequest request,HttpServletResponse response){
		return "/all/notreadInfo";
	}
	
	@RequestMapping(value="/addChatMessage.do", method=RequestMethod.POST)
	public void addChatMessage(HttpServletRequest request, HttpServletResponse response){
		try {
			ChatMessage chatMessage = new ChatMessage();
			Time time = new Time(new Date().getTime());
			chatMessage.setMessagetime(time);
			chatMessage.setFromUserId(Long.parseLong(request.getParameter("fromUserId")));
			chatMessage.setToUserId(Long.parseLong(request.getParameter("toUserId")));
			chatMessage.setData(request.getParameter("messageData"));
			chatMessage.setState(0);
			chatMessageService.add(chatMessage);
			response.getWriter().write("Y");
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/getInfoByState.do", method=RequestMethod.POST)
	public void getInfoByState(HttpServletRequest request, HttpServletResponse response){
		try {
			long fromUserId = Long.parseLong(request.getParameter("fromUserId"));
			long toUserId = Long.parseLong(request.getParameter("toUserId"));
			int state = Integer.parseInt(request.getParameter("state"));
			List<ChatMessage> chatMessages = chatMessageService.load(fromUserId, toUserId, state);
			JSONArray json = new JSONArray(chatMessages);
			response.getWriter().write(json+"");
		} catch (Exception e) {
			
		}
	}
	
	@RequestMapping(value="/getInfoBynotread.do", method=RequestMethod.POST)
	public void getInfoBynotread(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginedUser");
			List<ChatMessage> chatMessages = chatMessageService.notread(user.getId(),0);
			if(chatMessages != null){
				response.getWriter().write(chatMessages.size());
			}else{
				response.getWriter().write("0");
			}
		} catch (Exception e) {
			try {
				response.getWriter().write("0");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/loadChatMessage.do", method=RequestMethod.POST)
	public void loadChatMessage(HttpServletRequest request, HttpServletResponse response){
		try {
			long fromUserId = Long.parseLong(request.getParameter("fromUserId"));
			long toUserId = Long.parseLong(request.getParameter("toUserId"));
			
			List<ChatMessage> chatMessages = chatMessageService.load(fromUserId, toUserId);
			for(int i = 0 ; i < chatMessages.size(); i++){
				ChatMessage chatMessage = chatMessages.get(i);
				chatMessage.setState(1);
				chatMessageService.upload(chatMessage);
			}
			JSONArray json = new JSONArray(chatMessages);
			response.getWriter().write(json+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/getalluserforinfo.do", method=RequestMethod.POST)
	public void getalluserforinfo(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginedUser");
			
			List<ChatMessage> list = chatMessageService.loadFrom(user.getId());
			list.addAll(chatMessageService.loadTo(user.getId()));
			
			List<Long> list2 = new ArrayList<Long>();
			for(int i = 0; i < list.size(); i++){
				ChatMessage chatMessage = list.get(i);
				if(chatMessage.getToUserId() == user.getId()){
					long id = chatMessage.getFromUserId();
					if(list2.contains(id)){
						
					}else if(chatMessage.getFromUserId() == user.getId()){
						list2.add(id);
					}
				}else{
					long id = chatMessage.getToUserId();
					if(list2.contains(id)){
						
					}else{
						list2.add(id);
					}
				}
			}
			
			List<User> list3 = new ArrayList<User>();
			for(int i = 0; i < list2.size(); i++){
				User user1 = userService.load(list2.get(i));
				List<ChatMessage> chatMessages = chatMessageService.load(user1.getId(), user.getId(), 0);
				user1.setNum(chatMessages.size());
				list3.add(user1);
			}
			JSONArray json = new JSONArray(list3);
			response.getWriter().write(""+json);
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
