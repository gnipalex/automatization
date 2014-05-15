/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.SolutionPlanDao;
import khai.edu.automatization.lesson.model.SolutionPlan;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class SolutionPlanDaoImpl extends HibernateDaoSupport implements SolutionPlanDao{
    @Override
    public SolutionPlan get(Integer id) {
        return (SolutionPlan)this.getHibernateTemplate().get(SolutionPlan.class, id);
    }

    @Override
    public List<SolutionPlan> getAll() {
        return this.getHibernateTemplate().loadAll(SolutionPlan.class);   
    }

    @Override
    public void saveOrUpdate(SolutionPlan obj) {
        //this.getHibernateTemplate().merge(obj);
//        this.getHibernateTemplate().flush();
//        this.getHibernateTemplate().clear();
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(SolutionPlan obj) {
        this.getHibernateTemplate().delete(obj);
    }
}
