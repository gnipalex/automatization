/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service.impl;

import java.util.List;
import khai.edu.automatization.lesson.dao.LessonPlanDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Discipline;
import khai.edu.automatization.lesson.model.Group;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Semester;
import khai.edu.automatization.lesson.model.SolutionPlan;
import khai.edu.automatization.lesson.model.Speciality;
import khai.edu.automatization.lesson.service.LessonPlanService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
@Transactional
public class LessonPlanServiceImpl implements LessonPlanService {

    private LessonPlanDao lessonDao;
    
    @Override
    @Transactional
    public List<LessonPlan> getByFilterAllFields(Chair r_chair, Chair pr_chair, Speciality spec, Group group, Discipline disc, Semester sem) {
        List<LessonPlan> list = lessonDao.getLessonByFilter(r_chair, pr_chair, spec, group, disc, sem);
        if (list == null) return null;
        for (LessonPlan lp : list){
            this.getAllFields(lp);
        }
        return list;
    }

    private LessonPlan getAllFields(LessonPlan lp) {
        if (lp == null) return null;
        
        Hibernate.initialize(lp.getDiscipline());
        Hibernate.initialize(lp.getGroup());
        Hibernate.initialize(lp.getSpeciality());
        Hibernate.initialize(lp.getProducedChair());
        Hibernate.initialize(lp.getReadingChair());
        Hibernate.initialize(lp.getSemester());
        
        Hibernate.initialize(lp.getSolutions());
        if (lp.getSolutions() != null){
            for (SolutionPlan sp : lp.getSolutions()){
                Hibernate.initialize(sp.getRoom());
                if (sp.getRoom() != null){
                    Hibernate.initialize(sp.getRoom().getBuilding());
                }
                Hibernate.initialize(sp.getAppUser());
                Hibernate.initialize(sp.getTeacher());
            }
        }
        return lp;
    }

    public LessonPlanDao getLessonDao() {
        return lessonDao;
    }

    public void setLessonDao(LessonPlanDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    @Override
    public boolean contains(LessonPlan lp) {
        return this.contains(lp);
    }

    @Override
    public LessonPlan get(Integer id) {
        return this.get(id);
    }

    @Override
    public List<LessonPlan> getAll() {
        return this.lessonDao.getAll();
    }

    @Override
    public void saveOrUpdate(LessonPlan obj) {
        this.lessonDao.saveOrUpdate(obj);
    }

    @Override
    public void remove(LessonPlan obj) {
        this.lessonDao.remove(obj);
    }

    @Override
    public LessonPlan getWithAll(int id) {
        LessonPlan lp = this.lessonDao.get(id);
        if (lp != null){
            this.getAllFields(lp);
        }
        return lp;
    }

    @Override
    @Transactional
    public LessonPlan getByIdAndReadingChair(int id, Chair ch) {
        LessonPlan lp = this.lessonDao.getByIdAndReadingChair(id, ch);
        if (lp != null) {
            lp = this.getAllFields(lp);
        }
        return lp;
    }
}
