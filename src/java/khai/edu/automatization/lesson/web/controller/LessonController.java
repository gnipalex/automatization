/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.controller;

import java.util.List;
import javax.validation.Valid;
import khai.edu.automatization.lesson.dao.DiscTypeDao;
import khai.edu.automatization.lesson.dao.DisciplineDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.dao.SemesterDao;
import khai.edu.automatization.lesson.dao.SpecialityDao;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.ControlType;
import khai.edu.automatization.lesson.model.DiscType;
import khai.edu.automatization.lesson.model.Discipline;
import khai.edu.automatization.lesson.model.Group;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Room;
import khai.edu.automatization.lesson.model.Semester;
import khai.edu.automatization.lesson.model.SolutionPlan;
import khai.edu.automatization.lesson.model.Speciality;
import khai.edu.automatization.lesson.model.Teacher;
import khai.edu.automatization.lesson.service.AppUserService;
import khai.edu.automatization.lesson.service.ChairService;
import khai.edu.automatization.lesson.service.LessonPlanService;
import khai.edu.automatization.lesson.service.RoomService;
import khai.edu.automatization.lesson.service.SolutionPlanService;
import khai.edu.automatization.lesson.service.TeacherService;
import khai.edu.automatization.lesson.web.ajax.AjaxLessonFilter;
import khai.edu.automatization.lesson.web.ajax.AjaxRoom;
import khai.edu.automatization.lesson.web.ajax.AjaxSolution;
import khai.edu.automatization.lesson.web.ajax.AjaxStatus;
import khai.edu.automatization.lesson.web.ajax.AjaxTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("/lesson")
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
    private DiscTypeDao discTypeDao;
    @Autowired
    private DisciplineDao discDao;
    
    @Autowired
    private ChairService chairService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private SolutionPlanService solutionService;
    @Autowired
    private LazyInitializer lazyInit;
    
    @RequestMapping("")
    public String index(Model model){
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            return "redirect:welcome";
        }
        //получим пользователя вместе с кафедрой
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        //String ss = app_user.getReadingChair().getId() + " " + app_user.getReadingChair().getName();
        //получим список учебных планов для разрешения
        Chair ch = app_user.getReadingChair();
        List<LessonPlan> lessons = lessonService.getByFilterAllFields(ch, null, null, null, null, null, null, null);
        List<Speciality> specialities = specialityDao.getAllByReadingChair(ch);
        List<Semester> semesters = semesterDao.getAll();
        List<Chair> chairs = chairService.getAllByReadingChair(ch);
        List<DiscType> dtypes = discTypeDao.getAll();
        List<Discipline> disciplines = discDao.getByChair(ch);
        
        model.addAttribute("appUser", app_user);
        model.addAttribute("lessonPlans", lessons);
        model.addAttribute("specialities", specialities);
        model.addAttribute("semesters", semesters);
        model.addAttribute("chairs", chairs);
        model.addAttribute("dtypes", dtypes);
        model.addAttribute("disciplines", disciplines);
        
        return "lesson/index";
    }
    
    @RequestMapping(value = "/getFilteredPlans", method = RequestMethod.POST)
    public String getLessonTable(Model model, AjaxLessonFilter filter){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        Group group = null;
        if (filter.getGroup_id() != null){
            group = new Group();
            group.setId(filter.getGroup_id());
        }
        Discipline disc = null;
        if (filter.getDisc_id() != null){
            disc = new Discipline();
            disc.setId(filter.getDisc_id());
        }
        DiscType dt = null;
        if (filter.getDt_id() != null){
            dt = new DiscType();
            dt.setId(filter.getDt_id());
        }
        Chair prod_chair = null;
        if (filter.getPrchair_id() != null){
            prod_chair = new Chair();
            prod_chair.setId(filter.getPrchair_id());
        }
//        Chair read_chair = null;
//        if (filter.getRdchair_id() != null){
//            read_chair = new Chair();
//            read_chair.setId(filter.getRdchair_id());
//        }
        Chair read_chair = ch;
        
        Semester semester = null;
        if (filter.getSem_id() != null){
            semester = new Semester();
            semester.setId(filter.getSem_id());
        }
        ControlType ct = null;
        if (filter.getCt_id() != null){
            ct = new ControlType();
            ct.setId(filter.getCt_id());
        }
        Speciality spec = null;
        if (filter.getSpec_id() != null){
            spec = new Speciality();
            spec.setId(filter.getSpec_id());
        } 
        List<LessonPlan> lessons = lessonService.getByFilterAllFields(read_chair, prod_chair, spec, group, disc, dt, semester, ct);
        if (lessons != null){
            model.addAttribute("lessonPlans", lessons);
        }
        return "lesson/lesson_table";
    }
    
    @RequestMapping(value = "/viewLesson", method = RequestMethod.POST)
    public String getLessonWindow(Model model, Integer id) {
        if (id == null){
            return "lesson/lesson_window";
        }
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        LessonPlan lp = lessonService.getByIdAndReadingChair(id, app_user.getReadingChair());
        //List<Teacher> teachers = teacherService.getAllWithChair();
        if (lp != null) {
            model.addAttribute("has_data", true);
            model.addAttribute("lesson", lp);
        }
        //model.addAttribute("teachers", teachers);
        return "lesson/lesson_window";
    }
    
    @RequestMapping(value = "/getSolutions", method = RequestMethod.POST)
    public String getSolutions(Model model, Integer lesson_id){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        LessonPlan lp = lessonService.getByIdAndReadingChair(lesson_id, ch);
        if (lp != null) {
            model.addAttribute("has_data", true);
            model.addAttribute("lesson", lp);
        }
        return "lesson/solutions_list";
    }
    
    @RequestMapping(value = "/viewSolution", method = RequestMethod.POST)
    public String getSolution(Model model, AjaxSolution solution_ajax){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        if (solution_ajax.getLesson_id() != null){
            LessonPlan lp = lessonService.getByIdAndReadingChair(solution_ajax.getLesson_id(), ch);
            if (lp != null) {
                model.addAttribute("lesson", lp);
                model.addAttribute("lesson_id", lp.getId());
                if (solution_ajax.getSolution_id() != null){
                   SolutionPlan sp = solutionService.getWithAll(solution_ajax.getSolution_id());
                   if (sp != null){
                       model.addAttribute("solution", sp);
                   }
                }
                List<Teacher> teachers = teacherService.getByChair(ch);
                if (teachers != null){
                    model.addAttribute("teachers", teachers);
                }
                List<Room> rooms = roomService.getAll();
                if (rooms != null){
                    model.addAttribute("rooms", rooms);
                }
            }
        }
        
        return "lesson/solution_window";
    }
    
    @RequestMapping(value = "/deleteSolution", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus deleteSolution(AjaxSolution ajax_solution){
        AjaxStatus status = new AjaxStatus();
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        if (ajax_solution.getLesson_id() == null){
            status.setSuccess(false);
            status.setMessage("server: не задан id учебного плана");
            return status;
        }
        if (ajax_solution.getSolution_id() == null){
            status.setSuccess(false);
            status.setMessage("server: не задан id решения");
            return status;
        }
        LessonPlan lp = lessonService.getByIdAndReadingChair(ajax_solution.getLesson_id(), ch);
        if (lp == null){
            status.setSuccess(false);
            status.setMessage("server: учебный план не найден либо у вас нет прав на редактирование");
            return status;
        }
        //нужно также убедиться что решение действительно относиться к нашему учебному плану
        SolutionPlan sp = null;
        for (SolutionPlan s : lp.getSolutions()){
            if (s.getId().intValue() == ajax_solution.getSolution_id().intValue()){
                sp = s;
            }
        }
        if (sp == null){
            status.setSuccess(false);
            status.setMessage("server: учебный план не содержит заданное решение");
            return status;
        }
        
        solutionService.remove(sp);
        status.setSuccess(true);
        
        return status;
        
    }
    
    @RequestMapping(value = "/viewAddTeacher", method = RequestMethod.POST)
    public String getTeacherWindow(Model model){
        return "lesson/new_teacher_window";
    }
    
    @RequestMapping(value = "/saveTeacher", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveTeacher(@Valid AjaxTeacher teacher_ajax, BindingResult result){
        AjaxStatus status = new AjaxStatus();
        if (result.hasErrors()){
            status.setSuccess(false);
            status.setMessage("server: некоторые поля не были заполнены");
            return status;
        }
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        Teacher t = new Teacher();
        t.setChair(ch);
        t.setFirstName(teacher_ajax.getFirstName());
        t.setLastName(teacher_ajax.getLastName());
        t.setMiddleName(teacher_ajax.getMiddleName());
        t.setPost(teacher_ajax.getPost());
        
        if (teacherService.get(t, ch) != null){
            status.setSuccess(false);
            status.setMessage("server: запись уже существует");
            return status;
        }
        
        teacherService.saveOrUpdate(t);
        status.setSuccess(true);
        
        return status;
    }
    
    @RequestMapping(value = "/loadTeachers", method = RequestMethod.POST)
    public String getTeachers(Model model){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        List<Teacher> teachers = teacherService.getByChair(ch);
        if (teachers != null){
            model.addAttribute("teachers", teachers);
        }
        return "lesson/teachers_list";
    }
    
    @RequestMapping(value = "/saveRoom", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveRoom(@Valid AjaxRoom ajax_room, BindingResult result){
        AjaxStatus status = new AjaxStatus();
        if (result.hasErrors()){
            status.setSuccess(false);
            status.setMessage("server: некоторые поля не были заполнены");
            return status;
        }
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        if (roomService.getByName(ajax_room.getRoom()) != null){
            status.setSuccess(false);
            status.setMessage("server: запись уже существует");
            return status;
        }
        
        Room r = new Room();
        r.setName(ajax_room.getRoom());
        
        roomService.saveOrUpdate(r);
        
        status.setSuccess(true);
        return status;
    }
    
    @RequestMapping(value = "/loadRooms", method = RequestMethod.POST)
    public String getRooms(Model model){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        List<Room> rooms = roomService.getAll();
        if (rooms != null){
            model.addAttribute("rooms", rooms);
        }
        return "lesson/rooms_list";
    }
    
    @RequestMapping(value = "/viewAddRoom", method = RequestMethod.POST)
    public String getRoomWindow(Model model){
        return "lesson/new_room_window";
    }
    
    @RequestMapping(value = "/saveSolution", method = RequestMethod.POST)
    @ResponseBody 
    public AjaxStatus saveSolution(AjaxSolution ajax_solution){
        AjaxStatus status = new AjaxStatus();
        LessonPlan lp = null;
        SolutionPlan solution = null;
        
        if (ajax_solution.getLesson_id() == null){
            status.setSuccess(false);
            status.setMessage("server: не указан id учебного плана");
            return status;
        }
        
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        lp = lessonService.getByIdAndReadingChair(ajax_solution.getLesson_id(), ch);
        
        
        if (lp == null){
            status.setSuccess(false);
            status.setMessage("server: план не существует или у вас не прав на редактирование плана");
            return status;
        }     
        Teacher t = null;
        if (ajax_solution.getTeacher_id() != null){
            t = teacherService.get(ajax_solution.getTeacher_id());
        }
        Room r = null;
        if (ajax_solution.getRoom_id() != null){
            r = roomService.get(ajax_solution.getRoom_id());
        }
        
        if (t == null && r == null){
            status.setSuccess(false);
            status.setMessage("server: невозможно сохранить пустое решение");
            return status;
        }
        boolean result = false;
        if (ajax_solution.getSolution_id() != null){
            //нужно обновить план
            solution = solutionService.get(ajax_solution.getSolution_id());
            if (solution == null){
                status.setSuccess(false);
                status.setMessage("server: решение для редактирования не существует");
                return status;
            }
            result = solutionService.editSolution(
                    solution != null ? solution.getId() : null,
                    app_user != null ? app_user.getId() : null,
                    t != null ? t.getId() : null,
                    r != null ? r.getId() : null );
        } else {
            //создается новое решение
//            solution = new SolutionPlan();
//            solution.setRoom(r);
//            solution.setTeacher(t);
//            solution.setAppUser(app_user);
//            lp.getSolutions().add(solution);
//
//            lessonService.saveOrUpdate(lp);
            result = solutionService.createSolution(
                    lp != null ? lp.getId() : null,
                    app_user != null ? app_user.getId() : null,
                    t != null ? t.getId() : null,
                    r != null ? r.getId() : null);
        }            
        
        status.setSuccess(result);
        return status;
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

    public DiscTypeDao getDiscTypeDao() {
        return discTypeDao;
    }

    public void setDiscTypeDao(DiscTypeDao discTypeDao) {
        this.discTypeDao = discTypeDao;
    }
    
}
