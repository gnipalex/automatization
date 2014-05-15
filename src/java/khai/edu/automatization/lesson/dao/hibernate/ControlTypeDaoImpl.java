/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.ControlTypeDao;
import khai.edu.automatization.lesson.model.ControlType;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class ControlTypeDaoImpl extends HibernateDaoSupport implements ControlTypeDao{
    @Override
    public ControlType get(Integer id) {
        return (ControlType)this.getHibernateTemplate().get(ControlType.class, id);
    }

    @Override
    public List<ControlType> getAll() {
        return this.getHibernateTemplate().loadAll(ControlType.class);
    }

    @Override
    public void saveOrUpdate(ControlType obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(ControlType obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public ControlType getByName(String name) {
        List<ControlType> list = (List<ControlType>)this.getHibernateTemplate().find("from ControlType r where r.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
}
