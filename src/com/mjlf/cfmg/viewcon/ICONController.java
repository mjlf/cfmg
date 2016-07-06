package com.mjlf.cfmg.viewcon;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.Icon;

import org.apache.tomcat.jni.File;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mjlf.cfmg.entity.ICON;
import com.mjlf.cfmg.service.ICONService;
import com.mjlf.cfmg.service.LibraryService;
import com.mjlf.cfmg.service.UserService;
import com.mjlf.cfmg.utils.FileOp;
import com.mjlf.cfmg.utils.IMGID;
import com.mjlf.cfmg.utils.MD5;

/**
 * 图片控制层
 * @author mjlf
 *
 */
@Controller
@RequestMapping("cfmg")
public class ICONController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private ICONService iconService;
	
	@RequestMapping(value="/addicon.do", method=RequestMethod.POST)
	public void addinco(HttpServletRequest request, HttpServletResponse response){
		try {
			long libraryId = Long.parseLong(request.getParameter("libraryId"));
			String pictureName = request.getParameter("pictureName");
			long type = Long.parseLong(request.getParameter("type"));
			HttpSession session = request.getSession();
			
			String imgid = IMGID.createImgId();
			String path = request.getRealPath("/")+"img/"+imgid;
			java.io.File file = new java.io.File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			new FileOp().write(pictureName, file.getPath());
			ICON icon = new ICON();
			icon.setPictureName(imgid);
			icon.setCharacterId(libraryId);
			icon.setType(type);
			icon = iconService.add(icon);
			JSONObject json = new JSONObject(icon);
			response.getWriter().write(json + "");
		} catch (Exception e) {
			try {
				response.getWriter().write("N");
			} catch (Exception e2) {
				e.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/deleteIcon.do", method=RequestMethod.POST)
	public void deleteIcon(HttpServletRequest request, HttpServletResponse response){
		try {
			String id = request.getParameter("id");
			String picName = request.getParameter("picName");
			
			String path = request.getRealPath("/")+picName;
			iconService.delete(Long.parseLong(id));
			new FileOp().deleteFile(path);
			response.getWriter().write("Y");
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
