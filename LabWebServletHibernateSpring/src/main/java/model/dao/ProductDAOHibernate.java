package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.ProductBean;
import model.ProductDAO;

@Repository
public class ProductDAOHibernate implements ProductDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public ProductBean select(int id) {
		return this.getSession().get(ProductBean.class, id);
	}
	
	@Override
	public List<ProductBean> select() {
		return this.getSession().createQuery(
				"from ProductBean", ProductBean.class).list();
	}

	@Override
	public ProductBean insert(ProductBean bean) {
		if(bean!=null) {
			ProductBean temp =
					this.getSession().get(ProductBean.class, bean.getId());
			if(temp==null) {
				this.getSession().save(bean);
				return bean;
			}
		}
		return null;
	}

	@Override
	public ProductBean update(String name,
			double price, java.util.Date make, int expire, int id) {
		ProductBean result = this.getSession().get(ProductBean.class, id);
		if(result!=null) {
			result.setName(name);
			result.setPrice(price);
			result.setMake(make);
			result.setExpire(expire);
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		ProductBean result = this.getSession().get(ProductBean.class, id);
		if(result!=null) {
			this.getSession().delete(result);
			return true;
		}
		return false;
	}
}
