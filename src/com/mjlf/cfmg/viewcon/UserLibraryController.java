package com.mjlf.cfmg.viewcon;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.Size;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.LibraryBook;
import com.mjlf.cfmg.entity.User;
import com.mjlf.cfmg.service.LibraryBookService;
import com.mjlf.cfmg.service.LibraryService;
import com.mjlf.cfmg.service.UserService;
import com.mjlf.cfmg.utils.DateUtils;
import com.mjlf.cfmg.utils.LibraryBookCompare;

@Controller
@RequestMapping("cfmg")
public class UserLibraryController {
	
	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private LibraryBookService libraryBookService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/toUserLibraryList.do",method=RequestMethod.GET)
	public String toUserLibraryList(HttpServletRequest request,HttpServletResponse response){
		return "/common/librarys";
	}
	
	@RequestMapping(value="/findSomedayCanBookLibrary.do",method=RequestMethod.POST)
	public void findSomedayCanBookLibrary(HttpServletRequest request,HttpServletResponse response){
		try {
			long libraryId = Long.parseLong(request.getParameter("libraryId"));
			Date date = DateUtils.ParseDate(request.getParameter("findDate"));
			List<LibraryBook> list = libraryBookService.findByLibraryAndTime(libraryId, date);
			Library library = libraryService.find(libraryId);
			List<LibraryBook> books = new ArrayList<LibraryBook>();
			if(list.size() == 0){
				LibraryBook libraryBook1 = new LibraryBook();
				libraryBook1.setLibraryId(libraryId);
				libraryBook1.setStartTime(new Date(DateUtils.getTime(library.getStartTimeAtAM()) + date.getTime()));
				libraryBook1.setEndTime(new Date(DateUtils.getTime(library.getEndTimeAtAM()) + date.getTime()));
				books.add(libraryBook1);
				
				LibraryBook libraryBook2 = new LibraryBook();
				libraryBook2.setLibraryId(libraryId);
				libraryBook2.setStartTime(new Date(DateUtils.getTime(library.getStartTimeAtPM()) + date.getTime()));
				libraryBook2.setEndTime(new Date(DateUtils.getTime(library.getEndTimeAtPM()) + date.getTime()));
				books.add(libraryBook2);
			}else{
				long startTimeAM = date.getTime() + DateUtils.getTime(library.getStartTimeAtAM());
				long endTimeAM = date.getTime() + DateUtils.getTime(library.getEndTimeAtAM());
				
				long startTimePM = date.getTime() + DateUtils.getTime(library.getStartTimeAtPM());
				long endTimePM = date.getTime() + DateUtils.getTime(library.getEndTimeAtPM());
				
				long indexAMTime = startTimeAM;
				long indexPMTime = startTimePM;
				for(int i = 0; i < list.size(); i++){
					LibraryBook libraryBook = list.get(i);
					if(libraryBook.getStartTime().getTime() < startTimePM){
						if(libraryBook.getStartTime().getTime() > indexAMTime){
							LibraryBook book = new LibraryBook();
							book.setStartTime(new Date(indexAMTime));
							book.setEndTime(new Date(libraryBook.getStartTime().getTime()-1000));
							books.add(book);
							indexAMTime = libraryBook.getEndTime().getTime();
						}
					}else if(libraryBook.getStartTime().getTime() >= startTimePM){
						if(libraryBook.getStartTime().getTime() > indexPMTime){
							LibraryBook book = new LibraryBook();
							book.setStartTime(new Date(indexPMTime));
							book.setEndTime(new Date(libraryBook.getStartTime().getTime()-1000));
							books.add(book);
							indexPMTime = libraryBook.getEndTime().getTime();
						}
					}
				}
				if(indexAMTime < endTimeAM){
					LibraryBook book = new LibraryBook();
					book.setStartTime(new Date(indexAMTime));
					book.setEndTime(new Date(endTimeAM));
					books.add(book);
				}
				if(indexPMTime < endTimePM){
					LibraryBook book = new LibraryBook();
					book.setStartTime(new Date(indexPMTime));
					book.setEndTime(new Date(endTimePM));
					books.add(book);
				}
			}
			for(int i = 0; i < books.size(); i++){
				LibraryBook lio = books.get(i);
				lio.setStartTimeStr(DateUtils.formatDate(lio.getStartTime()));
				lio.setEndTimeStr(DateUtils.formatDate(lio.getEndTime()));
				lio.setLength(DateUtils.getLength(lio.getEndTime(), lio.getStartTime())+"");
				lio.setLibraryId(library.getId());
			}
			Collections.sort(books, new LibraryBookCompare());
			JSONObject son = new JSONObject();
			son.put("library", new JSONObject(library));
			
			User user = userService.load(library.getAdminId());
			JSONObject jsonUser = new JSONObject(user);
			son.put("user", jsonUser);
			
			JSONArray json = new JSONArray(books);
			son.put("books", json);
			
			response.getWriter().write(son + "");
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
