package com.mjlf.cfmg.daoImp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.mjlf.cfmg.dao.LibraryPeopleDao;
import com.mjlf.cfmg.entity.LibraryBook;
import com.mjlf.cfmg.entity.LibraryFile;
import com.mjlf.cfmg.entity.LibraryPeople;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.entity.User;

@Component
public class LibraryPeopleDaoImpl implements LibraryPeopleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public long add(LibraryPeople libraryPeople) {
		return (long) hibernateTemplate.save(libraryPeople);
	}

	@Override
	public void delete(long id) {
		hibernateTemplate.delete(loadById(id));
	}

	@Override
	public void update(LibraryPeople libraryPeople) {
		hibernateTemplate.update(libraryPeople);
	}

	@Override
	public List<LibraryPeople> load() {
		String hql = "from LibraryPeople";
		List<LibraryPeople> list = (List<LibraryPeople>)hibernateTemplate.find(hql);
		return list;
	}

	@Override
	public LibraryPeople findBySignINCode(String code){
		LibraryPeople libraryPeople = (LibraryPeople) (hibernateTemplate.find("from LibraryPeople where sign_inCode=?", new String[]{code,}).get(0));
		return libraryPeople;
	}
	
	@Override
	public List<LibraryPeople> load(long libraryBookId) {
		String hql = "from LibraryPeople where libraryBookId = ?";
		Object[] value = new Object[]{libraryBookId};
		List<LibraryPeople> list = (List<LibraryPeople>) hibernateTemplate.find(hql, value);
		return list;
	}

	@Override
	public LibraryPeople loadById(long id) {
		return hibernateTemplate.get(LibraryPeople.class, id);
	}

	@Override
	public PageValue load(PageValue pageValue) {
		String hql = new String("from LibraryPeople");

		List<LibraryPeople> list = (List<LibraryPeople>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setFirstResult((pageValue.getPageIndex() - 1) * pageValue.getEverypagenum());
				query.setMaxResults(pageValue.getEverypagenum());
				List list = query.list();
				return list;
			}
		});
		Long count = (Long) hibernateTemplate.find("select count(*) from LibraryPeople").iterator().next();
		pageValue.setAllCount(count.intValue());
		pageValue.countPageCount();
		pageValue.addValue(list);
		return pageValue;
	}

	@Override
	public PageValue loadByBook(PageValue pageValue, long libraryBookId) {
		StringBuffer hql = new StringBuffer("from LibraryPeople");

		LibraryPeople lib = (LibraryPeople) pageValue.getObject();

		List<LibraryPeople> list = (List<LibraryPeople>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				DetachedCriteria dc = DetachedCriteria.forClass(LibraryPeople.class);
				dc.add(Restrictions.eq("libraryBookId", libraryBookId));
				Criteria c = dc.getExecutableCriteria(session);
				List<LibraryFile> list = c.list();
				pageValue.setAllCount(list.size());
				c.setFirstResult((pageValue.getPageIndex() - 1) * pageValue.getEverypagenum());
				c.setMaxResults(pageValue.getEverypagenum());
				list = c.list();
				return list;
			}
		});
		pageValue.countPageCount();
		pageValue.addValue(list);
		hibernateTemplate.clear();
		return pageValue;
	}
}
