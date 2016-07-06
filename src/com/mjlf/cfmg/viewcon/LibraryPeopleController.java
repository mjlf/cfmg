package com.mjlf.cfmg.viewcon;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.LibraryBook;
import com.mjlf.cfmg.entity.LibraryPeople;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.mail.MailConfigure;
import com.mjlf.cfmg.mail.PropertiesMailConfig;
import com.mjlf.cfmg.mail.SendMail;
import com.mjlf.cfmg.service.LibraryBookService;
import com.mjlf.cfmg.service.LibraryPeopleService;
import com.mjlf.cfmg.service.LibraryService;
import com.mjlf.cfmg.service.UserService;
import com.mjlf.cfmg.utils.DateUtils;
import com.mjlf.cfmg.utils.RandomString;

@Controller
@RequestMapping("cfmg")
public class LibraryPeopleController {
	
	@Autowired
	private LibraryService libraryService;
	
	@Autowired 
	private LibraryBookService libraryBookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LibraryPeopleService libraryPeopleService;
	
	@RequestMapping(value="/toLibraryBookSigininpage.do",method=RequestMethod.GET)
	public String toLibraryBookSignin(HttpServletRequest request, HttpServletResponse response){
		return "/admin/libraryBookSignin";
	}
	
	@RequestMapping(value="/toLibraryBookPeoplepage.do",method=RequestMethod.GET)
	public String toLibraryBookPeoplepage(HttpServletRequest request, HttpServletResponse response){
		return "/common/libraryBookPeople";
	}
	
	@RequestMapping(value="/toLibraryBookSigininqrcode.do",method=RequestMethod.GET)
	public void toLibraryBookSigininqrcode(HttpServletRequest request, HttpServletResponse response){
		try {
			response.getWriter().write("http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/cfmg/admin/sign-in.html");
		} catch (Exception e) {
			try {
				response.getWriter().write("未知错误， 请重试！");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/findALibraryBookPeople.do",method=RequestMethod.POST)
	public void findALibraryBookPeople(HttpServletRequest request, HttpServletResponse response){
		try {
			long libraryBookId = Long.parseLong(request.getParameter("libraryBookId"));
			PageValue pageValue = new PageValue();
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everypagenum"));
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			
			pageValue = libraryPeopleService.loadByBook(pageValue, libraryBookId);
			JSONObject json = new JSONObject(pageValue);
			response.getWriter().write(json + "");
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/deleteAPeople.do",method=RequestMethod.POST)
	public void deleteAPeople(HttpServletRequest request, HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("peopleId"));
			libraryPeopleService.delete(id);
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
	@RequestMapping(value="/findApeopleforupdate.do",method=RequestMethod.POST)
	public void findApeopleforupdate(HttpServletRequest request, HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("peopleId"));
			LibraryPeople libraryPeople = libraryPeopleService.loadById(id);
			JSONObject json = new JSONObject(libraryPeople);
			response.getWriter().write(json+"");
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/updatePoepleAtOne.do",method=RequestMethod.POST)
	public void updatePoepleAtOne(HttpServletRequest request, HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("peopleId"));
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			
			LibraryPeople libraryPeople = libraryPeopleService.loadById(id);
			libraryPeople.setEmail(email);
			libraryPeople.setPhone(phone);
			libraryPeople.setName(name);
			
			libraryPeopleService.update(libraryPeople);
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
	
	@RequestMapping(value="/addPoepleAtOne.do",method=RequestMethod.POST)
	public void addPoepleAtOne(HttpServletRequest request, HttpServletResponse response){
		try {
			long bookblibraryid = Long.parseLong(request.getParameter("librarybookid"));
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			
			LibraryPeople libraryPeople = new LibraryPeople();
			libraryPeople.setEmail(email);
			libraryPeople.setPhone(phone);
			libraryPeople.setLibraryBookId(bookblibraryid);
			libraryPeople.setName(name);
			libraryPeople.setSign_inPassword(RandomString.getSignPassword());
        	libraryPeople.setIsSign_in(0);
        	libraryPeople.setSign_inCode(new Date().getTime()+"");
        	
        	long id = libraryPeopleService.add(libraryPeople);
        	libraryPeople = libraryPeopleService.loadById(id);
			JSONObject json = new JSONObject(libraryPeople);
			
			LibraryBook libraryBook = libraryBookService.load(libraryPeople.getLibraryBookId());
			libraryBook.setStartTimeStr(DateUtils.formatDate(libraryBook.getStartTime()));
			
			Library library = libraryService.find(libraryBook.getLibraryId());
			
			MailConfigure mailConfigure = PropertiesMailConfig.getMailConfig();
			mailConfigure.setPassword(mailConfigure.getPassword().substring(2));
			mailConfigure.setBody("您好！您将于"+libraryBook.getStartTimeStr()+"有一场会议， 会议地址"+library.getAddress()+"; 请提前半小时到场"
					+ "; 您的签到号:"+libraryPeople.getSign_inCode()+"; 签到密码为:"+libraryPeople.getSign_inPassword()+"; 如果您有会议需要的文化， 上传到如下服务地址"
							+ "其中登录名和密码与签到名与签到密码一致:"+"http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/cfmg/common/files.html");
			mailConfigure.addToUserName(libraryPeople.getEmail());
			mailConfigure.setSubject("签到账号密码");
			SendMail sen = new SendMail(mailConfigure);
			sen.send();
			
			response.getWriter().write(json+"");
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/logintobook.do",method=RequestMethod.POST)
	public void logintobook(HttpServletRequest request, HttpServletResponse response){
		try {
			String name = request.getParameter("name");
			String passwrod = request.getParameter("password");
			LibraryPeople libraryPeople = libraryPeopleService.findByCode(name);
			if(libraryPeople == null){
				response.getWriter().write("N");
			}else{
				if(libraryPeople.getSign_inPassword().equals(passwrod)){
					JSONObject json = new JSONObject(libraryPeople);
					response.getWriter().write(""+json);
					HttpSession session = request.getSession();
					session.setAttribute("userlogin", libraryPeople);
				}else{
					response.getWriter().write("N");
				}
			}
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/getuserName.do",method=RequestMethod.GET)
	public void getuserName(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			LibraryPeople libraryPeople = (LibraryPeople)session.getAttribute("userlogin");
			JSONObject json = new JSONObject(libraryPeople);
			response.getWriter().write("" + json);
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/signIncodepass.do",method=RequestMethod.POST)
	public void signIncodepass(HttpServletRequest request, HttpServletResponse response){
		try {
			String signCode = request.getParameter("signcode");
			String signpassword = request.getParameter("signpassword");
			
			LibraryPeople libraryPeople = libraryPeopleService.findByCode(signCode);
			if(libraryPeople == null){
				response.getWriter().write("N");
			}else{
				if(libraryPeople.getSign_inPassword().equals(signpassword)){
					libraryPeople.setIsSign_in(1);
					libraryPeople.setSign_inTime(new Date());
					libraryPeopleService.update(libraryPeople);
					response.getWriter().write("Y");
				}
			}
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
}
