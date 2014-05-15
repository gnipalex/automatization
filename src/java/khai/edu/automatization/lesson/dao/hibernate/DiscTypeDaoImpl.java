/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.DiscTypeDao;
import khai.edu.automatization.lesson.model.DiscType;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class DiscTypeDaoImpl extends HibernateDaoSupport implements DiscTypeDao{

    @Override
    public DiscType get(Integer id) {
        return (DiscType)this.getHibernateTemplate().get(DiscType.class, id);

    }

    @Override
    public List<DiscType> getAll() {
        return this.getHibernateTemplate().loadAll(DiscType.class);   
    }

    @Override
    public void saveOrUpdate(DiscType obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(DiscType obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public DiscType getByName(String name) {
        List<DiscType> list = (List<DiscType>)this.getHibernateTemplate()
                .find("from DiscType dt where dt.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
    
}
