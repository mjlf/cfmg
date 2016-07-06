package com.mjlf.cfmg.dao;

import java.util.List;

import com.mjlf.cfmg.entity.LibraryFile;
import com.mjlf.cfmg.entity.PageValue;

public interface LibraryFileDao {
	public void add(LibraryFile libraryFile);
	public void delete(long id);
	public void deleteByLibraryBook(long libraryBookId);
	public void update(LibraryFile libraryFile);
	public LibraryFile load(long id);
	public List<LibraryFile> loadByUser(long userId);
	public List<LibraryFile> loadByLibraryBook(long libraryBookId);
	public PageValue load(PageValue pageValue);
	public PageValue loadByUser(PageValue pageValue, long userId);
	public PageValue loadByLibraryBook(PageValue pageValue, long libraryBookId);
	public List<LibraryFile> load();
	
}
