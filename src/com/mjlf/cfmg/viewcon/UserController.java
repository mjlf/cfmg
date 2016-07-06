package com.mjlf.cfmg.viewcon;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.entity.User;
import com.mjlf.cfmg.service.LibraryService;
import com.mjlf.cfmg.service.UserService;
import com.mjlf.cfmg.utils.MD5;

import net.sf.json.JSONObject;


/**
 *����Controller��
 * @author mjlf
 *
 */
@Controller
@RequestMapping("cfmg")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LibraryService libraryService;
	
	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public String tologin(HttpServletRequest request,HttpServletResponse response){
		return "/page/login";
	}
	
	@RequestMapping(value="/regist.do", method=RequestMethod.POST)
	public void regist(HttpServletRequest request,HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String nickname = request.getParameter("nickname");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			User user = new User();
			user.setEmail(email);
			user.setPassword(MD5.string2MD5(password));
			user.setNickname(nickname);
			user.setPhone(phone);
			user.setUsername(username);
			userService.add(user);
			response.getWriter().write("Y");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("N");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/findusername.do", method=RequestMethod.POST)
	public void iscontainUsername(HttpServletRequest request,HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			if(userService.isContainUsername(username)){
				response.getWriter().write("Y");
			}else{
				response.getWriter().write("N");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/findphone.do", method=RequestMethod.POST)
	public void isContainPhone(HttpServletRequest request,HttpServletResponse response){
		try {
			String phone = request.getParameter("phone");
			if(userService.isContainPhone(phone)){
				response.getWriter().write("Y");
			}else{
				response.getWriter().write("N");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/findemail.do", method=RequestMethod.POST)
	public void iscontainEmail(HttpServletRequest request,HttpServletResponse response){
		try {
			String email = request.getParameter("email");
			if(userService.isContainEmail(email)){
				response.getWriter().write("Y");
			}else{
				response.getWriter().write("N");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			User user = new User();
			user.setUsername(username);
			user.setPassword(MD5.string2MD5(password));
			if(userService.login(user)){
				user = userService.findbyName(username);
				request.getSession(true).setAttribute("loginedUser", user);
				if(user.getIsOnline().equals("0")){
					user.setIsOnline("1");
					userService.update(user);
				}
				response.getWriter().write("Y");
			}else{
				response.getWriter().write("N");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/main.do", method=RequestMethod.GET)
	public String tomain(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginedUser");
		if(Integer.parseInt(user.getAdmin()) >= 1){//表示该用户为管理员
			return "/admin/index";
		}else{
			return "/common/index";
		}
	}
	
	@RequestMapping(value="showuser.do", method=RequestMethod.POST)
	public void showUser(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginedUser");
			response.getWriter().write(user.getNickname());
		} catch (Exception e) {
			try {
				response.getWriter().write("NULL");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="logout.do", method=RequestMethod.POST)
	public void logout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginedUser");
		if("1".equals(user.getIsOnline())){
			user.setIsOnline("0");
		}else if("0".equals(user.getIsOnline())){
			
		}
		userService.update(user);
		session.removeAttribute("loginedUser");
	}
	
	@RequestMapping(value="/clock.do", method=RequestMethod.GET)
	public String test(HttpServletRequest request,HttpServletResponse response){
		return "/all/clock";
	}
	
	@RequestMapping(value="/date.do", method=RequestMethod.GET)
	public String date(HttpServletRequest request,HttpServletResponse response){
		return "/all/date";
	}
	
	@RequestMapping(value="/toAllUser.do", method=RequestMethod.GET)
	public String getAllUser(HttpServletRequest request,HttpServletResponse response){
		return "/admin/users";
	}
	
	@RequestMapping(value="/findAllUser.do", method=RequestMethod.POST)
	public void findAllUser(HttpServletRequest request,HttpServletResponse response){
		try {
			PageValue pageValue = new PageValue();
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everypagenum"));
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			pageValue = userService.getPageValue(pageValue);
			List<Object> obj = (List<Object>)pageValue.getList().get(0);
			for(int i = 0; i < obj.size(); i++){
				User user = (User)obj.get(i);
				if("1".equals(user.getAdmin()) || "2".equals(user.getAdmin())){
					user.setCount(libraryService.getCountByAdmin(user.getId())+"");
				}
			}
			org.json.JSONObject json = new org.json.JSONObject(pageValue);
			response.getWriter().write(json+"");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/findAllIsAdminUser.do", method=RequestMethod.POST)
	public void findAllIsAdminUser(HttpServletRequest request,HttpServletResponse response){
		try {
			PageValue pageValue = new PageValue();
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everypagenum"));
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			pageValue = userService.getPageValueisAdmin(pageValue);
			List<Object> obj = (List<Object>)pageValue.getList().get(0);
			for(int i = 0; i < obj.size(); i++){
				User user = (User)obj.get(i);
				if("1".equals(user.getAdmin()) || "2".equals(user.getAdmin())){
					user.setCount(libraryService.getCountByAdmin(user.getId())+"");
				}
			}
			org.json.JSONObject json = new org.json.JSONObject(pageValue);
			response.getWriter().write(json+"");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/findAllNotIsAdminUser.do", method=RequestMethod.POST)
	public void findAllNotIsAdminUser(HttpServletRequest request,HttpServletResponse response){
		try {
			PageValue pageValue = new PageValue();
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everypagenum"));
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			pageValue = userService.getPageValueNotisAdmin(pageValue);
			org.json.JSONObject json = new org.json.JSONObject(pageValue);
			response.getWriter().write(json+"");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/changeUserOnlineState.do", method=RequestMethod.POST)
	public void changeUserOnlineState(HttpServletRequest request,HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("id"));
			User user = userService.load(id);
			if("0".equals(user.getIsOnline())){
				user.setIsOnline("1");//更改为在线状态
			}else if("1".equals(user.getIsOnline())){
				user.setIsOnline("0");//更改为下线状态
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/useropen.do", method=RequestMethod.GET)
	public void useropen(HttpServletRequest request,HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginedUser");
			if("0".equals(user.getIsOnline())){
				user.setIsOnline("1");//更改为在线状态
			}
			userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/userclose.do", method=RequestMethod.GET)
	public void userClose(HttpServletRequest request,HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginedUser");
			System.out.println(user);
			if("1".equals(user.getIsOnline())){
				user.setIsOnline("0");//更改为下线状态
			}
			userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/cancelAdmin.do", method=RequestMethod.POST)
	public void cancleAdmin(HttpServletRequest request,HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("id"));
			User user = userService.load(id);
			user.setAdmin("0");
			userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/setAdmin.do", method=RequestMethod.POST)
	public void setAdmin(HttpServletRequest request,HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("id"));
			User user = userService.load(id);
			user.setAdmin("1");
			userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/getUserId.do", method=RequestMethod.GET)
	public void getUserId(HttpServletRequest request,HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginedUser");
			response.getWriter().write(user.getId()+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/getUserInfo.do", method=RequestMethod.GET)
	public void getUserinfo(HttpServletRequest request,HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginedUser");
			org.json.JSONObject json = new org.json.JSONObject(user);
			response.getWriter().write(json+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/getUserInfoById.do", method=RequestMethod.POST)
	public void getUserInfoById(HttpServletRequest request,HttpServletResponse response){
		try {

			long id = Long.parseLong(request.getParameter("id"));
			User user = userService.load(id);
			org.json.JSONObject json = new org.json.JSONObject(user);
			response.getWriter().write(json+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
