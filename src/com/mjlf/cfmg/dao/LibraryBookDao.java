package com.mjlf.cfmg.dao;

import java.util.Date;
import java.util.List;

import com.mjlf.cfmg.entity.LibraryBook;
import com.mjlf.cfmg.entity.PageValue;

public interface LibraryBookDao {
	public void add(LibraryBook libraryBook);
	public void delete(long id);
	public void update(LibraryBook libraryBook);
	public LibraryBook load(long id);
	public List<LibraryBook> load();
	public List<LibraryBook> loadByUser(long userId);
	public List<LibraryBook> loadByLibrary(long LibraryId);
	public PageValue loadPageValue(PageValue pageValue);
	public PageValue loadPageValueByUser(PageValue pageValue, long UserId);
	public PageValue loadPageValueByLibrary(PageValue pageValue, long libraryId);
	public boolean loadByLibrary(long libraryId, Date startTime, Date endTime);
	List<LibraryBook> findByLibraryAndTime(long libraryId, Date date);
	List<LibraryBook> findByLibrarybyState(long userId,int state);
	PageValue loadPageValueByLibraryState(PageValue pageValue, int state, long userId, int type);
}
