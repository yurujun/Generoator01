package ${basepackage}.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDaoI<T> {
	public Serializable save(T t) throws Exception;

	public void delete(T t) throws Exception;

	public void update(T t) throws Exception;

	public void saveOrUpdate(T t) throws Exception;

	public T get(Class<T> c, Serializable id) throws Exception;

	public T get(String hql) throws Exception;

	public T get(StringBuffer hql) throws Exception;

	public T get(String hql, Map<String, Object> params) throws Exception;

	public T get(StringBuffer hql, Map<String, Object> params) throws Exception;

	public List<T> find(String hql) throws Exception;

	public List<T> find(StringBuffer hql) throws Exception;

	public List<T> find(String hql, Map<String, Object> params) throws Exception;

	public List<T> find(StringBuffer hql, Map<String, Object> params) throws Exception;

	public List<T> find(String hql, int page, int rows) throws Exception;

	public List<T> find(StringBuffer hql, int page, int rows) throws Exception;

	public List<T> find(String hql, int page, int rows, Map<String, Object> params) throws Exception;

	public List<T> find(StringBuffer hql, int page, int rows, Map<String, Object> params) throws Exception;

	public Long count(String hql) throws Exception;

	public Long count(StringBuffer hql) throws Exception;

	public Long count(String hql, Map<String, Object> params) throws Exception;

	public Long count(StringBuffer hql, Map<String, Object> params) throws Exception;

	public int excuteHql(String hql) throws Exception;

	public int excuteHql(StringBuffer hql) throws Exception;

	public int excuteHql(String hql, Map<String, Object> params) throws Exception;

	public int excuteHql(StringBuffer hql, Map<String, Object> params) throws Exception;
}
