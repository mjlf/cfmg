package com.mjlf.cfmg.daoImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.mjlf.cfmg.dao.ICONDao;
import com.mjlf.cfmg.entity.ICON;

@Component
public class ICONDaoImpl implements ICONDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public ICON add(ICON icon) {
		hibernateTemplate.save(icon);
		hibernateTemplate.flush();
		return icon;
	}

	@Override
	public void delete(long id) {
		ICON icon = (ICON) find(id);
		hibernateTemplate.delete(icon);
		hibernateTemplate.clear();
	}

	@Override
	public ICON find(long id) {
		ICON icon = (ICON)hibernateTemplate.get(ICON.class, id);
		hibernateTemplate.clear();
		return icon;
	}

	@Override
	public List<ICON> load(long characterId, long type) {
		List<ICON> list = null;
		String hql = "from ICON where characterId=? and type=?";
		Object[] value = new Object[]{characterId, type};
		list = (List<ICON>) hibernateTemplate.find(hql, value);
		hibernateTemplate.clear();
		return list;
	}

	@Override
	public List<ICON> load(long type) {
		List<ICON> list = null;
		String hql = "from ICON where type=?";
		Object[] value = new Object[]{ type};
		list = (List<ICON>) hibernateTemplate.find(hql, value);
		hibernateTemplate.clear();
		return list;
	}
}
