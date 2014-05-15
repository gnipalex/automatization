/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao.hibernate;

import java.util.List;
import khai.edu.automatization.lesson.dao.LessonPlanDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.ControlType;
import khai.edu.automatization.lesson.model.DiscType;
import khai.edu.automatization.lesson.model.Discipline;
import khai.edu.automatization.lesson.model.Group;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Semester;
import khai.edu.automatization.lesson.model.Speciality;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Alex
 */
public class LessonPlanDaoImpl extends HibernateDaoSupport implements LessonPlanDao{
    @Override
    public LessonPlan get(Integer id) {
        return (LessonPlan)this.getHibernateTemplate().get(LessonPlan.class, id);
    }

    @Override
    public List<LessonPlan> getAll() {
        return this.getHibernateTemplate().loadAll(LessonPlan.class);   
    }

    @Override
    public void saveOrUpdate(LessonPlan obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    @Override
    public void remove(LessonPlan obj) {
        this.getHibernateTemplate().delete(obj);
    }

    @Override
    public List<LessonPlan> getByGroup(Group group, DiscType discType) {
        List<LessonPlan> list = null;
        if (discType != null){
            list = (List<LessonPlan>)this.getHibernateTemplate().find("from LessonPlan lp where lp.group_id = ? and lp.disctype_id = ?", group.getId(), discType.getId());
        } else {
            list = (List<LessonPlan>)this.getHibernateTemplate().find("from LessonPlan lp where lp.group_id = ?", group.getId());
        }
        return list;
    }

    @Override
    public boolean contains(LessonPlan lp) {
        return this.getHibernateTemplate().contains(lp);
    }
    
    @Override
    public List<LessonPlan> getLessonByFilter(Chair r_chair, Chair pr_chair, Speciality spec, Group group, Discipline disc, DiscType dtype, Semester sem, ControlType ctype){
        String q_from = "select lp from LessonPlan lp ";
        StringBuilder q_join = new StringBuilder(100);
        StringBuilder q_where = new StringBuilder(200);
        q_where.append("where ");
        boolean first_arg = true;
        if (group != null){
            q_join.append("inner join lp.groups gr ");
            if (!first_arg){
                q_where.append(" AND ");  
            }
            first_arg = false;
            q_where.append(" gr.id = ");
            q_where.append(group.getId());
        }

        if (r_chair != null){
            if (!first_arg) {
                q_where.append(" AND ");
            }
            first_arg = false;
            q_where.append(" lp.readingChair.id = ");
            q_where.append(r_chair.getId());
        }
        if (pr_chair != null){
            if (!first_arg) {
                q_where.append(" AND ");
            }
            first_arg = false;
            q_where.append(" lp.producedChair.id = ");
            q_where.append(pr_chair.getId());
        }
        if (spec != null){
            if (!first_arg) {
                q_where.append(" AND ");  
            }
            first_arg = false;
            q_where.append(" lp.speciality.id = ");
            q_where.append(spec.getId());
        }
        
        if (disc != null){
            if (!first_arg) {
                q_where.append(" AND ");
            }
            first_arg = false;
            q_where.append(" lp.discipline.id = ");
            q_where.append(disc.getId());
        }
        if (dtype != null){
            if (!first_arg) {
                q_where.append(" AND ");
            }
            first_arg = false;
            q_where.append(" lp.discType.id = ");
            q_where.append(dtype.getId());
        }
        if (sem != null){
            if (!first_arg) {
                q_where.append(" AND ");
            }
            first_arg = false;
            q_where.append(" lp.semester.id = ");
            q_where.append(sem.getId());
        }
        if (ctype != null){
            if (!first_arg) {
                q_where.append(" AND ");
            }
            first_arg = false;
            q_where.append(" lp.controlType.id = ");
            q_where.append(ctype.getId());
        }
        String final_query = q_from;
        if (!first_arg){
            final_query += q_join.toString() + q_where.toString();
        }
        List<LessonPlan> list = (List<LessonPlan>)this.getHibernateTemplate().find(final_query);
        return list.size() > 0 ? list : null;
    }

    @Override
    public LessonPlan getByIdAndReadingChair(int id, Chair ch) {
       List<LessonPlan> list = (List<LessonPlan>)this.getHibernateTemplate().find("from LessonPlan lp where lp.id = ? AND lp.readingChair.id = ?", id, ch.getId());
       return list != null && list.size() > 0 ? list.get(0) : null;
    }

}
