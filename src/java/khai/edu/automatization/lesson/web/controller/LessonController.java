/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.controller;

import java.util.List;
import khai.edu.automatization.lesson.dao.DisciplineDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.dao.SemesterDao;
import khai.edu.automatization.lesson.dao.SpecialityDao;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.Building;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Discipline;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Semester;
import khai.edu.automatization.lesson.model.Speciality;
import khai.edu.automatization.lesson.model.Teacher;
import khai.edu.automatization.lesson.service.AppUserService;
import khai.edu.automatization.lesson.service.BuildingService;
import khai.edu.automatization.lesson.service.ChairService;
import khai.edu.automatization.lesson.service.LessonPlanService;
import khai.edu.automatization.lesson.service.RoomService;
import khai.edu.automatization.lesson.service.SolutionPlanService;
import khai.edu.automatization.lesson.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("")
public class LessonController { 
    @Autowired
    private LessonPlanService lessonService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SpecialityDao specialityDao;
    @Autowired
    private SemesterDao semesterDao;
    @Autowired
    private DisciplineDao discDao;
    
    @Autowired
    private ChairService chairService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private SolutionPlanService solutionService;
    @Autowired
    private LazyInitializer lazyInit;
    
    @RequestMapping("/lesson")
    public String index(Model model){
        //получим пользователя вместе с кафедрой
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        //String ss = app_user.getReadingChair().getId() + " " + app_user.getReadingChair().getName();
        //получим список учебных планов для разрешения
        Chair ch = app_user.getReadingChair();
        List<LessonPlan> lessons = lessonService.getByFilterAllFields(ch, null, null, null, null, null);
        List<Speciality> specialities = specialityDao.getAllByReadingChair(ch);
        List<Semester> semesters = semesterDao.getAll();
        List<Chair> chairs = chairService.getAllByReadingChair(ch);
        List<Discipline> disciplines = discDao.getByChair(ch);
        
        model.addAttribute("appUser", app_user);
        model.addAttribute("lessonPlans", lessons);
        model.addAttribute("specialities", specialities);
        model.addAttribute("semesters", semesters);
        model.addAttribute("chairs", chairs);
        model.addAttribute("disciplines", disciplines);
        
        return "lesson/index";
    }
    
    @RequestMapping("/chair_lessons")
    public String chairLessons(Model model){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        List<LessonPlan> lessons = lessonService.getByFilterAllFields(null, ch, null, null, null, null);
        //List<Speciality> specialities = specialityDao.getAll();
        //List<Semester> semesters = semesterDao.getAll();
        
        model.addAttribute("appUser", app_user);
        model.addAttribute("lessonPlans", lessons);
        //model.addAttribute("specialities", specialities);
        //model.addAttribute("semesters", semesters);
        
        return "lesson/chair_plans";
    }
    
    @RequestMapping("/teachers")
    public String teachers(Model model){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        model.addAttribute("chair", ch);
        List<Teacher> teachers = teacherService.getByChair(ch);
        if (teachers != null){
            model.addAttribute("teachers", teachers);
        }
        return "lesson/teachers";
    }
    
    @RequestMapping("/rooms")
    public String rooms(Model model){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        List<Building> buildings = buildingService.getAll();
        if (buildings != null){
            model.addAttribute("buildings", buildings);
        }
        return "lesson/rooms";
    }
    
//////////////////////////////////////////////////////
    public LessonPlanService getLessonService() {
        return lessonService;
    }

    public void setLessonService(LessonPlanService lessonService) {
        this.lessonService = lessonService;
    }

    public AppUserService getAppUserService() {
        return appUserService;
    }

    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    
    public LazyInitializer getLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(LazyInitializer lazyInit) {
        this.lazyInit = lazyInit;
    }

    public SpecialityDao getSpecialityDao() {
        return specialityDao;
    }

    public void setSpecialityDao(SpecialityDao specialityDao) {
        this.specialityDao = specialityDao;
    }

    public SemesterDao getSemesterDao() {
        return semesterDao;
    }

    public void setSemesterDao(SemesterDao semesterDao) {
        this.semesterDao = semesterDao;
    }

    public ChairService getChairService() {
        return chairService;
    }

    public void setChairService(ChairService chairService) {
        this.chairService = chairService;
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public SolutionPlanService getSolutionService() {
        return solutionService;
    }

    public void setSolutionService(SolutionPlanService solutionService) {
        this.solutionService = solutionService;
    }   

    public DisciplineDao getDiscDao() {
        return discDao;
    }

    public void setDiscDao(DisciplineDao discDao) {
        this.discDao = discDao;
    }

    public BuildingService getBuildingService() {
        return buildingService;
    }

    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }
}
