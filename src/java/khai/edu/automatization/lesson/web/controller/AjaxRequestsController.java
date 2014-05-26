package khai.edu.automatization.lesson.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.Valid;
import khai.edu.automatization.lesson.dao.DisciplineDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.dao.SemesterDao;
import khai.edu.automatization.lesson.dao.SpecialityDao;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.Building;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Discipline;
import khai.edu.automatization.lesson.model.Group;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Room;
import khai.edu.automatization.lesson.model.Semester;
import khai.edu.automatization.lesson.model.SolutionPlan;
import khai.edu.automatization.lesson.model.Speciality;
import khai.edu.automatization.lesson.model.Teacher;
import khai.edu.automatization.lesson.service.AppUserService;
import khai.edu.automatization.lesson.service.BuildingService;
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
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("/ajax")
public class AjaxRequestsController {

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
    private BuildingService buildingService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private SolutionPlanService solutionService;
    @Autowired
    private LazyInitializer lazyInit;

    private boolean hasPermision() {
        if (!(SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal() instanceof UserDetails)) {
            return false;
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        for (GrantedAuthority aut : user.getAuthorities()) {
            if (aut.getAuthority().equals("ROLE_USER")) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/getFilteredPlans", method = RequestMethod.POST)
    public String getLessonTable(Model model, AjaxLessonFilter filter) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        Group group = null;
        if (filter.getGroup_id() != null) {
            group = new Group();
            group.setId(filter.getGroup_id());
        }
        Discipline disc = null;
        if (filter.getDisc_id() != null) {
            disc = new Discipline();
            disc.setId(filter.getDisc_id());
        }
        Chair prod_chair = null;
        if (filter.getPrchair_id() != null) {
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
        if (filter.getSem_id() != null) {
            semester = new Semester();
            semester.setId(filter.getSem_id());
        }
        Speciality spec = null;
        if (filter.getSpec_id() != null) {
            spec = new Speciality();
            spec.setId(filter.getSpec_id());
        }
        List<LessonPlan> lessons = lessonService.getByFilterAllFields(read_chair, prod_chair, spec, group, disc, semester);
        List<LessonPlan> filtered_lessons = new ArrayList<>();
        if (lessons != null) {
            boolean was_filter = false;
            was_filter = filter.isCp() ? true : was_filter;
            was_filter = filter.isCw() ? true : was_filter;
            was_filter = filter.isDiv_zachet() ? true : was_filter;
            was_filter = filter.isExam() ? true : was_filter;
            was_filter = filter.isZachet() ? true : was_filter;
            for (LessonPlan lp : lessons) {
                if (filter.isCp() && lp.getCp().booleanValue()) {
                    filtered_lessons.add(lp);
                    continue;
                }
                if (filter.isCw() && lp.getCw().booleanValue()) {
                    filtered_lessons.add(lp);
                    continue;
                }
                if (filter.isDiv_zachet() && lp.getDiv_zachet().booleanValue()) {
                    filtered_lessons.add(lp);
                    continue;
                }
                if (filter.isExam() && lp.getExam().booleanValue()) {
                    filtered_lessons.add(lp);
                    continue;
                }
                if (filter.isZachet() && lp.getZachet().booleanValue()) {
                    filtered_lessons.add(lp);
                    continue;
                }
            }
            if (filtered_lessons.size() > 0) {
                model.addAttribute("lessonPlans", filtered_lessons);
            } else {
                if (!was_filter) {
                    model.addAttribute("lessonPlans", lessons);
                }
            }
        }
        return "lesson/ajax/lesson_table";
    }

    @RequestMapping(value = "/viewLesson", method = RequestMethod.POST)
    public String getLessonWindow(Model model, Integer id) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }
        if (id == null) {
            return "lesson/ajax/lesson_window";
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        LessonPlan lp = lessonService.getByIdAndReadingChair(id, app_user.getReadingChair());
        //List<Teacher> teachers = teacherService.getAllWithChair();
        if (lp != null) {
            model.addAttribute("has_data", true);
            model.addAttribute("lesson", lp);
        }
        //model.addAttribute("teachers", teachers);
        return "lesson/ajax/lesson_window";
    }

    @RequestMapping(value = "/getSolutions", method = RequestMethod.POST)
    public String getSolutions(Model model, Integer lesson_id) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        LessonPlan lp = lessonService.getByIdAndReadingChair(lesson_id, ch);
        if (lp != null) {
            model.addAttribute("has_data", true);
            model.addAttribute("lesson", lp);
        }
        return "lesson/ajax/solutions_list";
    }

    @RequestMapping(value = "/viewSolution", method = RequestMethod.POST)
    public String getSolution(Model model, AjaxSolution solution_ajax) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        if (solution_ajax.getLesson_id() != null) {
            LessonPlan lp = lessonService.getByIdAndReadingChair(solution_ajax.getLesson_id(), ch);
            if (lp != null) {
                model.addAttribute("lesson", lp);
                model.addAttribute("lesson_id", lp.getId());
                if (solution_ajax.getSolution_id() != null) {
                    SolutionPlan sp = solutionService.getWithAll(solution_ajax.getSolution_id());
                    if (sp != null) {
                        model.addAttribute("solution", sp);
                    }
                }
                List<Teacher> teachers = teacherService.getByChair(ch);
                if (teachers != null) {
                    model.addAttribute("teachers", teachers);
                }
                List<Building> buildings = buildingService.getAll();
                if(buildings != null){
                    model.addAttribute("buildings", buildings);
                } 
            }
        }

        return "lesson/ajax/solution_window";
    }

