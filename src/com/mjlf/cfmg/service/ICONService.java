package com.mjlf.cfmg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjlf.cfmg.daoImp.ICONDaoImpl;
import com.mjlf.cfmg.entity.ICON;

@Service
public class ICONService {

	@Autowired
	private ICONDaoImpl iconDaoImpl;
	
	public ICON add(ICON icon){
		return iconDaoImpl.add(icon);
	}
	
	public void delete(long id){
		iconDaoImpl.delete(id);
	}
	
	public ICON find(long id){
		return iconDaoImpl.find(id);
	}
	
	public List<ICON> load(long characterId, long type){
		return iconDaoImpl.load(characterId, type);
	}
	
	public List<ICON> load(long type){
		return iconDaoImpl.load(type);
	}
}
