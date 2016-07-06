package com.mjlf.cfmg.dao;

import java.util.List;

import com.mjlf.cfmg.entity.LibraryPeople;
import com.mjlf.cfmg.entity.PageValue;

public interface LibraryPeopleDao {
	public long add(LibraryPeople libraryPeople);
	public void delete(long id);
	public void update(LibraryPeople libraryPeople);
	public List<LibraryPeople> load();
	public List<LibraryPeople> load(long libraryBookId);
	public LibraryPeople loadById(long id);
	public PageValue load(PageValue pageValue);
	public PageValue loadByBook(PageValue pageValue, long libraryBookId);
	LibraryPeople findBySignINCode(String code);
}
