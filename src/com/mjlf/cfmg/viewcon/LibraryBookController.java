package com.mjlf.cfmg.viewcon;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.LibraryBook;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.entity.User;
import com.mjlf.cfmg.service.LibraryBookService;
import com.mjlf.cfmg.service.LibraryService;
import com.mjlf.cfmg.service.UserService;
import com.mjlf.cfmg.utils.DateUtils;

@Controller
@RequestMapping("cfmg")
public class LibraryBookController {
	@Autowired
	private LibraryBookService libraryBookService;
	
	@Autowired
	private LibraryService libraryService;
	
	@Autowired 
	private UserService userService;
	
	
	@RequestMapping(value="/toBookLibraryPage.do", method=RequestMethod.GET)
	public String toBookLibraryPage(HttpServletRequest request, HttpServletResponse response){
		return "/admin/libraryBookDoing";
	}
	@RequestMapping(value="/toBookLibraryPagedid.do", method=RequestMethod.GET)
	public String toBookLibraryPagedid(HttpServletRequest request, HttpServletResponse response){
		return "/admin/libraryBookDid";
	}
	
	@RequestMapping(value="/toBookLibraryCommonPagedid.do", method=RequestMethod.GET)
	public String toBookLibraryCommonPagedid(HttpServletRequest request, HttpServletResponse response){
		return "/common/libraryBookDid";
	}
	
	@RequestMapping(value="/toBookLibraryPageUserWait.do", method=RequestMethod.GET)
	public String toBookLibraryPageUserWait(HttpServletRequest request, HttpServletResponse response){
		return "/common/libraryBookDoing";
	}
	@RequestMapping(value="/toBookLibrary.do", method=RequestMethod.POST)
	public void toBookLibrary(HttpServletRequest request, HttpServletResponse response){
		try {
			String startTimeStr = request.getParameter("startTimeStr");
			String endTimeStr = request.getParameter("endTimeStr");
			
			String bookStartTimeStr = request.getParameter("bookStartTimeStr");
			String bookEndTimeStr = request.getParameter("bookEndTimeStr");
			int peopleNum = Integer.parseInt(request.getParameter("peopleNum"));
			Long libraryId = Long.parseLong(request.getParameter("libraryId"));
			
			Date startTime = DateUtils.ParseDateTime(startTimeStr);
			Date endTime = DateUtils.ParseDateTime(endTimeStr);
			
			Date bookStartTime = DateUtils.ParseDateTime(bookStartTimeStr);
			Date bookEndTime = DateUtils.ParseDateTime(bookEndTimeStr);
			Library library = libraryService.find(libraryId);
			
			if(bookStartTime.getTime() >= startTime.getTime() &&
					bookEndTime.getTime() <= endTime.getTime() &&
					bookEndTime.getTime() > bookStartTime.getTime() &&
					peopleNum <= library.getPeople()){
				//可预订
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("loginedUser");
				
				LibraryBook libraryBook = new LibraryBook();
				libraryBook.setBookTime(new Date());
				libraryBook.setBookUserId(user.getId());
				libraryBook.setEndTime(bookEndTime);
				libraryBook.setStartTime(bookStartTime);
				libraryBook.setLibraryId(libraryId);
				libraryBook.setState(0);
				libraryBook.setPeopleNum(peopleNum);
				libraryBook.setAdminUserId(library.getAdminId());
				
				libraryBookService.add(libraryBook);
				response.getWriter().write("Y");
			}else{
				//不可预定
				response.getWriter().write("N");
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
	
	@RequestMapping(value="/findLibraryBookByLibraryIdAndState.do", method=RequestMethod.POST)
	public void findLibraryBookByLibraryIdAndState(HttpServletRequest request, HttpServletResponse response){
		try {
			int state = Integer.parseInt(request.getParameter("state"));
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginedUser");
			
			List<LibraryBook> list = libraryBookService.findByLibrarybyState(user.getId(),state);
			JSONArray json = new JSONArray(list);
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
	
	@RequestMapping(value="/findLibraryBookDoing.do", method=RequestMethod.POST)
	public void findLibraryBookDoing(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginedUser");
			
			List<LibraryBook> list = libraryBookService.findByLibrarybyState(user.getId(),0);
			for(int i = 0; i < list.size(); i++){
				LibraryBook libraryBook = list.get(i);
				User user2 = userService.load(libraryBook.getBookUserId());
				libraryBook.setNameBookUser(user2.getNickname());
				Library library = libraryService.find(libraryBook.getLibraryId());
				libraryBook.setLibraryPeopleNum(library.getPeople());
				libraryBook.setAddress(library.getAddress());
				libraryBook.setStartTimeStr(DateUtils.formatDate(libraryBook.getStartTime()));
				libraryBook.setEndTimeStr(DateUtils.formatDate(libraryBook.getEndTime()));
			}
			JSONArray json = new JSONArray(list);
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
	
	@RequestMapping(value="/updataLibraryBookDoing.do", method=RequestMethod.POST)
	public void updataLibraryBookDoing(HttpServletRequest request, HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("libraryBookId"));
			LibraryBook libraryBook = libraryBookService.load(id);
			libraryBook.setState(1);
			libraryBookService.update(libraryBook);
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
	
	@RequestMapping(value="/findLibraryBookDoingPageValue.do", method=RequestMethod.POST)
	public void findLibraryBookDoingPageValue(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginedUser");
			PageValue pageValue = new PageValue();
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everypagenum"));
			int state = Integer.parseInt(request.getParameter("state"));
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			int type = 0;
			if("0".equals(user.getAdmin())){
				type = 0;
			}else{
				type = 1;
			}
			pageValue = libraryBookService.loadPageValueByLibraryState(pageValue, state, user.getId(), type);
			List<Object> list = (List<Object>) pageValue.getList().get(0);
			for(int i = 0; i < list.size(); i++){
				LibraryBook libraryBook = (LibraryBook)(list.get(i));
				if(type == 0){
					User user2 = userService.load(libraryBook.getAdminUserId());
					libraryBook.setNameBookUser(user2.getNickname());
				}else{
					User user2 = userService.load(libraryBook.getBookUserId());
					libraryBook.setNameBookUser(user2.getNickname());
				}
				Library library = libraryService.find(libraryBook.getLibraryId());
				libraryBook.setLibraryPeopleNum(library.getPeople());
				libraryBook.setAddress(library.getAddress());
				libraryBook.setStartTimeStr(DateUtils.formatDate(libraryBook.getStartTime()));
				libraryBook.setEndTimeStr(DateUtils.formatDate(libraryBook.getEndTime()));
			}
			org.json.JSONObject json = new org.json.JSONObject(pageValue);
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
	
}
