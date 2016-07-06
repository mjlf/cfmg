package com.mjlf.cfmg.viewcon;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.LibraryBook;
import com.mjlf.cfmg.entity.LibraryFile;
import com.mjlf.cfmg.entity.LibraryPeople;
import com.mjlf.cfmg.mail.MailConfigure;
import com.mjlf.cfmg.mail.PropertiesMailConfig;
import com.mjlf.cfmg.mail.SendMail;
import com.mjlf.cfmg.service.LibraryBookService;
import com.mjlf.cfmg.service.LibraryFileService;
import com.mjlf.cfmg.service.LibraryPeopleService;
import com.mjlf.cfmg.service.LibraryService;
import com.mjlf.cfmg.utils.DateUtils;
import com.mjlf.cfmg.utils.ImportExecl;
import com.mjlf.cfmg.utils.RandomString;  
  
@Controller("uploadAction")
@RequestMapping("cfmg")
public class UploadAction {  
  
	@Autowired
	private LibraryPeopleService LibraryPeopleService;
	
	@Autowired
	private LibraryFileService libraryFileService;
	
	@Autowired
	private LibraryBookService libraryBookService;
	
	@Autowired
	private LibraryService libraryService;
	
    @RequestMapping(value = "/upload.do") 
    public void upload(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap model) {  
  
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  
        System.out.println(path);  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
//        model.addAttribute("fileUrl", request.getContextPath()+"/upload/"+fileName);  
    }  
    
    @RequestMapping(value = "/uploadLibraryBookFile.do")
    public void uploadFile(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap model) { 
    	try {
    		 
    		 String path = request.getSession().getServletContext().getRealPath("upload");  
    	        String fileName = file.getOriginalFilename();
    	        String name = (new Date().getTime())+"";
    	        System.out.println(path);  
    	        int index = fileName.lastIndexOf('.');
    	        String type = fileName.substring(index);
    	        name += type;
    	        File targetFile = new File(path, name); 
    	        if(!targetFile.exists()){  
    	            targetFile.mkdirs();  
    	        }  
    	        //保存  
    	        try {  
    	            file.transferTo(targetFile);  
    	        } catch (Exception e) {  
    	            e.printStackTrace();  
    	        }  
    	        
    	        HttpSession session = request.getSession();
    	        LibraryPeople libraryPeople = (LibraryPeople)session.getAttribute("userlogin");
    	        LibraryFile libraryFile = new LibraryFile();
    	        libraryFile.setFileLength(file.getSize());
    	        libraryFile.setFileName(fileName);
    	        libraryFile.setFilePath(request.getContextPath()+"/upload/"+name);
    	        libraryFile.setFileType(type);
    	        libraryFile.setLibraryBookId(libraryPeople.getLibraryBookId());
    	        libraryFile.setUploadTime(new Date());
    	        libraryFile.setUploadUserId(libraryPeople.getId());
    	        libraryFile.setFileNowName(name);
    	        
    	        libraryFileService.add(libraryFile);
    	        
//    	        model.addAttribute("fileUrl", request.getContextPath()+"/upload/"+name);  
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
  
    @RequestMapping(value = "/uploadPeopleFile.do", method=RequestMethod.POST)  
    public void uploadPeopleFile(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap model) {  
       try {
	    	   String path = request.getSession().getServletContext().getRealPath("upload");  
	           String fileName = file.getOriginalFilename();  
	           File targetFile = new File(path, fileName);  
	           if(!targetFile.exists()){  
	               targetFile.mkdirs();  
	           }  
	           try {  
	               file.transferTo(targetFile);  
	           } catch (Exception e) {  
	               e.printStackTrace();  
	           }  
	           ImportExecl inExecl = new ImportExecl();
	           List<List<String>> list = inExecl.read(targetFile.getPath());
	           
	           MailConfigure mailConfigure = PropertiesMailConfig.getMailConfig();
	           for(int i = 1; i < list.size(); i++){
	           	List<String> info = list.get(i);
	           	LibraryPeople libraryPeople = new LibraryPeople();
	           	double di = Double.parseDouble(info.get(0));
	           	long id = (long)di;
	           	double phone = Double.parseDouble(info.get(2));
	           	long p = (long)phone;
	           	libraryPeople.setLibraryBookId(id);
	           	libraryPeople.setName(info.get(1));
	           	libraryPeople.setPhone(p+"");
	           	libraryPeople.setEmail(info.get(3));
	           	libraryPeople.setSign_inPassword(RandomString.getSignPassword());
	           	libraryPeople.setIsSign_in(0);
	           	libraryPeople.setSign_inCode(new Date().getTime()+"");
	           	
	           	LibraryBook libraryBook = libraryBookService.load(libraryPeople.getLibraryBookId());
	   			libraryBook.setStartTimeStr(DateUtils.formatDate(libraryBook.getStartTime()));
	   			
	   			Library library = libraryService.find(libraryBook.getLibraryId());
	   			
	   			mailConfigure.setPassword(mailConfigure.getPassword().substring(2));
	   			mailConfigure.setBody("您好！您将于"+libraryBook.getStartTimeStr()+"有一场会议， 会议地址"+library.getAddress()+"; 请提前半小时到场"
	   					+ "; 您的签到号:"+libraryPeople.getSign_inCode()+"; 签到密码为:"+libraryPeople.getSign_inPassword()+"; 如果您有会议需要的文化， 上传到如下服务地址"
	   							+ "其中登录名和密码与签到名与签到密码一致:"+"http://"+request.getRemoteAddr()+":"+request.getRemotePort()+"/cfmg/common/files.html");
	   			mailConfigure.addToUserName(libraryPeople.getEmail());
	   			mailConfigure.setSubject("签到账号密码");
	   			SendMail sen = new SendMail(mailConfigure);
	   			sen.send();
	           	
	           	LibraryPeopleService.add(libraryPeople);
	           }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
    
    @RequestMapping(value="/touploadFile.do", method=RequestMethod.GET)
	public String touploadFile(HttpServletRequest request, HttpServletResponse response){
		return "/all/upload";
	}
}  