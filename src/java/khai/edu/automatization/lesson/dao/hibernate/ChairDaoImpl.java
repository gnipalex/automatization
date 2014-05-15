/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.model.Chair;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class ChairDaoImpl extends HibernateDaoSupport implements ChairDao {

    @Override
    public Chair get(Integer id) {
        return (Chair)this.getHibernateTemplate().get(Chair.class, id);
    }

    @Override
    public List<Chair> getAll() {
        return this.getHibernateTemplate().loadAll(Chair.class);
    }

    @Override
    public void saveOrUpdate(Chair obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Chair obj) {
        this.getHibernateTemplate().delete(obj);
    }
    
    @Override
    public Chair getByName(String name){
        List<Chair> list = (List<Chair>)this.getHibernateTemplate()
                .find("from Chair ch where ch.name = ?", name);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<Chair> getAllByReadingChair(Chair rd_chair) {
        if (rd_chair == null) return null;
        List<Chair> list = (List<Chair>)this.getHibernateTemplate()
                .find("select distinct ch from Chair ch join ch.producedLessonPlans lp where lp.readingChair.id = ?", rd_chair.getId());
        return list.size() > 0 ? list : null;
    }
  
    
}
