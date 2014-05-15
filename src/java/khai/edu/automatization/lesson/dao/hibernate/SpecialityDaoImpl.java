/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.SpecialityDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Speciality;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class SpecialityDaoImpl extends HibernateDaoSupport implements SpecialityDao {
    @Override
    public Speciality get(Integer id) {
        return (Speciality)this.getHibernateTemplate().get(Speciality.class, id);
    }

    @Override
    public List<Speciality> getAll() {
        return this.getHibernateTemplate().loadAll(Speciality.class);   
    }

    @Override
    public void saveOrUpdate(Speciality obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Speciality obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public Speciality getByName(String name) {
        List<Speciality> list = (List<Speciality>)this.getHibernateTemplate()
                .find("from Speciality s where s.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null;     
    }

    @Override
    public List<Speciality> getAllByReadingChair(Chair rd_chair) {
        if (rd_chair == null) return null;
        List<Speciality> list = (List<Speciality>)this.getHibernateTemplate()
                .find("select distinct lp.speciality from LessonPlan lp where lp.readingChair.id = ?", rd_chair.getId());
        return list.size() > 0 ? list : null;
    }
    
    
}
