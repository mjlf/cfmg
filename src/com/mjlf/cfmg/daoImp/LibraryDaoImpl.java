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

import com.mjlf.cfmg.dao.LibraryDao;
import com.mjlf.cfmg.entity.Library;
import com.mjlf.cfmg.entity.PageValue;

@Component
public class LibraryDaoImpl implements LibraryDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public Library add(Library library) {
		hibernateTemplate.save(library);
		hibernateTemplate.flush();
		return library;
	}
	
	@Override
	public void delete(long id) {
		hibernateTemplate.delete(find(id));
	}
	
	@Override
	public void update(Library library) {
		hibernateTemplate.update(library);
	}
	
	@Override
	public Library find(long id) {
		Library library = (Library)hibernateTemplate.get(Library.class, id);
		return library;
	}

	@Override
	public List<Library> load() {
		List<Library> list = null;
		String hql = "from Library";
		list = (List<Library>) hibernateTemplate.find(hql);
		return list;
	}

	@Override
	public List<Library> load(String address, int peopleNum){
		List<Library> list = null;
 		try {
			String hql = "from Library where address like '%"+address+"%' and people >= ?";
			Object[] value = new Object[]{peopleNum};
			list = (List<Library>) hibernateTemplate.find(hql, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return list;
	}
	
	@Override
	public PageValue pageLoad(PageValue pageValue) {
		String hql = "from Library order by adminId";
		List<Library> list = (List<Library>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				  Query query = session.createQuery(hql);
	              query.setFirstResult((pageValue.getPageIndex()-1)*pageValue.getEverypagenum());
	              query.setMaxResults(pageValue.getEverypagenum());
	              List list = query.list();
		          return list;
			}
		});
//		List<Library> list = (List<Library>) hibernateTemplate.find(hql);
		
		Long count = (Long) hibernateTemplate.find("select count(*) from Library").iterator().next();
		pageValue.setAllCount(count.intValue());
		pageValue.countPageCount();
		pageValue.addValue(list);
		hibernateTemplate.clear();
		return pageValue;
	}

	@Override
	public PageValue pageLoadCondition(PageValue pageValue) {
		StringBuffer hql = new StringBuffer("from Library order by adminId");
		
		Library lib = (Library) pageValue.getObject();
		
         List<Library> list = (List<Library>) hibernateTemplate.execute(new HibernateCallback() {

 			@Override
 			public Object doInHibernate(Session session) throws HibernateException {
 				 DetachedCriteria dc = DetachedCriteria.forClass(Library.class);
 				 if(lib.getCheckamdin() == 1){
 					 dc.add(Restrictions.eq("adminId", lib.getAdminId()));
 				 }else if(lib.getCheckamdin() == 0){
 					 dc.add(Restrictions.not(Restrictions.eq("adminId", lib.getAdminId())));
 				 }
 				 dc.add(Restrictions.eq("isProjection", lib.getIsProjection()));
 				 dc.add(Restrictions.eq("videoConferencing", lib.getVideoConferencing()));
 				 dc.add(Restrictions.between("people", lib.getStartnum(), lib.getEndnum()));
 				 dc.add(Restrictions.or(Restrictions.and(Restrictions.le("startTimeAtAM", lib.getCheckStartTime()), Restrictions.ge("endTimeAtAM", lib.getCheckEndTime())),
 						 Restrictions.and(Restrictions.le("startTimeAtPM", lib.getCheckStartTime()), Restrictions.ge("endTimeAtPM", lib.getCheckEndTime()))));
 				 Criteria c = dc.getExecutableCriteria(session);
 				 List<Library> list = c.list();
 				 pageValue.setAllCount(list.size());
 				 c.setFirstResult((pageValue.getPageIndex()-1)*pageValue.getEverypagenum());
 				 c.setMaxResults(pageValue.getEverypagenum());
 				 c.addOrder(Order.asc("adminId"));
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
	public long getCountByAdmin(long admin){
		return (Long) hibernateTemplate.find("select count(*) from Library where adminId="+admin).iterator().next();
	}
}
