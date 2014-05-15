/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service.impl;

import java.util.List;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.dao.LessonPlanDao;
import khai.edu.automatization.lesson.dao.RoomDao;
import khai.edu.automatization.lesson.dao.SolutionPlanDao;
import khai.edu.automatization.lesson.dao.TeacherDao;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Room;
import khai.edu.automatization.lesson.model.SolutionPlan;
import khai.edu.automatization.lesson.model.Teacher;
import khai.edu.automatization.lesson.service.SolutionPlanService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
public class SolutionPlanServiceImpl implements SolutionPlanService {
    
    private SolutionPlanDao solutionDao; 
    private LessonPlanDao lessonDao;
    private AppUserDao userDao;
    private TeacherDao teacherDao;
    private RoomDao roomDao;
    
    @Override
    public SolutionPlan get(Integer id) {
        return this.solutionDao.get(id);
    }

    @Override
    public List<SolutionPlan> getAll() {
        return this.solutionDao.getAll();
    }

    @Override
    @Transactional
    public void saveOrUpdate(SolutionPlan obj) {
        this.solutionDao.saveOrUpdate(obj);
    }

    @Override
    @Transactional
    public void remove(SolutionPlan obj) {
        this.solutionDao.remove(obj);
    }

    public SolutionPlanDao getSolutionDao() {
        return solutionDao;
    }

    public void setSolutionDao(SolutionPlanDao solutionDao) {
        this.solutionDao = solutionDao;
    }

    @Override
    @Transactional(readOnly = true)
    public SolutionPlan getWithAll(Integer id) {
        SolutionPlan sp = this.solutionDao.get(id);
        if (sp != null){
            Hibernate.initialize(sp.getAppUser());
            Hibernate.initialize(sp.getRoom());
            Hibernate.initialize(sp.getTeacher());
        }
        return sp;
    }
    @Override
    @Transactional
    public boolean createSolution(Integer lp_id, Integer user_id, Integer teacher_id, Integer room_id){
        
        LessonPlan lesson = lessonDao.get(lp_id);
        if (lesson == null) {
            return false;
        }
        AppUser user = null;
        if (user_id != null){
            user = userDao.get(user_id);
        }
        Teacher teacher = null;
        if (teacher_id != null){
            teacher = teacherDao.get(teacher_id);
        }
        Room room = null;
        if (room_id != null){
            room = roomDao.get(room_id);
        }
        
        SolutionPlan sp = new SolutionPlan();
        sp.setAppUser(user);
        sp.setLessonPlan(lesson);
        sp.setRoom(room);
        sp.setTeacher(teacher);
        
        try {
            solutionDao.saveOrUpdate(sp);
        } catch(Exception ex){
            return false;
        }
        return true;
    }
    
    @Override
    @Transactional
    public boolean editSolution(Integer solution_id, Integer user_id, Integer teacher_id, Integer room_id){
        if (solution_id == null){
            return false;
        }
        SolutionPlan sp = solutionDao.get(solution_id);
        if (sp == null){
            return false;
        }
        AppUser user = null;
        if (user_id != null){
            user = userDao.get(user_id);
        }
        Teacher teacher = null;
        if (teacher_id != null){
            teacher = teacherDao.get(teacher_id);
        }
        Room room = null;
        if (room_id != null){
            room = roomDao.get(room_id);
        }
        
        sp.setAppUser(user);
        sp.setRoom(room);
        sp.setTeacher(teacher);
        
        try {
            solutionDao.saveOrUpdate(sp);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public LessonPlanDao getLessonDao() {
        return lessonDao;
    }

    public void setLessonDao(LessonPlanDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    public AppUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(AppUserDao userDao) {
        this.userDao = userDao;
    }

    public TeacherDao getTeacherDao() {
        return teacherDao;
    }

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }

    public void setRoomDao(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

}
