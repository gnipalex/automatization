/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.DisciplineDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Discipline;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class DisciplineDaoImpl extends HibernateDaoSupport implements DisciplineDao{
    @Override
    public Discipline get(Integer id) {
        return (Discipline)this.getHibernateTemplate().get(Discipline.class, id);
    }

    @Override
    public List<Discipline> getAll() {
        return this.getHibernateTemplate().loadAll(Discipline.class);   
    }

    @Override
    public void saveOrUpdate(Discipline obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Discipline obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public List<Discipline> getByName(String name, Chair rd_chair) {
        StringBuilder query = new StringBuilder(100);
        query.append("from Discipline d ");
        query.append(" where d.name = '");
        query.append(name.replace("'", "''"));
        query.append("' ");
        if (rd_chair != null){
            query.append(" AND d.readingChair.name = '");
            query.append(rd_chair.getName().replace("'", "''"));
            query.append("'");
            
        }
        List<Discipline> list = (List<Discipline>)this.getHibernateTemplate()
                .find(query.toString());
        return list != null && list.size() > 0 ? list : null;    
    }
    
    @Override
    public List<Discipline> getByChair(Chair ch){
        if (ch == null){
            return null;
        }
        List<Discipline> list = this.getHibernateTemplate().find("from Discipline d where d.readingChair.id = ?", ch.getId());
        return list != null && list.size() > 0 ? list : null;
    }  
}
