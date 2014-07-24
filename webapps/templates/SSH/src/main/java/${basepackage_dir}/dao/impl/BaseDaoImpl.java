package ${basepackage}.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ${basepackage}.dao.BaseDaoI;

@SuppressWarnings("unchecked")
@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public Serializable save(T t)  throws Exception{
		return this.getSession().save(t);
	}

	@Override
	public void delete(T t)  throws Exception{
		this.getSession().delete(t);

	}

	@Override
	public void update(T t)  throws Exception{
		this.getSession().update(t);
	}

	@Override
	public void saveOrUpdate(T t)  throws Exception{
		this.getSession().saveOrUpdate(t);

	}

	@Override
	public T get(Class<T> c, Serializable id) throws Exception {
		return (T) this.getSession().get(c, id);
	}

	@Override
	public T get(String hql)  throws Exception{
		Query q = this.getSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public T get(String hql, Map<String, Object> params) throws Exception {
		Query q = this.getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public List<T> find(String hql) throws Exception {
		Query q = this.getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) throws Exception {
		Query q = this.getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, int page, int rows) throws Exception {
		Query q = this.getSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, int page, int rows, Map<String, Object> params) throws Exception {
		Query q = this.getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Long count(String hql) throws Exception {
		Query q = this.getSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, Map<String, Object> params) throws Exception {
		Query q = this.getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int excuteHql(String hql) throws Exception {
		return this.getSession().createQuery(hql).executeUpdate();
	}

	@Override
	public int excuteHql(String hql, Map<String, Object> params) throws Exception {
		Query q = this.getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public T get(StringBuffer hql) throws Exception {
		return this.get(hql.toString());
	}

	@Override
	public T get(StringBuffer hql, Map<String, Object> params) throws Exception {
		return this.get(hql.toString(), params);
	}

	@Override
	public List<T> find(StringBuffer hql) throws Exception {
		return this.find(hql.toString());
	}

	@Override
	public List<T> find(StringBuffer hql, Map<String, Object> params) throws Exception {
		return this.find(hql.toString(), params);
	}

	@Override
	public List<T> find(StringBuffer hql, int page, int rows) throws Exception {
		return this.find(hql.toString(), page, rows);
	}

	@Override
	public List<T> find(StringBuffer hql, int page, int rows, Map<String, Object> params) throws Exception {
		return this.find(hql.toString(), page, rows, params);
	}

	@Override
	public Long count(StringBuffer hql) throws Exception {
		return this.count(hql.toString());
	}

	@Override
	public Long count(StringBuffer hql, Map<String, Object> params) throws Exception {
		return this.count(hql.toString(), params);
	}

	@Override
	public int excuteHql(StringBuffer hql) throws Exception {
		return this.excuteHql(hql.toString());
	}

	@Override
	public int excuteHql(StringBuffer hql, Map<String, Object> params) throws Exception {
		return this.excuteHql(hql.toString(), params);
	}

}
