package com.mjlf.cfmg.utils;

import java.util.Comparator;

import com.mjlf.cfmg.entity.LibraryBook;

public class LibraryBookCompare implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		LibraryBook libraryBook1 = null;
		LibraryBook libraryBook2 = null;
		if(o1 instanceof LibraryBook){
			libraryBook1 = (LibraryBook)o1;
		}
		if(o2 instanceof LibraryBook){
			libraryBook2 = (LibraryBook) o2;
		}
		
		if(libraryBook1.getLibraryId() > libraryBook2.getLibraryId()){
			return 1;
		}else if(libraryBook1.getLibraryId() < libraryBook2.getLibraryId()){
			return -1;
		}else{
			if(libraryBook1.getStartTime().getTime() > libraryBook2.getStartTime().getTime()){
				return 1;
			}else if (libraryBook1.getStartTime().getTime() == libraryBook2.getStartTime().getTime()){
				return 0;
			}else{
				return -1;
			}
		}
	}
}