    @RequestMapping(value = "/deleteSolution", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus deleteSolution(AjaxSolution ajax_solution) {
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("ошибка доступа");
            return status;
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        if (ajax_solution.getLesson_id() == null) {
            status.setSuccess(false);
            status.setMessage("server: не задан id учебного плана");
            return status;
        }
        if (ajax_solution.getSolution_id() == null) {
            status.setSuccess(false);
            status.setMessage("server: не задан id решения");
            return status;
        }
        LessonPlan lp = lessonService.getByIdAndReadingChair(ajax_solution.getLesson_id(), ch);
        if (lp == null) {
            status.setSuccess(false);
            status.setMessage("server: учебный план не найден либо у вас нет прав на редактирование");
            return status;
        }
        //нужно также убедиться что решение действительно относиться к нашему учебному плану
        SolutionPlan sp = null;
        for (SolutionPlan s : lp.getSolutions()) {
            if (s.getId().intValue() == ajax_solution.getSolution_id().intValue()) {
                sp = s;
            }
        }
        if (sp == null) {
            status.setSuccess(false);
            status.setMessage("server: учебный план не содержит заданное решение");
            return status;
        }

        solutionService.remove(sp);
        status.setSuccess(true);

        return status;
    }

    @RequestMapping(value = "/viewAddTeacher", method = RequestMethod.POST)
    public String getNewTeacherWindow(Model model) {
        return "lesson/ajax/new_teacher_window";
    }

    @RequestMapping(value = "/saveTeacher", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveTeacher(@Valid AjaxTeacher teacher_ajax, BindingResult result) {
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("ошибка доступа");
            return status;
        }
        if (result.hasErrors()) {
            status.setSuccess(false);
            status.setMessage("server: некоторые поля не были заполнены");
            return status;
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        Teacher t = new Teacher();
        t.setChair(ch);
        t.setFirstName(teacher_ajax.getFirstName());
        t.setLastName(teacher_ajax.getLastName());
        t.setMiddleName(teacher_ajax.getMiddleName());
        t.setPost(teacher_ajax.getPost());

        if (teacherService.get(t, ch) != null) {
            status.setSuccess(false);
            status.setMessage("server: запись уже существует");
            return status;
        }

        teacherService.saveOrUpdate(t);
        status.setSuccess(true);

        return status;
    }

    @RequestMapping(value = "/loadTeachers", method = RequestMethod.POST)
    public String getTeachers(Model model) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        List<Teacher> teachers = teacherService.getByChair(ch);
        if (teachers != null) {
            model.addAttribute("teachers", teachers);
        }
        return "lesson/ajax/teachers_list";
    }

    @RequestMapping(value = "/saveRoom", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveRoom(@Valid AjaxRoom ajax_room, BindingResult result) {
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("ошибка доступа");
            return status;
        }
        if (result.hasErrors()) {
            status.setSuccess(false);
            status.setMessage("server: некоторые поля не были заполнены");
            return status;
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        if (roomService.getByName(ajax_room.getRoom()) != null) {
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
    public String loadRooms(Model model, Integer b_id) {
        if (!hasPermision()) {
            return "lesson/ajax/rooms_list";
        }
        if (b_id == null){
            return "lesson/ajax/rooms_list";
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        Building b = buildingService.getWithRooms(b_id);
        if (b != null) {
            model.addAttribute("rooms", b.getRooms());
        }
        return "lesson/ajax/rooms_list";
    }

    @RequestMapping(value = "/viewAddRoom", method = RequestMethod.POST)
    public String getRoomWindow(Model model) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }
        return "lesson/new_room_window";
    }

    @RequestMapping(value = "/saveSolution", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveSolution(AjaxSolution ajax_solution) {
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("ошибка доступа");
            return status;
        }

        LessonPlan lp = null;
        SolutionPlan solution = null;

        if (ajax_solution.getLesson_id() == null) {
            status.setSuccess(false);
            status.setMessage("server: не указан id учебного плана");
            return status;
        }

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        lp = lessonService.getByIdAndReadingChair(ajax_solution.getLesson_id(), ch);

        if (lp == null) {
            status.setSuccess(false);
            status.setMessage("server: план не существует или у вас не прав на редактирование плана");
            return status;
        }
        Teacher t = null;
        if (ajax_solution.getTeacher_id() != null) {
            t = teacherService.get(ajax_solution.getTeacher_id());
        }
        Room r = null;
        if (ajax_solution.getRoom_id() != null) {
            r = roomService.get(ajax_solution.getRoom_id());
        }

        if (t == null && r == null) {
            status.setSuccess(false);
            status.setMessage("server: невозможно сохранить пустое решение");
            return status;
        }
        boolean result = false;
        if (ajax_solution.getSolution_id() != null) {
            //нужно обновить план
            solution = solutionService.get(ajax_solution.getSolution_id());
            if (solution == null) {
                status.setSuccess(false);
                status.setMessage("server: решение для редактирования не существует");
                return status;
            }
            result = solutionService.editSolution(ajax_solution);
        } else {
            //создается новое решение
            result = solutionService.createSolution(ajax_solution);
        }
        status.setSuccess(result);
        return status;
    }

    @RequestMapping(value = "/getManyWindow", method = RequestMethod.POST)
    public String showManyEditWindow(Model model, String ids) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }
        if (ids == null) {
            return "lesson/ajax/not_found";
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        List<LessonPlan> plansToEdit = new ArrayList<>();
        Matcher m = Pattern.compile("\\d+(?=;)").matcher(ids);
        while (m.find()) {
            int id = 0;
            try {
                id = Integer.parseInt(m.group());
            } catch (NumberFormatException e) {
                continue;
            }
            LessonPlan lp = lessonService.getByIdAndReadingChair(id, ch);
            if (lp != null) {
                plansToEdit.add(lp);
            }
        }
        if (plansToEdit.isEmpty()) {
            return "lesson/ajax/not_found";
        }
        model.addAttribute("lessons", plansToEdit);
        List<Teacher> teachers = teacherService.getByChair(ch);
        if (teachers != null) {
            model.addAttribute("teachers", teachers);
        }
        List<Building> buildings = buildingService.getAll();
        if (buildings != null) {
            model.addAttribute("buildings", buildings);
        }
        return "lesson/ajax/many_edit_window";
    }
    
    @RequestMapping(value = "/loadRoomsForManyEdit", method = RequestMethod.POST)
    public String loadRoomsForManyEdit(Model model, Integer b_id){
        if (!hasPermision()) {
            return "lesson/ajax/manyedit_rooms_list";
        }
        if (b_id == null) {
            return "lesson/ajax/manyedit_rooms_list";
        }
        Building b = buildingService.getWithRooms(b_id);
        if (b != null){
            model.addAttribute("rooms", b.getRooms());
        }
        return "lesson/ajax/manyedit_rooms_list";
    }
    
    @RequestMapping(value = "/saveManyEditSolution", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveManyEditSolution(String ids_lab, String ids_pract, String ids_lect, Integer t_id, Integer r_id){
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("server: ошибка доступа");
            return status;
        }
        if (t_id == null){
            status.setSuccess(false);
            status.setMessage("server: не задан id преподавателя");
            return status;
        }
        List<Integer> lab_parsed_ids = new ArrayList<>();
        List<Integer> pract_parsed_ids = new ArrayList<>();
        List<Integer> lect_parsed_ids = new ArrayList<>();
        Pattern p_num = Pattern.compile("\\d+(?=;)");
        if (ids_lab != null){
            Matcher m_lab = p_num.matcher(ids_lab);
            while(m_lab.find()){
                Integer id;
                try {
                    id = Integer.parseInt(m_lab.group());
                } catch (NumberFormatException e){
                    continue;
                }
                lab_parsed_ids.add(id);
            }
        }
        if (ids_lect != null){
            Matcher m_lect = p_num.matcher(ids_lect);
            while(m_lect.find()){
                Integer id;
                try {
                    id = Integer.parseInt(m_lect.group());
                } catch (NumberFormatException e){
                    continue;
                }
                lect_parsed_ids.add(id);
            }
        }
        if (ids_pract != null){
            Matcher m_pract = p_num.matcher(ids_pract);
            while(m_pract.find()){
                Integer id;
                try {
                    id = Integer.parseInt(m_pract.group());
                } catch (NumberFormatException e){
                    continue;
                }
                pract_parsed_ids.add(id);
            }
        }
        boolean result = true;
        try{
             result = solutionService.createManySolutions(
                    lab_parsed_ids,
                    pract_parsed_ids,
                    lect_parsed_ids,
                    t_id,
                    r_id);
        } catch (Exception e){
            result = false;
            System.err.println(e);
        }
        status.setSuccess(result);
        status.setMessage("server: ошибка при сохранении множества назначений");
        return status;
    }

    @RequestMapping(value = "/showTeacherWindow", method = RequestMethod.POST)
    public String showTeacherWindow(Model model, Integer id) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        if (id == null) {
            return "lesson/ajax/teacher_info_window";
        }

        Teacher t = teacherService.getWithSolutionAndLessonPlans(id);
        if (t != null) {
            model.addAttribute("teacher", t);
        }

        return "lesson/ajax/teacher_info_window";
    }

    @RequestMapping(value = "/deleteTeacherSolution", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus deleteTeacherSolution(Integer id) {
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("server: ошибка, нет доступа");
            return status;
        }
        if (id == null) {
            status.setSuccess(false);
            status.setMessage("server: не задан id");
            return status;
        }

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        SolutionPlan sp = solutionService.getWithAll(id);
        if (sp == null) {
            status.setSuccess(false);
            status.setMessage("server: решение не найдено");
            return status;
        }
        if (!sp.getLessonPlan().getReadingChair().getId().equals(ch.getId())) {
            status.setSuccess(false);
            status.setMessage("server: нет права редактирования данного решения");
            return status;
        }
        solutionService.remove(sp);
        status.setSuccess(true);
        return status;
    }

    @RequestMapping(value = "/loadTeacherSolutions", method = RequestMethod.POST)
    public String loadTeacherSolutions(Model model, Integer id) {
        if (!hasPermision()) {
            return "lesson/ajax/access_error";
        }
        if (id == null) {
            return "lesson/ajax/teachers_solutions_list";
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();

        Teacher teacher = teacherService.getWithSolutionAndLessonPlans(id);
        if (teacher != null) {
            model.addAttribute("teacher", teacher);
        }
        return "lesson/ajax/teachers_solutions_list";
    }

    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus deleteTeacher(Integer id) {
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("server: ошибка, нет доступа");
            return status;
        }
        if (id == null) {
            status.setSuccess(false);
            status.setMessage("server: не задан id");
            return status;
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        Teacher t = teacherService.getWithChair(id);
        if (t == null){
            status.setSuccess(false);
            status.setMessage("server: преподаватель не найден");
            return status;
        }
        
        if (!t.getChair().getId().equals(ch.getId())){
            status.setSuccess(false);
            status.setMessage("server: нет права редактирования данного преподавателя");
            return status;
        }
        
        teacherService.remove(t);
        status.setSuccess(true);
        return status;
    }

    @RequestMapping(value = "/editTeacher", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus editTeacher(@Valid AjaxTeacher ajax_t, BindingResult result){
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("server: ошибка, нет доступа");
            return status;
        }
        if (result.hasErrors()){
            status.setSuccess(false);
            status.setMessage("server:невозможно сохранить,некоторые поля не заполнены");
            return status;
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        Teacher t = teacherService.getWithChair(ajax_t.getId());
        if (t == null){
            status.setSuccess(false);
            status.setMessage("server: преподаватель не найден");
            return status;
        }
        
        if (!t.getChair().getName().equals(ch.getName())){
            status.setSuccess(false);
            status.setMessage("server: нет права редактирования данного преподавателя");
            return status;
        }
        t.setFirstName(ajax_t.getFirstName());
        t.setLastName(ajax_t.getLastName());
        t.setMiddleName(ajax_t.getMiddleName());
        t.setPost(ajax_t.getPost());
        teacherService.saveOrUpdate(t);
        
        status.setSuccess(true);
        return status;
    }
    
    @RequestMapping(value = "/viewNewTeacherWindow", method = RequestMethod.POST)
    public String viewNewTeacher(Model model){
        return "lesson/ajax/new_teacher_window";
    }
  
    @RequestMapping(value = "/saveRoomToBuilding", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveRoomToBuilding(Integer b_id, String room_name){
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("server: ошибка, нет доступа");
            return status;
        }
        if (b_id == null){
            status.setSuccess(false);
            status.setMessage("server: ошибка, не задан id корпуса");
            return status;
        }
        if (room_name == null || room_name.length() == 0){
            status.setSuccess(false);
            status.setMessage("server: ошибка, нельзя сохранить пустое название аудитории");
            return status;
        }
        Building building = buildingService.get(b_id);
        if (building == null){
            status.setSuccess(false);
            status.setMessage("server: ошибка, корпус не найден");
            return status;
        }
        Room room = roomService.getByName(room_name);
        if (room != null && room.getBuilding().getId().equals(building.getId())){
            status.setSuccess(false);
            status.setMessage("server: ошибка, аудитория уже существует");
            return status;
        }
        room = new Room();
        room.setName(room_name);
        room.setBuilding(building);
        roomService.saveOrUpdate(room);
        
        status.setSuccess(true);
        return status;
    }
    
    @RequestMapping(value = "/loadRoomsByBuilding", method = RequestMethod.POST)
    public String loadRoomsByBuilding(Model model, Integer id){
        if (!hasPermision()) {
            return "lesson/ajax/rooms_list_page";
        }
        if (id == null){
            return "lesson/ajax/rooms_list_page";
        }
        Building b = buildingService.getWithRooms(id);
        if (b != null){
            model.addAttribute("building", b);
        }
        return "lesson/ajax/rooms_list_page";
    }
    
    @RequestMapping(value = "/saveNewBuilding", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveNewBuilding(String b_name){
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("server: ошибка, нет доступа");
            return status;
        }
        if (b_name == null || b_name.length() == 0){
            status.setSuccess(false);
            status.setMessage("server: ошибка, нельзя сохранить пустое название корпуса");
            return status;
        }
        Building b = buildingService.getByName(b_name);
        if (b != null){
            status.setSuccess(false);
            status.setMessage("server: ошибка, корпус уже существует");
            return status;
        }
        b = new Building();
        b.setName(b_name);
        
        buildingService.saveOrUpdate(b);
        
        status.setSuccess(true);
        return status;
    }
    /*
    @RequestMapping(value = "/showNewGroupWindow", method = RequestMethod.POST)
    public String showNewGroupWindow(Model model, Integer sp_id, Integer sem_id){
        if (!hasPermision()) {
            return "lesson/ajax/new_group_edit_window";
        }
        if (sp_id == null || sem_id == null){
            return "lesson/ajax/new_group_edit_window";
        }
        Speciality sp = new Speciality();
        sp.setId(sp_id);
        Semester sem = new Semester();
        sem.setId(sem_id);
        List<LessonPlan> lessons = lessonService.getByFilterAllFields(null, null, sp, null, null, sem);
        if (lessons != null) {
            model.addAttribute("lessons", lessons);
        }
        return "lesson/ajax/new_group_edit_window";
    }
    
    @RequestMapping(value = "/saveNewBuilding", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveNewGroup(String lp_ids, String name){
        AjaxStatus status = new AjaxStatus();
        if (!hasPermision()) {
            status.setSuccess(false);
            status.setMessage("server: ошибка, нет доступа");
            return status;
        }
    }
    */
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

    public DisciplineDao getDiscDao() {
        return discDao;
    }

    public void setDiscDao(DisciplineDao discDao) {
        this.discDao = discDao;
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

    public LazyInitializer getLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(LazyInitializer lazyInit) {
        this.lazyInit = lazyInit;
    }

    public BuildingService getBuildingService() {
        return buildingService;
    }

    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }
}
