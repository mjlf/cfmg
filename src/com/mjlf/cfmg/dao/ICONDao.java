package com.mjlf.cfmg.dao;

import java.util.List;

import javax.swing.Icon;

import com.mjlf.cfmg.entity.ICON;

public interface ICONDao {
	public ICON add(ICON icon);
	public void delete(long id);
	public ICON find(long id);
	public List<ICON> load(long characterId, long type);
	public List<ICON> load(long type);
}
