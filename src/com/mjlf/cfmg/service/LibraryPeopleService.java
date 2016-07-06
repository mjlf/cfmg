package com.mjlf.cfmg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjlf.cfmg.dao.LibraryPeopleDao;
import com.mjlf.cfmg.daoImp.LibraryPeopleDaoImpl;
import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.LibraryPeople;
import com.mjlf.cfmg.entity.PageValue;

@Service
public class LibraryPeopleService {
	@Autowired
	private LibraryPeopleDaoImpl libraryPeopleDaoImpl;
	
	public long add(LibraryPeople libraryPeople) {
		
		return libraryPeopleDaoImpl.add(libraryPeople);
	}

	
	public void delete(long id) {
		
		libraryPeopleDaoImpl.delete(id);
	}

	
	public void update(LibraryPeople libraryPeople) {
		
		libraryPeopleDaoImpl.update(libraryPeople);
	}

	
	public List<LibraryPeople> load() {
		
		return libraryPeopleDaoImpl.load();
	}
	
	public LibraryPeople findByCode(String code){
		return libraryPeopleDaoImpl.findBySignINCode(code);
	}
	
	public List<LibraryPeople> load(long libraryBookId) {
		
		return libraryPeopleDaoImpl.load(libraryBookId);
	}

	
	public LibraryPeople loadById(long id) {
		
		return libraryPeopleDaoImpl.loadById(id);
	}

	
	public PageValue load(PageValue pageValue) {
		
		return libraryPeopleDaoImpl.load(pageValue);
	}

	
	public PageValue loadByBook(PageValue pageValue, long libraryBookId) {
		
		return libraryPeopleDaoImpl.loadByBook(pageValue, libraryBookId);
	}

}
