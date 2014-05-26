/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service.impl;

import java.util.ArrayList;
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
import khai.edu.automatization.lesson.web.ajax.AjaxSolution;
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
        if (sp != null) {
            Hibernate.initialize(sp.getAppUser());
            Hibernate.initialize(sp.getRoom());
            if (sp.getRoom() != null) {
                Hibernate.initialize(sp.getRoom().getBuilding());
            }
            Hibernate.initialize(sp.getTeacher());
            Hibernate.initialize(sp.getLessonPlan());
            if (sp.getLessonPlan() != null) {
                Hibernate.initialize(sp.getLessonPlan().getDiscipline());
                Hibernate.initialize(sp.getLessonPlan().getGroup());
                Hibernate.initialize(sp.getLessonPlan().getSpeciality());
                Hibernate.initialize(sp.getLessonPlan().getProducedChair());
                Hibernate.initialize(sp.getLessonPlan().getReadingChair());
                Hibernate.initialize(sp.getLessonPlan().getSemester());
            }
        }
        return sp;
    }

    @Override
    @Transactional
    public boolean createSolution(AjaxSolution ajax_sol) {
        if (ajax_sol.getLesson_id() == null) {
            return false;
        }
        LessonPlan lesson = lessonDao.get(ajax_sol.getLesson_id().intValue());
        if (lesson == null) {
            return false;
        }
        AppUser user = null;
        if (ajax_sol.getUser_id() != null) {
            user = userDao.get(ajax_sol.getUser_id().intValue());
        }
        Teacher teacher = null;
        if (ajax_sol.getTeacher_id() != null) {
            teacher = teacherDao.get(ajax_sol.getTeacher_id().intValue());
        }
        Room room = null;
        if (ajax_sol.getRoom_id() != null) {
            room = roomDao.get(ajax_sol.getRoom_id().intValue());
        }
        List<SolutionPlan> sol_plans = new ArrayList<>();
        if (ajax_sol.isLab()) {
            SolutionPlan sp = new SolutionPlan();
            sp.setAppUser(user);
            sp.setLessonPlan(lesson);
            sp.setRoom(room);
            sp.setTeacher(teacher);
            sp.setLab(true);
            sol_plans.add(sp);
        }
        if (ajax_sol.isLect()) {
            SolutionPlan sp = new SolutionPlan();
            sp.setAppUser(user);
            sp.setLessonPlan(lesson);
            sp.setRoom(room);
            sp.setTeacher(teacher);
            sp.setLection(true);
            sol_plans.add(sp);
        }
        if (ajax_sol.isPract()) {
            SolutionPlan sp = new SolutionPlan();
            sp.setAppUser(user);
            sp.setLessonPlan(lesson);
            sp.setRoom(room);
            sp.setTeacher(teacher);
            sp.setPractice(true);
            sol_plans.add(sp);
        }

        try {
            for (SolutionPlan sp : sol_plans) {
                solutionDao.saveOrUpdate(sp);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean editSolution(AjaxSolution ajax_sol) {
        if (ajax_sol.getSolution_id() == null) {
            return false;
        }
        SolutionPlan sp = solutionDao.get(ajax_sol.getSolution_id().intValue());
        if (sp == null) {
            return false;
        }
        AppUser user = null;
        if (ajax_sol.getUser_id() != null) {
            user = userDao.get(ajax_sol.getUser_id().intValue());
        }
        Teacher teacher = null;
        if (ajax_sol.getTeacher_id() != null) {
            teacher = teacherDao.get(ajax_sol.getTeacher_id().intValue());
        }
        Room room = null;
        if (ajax_sol.getRoom_id() != null) {
            room = roomDao.get(ajax_sol.getRoom_id().intValue());
        }

        sp.setAppUser(user);
        sp.setRoom(room);
        sp.setTeacher(teacher);

        try {
            solutionDao.saveOrUpdate(sp);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean createManySolutions(List<Integer> lab_ids, List<Integer> pract_ids, List<Integer> lect_ids, Integer t_id, Integer r_id) {
        if (t_id == null){
            return false;
        }
        
        Teacher teacher = teacherDao.get(t_id);
        Room room = r_id != null ? roomDao.get(r_id): null;
        
        if (lab_ids != null) {
            for (int id : lab_ids) {
                LessonPlan lp = lessonDao.get(id);
                if (lp != null){
                    SolutionPlan sp = new SolutionPlan();
                    sp.setLab(true);
                    sp.setLessonPlan(lp);
                    sp.setTeacher(teacher);
                    sp.setRoom(room);
                    solutionDao.saveOrUpdate(sp);
                }
            }
        }
        if (pract_ids != null) {
            for (int id : pract_ids) {
                LessonPlan lp = lessonDao.get(id);
                if (lp != null){
                    SolutionPlan sp = new SolutionPlan();
                    sp.setPractice(true);
                    sp.setLessonPlan(lp);
                    sp.setTeacher(teacher);
                    sp.setRoom(room);
                    solutionDao.saveOrUpdate(sp);
                }
            }
        }
        if (lect_ids != null) {
            for (int id : lect_ids) {
                LessonPlan lp = lessonDao.get(id);
                if (lp != null){
                    SolutionPlan sp = new SolutionPlan();
                    sp.setLection(true);
                    sp.setLessonPlan(lp);
                    sp.setTeacher(teacher);
                    sp.setRoom(room);
                    solutionDao.saveOrUpdate(sp);
                }
            }
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
