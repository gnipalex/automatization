/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.GroupDao;
import khai.edu.automatization.lesson.model.Group;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class GroupDaoImpl extends HibernateDaoSupport implements GroupDao{
    @Override
    public Group get(Integer id) {
        return (Group)this.getHibernateTemplate().get(Group.class, id);
    }

    @Override
    public List<Group> getAll() {
        return this.getHibernateTemplate().loadAll(Group.class);   
    }

    @Override
    public void saveOrUpdate(Group obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Group obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public Group getByName(String name) {
        List<Group> list = (List<Group>)this.getHibernateTemplate()
                .find("from Group g where g.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null;   
    }
}
