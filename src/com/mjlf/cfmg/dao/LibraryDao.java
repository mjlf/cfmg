package com.mjlf.cfmg.dao;

import java.util.List;

import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.PageValue;

public interface LibraryDao {
	public Library add(Library library);
	public void delete(long id);
	public void update(Library library);
	public Library find(long id);
	public List<Library> load();
	public PageValue pageLoad(PageValue pageValue);
	public PageValue pageLoadCondition(PageValue pageValue);
	long getCountByAdmin(long admin);
	List<Library> load(String address, int peopleNum);
}
