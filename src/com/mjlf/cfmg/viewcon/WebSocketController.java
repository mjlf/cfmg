package com.mjlf.cfmg.viewcon;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;

import com.mjlf.cfmg.daoimphib.UserDaoImpForHib;
import com.mjlf.cfmg.entity.User;
import com.mjlf.cfmg.service.UserService;

@ServerEndpoint("/cfmg/websocket.do")
public class WebSocketController {
	
    private static final Set<WebSocketController> connections = new CopyOnWriteArraySet<>();
 
    private Session session;
    private long id;
    private String nickname;
 
    @OnOpen
    public void start(Session session) {
        this.session = session;
        connections.add(this);
    }
 
    @OnClose
    public void end() {
       connections.remove(this);
    }
 
    @OnMessage
    public void incoming(String message) {
    	try {
    		if(message.startsWith("!!<<id>>!!:")){
    			long userId = Long.parseLong(message.split(":")[1]);
    			setNickname(userId);
    		}else if(message.startsWith("!!<<id:msg>>!!:")){
    			message = message.substring("!!<<id:msg>>!!:".length());
    			String[] all = message.split(":");
    			if(all.length >= 4){
    				broadcast(all[1]+":"+all[2]+":"+all[3], Long.parseLong(all[1]));
    			}
    		}else if(message.startsWith("!!<<id:bookLibrary>>!!:")){
    			long toUserId = Long.parseLong(message.split(":")[2]);
    			broadcast(message, toUserId);
    		}else if(message.startsWith("!!<<id:bookLibraryAccept>>!!")){
    			long toUserId = Long.parseLong(message.split(":")[2]);
    			broadcast(message, toUserId);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    @OnError
    public void onError(Throwable t) throws Throwable {
    	System.out.println(t.getMessage());
    }
 
    private static void broadcast(String msg, long toUserid) {
        for (WebSocketController client : connections) {
            try {
            	if(toUserid == -1){
            		client.session.getBasicRemote().sendText(msg);
            	}else if(client.id == toUserid){
            		synchronized (client) {
            			client.session.getBasicRemote().sendText(msg);
            			break;
            		}
            	}
            } catch (IOException e) {
            	e.printStackTrace();
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                }
            }
        }
    }
    
    private void logout (){
    	UserDaoImpForHib daoImpForHib = new UserDaoImpForHib();
    	User user = daoImpForHib.load(id);
    	user.setIsOnline("0");
    	daoImpForHib.update(user);
    	
    }
    
    public void setNickname(long id){
    	this.id= id;
    	UserDaoImpForHib daoImpForHib = new UserDaoImpForHib();
    	User user = daoImpForHib.load(id);
    	this.nickname = user.getNickname();
    }
    
    public String getNickName(long id){
    	UserDaoImpForHib daoImpForHib = new UserDaoImpForHib();
    	User user = daoImpForHib.load(id);
    	return user.getNickname();
    }
}
