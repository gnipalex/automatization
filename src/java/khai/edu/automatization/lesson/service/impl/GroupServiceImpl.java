/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service.impl;

import java.util.ArrayList;
import java.util.List;
import khai.edu.automatization.lesson.dao.GroupDao;
import khai.edu.automatization.lesson.dao.LessonPlanDao;
import khai.edu.automatization.lesson.model.Group;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Speciality;
import khai.edu.automatization.lesson.service.GroupService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
public class GroupServiceImpl implements GroupService{
    private GroupDao groupDao;
    private LessonPlanDao lessonDao;
    
    @Transactional
    public boolean createGroup(List<Integer> lessons, String name) {
        //проблема в том что группа относится к 2м семестрам, да и вообще нежна ли эта функция?
        //можно просто загрузить из файла XLS
        if (lessons == null || lessons.isEmpty()){
            return false;
        }
        Group g = groupDao.getByName(name);
        if (g != null){
            return false;
        }
        g = new Group();
        g.setName(name);
        for (Integer id : lessons){
            LessonPlan lp = lessonDao.get(id);
            if (lp != null){
                LessonPlan group_lp = new LessonPlan();
                group_lp.setCp(lp.getCp());
                group_lp.setCw(lp.getCw());
                group_lp.setDiv_zachet(lp.getDiv_zachet());
                group_lp.setExam(lp.getExam());
                group_lp.setHours1Half(lp.getHours1Half());
                group_lp.setHours2Half(lp.getHours2Half());
                group_lp.setHoursAll(lp.getHoursAll());
                group_lp.setHoursLab1Half(lp.getHoursLab1Half());
                group_lp.setHoursLab2Half(lp.getHoursLab2Half());
                group_lp.setHoursLabAll(lp.getHoursLabAll());
                group_lp.setHoursLect1Half(lp.getHoursLect1Half());
                group_lp.setHoursLect2Half(lp.getHoursLect2Half());
                group_lp.setHoursLectAll(lp.getHoursLectAll());
                group_lp.setHoursOwn(lp.getHoursOwn());
                group_lp.setHoursPract1Half(lp.getHoursPract1Half());
                group_lp.setHoursPract2Half(lp.getHoursPract2Half());
                group_lp.setHoursPractAll(lp.getHoursPractAll());
                group_lp.setRgr(lp.getRgr());
                group_lp.setRk(lp.getRk());
                group_lp.setRr(lp.getRr());
                group_lp.setZachet(lp.getZachet());
                Hibernate.initialize(lp.getDiscipline());
                group_lp.setDiscipline(lp.getDiscipline());
                Hibernate.initialize(lp.getProducedChair());
                group_lp.setProducedChair(lp.getProducedChair());
                Hibernate.initialize(lp.getReadingChair());
                group_lp.setReadingChair(lp.getReadingChair());
                Hibernate.initialize(lp.getSemester());
                group_lp.setSemester(lp.getSemester());
                Hibernate.initialize(lp.getSpeciality());
                group_lp.setSpeciality(lp.getSpeciality());
                
                g.getLessonPlans().add(group_lp);
                g.setSpeciality(lp.getSpeciality());
            }
        }
        
        groupDao.saveOrUpdate(g);
        return true;
    }
    
}
