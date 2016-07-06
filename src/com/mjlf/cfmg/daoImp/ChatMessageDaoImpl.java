package com.mjlf.cfmg.daoImp;

import java.sql.Time;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.mjlf.cfmg.dao.ChatMessageDao;
import com.mjlf.cfmg.entity.ChatMessage;
import com.mjlf.cfmg.entity.Library;

@Component
public class ChatMessageDaoImpl implements ChatMessageDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void add(ChatMessage chatMessage) {
		hibernateTemplate.save(chatMessage);
		hibernateTemplate.flush();
	}

	@Override
	public void upload(ChatMessage chatMessage) {
		hibernateTemplate.update(chatMessage);
	}
	@Override
	public void clear() {
		hibernateTemplate.deleteAll(load());
	}

	@Override
	public List<ChatMessage> loadFrom(long fromUserId) {
		StringBuffer hql = new StringBuffer("from chatMessage order by messagetime");
		
         List<ChatMessage> list = (List<ChatMessage>) hibernateTemplate.execute(new HibernateCallback() {

 			@Override
 			public Object doInHibernate(Session session) throws HibernateException {
 				 DetachedCriteria dc = DetachedCriteria.forClass(ChatMessage.class);
 				 dc.add(Restrictions.eq("fromUserId", fromUserId));
 				 Criteria c = dc.getExecutableCriteria(session);
 				 List<Library> list = c.list();
 				 c.addOrder(Order.asc("messagetime"));
 				 list = c.list();
 				 return list;
 			}
 		});
		return list;
	}

	@Override
	public List<ChatMessage> loadTo(long toUserId) {
		StringBuffer hql = new StringBuffer("from chatMessage order by messagetime");
		
        List<ChatMessage> list = (List<ChatMessage>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				 DetachedCriteria dc = DetachedCriteria.forClass(ChatMessage.class);
				 dc.add(Restrictions.eq("toUserId", toUserId));
				 Criteria c = dc.getExecutableCriteria(session);
				 List<Library> list = c.list();
				 c.addOrder(Order.asc("messagetime"));
				 list = c.list();
				 return list;
			}
		});
		return list;
	}

	@Override
	public List<ChatMessage> load(long fromUserId, long toUserId, int state) {
		StringBuffer hql = new StringBuffer("from chatMessage order by messagetime");
		
        List<ChatMessage> list = (List<ChatMessage>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				 DetachedCriteria dc = DetachedCriteria.forClass(ChatMessage.class);
				 dc.add(Restrictions.or(Restrictions.eq("fromUserId", fromUserId), Restrictions.eq("fromUserId", toUserId)));
				 dc.add(Restrictions.or(Restrictions.eq("toUserId", fromUserId), Restrictions.eq("toUserId", toUserId)));
				 dc.add(Restrictions.eq("state", state));
				 Criteria c = dc.getExecutableCriteria(session);
				 List<ChatMessage> list = c.list();
				 c.addOrder(Order.asc("messagetime"));
				 list = c.list();
				 return list;
			}
		});
		return list;
	}
	
	@Override
	public List<ChatMessage> load(long fromUserId, long toUserId) {
		StringBuffer hql = new StringBuffer("from chatMessage order by messagetime");
		
        List<ChatMessage> list = (List<ChatMessage>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				 DetachedCriteria dc = DetachedCriteria.forClass(ChatMessage.class);
				 dc.add(Restrictions.or(Restrictions.eq("fromUserId", fromUserId), Restrictions.eq("fromUserId", toUserId)));
				 dc.add(Restrictions.or(Restrictions.eq("toUserId", fromUserId), Restrictions.eq("toUserId", toUserId)));
				 Criteria c = dc.getExecutableCriteria(session);
				 List<ChatMessage> list = c.list();
				 c.addOrder(Order.asc("messagetime"));
				 list = c.list();
				 return list;
			}
		});
		return list;
	}
	
	@Override
	public List<ChatMessage> notread(long toUserId, int state) {
		StringBuffer hql = new StringBuffer("from chatMessage");
		
        List<ChatMessage> list = (List<ChatMessage>) hibernateTemplate.execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				 DetachedCriteria dc = DetachedCriteria.forClass(ChatMessage.class);
				 dc.add(Restrictions.eq("toUserId", toUserId));
				 dc.add(Restrictions.eq("state", state));
				 Criteria c = dc.getExecutableCriteria(session);
				 List<ChatMessage> list = c.list();
				 c.addOrder(Order.asc("messagetime"));
				 list = c.list();
				 return list;
			}
		});
		return list;
	}

	@Override
	public List<ChatMessage> loadByTime(Time startTime, Time endTime, long fromUserId, long toUserId) {
		return null;
	}

	@Override
	public void delete(Time startTime, Time endTime, long fromUserId, long toUserId) {

	}

	@Override
	public List<ChatMessage> load() {
		String hql = "from chatMessage";
		List<ChatMessage> list = null;
		list = (List<ChatMessage>) hibernateTemplate.find(hql);
		return list;
	}
}
