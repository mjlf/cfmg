package com.mjlf.cfmg.viewcon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class BookController {

	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LibraryBookService libraryBookService;
	
	@RequestMapping(value="/tobookPagewriteinfo.do", method = RequestMethod.GET)
	public String tobookPagewriteinfo(HttpServletRequest request, HttpServletResponse response){
		return "/common/book";
	}
	
	@RequestMapping(value="/findSomedayCanBookLibraryBook.do",method=RequestMethod.POST)
	public void findSomedayCanBookLibraryBook(HttpServletRequest request,HttpServletResponse response){
		try {
			int peopleNum = Integer.parseInt(request.getParameter("peopleNum"));
			String address = request.getParameter("address");
			String time = request.getParameter("time");
			Date date = DateUtils.ParseDate(time);
			List<Library> list = libraryService.load(address, peopleNum);
			
			List<LibraryBook> booksLib = new ArrayList<LibraryBook>();
			List<LibraryBook> books = new ArrayList<LibraryBook>();
			for(int i = 0; i < list.size(); i++){
				long libraryId = list.get(i).getId();
				Library library = list.get(i);
				booksLib = libraryBookService.findByLibraryAndTime(list.get(i).getId(), date);
				if(booksLib.size() == 0){
					LibraryBook libraryBook1 = new LibraryBook();
					libraryBook1.setLibraryId(libraryId);
					libraryBook1.setStartTime(new Date(DateUtils.getTime(library.getStartTimeAtAM()) + date.getTime()));
					libraryBook1.setEndTime(new Date(DateUtils.getTime(library.getEndTimeAtAM()) + date.getTime()));
					libraryBook1.setLibrary(library);
					libraryBook1.setUser(userService.load(library.getAdminId()));
					books.add(libraryBook1);
					
					LibraryBook libraryBook2 = new LibraryBook();
					libraryBook2.setLibraryId(libraryId);
					libraryBook2.setStartTime(new Date(DateUtils.getTime(library.getStartTimeAtPM()) + date.getTime()));
					libraryBook2.setEndTime(new Date(DateUtils.getTime(library.getEndTimeAtPM()) + date.getTime()));
					libraryBook2.setLibrary(library);
					libraryBook2.setUser(userService.load(library.getAdminId()));
					books.add(libraryBook2);
				}else{
					long startTimeAM = date.getTime() + DateUtils.getTime(library.getStartTimeAtAM());
					long endTimeAM = date.getTime() + DateUtils.getTime(library.getEndTimeAtAM());
					
					long startTimePM = date.getTime() + DateUtils.getTime(library.getStartTimeAtPM());
					long endTimePM = date.getTime() + DateUtils.getTime(library.getEndTimeAtPM());
					
					long indexAMTime = startTimeAM;
					long indexPMTime = startTimePM;
					for(int j = 0; j < booksLib.size(); j++){
						LibraryBook libraryBook = booksLib.get(j);
						if(libraryBook.getStartTime().getTime() < startTimePM){
							if(libraryBook.getStartTime().getTime() > indexAMTime){
								LibraryBook book = new LibraryBook();
								book.setLibraryId(libraryId);
								book.setStartTime(new Date(indexAMTime));
								book.setEndTime(new Date(libraryBook.getStartTime().getTime()-1000));
								book.setLibrary(library);
								book.setUser(userService.load(library.getAdminId()));
								books.add(book);
								indexAMTime = libraryBook.getEndTime().getTime();
							}
						}else if(libraryBook.getStartTime().getTime() >= startTimePM){
							if(libraryBook.getStartTime().getTime() > indexPMTime){
								LibraryBook book = new LibraryBook();
								book.setLibraryId(libraryId);
								book.setStartTime(new Date(indexPMTime));
								book.setEndTime(new Date(libraryBook.getStartTime().getTime()-1000));
								book.setLibrary(library);
								book.setUser(userService.load(library.getAdminId()));
								books.add(book);
								indexPMTime = libraryBook.getEndTime().getTime();
							}
						}
					}
					if(indexAMTime < endTimeAM){
						LibraryBook book = new LibraryBook();
						book.setLibraryId(libraryId);
						book.setStartTime(new Date(indexAMTime));
						book.setEndTime(new Date(endTimeAM));
						book.setLibrary(library);
						book.setUser(userService.load(library.getAdminId()));
						books.add(book);
					}
					if(indexPMTime < endTimePM){
						LibraryBook book = new LibraryBook();
						book.setLibraryId(libraryId);
						book.setStartTime(new Date(indexPMTime));
						book.setEndTime(new Date(endTimePM));
						book.setLibrary(library);
						book.setUser(userService.load(library.getAdminId()));
						books.add(book);
					}
				}
			}
			for(int i = 0; i < books.size(); i++){
				LibraryBook lio = books.get(i);
				lio.setStartTimeStr(DateUtils.formatDate(lio.getStartTime()));
				lio.setEndTimeStr(DateUtils.formatDate(lio.getEndTime()));
				lio.setLength(DateUtils.getLength(lio.getEndTime(), lio.getStartTime())+"");
			}
			Collections.sort(books, new LibraryBookCompare());
			JSONArray json = new JSONArray(books);
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
