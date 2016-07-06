package com.mjlf.cfmg.viewcon;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.User;
import com.mjlf.cfmg.mail.MailConfigure;
import com.mjlf.cfmg.mail.PropertiesMailConfig;
import com.mjlf.cfmg.mail.SendMail;
import com.mjlf.cfmg.service.UserService;
import com.mjlf.cfmg.utils.MD5;
import com.mjlf.cfmg.utils.RandomString;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("cfmg")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/adminsetting.do", method=RequestMethod.GET)
	public String toSetting(HttpServletRequest request, HttpServletResponse response){
		return "/all/setting";
	}
	
	@RequestMapping(value="/toknow.do", method=RequestMethod.GET)
	public String toknow(HttpServletRequest request, HttpServletResponse response){
		return "/all/help";
	}
	@RequestMapping(value="/repass.do", method=RequestMethod.GET)
	public String toRepass(HttpServletRequest request, HttpServletResponse response){
		return "/all/repass";
	}
	
	@RequestMapping(value="/forgetrepass.do", method=RequestMethod.GET)
	public String forgetPasss(HttpServletRequest request, HttpServletResponse response){
		return "/all/forgetpass";
	}
	@RequestMapping(value="/userinfo.do", method=RequestMethod.GET)
	public void getInfo(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try {
			HttpSession session = request.getSession();
			User user =  (User) session.getAttribute("loginedUser");
			JSONObject json = JSONObject.fromObject(user);
			out = response.getWriter();
			out.write(json+"");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
		
	@RequestMapping(value="/changeInfo.do", method=RequestMethod.POST)
	public void changeInfo(HttpServletRequest request, HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			String nickname = request.getParameter("nickname");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			
			User user = userService.findbyName(username);
			user.setNickname(nickname);
			user.setEmail(email);
			user.setPhone(phone);
			
			userService.update(user);
			request.getSession().setAttribute("loginedUser", user);
		} catch (Exception e) {

		}
	}
	@RequestMapping(value="/checkPhone.do", method=RequestMethod.POST)
	public void isContainPhone(HttpServletRequest request,HttpServletResponse response){
		try {
			String phone = request.getParameter("phone");
			if(userService.isContainPhone(phone)){
				User user = (User)request.getSession().getAttribute("loginedUser");
				if(user.getPhone().equals(phone)){
					response.getWriter().write("N");
				}else{
					response.getWriter().write("Y");
				}
			}else{
				response.getWriter().write("N");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/checkEmail.do", method=RequestMethod.POST)
	public void iscontainEmail(HttpServletRequest request,HttpServletResponse response){
		try {
			String email = request.getParameter("email");
			if(userService.isContainEmail(email)){
				User user = (User)request.getSession().getAttribute("loginedUser");
				if(user.getEmail().equals(email)){
					response.getWriter().write("N");
				}else{
					response.getWriter().write("Y");
				}
			}else{
				response.getWriter().write("N");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/chagepassword.do", method=RequestMethod.POST)
	public void chagePassword(HttpServletRequest request,HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			String oldpassword = request.getParameter("oldpassword");
			String newpassword = request.getParameter("newpassword");
			User user = (User) session.getAttribute("loginedUser");
			if(user.getPassword().equals(MD5.string2MD5(oldpassword))){
				user.setPassword(MD5.string2MD5(newpassword));
				userService.update(user);
				response.getWriter().write("Y");
			}else{
				response.getWriter().write("N");
			}
		} catch (Exception e) {
			
		}
	}
	
	@RequestMapping(value="/sendmailtoresetpass.do", method=RequestMethod.POST)
	public void sendMailtoResetPass(HttpServletRequest request,HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			User user = userService.findbyName(username);
			if(user.getEmail().equals(email)){
					
				String repass = RandomString.getPassword();
				MailConfigure mailConfigure = PropertiesMailConfig.getMailConfig();
				mailConfigure.setPassword(mailConfigure.getPassword().substring(2));
				mailConfigure.setBody(mailConfigure.getBody().replace("xxx", repass));
				mailConfigure.addToUserName(email);
				SendMail sen = new SendMail(mailConfigure);
				user.setPassword(MD5.string2MD5(repass));
				
				userService.update(user);
				sen.send();
				response.getWriter().write("Y");
			}else{
				response.getWriter().write("N");
			}
		} catch (Exception e) {
			try {
				response.getWriter().write("H");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
}
