package com.mjlf.cfmg.viewcon;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.LibraryFile;
import com.mjlf.cfmg.entity.LibraryPeople;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.service.LibraryBookService;
import com.mjlf.cfmg.service.LibraryFileService;
import com.mjlf.cfmg.service.LibraryPeopleService;
import com.mjlf.cfmg.service.LibraryService;
import com.mjlf.cfmg.service.UserService;
import com.mjlf.cfmg.utils.DateUtils;
import com.mjlf.cfmg.utils.FileOp;

@Controller
@RequestMapping("cfmg")
public class LibraryFileController {
	
	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LibraryBookService libraryBookService;
	
	@Autowired
	private LibraryPeopleService libraryPeopleService;
	
	@Autowired
	private LibraryFileService libraryFileService;
	
	@RequestMapping(value="/toLibraryFileList.do", method=RequestMethod.GET)
	public String toSetting(HttpServletRequest request, HttpServletResponse response){
		return "/common/libraryBookFile";
	}
	
	@RequestMapping(value="/loadLibraryBookFile.do",method=RequestMethod.POST)
	public void loadLibraryBookFile(HttpServletRequest request, HttpServletResponse response){
		try {
			long bookId = Long.parseLong(request.getParameter("bookLibraryId"));
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everyPageNum"));
			
			PageValue pageValue = new PageValue();
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			
			pageValue = libraryFileService.loadByLibraryBook(pageValue, bookId);
			
			List<Object> list = (List<Object>) pageValue.getList().get(0);
			
			for(int i = 0; i < list.size(); i++){
				LibraryFile libraryFile = (LibraryFile)list.get(i);
				LibraryPeople libraryPeople = libraryPeopleService.loadById(libraryFile.getUploadUserId());
				libraryFile.setUploadUserName(libraryPeople.getName());
				libraryFile.setUploadTimeStr(DateUtils.formatDate(libraryFile.getUploadTime()));
			}
			
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
	
	@RequestMapping(value="/deleteLibraryBookFile.do",method=RequestMethod.POST)
	public void deleteLibraryBookFile(HttpServletRequest request, HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("fileId"));
			LibraryFile libraryFile = libraryFileService.load(id);
			libraryFileService.delete(id);
			String path = request.getSession().getServletContext().getRealPath("upload");
			System.out.println(path);
			new FileOp().deleteFile(path+File.separator+libraryFile.getFileNowName());
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
	
	@RequestMapping(value="/findAllFileByLibraryBook.do",method=RequestMethod.POST)
	public void findAllFileByLibraryBook(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			LibraryPeople libraryPeople = (LibraryPeople)session.getAttribute("userlogin");
			if(libraryPeople == null){
				response.getWriter().write("N");
				return;
			}
			PageValue pageValue = new PageValue();
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everypagenum"));
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			
			pageValue = libraryFileService.loadByLibraryBook(pageValue, libraryPeople.getLibraryBookId());
			List<Object> list = (List<Object>) pageValue.getList().get(0);
			
			for(int i = 0; i < list.size(); i++){
				LibraryFile libraryFile = (LibraryFile)list.get(i);
				LibraryPeople libraryPeople1 = libraryPeopleService.loadById(libraryFile.getUploadUserId());
				libraryFile.setUploadUserName(libraryPeople1.getName());
				libraryFile.setUploadTimeStr(DateUtils.formatDate(libraryFile.getUploadTime()));
//				request.getRemoteAddr();  IP获取
//				request.getRemotePort(): 端口号
			
				libraryFile.setDownloadPath("http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/cfmg/upload/"+libraryFile.getFileNowName());
			}
			JSONObject json = new JSONObject(pageValue);
			response.getWriter().write(""+json);
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();;
		}
	}
}
