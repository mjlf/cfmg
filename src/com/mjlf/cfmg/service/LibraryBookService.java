package com.mjlf.cfmg.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjlf.cfmg.dao.LibraryBookDao;
import com.mjlf.cfmg.daoImp.LibraryBookDaoImpl;
import com.mjlf.cfmg.entity.LibraryBook;
import com.mjlf.cfmg.entity.PageValue;

@Service
public class LibraryBookService {
	
	@Autowired
	private LibraryBookDaoImpl libraryBookDaoImpl;
	
	public void add(LibraryBook libraryBook) {
		libraryBookDaoImpl.add(libraryBook);
	}

	 
	public void delete(long id) {
		libraryBookDaoImpl.delete(id);
	}

	 
	public void update(LibraryBook libraryBook) {

		libraryBookDaoImpl.update(libraryBook);
	}

	 
	public LibraryBook load(long id) {
		return libraryBookDaoImpl.load(id);
	}

	 
	public List<LibraryBook> load() {
		return libraryBookDaoImpl.load();
	}

	 
	public List<LibraryBook> loadByUser(long userId) {
		return libraryBookDaoImpl.loadByUser(userId);
	}

	 
	public List<LibraryBook> loadByLibrary(long LibraryId) {
		return libraryBookDaoImpl.loadByLibrary(LibraryId);
	}

	public List<LibraryBook> findByLibraryAndTime(long libraryId, Date date){
		return libraryBookDaoImpl.findByLibraryAndTime(libraryId, date);
	}
	 
	public PageValue loadPageValue(PageValue pageValue) {
		return libraryBookDaoImpl.loadPageValue(pageValue);
	}

	 
	public PageValue loadPageValueByUser(PageValue pageValue, long UserId) {
		return loadPageValueByUser(pageValue, UserId);
	}

	 
	public PageValue loadPageValueByLibrary(PageValue pageValue, long libraryId) {
		return libraryBookDaoImpl.loadPageValueByLibrary(pageValue, libraryId);
	}

	public boolean loadByLibrary(long libraryId, Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<LibraryBook> findByLibrarybyState(long userId,int state){
		return libraryBookDaoImpl.findByLibrarybyState(userId ,state);
	}
	
	public PageValue loadPageValueByLibraryState(PageValue pageValue, int state, long userId, int type){
		return libraryBookDaoImpl.loadPageValueByLibraryState(pageValue, state, userId, type);
	}
}
