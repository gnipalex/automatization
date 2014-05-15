/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import khai.edu.automatization.lesson.dao.TeacherDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Teacher;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class TeacherDaoImpl extends HibernateDaoSupport implements TeacherDao{
    @Override
    public Teacher get(Integer id) {
        return (Teacher)this.getHibernateTemplate().get(Teacher.class, id);
    }

    @Override
    public List<Teacher> getAll() {
        return this.getHibernateTemplate().loadAll(Teacher.class);   
    }

    @Override
    public void saveOrUpdate(Teacher obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(Teacher obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public Teacher get(String fname, String mname, String lname) {
        List<Teacher> list = (List<Teacher>)this.getHibernateTemplate()
                .find("from Teacher t where t.firstName = ? AND t.middleName = ? AND lastName = ?", fname, mname, lname);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<Teacher> getByChair(Chair ch) {
        List<Teacher> list = this.getHibernateTemplate().find("from Teacher t where t.chair.id = ?", ch.getId());
        return list;
    }

    @Override
    public List<Teacher> get(Teacher t, Chair ch) {
        List<String> sub_queries = new ArrayList<String>(10);
        StringBuilder query = new StringBuilder(100);
        query.append("from Teacher t ");
        if (t != null){
            if (t.getFirstName() != null){
                sub_queries.add("t.firstName = '" + t.getFirstName() + "'");
            }
            if (t.getLastName() != null){
                sub_queries.add("t.lastName = '" + t.getLastName() + "'");
            }
            if (t.getMiddleName() != null){
                sub_queries.add("t.middleName = '" + t.getMiddleName() + "'");
            }
            if (t.getPost() != null){
                sub_queries.add("t.post = '" + t.getPost() + "'");
            }
        }
        if (ch != null){
            sub_queries.add("t.chair.name = '" + ch.getName() + "'");
        }
        if (sub_queries.size() > 0) {
            query.append("where ");
            for (int i = 0; i< sub_queries.size(); i++){
               if (i > 0) {
                   query.append(" AND ");
               }
               query.append(sub_queries.get(i)); 
            }   
        }
        List<Teacher> list = this.getHibernateTemplate().find(query.toString());
        return list != null && list.size() > 0 ? list : null;
    }
}
