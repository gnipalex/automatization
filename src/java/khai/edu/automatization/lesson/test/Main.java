/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.dao.GroupDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.dao.LessonPlanDao;
import khai.edu.automatization.lesson.dao.RoleDao;
import khai.edu.automatization.lesson.dao.SpecialityDao;
import khai.edu.automatization.lesson.dao.TeacherDao;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Group;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Role;
import khai.edu.automatization.lesson.model.Speciality;
import khai.edu.automatization.lesson.model.Teacher;
import khai.edu.automatization.lesson.service.AppUserService;
import khai.edu.automatization.lesson.service.ChairService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Alex
 */
public class Main {
    public static void main(String[] args){
        ApplicationContext appContext = null;
        try{
            appContext = new ClassPathXmlApplicationContext("khai/edu/automatization/lesson/test/appContext.xml");
        } catch(BeansException bex){
            System.out.println("Ошибка при создании контекста Spring\n" + bex.getMessage());
            return;
        }
        //TeacherDao td = (TeacherDao)appContext.getBean("teacherDao");
        LazyInitializer lazyInit = (LazyInitializer)appContext.getBean("lazyInitializer");
        AppUserDao appUserDao = (AppUserDao)appContext.getBean("appUserDao");
        RoleDao roleDao = (RoleDao)appContext.getBean("roleDao");
        LessonPlanDao lessonDao = (LessonPlanDao)appContext.getBean("lessonPlanDao");
        GroupDao groupDao = (GroupDao)appContext.getBean("groupDao");
        ChairDao chairDao = (ChairDao)appContext.getBean("chairDao");
        SpecialityDao specDao = (SpecialityDao)appContext.getBean("specialityDao");
        
        AppUserService userService = (AppUserService)appContext.getBean("appUserService");
        ChairService chairService = (ChairService)appContext.getBean("chairService");
        
        Chair ch = chairDao.getByName("702");
        if (ch != null){
//            List<Chair> list = chairDao.getAllByReadingChair(ch);
//            System.out.println(list != null ? list.size() : "ой, пустоЙ");
            List<Speciality> list = specDao.getAllByReadingChair(ch);
            System.out.println(list != null ? list.size() : "ой, пустоЙ");
        }
        
    }
}
