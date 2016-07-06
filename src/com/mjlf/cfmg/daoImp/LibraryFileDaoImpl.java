package com.mjlf.cfmg.daoImp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.mjlf.cfmg.dao.LibraryFileDao;
import com.mjlf.cfmg.entity.LibraryFile;
import com.mjlf.cfmg.entity.PageValue;

@Component
public class LibraryFileDaoImpl implements LibraryFileDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void add(LibraryFile libraryFile) {
		hibernateTemplate.save(libraryFile);
	}

	@Override
	public void delete(long id) {
		hibernateTemplate.delete(load(id));
	}

	@Override
	public void deleteByLibraryBook(long libraryBookId) {
		List<LibraryFile> list = loadByLibraryBook(libraryBookId);
		for(int i = list.size()-1; i >= 0; i--){
			hibernateTemplate.delete(list.get(i));
		}
	}

	@Override
	public void update(LibraryFile libraryFile) {
		hibernateTemplate.update(libraryFile);
	}

	@Override
	public LibraryFile load(long id) {
		return hibernateTemplate.get(LibraryFile.class, id);
	}

	@Override
	public List<LibraryFile> loadByUser(long userId) {
		String hql = "from LibraryFile where uploadUserId = ?";
		Object[] value = new Object[]{userId};
		List<LibraryFile> list = (List<LibraryFile>)hibernateTemplate.find(hql, value);
		return list;
	}

	@Override
	public List<LibraryFile> loadByLibraryBook(long libraryBookId) {
		String hql = "from LibraryFile where libraryId = ?";
		Object[] value = new Object[]{libraryBookId};
		List<LibraryFile> list = (List<LibraryFile>)hibernateTemplate.find(hql, value);
		return list;
	}

	@Override
	public List<LibraryFile> load() {
		String hql = "from LibraryFile";
		List<LibraryFile> list = (List<LibraryFile>)hibernateTemplate.find(hql);
		return list;
	}

	@Override
	public PageValue load(PageValue pageValue) {
		String hql = new String("from LibraryFile");

		List<LibraryFile> list = (List<LibraryFile>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setFirstResult((pageValue.getPageIndex() - 1) * pageValue.getEverypagenum());
				query.setMaxResults(pageValue.getEverypagenum());
				List list = query.list();
				return list;
			}
		});
		Long count = (Long) hibernateTemplate.find("select count(*) from LibraryFile").iterator().next();
		pageValue.setAllCount(count.intValue());
		pageValue.countPageCount();
		pageValue.addValue(list);
		return pageValue;
	}

	@Override
	public PageValue loadByUser(PageValue pageValue, long userId) {
		StringBuffer hql = new StringBuffer("from LibraryFile");

		LibraryFile lib = (LibraryFile) pageValue.getObject();

		List<LibraryFile> list = (List<LibraryFile>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				DetachedCriteria dc = DetachedCriteria.forClass(LibraryFile.class);
				dc.add(Restrictions.eq("uploadUserId", userId));
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

	@Override
	public PageValue loadByLibraryBook(PageValue pageValue, long libraryBookId) {
		StringBuffer hql = new StringBuffer("from LibraryFile");

		LibraryFile lib = (LibraryFile) pageValue.getObject();

		List<LibraryFile> list = (List<LibraryFile>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				DetachedCriteria dc = DetachedCriteria.forClass(LibraryFile.class);
				dc.add(Restrictions.eq("libraryBookId", libraryBookId));
				Criteria c = dc.getExecutableCriteria(session);
				List<LibraryFile> list = c.list();
				pageValue.setAllCount(list.size());
				c.setFirstResult((pageValue.getPageIndex() - 1) * pageValue.getEverypagenum());
				c.setMaxResults(pageValue.getEverypagenum());
				c.addOrder(Order.desc("uploadTime"));
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
