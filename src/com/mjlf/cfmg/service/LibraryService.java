package com.mjlf.cfmg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjlf.cfmg.daoImp.LibraryDaoImpl;
import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.PageValue;

@Service
public class LibraryService {
	
	@Autowired
	private LibraryDaoImpl libraryDaoImpl;
	
	public Library add(Library library){
		return libraryDaoImpl.add(library);
	}
	
	public void delete(long id){
		libraryDaoImpl.delete(id);
	}
	
	public void update(Library library){
		libraryDaoImpl.update(library);
	}
	
	public Library find(long id){
		return libraryDaoImpl.find(id);
	}
	
	public List<Library> load(){
		return libraryDaoImpl.load();
	}
	
	public List<Library> load(String address, int peopleNum){
		return libraryDaoImpl.load(address, peopleNum);
	}
	
	public PageValue pageLoad(PageValue pageValue){
		return libraryDaoImpl.pageLoad(pageValue);
	}
	
	public PageValue pageLoadCondition(PageValue pageValue){
		return libraryDaoImpl.pageLoadCondition(pageValue);
	}
	
	public long getCountByAdmin(long admin){
		return libraryDaoImpl.getCountByAdmin(admin);
	}
}
