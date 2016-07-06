package com.mjlf.cfmg.daoImp;

import java.util.Date;
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

import com.mjlf.cfmg.dao.LibraryBookDao;
import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.LibraryBook;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.entity.User;

@Component
public class LibraryBookDaoImpl implements LibraryBookDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void add(LibraryBook libraryBook) {
		hibernateTemplate.save(libraryBook);
	}

	@Override
	public void delete(long id) {
		LibraryBook libraryBook = load(id);
		hibernateTemplate.delete(libraryBook);
	}

	@Override
	public void update(LibraryBook libraryBook) {
		hibernateTemplate.update(libraryBook);
	}

	@Override
	public LibraryBook load(long id) {
		return hibernateTemplate.get(LibraryBook.class, id);
	}

	@Override
	public List<LibraryBook> load() {
		String hql = "from LibraryBook";
		List<LibraryBook> list = (List<LibraryBook>) hibernateTemplate.find(hql);
		return list;
	}

	@Override
	public List<LibraryBook> loadByUser(long userId) {
		String hql = "from LibraryBook where bookUserId = ?";
		Object[] value = new Object[] { userId };
		return (List<LibraryBook>) hibernateTemplate.find(hql, value);
	}

	@Override
	public List<LibraryBook> loadByLibrary(long libraryId) {
		String hql = "from LibraryBook where libraryId = ?";
		Object[] value = new Object[] { libraryId };
		return (List<LibraryBook>) hibernateTemplate.find(hql, value);
	}

	@Override
	public boolean loadByLibrary(long libraryId, Date startTime, Date endTime) {

		String hql = "count(*) from LibraryBook where "
				+ "startTime between ? and ? and endTime between ? and ?";
		Object[] value = new Object[] { startTime, endTime, startTime, endTime };
		Long count = (Long) hibernateTemplate
				.find("select count(*) from LibraryBook").iterator().next();
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public PageValue loadPageValue(PageValue pageValue) {
		String hql = new String("from LibraryBook");

		List<LibraryBook> list = (List<LibraryBook>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setFirstResult((pageValue.getPageIndex() - 1) * pageValue.getEverypagenum());
				query.setMaxResults(pageValue.getEverypagenum());
				List list = query.list();
				return list;
			}
		});
		Long count = (Long) hibernateTemplate.find("select count(*) from LibraryBook").iterator().next();
		pageValue.setAllCount(count.intValue());
		pageValue.countPageCount();
		pageValue.addValue(list);
		return pageValue;
	}

	@Override
	public PageValue loadPageValueByUser(PageValue pageValue, long UserId) {
		StringBuffer hql = new StringBuffer("from LibraryBook");

		LibraryBook lib = (LibraryBook) pageValue.getObject();

		List<LibraryBook> list = (List<LibraryBook>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				DetachedCriteria dc = DetachedCriteria.forClass(LibraryBook.class);
				dc.add(Restrictions.eq("bookUserId", UserId));
				Criteria c = dc.getExecutableCriteria(session);
				List<LibraryBook> list = c.list();
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
	public PageValue loadPageValueByLibrary(PageValue pageValue, long libraryId) {
		StringBuffer hql = new StringBuffer("from LibraryBook");

		LibraryBook lib = (LibraryBook) pageValue.getObject();

		List<LibraryBook> list = (List<LibraryBook>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				DetachedCriteria dc = DetachedCriteria.forClass(LibraryBook.class);
				dc.add(Restrictions.eq("libraryId", libraryId));
				Criteria c = dc.getExecutableCriteria(session);
				List<LibraryBook> list = c.list();
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
	public PageValue loadPageValueByLibraryState(PageValue pageValue, int state, long userId, int type) {
		StringBuffer hql = new StringBuffer("from LibraryBook");

		LibraryBook lib = (LibraryBook) pageValue.getObject();

		List<LibraryBook> list = (List<LibraryBook>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				DetachedCriteria dc = DetachedCriteria.forClass(LibraryBook.class);
				dc.add(Restrictions.eq("state", state));
				if(type == 1){
					dc.add(Restrictions.eq("adminUserId", userId));
				}else if(type == 0){
					dc.add(Restrictions.eq("bookUserId", userId));
				}
				Criteria c = dc.getExecutableCriteria(session);
				List<LibraryBook> list = c.list();
				pageValue.setAllCount(list.size());
				c.setFirstResult((pageValue.getPageIndex() - 1) * pageValue.getEverypagenum());
				c.setMaxResults(pageValue.getEverypagenum());
				if(state == 1){
					c.addOrder(Order.desc("startTime"));
				}else if(state == 0){
					c.addOrder(Order.asc("bookTime"));
				}
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
	public List<LibraryBook> findByLibraryAndTime(long libraryId, Date date){
		Date date1 = new Date(date.getTime() + (24*60*60*1000));
		List<LibraryBook> list = null;
		String hql = "from LibraryBook where libraryId = ? and startTime >= ? and endTime <= ? order by startTime";
		Object[] value = new Object[]{libraryId, date, date1};
		list = (List<LibraryBook>) hibernateTemplate.find(hql, value);
		return list;
	}
	
	@Override
	public List<LibraryBook> findByLibrarybyState(long userId,int state){
		List<LibraryBook> list = null;
		String hql = "from LibraryBook where adminUserId = ? and state = ? order by bookTime";
		Object[] value = new Object[]{userId, state};
		list = (List<LibraryBook>) hibernateTemplate.find(hql, value);
		return list;
	}
}
