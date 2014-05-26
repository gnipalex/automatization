/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service.impl;

import java.util.List;
import khai.edu.automatization.lesson.dao.TeacherDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.SolutionPlan;
import khai.edu.automatization.lesson.model.Teacher;
import khai.edu.automatization.lesson.service.TeacherService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private TeacherDao teacherDao;

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getAllWithChair() {
        List<Teacher> list = teacherDao.getAll();
        if (list == null) {
            return null;
        }
        for (Teacher t : list) {
            Hibernate.initialize(t.getChair());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher getWithSolutionAndLessonPlans(int id) {
        Teacher t = this.teacherDao.get(id);
        if (t == null) {
            return null;
        }
        Hibernate.initialize(t.getSolutionPlans());
        if (t.getSolutionPlans() == null) {
            return t;
        }
        for (SolutionPlan sp : t.getSolutionPlans()) {
            Hibernate.initialize(sp.getAppUser());
            Hibernate.initialize(sp.getRoom());
            if (sp.getRoom() != null) {
                Hibernate.initialize(sp.getRoom().getBuilding());
            }
            Hibernate.initialize(sp.getLessonPlan());
            if (sp.getLessonPlan() != null) {
                Hibernate.initialize(sp.getLessonPlan().getDiscipline());
                Hibernate.initialize(sp.getLessonPlan().getGroup());
                Hibernate.initialize(sp.getLessonPlan().getProducedChair());
                Hibernate.initialize(sp.getLessonPlan().getReadingChair());
                Hibernate.initialize(sp.getLessonPlan().getSemester());
                Hibernate.initialize(sp.getLessonPlan().getSpeciality());
            }
            Hibernate.initialize(sp.getAppUser());
            Hibernate.initialize(sp.getRoom());
        }
        return t;
    }

    public TeacherDao getTeacherDao() {
        return teacherDao;
    }

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Override
    @Transactional
    public Teacher getWithChair(int id) {
        Teacher t = this.teacherDao.get(id);
        if (t != null) {
            Hibernate.initialize(t.getChair());
        }
        return t;
    }

    @Override
    public Teacher get(String fname, String mname, String lname) {
        return this.teacherDao.get(fname, mname, lname);
    }

    @Override
    public Teacher get(Integer id) {
        return this.teacherDao.get(id);
    }

    @Override
    public List<Teacher> getAll() {
        return this.teacherDao.getAll();
    }

    @Override
    @Transactional
    public void saveOrUpdate(Teacher obj) {
        this.teacherDao.saveOrUpdate(obj);
    }

    @Override
    public void remove(Teacher obj) {
        this.teacherDao.remove(obj);
    }

    @Override
    @Transactional
    public List<Teacher> getByChair(Chair ch) {
        List<Teacher> list = this.teacherDao.getByChair(ch);
        if (list != null) {
            for (Teacher t : list) {
                Hibernate.initialize(t.getChair());
                Hibernate.initialize(t.getSolutionPlans());
            }
        }
        return list;
    }

    @Override
    @Transactional
    public List<Teacher> get(Teacher t, Chair ch) {
        List<Teacher> list = this.teacherDao.get(t, ch);
        if (list != null) {
            for (Teacher x : list) {
                Hibernate.initialize(x.getChair());
            }
        }
        return list;
    }
}
