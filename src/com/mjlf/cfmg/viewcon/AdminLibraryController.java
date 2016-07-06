package com.mjlf.cfmg.viewcon;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.ICON;
import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.entity.User;
import com.mjlf.cfmg.service.ICONService;
import com.mjlf.cfmg.service.LibraryService;
import com.mjlf.cfmg.service.UserService;
import com.mjlf.cfmg.utils.FileOp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("cfmg")
public class AdminLibraryController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private ICONService iconService;
	
	@RequestMapping(value="/toAddLibrary.do",method=RequestMethod.GET)
	public String toAddLibrary(HttpServletRequest request,HttpServletResponse response){
		return "/admin/addLibrary";
	}
	
	@RequestMapping(value="/addLibrary.do", method=RequestMethod.POST)
	public void addLibrary(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("loginedUser");
			int people = Integer.parseInt(request.getParameter("people"));
			String address = request.getParameter("address");
			int amhourstart = Integer.parseInt(request.getParameter("amhourstart"));
			int amminstart = Integer.parseInt(request.getParameter("amminstart"));
			int amsecstart = Integer.parseInt(request.getParameter("amsecstart"));
			
			int amhourend = Integer.parseInt(request.getParameter("amhourend"));
			int amminend = Integer.parseInt(request.getParameter("amminend"));
			int amsecend = Integer.parseInt(request.getParameter("amsecend"));
			
			int pmhourstart = Integer.parseInt(request.getParameter("pmhourstart"));
			int pmminstart = Integer.parseInt(request.getParameter("pmminstart"));
			int pmsecstart = Integer.parseInt(request.getParameter("pmsecstart"));
			
			int pmhourend = Integer.parseInt(request.getParameter("pmhourend"));
			int pmminend = Integer.parseInt(request.getParameter("pmminend"));
			int pmsecend = Integer.parseInt(request.getParameter("pmsecend"));
			String isProjection = request.getParameter("isProjection");
			String videoConferencing = request.getParameter("videoConferencing");
			
			String disc = request.getParameter("disc");
			String bookAndKnow = request.getParameter("bookAndKnow");
			
			Library library = new Library();
			library.setAddress(address);
			library.setAdminId(user.getId());
			library.setBookAndKnow(bookAndKnow);
			library.setDisc(disc);
			library.setEndTimeAtAM(new Time(amhourend, amminend, amsecend));
			library.setStartTimeAtAM(new Time(amhourstart, amminstart, amsecstart));
			library.setStartTimeAtPM(new Time(pmhourstart, pmminstart, pmsecstart));
			library.setEndTimeAtPM(new Time(pmhourend, pmminend, pmsecend));
			library.setIsProjection(isProjection);
			library.setPeople(people);
			library.setVideoConferencing(videoConferencing);
			library = libraryService.add(library);
			if(library == null || (library.getId()) == 0){
				response.getWriter().write("N");
			}else{
				response.getWriter().write(library.getId()+"");
			}
		} catch (Exception e) {
			try {
				response.getWriter().write("H");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/editLibrary.do", method=RequestMethod.POST)
	public void editLibrary(HttpServletRequest request, HttpServletResponse response){
		try {
			
			int people = Integer.parseInt(request.getParameter("people"));
			long id = Long.parseLong(request.getParameter("id"));
			String address = request.getParameter("address");
			int amhourstart = Integer.parseInt(request.getParameter("amhourstart"));
			int amminstart = Integer.parseInt(request.getParameter("amminstart"));
			int amsecstart = Integer.parseInt(request.getParameter("amsecstart"));
			
			int amhourend = Integer.parseInt(request.getParameter("amhourend"));
			int amminend = Integer.parseInt(request.getParameter("amminend"));
			int amsecend = Integer.parseInt(request.getParameter("amsecend"));
			
			int pmhourstart = Integer.parseInt(request.getParameter("pmhourstart"));
			int pmminstart = Integer.parseInt(request.getParameter("pmminstart"));
			int pmsecstart = Integer.parseInt(request.getParameter("pmsecstart"));
			
			int pmhourend = Integer.parseInt(request.getParameter("pmhourend"));
			int pmminend = Integer.parseInt(request.getParameter("pmminend"));
			int pmsecend = Integer.parseInt(request.getParameter("pmsecend"));
			String isProjection = request.getParameter("isProjection");
			String videoConferencing = request.getParameter("videoConferencing");
			
			String disc = request.getParameter("disc");
			String bookAndKnow = request.getParameter("bookAndKnow");
			
			Library library = libraryService.find(id);
			library.setAddress(address);
			library.setBookAndKnow(bookAndKnow);
			library.setDisc(disc);
			library.setEndTimeAtAM(new Time(amhourend, amminend, amsecend));
			library.setStartTimeAtAM(new Time(amhourstart, amminstart, amsecstart));
			library.setStartTimeAtPM(new Time(pmhourstart, pmminstart, pmsecstart));
			library.setEndTimeAtPM(new Time(pmhourend, pmminend, pmsecend));
			library.setIsProjection(isProjection);
			library.setPeople(people);
			library.setVideoConferencing(videoConferencing);
			libraryService.update(library);
			response.getWriter().write("Y");
		} catch (Exception e) {
			try {
				response.getWriter().write("H");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/toLibraryList.do",method=RequestMethod.GET)
	public String toLibraryList(HttpServletRequest request,HttpServletResponse response){
		return "/admin/librarys";
	}
	
	@RequestMapping(value="/findallLibrary.do",method=RequestMethod.POST)
	public void findAllLibrary(HttpServletRequest request,HttpServletResponse response){
		try {
			PageValue pageValue = new PageValue();
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everypagenum"));
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			pageValue = libraryService.pageLoad(pageValue);
			JSONObject json = new JSONObject();
			json.put("allcount", pageValue.getAllCount());
			json.put("pagecount", pageValue.getPageCount());
			json.put("everypagenum", pageValue.getEverypagenum());
			json.put("pageindex", pageValue.getPageIndex());
			User user = (User) request.getSession().getAttribute("loginedUser");
			JSONArray j = new JSONArray();
			List<Object> list = (List<Object>)pageValue.getList().get(0);
			for(int i = 0; i < list.size(); i++){
				Library library = (Library)(list.get(i));
				JSONObject s = new JSONObject();
				s.put("address", library.getAddress());
				s.put("id", library.getId());
				User user1 = userService.load(library.getAdminId());
				s.put("admin", user1.getNickname());
				s.put("adminId", user1.getId());
				if(user.getId() == library.getAdminId()){
					s.put("isControl", 1);
				}else{
					s.put("isControl", 0);
				}
				String ams = "";
				String ame = "";
				String pms = "";
				String pme = "";
				if(library.getStartTimeAtAM().getHours()<10){
					ams += "0"+library.getStartTimeAtAM().getHours()+":";
				}else{
					ams += library.getStartTimeAtAM().getHours()+":";
				}
				if(library.getStartTimeAtAM().getMinutes() <10){
					ams += "0"+library.getStartTimeAtAM().getMinutes();
				}else{
					ams += library.getStartTimeAtAM().getMinutes();
				}
				
				if(library.getEndTimeAtAM().getHours()<10){
					ame += "0"+library.getEndTimeAtAM().getHours()+":";
				}else{
					ame += library.getEndTimeAtAM().getHours()+":";
				}
				if(library.getEndTimeAtAM().getMinutes() <10){
					ame += "0"+library.getEndTimeAtAM().getMinutes();
				}else{
					ame += library.getEndTimeAtAM().getMinutes();
				}
				
				if(library.getStartTimeAtPM().getHours()<10){
					pms += "0"+library.getStartTimeAtPM().getHours()+":";
				}else{
					pms += library.getStartTimeAtPM().getHours()+":";
				}
				if(library.getStartTimeAtPM().getMinutes() <10){
					pms += "0"+library.getStartTimeAtPM().getMinutes();
				}else{
					pms += library.getStartTimeAtPM().getMinutes();
				}
				
				if(library.getEndTimeAtPM().getHours()<10){
					pme += "0"+library.getEndTimeAtPM().getHours()+":";
				}else{
					pme += library.getEndTimeAtPM().getHours()+":";
				}
				if(library.getEndTimeAtPM().getMinutes() <10){
					pme += "0"+library.getEndTimeAtPM().getMinutes();
				}else{
					pme += library.getEndTimeAtPM().getMinutes();
				}
				s.put("ams", ams);
				s.put("ame", ame);
				s.put("pms", pms);
				s.put("pme", pme);
				
				s.put("people", library.getPeople());
				if(library.getIsProjection().equals("1")){
					s.put("isProjection", "是");
				}else{
					s.put("isProjection", "否");
				}
				if(library.getVideoConferencing().equals("1")){
					s.put("videoConferencing", "是");
				}else{
					s.put("videoConferencing", "否");
				}
				j.add(s);
			}
			json.put("list", j);
			response.getWriter().write(json + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/findConditionLibrary.do",method=RequestMethod.POST)
	public void findConditionLibrary(HttpServletRequest request,HttpServletResponse response){
		try {
			User user = (User) request.getSession().getAttribute("loginedUser");
			PageValue pageValue = new PageValue();
			int index = Integer.parseInt(request.getParameter("index"));
			int everyPageNum = Integer.parseInt(request.getParameter("everypagenum"));
			int isp = Integer.parseInt(request.getParameter("isp"));
			int isa = Integer.parseInt(request.getParameter("isa"));
			int admin = Integer.parseInt(request.getParameter("admin"));
			int startnum = Integer.parseInt(request.getParameter("startnum"));
			int endnum = Integer.parseInt(request.getParameter("endnum"));
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			
			pageValue.setPageIndex(index);
			pageValue.setEverypagenum(everyPageNum);
			Library lib = new Library();
			lib.setAdminId(user.getId());
			lib.setCheckamdin(admin);
			lib.setIsProjection(isp+"");
			lib.setVideoConferencing(isa + "");
			lib.setStartnum(startnum);
			lib.setEndnum(endnum);
			int hourstart = Integer.parseInt(starttime.split(":")[0]);
			int minstart = Integer.parseInt(starttime.split(":")[1]);
			int secstart = Integer.parseInt(starttime.split(":")[2]);
			Time checkStartTime = new Time(hourstart, minstart, secstart);
			
			int hourend = Integer.parseInt(endtime.split(":")[0]);
			int minend = Integer.parseInt(endtime.split(":")[1]);
			int secend = Integer.parseInt(endtime.split(":")[2]);
			Time checkEndTime = new Time(hourend, minend, secend);
			
			lib.setCheckStartTime(checkStartTime);
			lib.setCheckEndTime(checkEndTime);
			pageValue.setObject(lib);
			
			pageValue = libraryService.pageLoadCondition(pageValue);
			JSONObject json = new JSONObject();
			json.put("allcount", pageValue.getAllCount());
			json.put("pagecount", pageValue.getPageCount());
			json.put("everypagenum", pageValue.getEverypagenum());
			json.put("pageindex", pageValue.getPageIndex());
			JSONArray j = new JSONArray();
			List<Object> list = (List<Object>)pageValue.getList().get(0);
			for(int i = 0; i < list.size(); i++){
				Library library = (Library)(list.get(i));
				JSONObject s = new JSONObject();
				s.put("address", library.getAddress());
				s.put("admin", userService.load(library.getAdminId()).getNickname());
				s.put("id", library.getId());
				if(user.getId() == library.getAdminId()){
					s.put("isControl", 1);
				}else{
					s.put("isControl", 0);
				}
				String ams = "";
				String ame = "";
				String pms = "";
				String pme = "";
				if(library.getStartTimeAtAM().getHours()<10){
					ams += "0"+library.getStartTimeAtAM().getHours()+":";
				}else{
					ams += library.getStartTimeAtAM().getHours()+":";
				}
				if(library.getStartTimeAtAM().getMinutes() <10){
					ams += "0"+library.getStartTimeAtAM().getMinutes();
				}else{
					ams += library.getStartTimeAtAM().getMinutes();
				}
				
				if(library.getEndTimeAtAM().getHours()<10){
					ame += "0"+library.getEndTimeAtAM().getHours()+":";
				}else{
					ame += library.getEndTimeAtAM().getHours()+":";
				}
				if(library.getEndTimeAtAM().getMinutes() <10){
					ame += "0"+library.getEndTimeAtAM().getMinutes();
				}else{
					ame += library.getEndTimeAtAM().getMinutes();
				}
				
				if(library.getStartTimeAtPM().getHours()<10){
					pms += "0"+library.getStartTimeAtPM().getHours()+":";
				}else{
					pms += library.getStartTimeAtPM().getHours()+":";
				}
				if(library.getStartTimeAtPM().getMinutes() <10){
					pms += "0"+library.getStartTimeAtPM().getMinutes();
				}else{
					pms += library.getStartTimeAtPM().getMinutes();
				}
				
				if(library.getEndTimeAtPM().getHours()<10){
					pme += "0"+library.getEndTimeAtPM().getHours()+":";
				}else{
					pme += library.getEndTimeAtPM().getHours()+":";
				}
				if(library.getEndTimeAtPM().getMinutes() <10){
					pme += "0"+library.getEndTimeAtPM().getMinutes();
				}else{
					pme += library.getEndTimeAtPM().getMinutes();
				}
				
				s.put("ams", ams);
				s.put("ame", ame);
				s.put("pms", pms);
				s.put("pme", pme);
				
				s.put("people", library.getPeople());
				if(library.getIsProjection().equals("1")){
					s.put("isProjection", "是");
				}else{
					s.put("isProjection", "否");
				}
				if(library.getVideoConferencing().equals("1")){
					s.put("videoConferencing", "是");
				}else{
					s.put("videoConferencing", "否");
				}
				j.add(s);
			}
			json.put("list", j);
			response.getWriter().write(json + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/findALibrary.do",method=RequestMethod.POST)
	public void findALibrary(HttpServletRequest request,HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("libraryId"));
			Library library = libraryService.find(id);
			User user = userService.load(library.getAdminId());
			library.setAdminName(user.getNickname());
			JSONObject s = gets(library);
			JSONArray arr = new JSONArray();
			List<ICON> icons = iconService.load(library.getId(), 1000);
			for(ICON icon : icons){
				String str = new FileOp().readFile(request.getRealPath("/")+"img/"+icon.getPictureName());
				JSONObject json = new JSONObject();
				json.put("img", str);
				arr.add(json);
			}
			s.put("imgs", arr);
			response.getWriter().write(s+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/findALibraryNotImg.do",method=RequestMethod.POST)
	public void findALibraryNotImg(HttpServletRequest request,HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("libraryId"));
			Library library = libraryService.find(id);
			User user = userService.load(library.getAdminId());
			library.setAdminName(user.getNickname());
			org.json.JSONObject s =new org.json.JSONObject(library);
			response.getWriter().write(s+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/toEditLibrary.do",method=RequestMethod.GET)
	public String toEditLibrary(HttpServletRequest request,HttpServletResponse response){
		return "/admin/editLibrary";
	}
	
	@RequestMapping(value="/editFindALibrary.do",method=RequestMethod.POST)
	public void editFindALibrary(HttpServletRequest request,HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("libraryId"));
			Library library = libraryService.find(id);
			User user = userService.load(library.getAdminId());
			library.setAdminName(user.getNickname());
			JSONObject s = getEditLibrary (library);
			
			JSONArray arr = new JSONArray();
			List<ICON> icons = iconService.load(library.getId(), 1000);
			for(ICON icon : icons){
				String str = new FileOp().readFile(request.getRealPath("/")+"img/"+icon.getPictureName());
				JSONObject json = new JSONObject();
				json.put("img", str);
				json.put("picName", icon.getPictureName());
				json.put("id", icon.getId());
				arr.add(json);
			}
			s.put("imgs", arr);
			response.getWriter().write(s+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/deleteLibrary.do",method=RequestMethod.POST)
	public void deleteLibrary(HttpServletRequest request,HttpServletResponse response){
		try {
			long id = Long.parseLong(request.getParameter("id"));
			List<ICON> icons = iconService.load(id, 1000);
			for(ICON icon : icons){
				String path = request.getRealPath("/")+icon.getPictureName();
				new FileOp().deleteFile(path);
				iconService.delete(icon.getId());
			}
			libraryService.delete(id);
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
	@RequestMapping(value="/getCountByAdmin.do",method=RequestMethod.POST)
	public void getCountByAdmin(HttpServletRequest request,HttpServletResponse response){
		try {
			long admin = Long.parseLong(request.getParameter("admin"));
			long count = libraryService.getCountByAdmin(admin);
			response.getWriter().write(count+"");
		} catch (Exception e) {
			try {
				response.getWriter().write("-");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public JSONObject getEditLibrary (Library library){
		JSONObject s = new JSONObject();
		s.put("address", library.getAddress());
		s.put("admin", userService.load(library.getAdminId()).getNickname());
		s.put("id", library.getId());
		s.put("peoplenum", library.getPeople());
		s.put("disc", library.getDisc());
		s.put("know", library.getBookAndKnow());
		
		s.put("amshour", library.getStartTimeAtAM().getHours());
		s.put("amsmin", library.getStartTimeAtAM().getMinutes());
		s.put("amssec", library.getStartTimeAtAM().getSeconds());
		
		s.put("amehour", library.getEndTimeAtAM().getHours());
		s.put("amemin", library.getEndTimeAtAM().getMinutes());
		s.put("amesec", library.getEndTimeAtAM().getSeconds());
		
		s.put("pmshour", library.getStartTimeAtPM().getHours());
		s.put("pmsmin", library.getStartTimeAtPM().getMinutes());
		s.put("pmssec", library.getStartTimeAtPM().getSeconds());
		
		s.put("pmehour", library.getEndTimeAtPM().getHours());
		s.put("pmemin", library.getEndTimeAtPM().getMinutes());
		s.put("pmesec", library.getEndTimeAtPM().getSeconds());
		
		s.put("isp", library.getIsProjection());
		s.put("isa", library.getVideoConferencing());
		
		return s;
	}
	
	public JSONObject gets (Library library){
		JSONObject s = new JSONObject();
		s.put("address", library.getAddress());
		s.put("admin", userService.load(library.getAdminId()).getNickname());
		s.put("id", library.getId());
		s.put("peoplenum", library.getPeople());
		s.put("disc", library.getDisc());
		s.put("know", library.getBookAndKnow());
		s.put("adminId", library.getAdminId());
		
		String ams = "";
		String ame = "";
		String pms = "";
		String pme = "";
		if(library.getStartTimeAtAM().getHours()<10){
			ams += "0"+library.getStartTimeAtAM().getHours()+":";
		}else{
			ams += library.getStartTimeAtAM().getHours()+":";
		}
		if(library.getStartTimeAtAM().getMinutes() <10){
			ams += "0"+library.getStartTimeAtAM().getMinutes();
		}else{
			ams += library.getStartTimeAtAM().getMinutes();
		}
		
		if(library.getEndTimeAtAM().getHours()<10){
			ame += "0"+library.getEndTimeAtAM().getHours()+":";
		}else{
			ame += library.getEndTimeAtAM().getHours()+":";
		}
		if(library.getEndTimeAtAM().getMinutes() <10){
			ame += "0"+library.getEndTimeAtAM().getMinutes();
		}else{
			ame += library.getEndTimeAtAM().getMinutes();
		}
		
		if(library.getStartTimeAtPM().getHours()<10){
			pms += "0"+library.getStartTimeAtPM().getHours()+":";
		}else{
			pms += library.getStartTimeAtPM().getHours()+":";
		}
		if(library.getStartTimeAtPM().getMinutes() <10){
			pms += "0"+library.getStartTimeAtPM().getMinutes();
		}else{
			pms += library.getStartTimeAtPM().getMinutes();
		}
		
		if(library.getEndTimeAtPM().getHours()<10){
			pme += "0"+library.getEndTimeAtPM().getHours()+":";
		}else{
			pme += library.getEndTimeAtPM().getHours()+":";
		}
		if(library.getEndTimeAtPM().getMinutes() <10){
			pme += "0"+library.getEndTimeAtPM().getMinutes();
		}else{
			pme += library.getEndTimeAtPM().getMinutes();
		}
		
		s.put("ams", ams);
		s.put("ame", ame);
		s.put("pms", pms);
		s.put("pme", pme);
		
		s.put("people", library.getPeople());
		if(library.getIsProjection().equals("1")){
			s.put("isProjection", "是");
		}else{
			s.put("isProjection", "否");
		}
		if(library.getVideoConferencing().equals("1")){
			s.put("videoConferencing", "是");
		}else{
			s.put("videoConferencing", "否");
		}
		return s;
	}
	
	
}
