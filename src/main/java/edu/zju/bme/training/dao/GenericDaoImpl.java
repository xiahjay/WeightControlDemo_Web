package edu.zju.bme.training.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * @author Liu
 * 
 * @param <T>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class GenericDaoImpl<T, ID extends Serializable> implements
		GenericDao<T, ID> {
	private Class<T> clazz;

	/**
	 * 閫氳繃鏋勯?犳柟娉曟寚瀹欴AO鐨勫叿浣撳疄鐜扮被
	 */
	public GenericDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
		// System.out.println("DAO鐨勭湡瀹炲疄鐜扮被鏄細" + this.clazz.getName());
	}

	/**
	 * 鍚慏AO灞傛敞鍏essionFactory
	 */
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * 鑾峰彇褰撳墠宸ヤ綔鐨凷ession
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T entity) {
		this.getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		this.getSession().update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(ID id) {
		this.getSession().delete(this.findById(id));
	}

	@Override
	public void delete(T entity) {
		this.getSession().delete(entity);
	}

	@Override
	public T findById(ID id) {
		return (T) this.getSession().get(this.clazz, id);
	}

	@Override
	public List findByHQL(String hql, Object... params) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++) {
			query.setParameter(i, params);
		}
		return query.list();
	}

	@Override
	public List findByHQL(String hql, Map<String, ?> map) {
		Query query = this.getSession().createQuery(hql);
		query.setProperties(map);
		return query.list();
	}

	@Override
	public List<T> selectAll() {
		String hql = "from " + clazz.getName();
		return this.findByHQL(hql);
	}

	@Override
	public T findUniqueByProperty(String propertyName, Object value) {
		Criteria criteria = this.getSession().createCriteria(clazz)
				.add(Restrictions.eq(propertyName, value)); // 澧炲姞灞炴?х浉绛夌害鏉?
		return (T) criteria.uniqueResult();
	}
}
