/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.RoleDao;
import khai.edu.automatization.lesson.model.Role;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class RoleDaoImpl extends HibernateDaoSupport implements RoleDao{
    @Override
    public Role get(Integer id) {
        return (Role)this.getHibernateTemplate().get(Role.class, id);
    }

    @Override
    public List<Role> getAll() {
        return this.getHibernateTemplate().loadAll(Role.class);
    }

    @Override
    public void saveOrUpdate(Role obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Role obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public Role getByName(String name) {
        List<Role> list = (List<Role>)this.getHibernateTemplate().find("from Role r where r.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
}
