/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.SemesterDao;
import khai.edu.automatization.lesson.model.Semester;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class SemesterDaoImpl extends HibernateDaoSupport implements SemesterDao {
    @Override
    public Semester get(Integer id) {
        return (Semester)this.getHibernateTemplate().get(Semester.class, id);
    }

    @Override
    public List<Semester> getAll() {
        return this.getHibernateTemplate().loadAll(Semester.class);   
    }

    @Override
    public void saveOrUpdate(Semester obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Semester obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public Semester getByName(String name) {
        List<Semester> list = (List<Semester>)this.getHibernateTemplate()
                .find("from Semester s where s.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null; 
    }
    
}
