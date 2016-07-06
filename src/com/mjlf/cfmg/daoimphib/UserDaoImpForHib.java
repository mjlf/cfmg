package com.mjlf.cfmg.daoimphib;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mjlf.cfmg.dao.UserDao;
import com.mjlf.cfmg.entity.PageValue;
import com.mjlf.cfmg.entity.User;
import com.mjlf.cfmg.utils.SessionFactoryUtil;

public class UserDaoImpForHib implements UserDao {

	private HibernateTemplate hibernateTemplate = new HibernateTemplate(SessionFactoryUtil.getSessionFactory());
	

	@Override
	public void add(User user) {
		hibernateTemplate.save(user);
	}

	@Override
	public void delete(long id) {
		hibernateTemplate.delete(load(id));
	}

	@Override
	public boolean login(User user) {
		List<User> list = load();
		for(User u : list){
			if(user.getUsername().equals(u.getUsername()) && 
					user.getPassword().equals(u.getPassword())){
				return true;
			}
		}
		return false;
	}

	@Override
	public User load(long id) {
		User user = hibernateTemplate.get(User.class, id);
		hibernateTemplate.flush();
		return user;
	}

	@Override
	public List<User> load() {
		List<User> list = (List<User>) hibernateTemplate.find("from User");
		return list;
	}

	@Override
	public void update(User user) {
		hibernateTemplate.update(user);
	}

	@Override
	public PageValue pageLoad(PageValue pageValue) {
		String hql = "from User";
		hibernateTemplate.setFetchSize((pageValue.getPageIndex()-1)*pageValue.getEverypagenum());
		hibernateTemplate.setMaxResults(pageValue.getEverypagenum());
		List<User> list = (List<User>) hibernateTemplate.find(hql);
		Long count = (Long) hibernateTemplate.find("select count(*) from User").iterator().next();
		pageValue.setAllCount(count.intValue());
		pageValue.countPageCount();
		pageValue.addValue(list);
		return pageValue;
	}

	@Override
	public boolean isContainUsername(String username) {
		List<String> list = (List<String>) hibernateTemplate.find("SELECT username FROM User WHERE username = ?", new String[]{username});
		if(list != null && list.size() != 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isContainPhone(String phone) {
		List<String> list = (List<String>) hibernateTemplate.find("select phone FROM User WHERE phone = ?", new String[]{phone});
		if(list != null && list.size() != 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isContainEmail(String email) {
		List<String> list = (List<String>) hibernateTemplate.find("select email FROM User WHERE email = ?", new String[]{email});
		if(list != null && list.size() != 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public User findbyName(String name) {
		User user = (User) (hibernateTemplate.find("from User where username=?", new String[]{name,}).get(0));
		return user;
	}

	@Override
	public PageValue pageLoadIsadmin(PageValue pageValue) {
		String hql = "from User where admin = '1' or admin = '2' order by id";
		hibernateTemplate.setFetchSize((pageValue.getPageIndex()-1)*pageValue.getEverypagenum());
		hibernateTemplate.setMaxResults(pageValue.getEverypagenum());
		List<User> list = (List<User>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				  Query query = session.createQuery(hql);
	              query.setFirstResult((pageValue.getPageIndex()-1)*pageValue.getEverypagenum());
	              query.setMaxResults(pageValue.getEverypagenum());
	              List list = query.list();
		          return list;
			}
		});
		Long count = (Long) hibernateTemplate.find("select count(*) from User where admin = '1' or admin = '2'").iterator().next();
		pageValue.setAllCount(count.intValue());
		pageValue.countPageCount();
		pageValue.addValue(list);
		return pageValue;
	}
	@Override
	public PageValue pageLoadNotIsAdmin(PageValue pageValue) {
		String hql = "from User where admin = '0' order by id";
		hibernateTemplate.setFetchSize((pageValue.getPageIndex()-1)*pageValue.getEverypagenum());
		hibernateTemplate.setMaxResults(pageValue.getEverypagenum());
		List<User> list = (List<User>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				  Query query = session.createQuery(hql);
	              query.setFirstResult((pageValue.getPageIndex()-1)*pageValue.getEverypagenum());
	              query.setMaxResults(pageValue.getEverypagenum());
	              List list = query.list();
		          return list;
			}
		});
		Long count = (Long) hibernateTemplate.find("select count(*) from User  where admin = '0' ").iterator().next();
		pageValue.setAllCount(count.intValue());
		pageValue.countPageCount();
		pageValue.addValue(list);
		return pageValue;
	}

}
