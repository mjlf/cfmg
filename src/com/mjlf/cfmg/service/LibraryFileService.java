package com.mjlf.cfmg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjlf.cfmg.dao.LibraryFileDao;
import com.mjlf.cfmg.daoImp.LibraryFileDaoImpl;
import com.mjlf.cfmg.entity.LibraryFile;
import com.mjlf.cfmg.entity.PageValue;

@Service
public class LibraryFileService {
	
	@Autowired
	private LibraryFileDaoImpl libraryFileDaoImpl;
	
	public void add(LibraryFile libraryFile) {
		libraryFileDaoImpl.add(libraryFile);
	}

	public void delete(long id) {
		libraryFileDaoImpl.delete(id);
	}

	public void deleteByLibraryBook(long libraryBookId) {
		libraryFileDaoImpl.deleteByLibraryBook(libraryBookId);
	}

	public void update(LibraryFile libraryFile) {
		libraryFileDaoImpl.update(libraryFile);
	}

	public LibraryFile load(long id) {
		return libraryFileDaoImpl.load(id);
	}

	public List<LibraryFile> loadByUser(long userId) {
		return libraryFileDaoImpl.loadByUser(userId);
	}

	public List<LibraryFile> loadByLibraryBook(long libraryBookId) {
		return libraryFileDaoImpl.loadByLibraryBook(libraryBookId);
	}

	public PageValue load(PageValue pageValue) {
		return libraryFileDaoImpl.load(pageValue);
	}

	public PageValue loadByUser(PageValue pageValue, long userId) {
		return libraryFileDaoImpl.loadByUser(pageValue, userId);
	}

	public PageValue loadByLibraryBook(PageValue pageValue, long libraryBookId) {
		
		return libraryFileDaoImpl.loadByLibraryBook(pageValue,libraryBookId);
	}

	public List<LibraryFile> load() {

		return libraryFileDaoImpl.load();
	}

}
