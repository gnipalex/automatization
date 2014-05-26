/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.controller;

import java.io.IOException;
import java.util.List;
import javax.transaction.TransactionManager;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.dao.DisciplineDao;
import khai.edu.automatization.lesson.dao.GroupDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.dao.LessonPlanDao;
import khai.edu.automatization.lesson.dao.RoleDao;
import khai.edu.automatization.lesson.dao.SemesterDao;
import khai.edu.automatization.lesson.dao.SpecialityDao;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Role;
import khai.edu.automatization.lesson.service.AppUserService;
import khai.edu.automatization.lesson.util.PlansImporter;
import khai.edu.automatization.lesson.web.ajax.AjaxStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("")
public class AdminController {

    @Autowired
    private AppUserDao appUserDao;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private LazyInitializer lazyInit;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private LessonPlanDao lessonDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private ChairDao chairDao;
    @Autowired
    private DisciplineDao disciplineDao;
    @Autowired
    private SpecialityDao specialityDao;
    @Autowired
    private SemesterDao semesterDao;
    @Autowired
    private HibernateTransactionManager txManager;

    @RequestMapping("/config/users")
    public String listUsers(Model model) {
        List<AppUser> users = appUserService.getWithAll();
        model.addAttribute("users", users);
        return "admin/config_users_list";
    }

    @RequestMapping(value = "/config/activate")
    public String activate(Model model, Integer id, Boolean activate) {
        if (id == null || activate == null) {
            model.addAttribute("error", "неверный запрос");
            return "error";
        }
        AppUser user = appUserDao.get(id);
        if (user == null) {
            model.addAttribute("error", "пользователь " + id + "не существует");
            return "error";
        }
        user.setIsActive(activate);
        appUserDao.saveOrUpdate(user);
        return "redirect:/config/users";
    }

    @RequestMapping(value = "/config/users/showUserRolesWindow", method = RequestMethod.POST)
    public String showUserRolesWindow(Model model, Integer id) {
        if (id == null) {
            return "admin/ajax/user_roles_window";
        }
        AppUser user = appUserService.getWithRoles(id);
        List<Role> roles = roleDao.getAll();
        model.addAttribute("roles", roles);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "admin/ajax/user_roles_window";
    }

    @RequestMapping(value = "/config/users/saveRoleToUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus saveRoleToUser(Integer role_id, Integer id) {
        AjaxStatus status = new AjaxStatus();
        if (id == null) {
            status.setSuccess(false);
            status.setMessage("server: не задан id пользователя");
            return status;
        }
        AppUser user = appUserService.getWithRoles(id);
        if (user == null) {
            status.setSuccess(false);
            status.setMessage("server: пользователь не найден");
            return status;
        }
        if (role_id == null) {
            status.setSuccess(false);
            status.setMessage("server: роли не заданы(null)");
            return status;
        }
        Role role = roleDao.get(role_id);
        appUserService.addRole(user, role);
        status.setSuccess(true);
        return status;
    }

    @RequestMapping(value = "/config/users/removeUserRole", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus removeRole(Integer role_id, Integer id) {
        AjaxStatus status = new AjaxStatus();
        if (id == null) {
            status.setSuccess(false);
            status.setMessage("server: не задан id пользователя");
            return status;
        }
        AppUser user = appUserService.getWithRoles(id);
        if (user == null) {
            status.setSuccess(false);
            status.setMessage("server: пользователь не найден");
            return status;
        }
        if (role_id == null) {
            status.setSuccess(false);
            status.setMessage("server: роли не заданы(null)");
            return status;
        }
        Role role = roleDao.get(role_id);
        if (role == null) {
            status.setSuccess(false);
            status.setMessage("server: роль не найдена");
            return status;
        }
        appUserService.removeRole(user, role);
        status.setSuccess(true);
        return status;
    }

    @RequestMapping(value = "/config/users/removeUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus removeUser(Integer id) {
        AjaxStatus status = new AjaxStatus();
        if (id == null) {
            status.setSuccess(false);
            status.setMessage("server: не задан id пользователя");
            return status;
        }
        AppUser user = appUserService.getWithRoles(id);
        if (user == null) {
            status.setSuccess(false);
            status.setMessage("server: пользователь не найден");
            return status;
        }

        appUserService.remove(user);

        status.setSuccess(true);
        return status;
    }

    @RequestMapping(value = "/config/users/loadUserRoles", method = RequestMethod.POST)
    public String loadUserRoles(Model model, Integer id) {
        if (id == null) {
            return "admin/ajax/roles_list";
        }
        AppUser user = appUserService.getWithRoles(id);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "admin/ajax/roles_list";
    }

    @RequestMapping(value = "/admin/plansLoad")
    public String plansLoad(Model model) {
        return "admin/plans_load";
    }

    /**
     * Saves plans from admin or config page
     */
    private String savePlansToDB(Model model, MultipartFile file, Boolean overwrite, Integer start, Integer count, String chairName){
        if (file == null || file.isEmpty()) {
            model.addAttribute("message", "ошибка,файл не задан или пуст");
            return "admin/plans_load_result";
        }
        boolean readAll = false;
        if (count == null || start == null) {
            readAll = true;
        }

        PlansImporter importer = null;
        try {
            importer = new PlansImporter(file.getInputStream(),
                    overwrite != null ? overwrite : false, chairName);
        } catch (IOException e) {
            model.addAttribute("message", "ошибка, загруженный файл не может быть прочитан");
            return "admin/plans_load_result";
        }
        importer.setChairDao(chairDao);
        importer.setDisciplineDao(disciplineDao);
        importer.setGroupDao(groupDao);
        importer.setLazyInit(lazyInit);
        importer.setLessonDao(lessonDao);
        importer.setSemesterDao(semesterDao);
        importer.setSpecialityDao(specialityDao);
        importer.setTxTemplate(new TransactionTemplate(this.txManager));
        
        int saved_rows = 0;
        
        try {
            //importer 
            if (readAll){
                saved_rows = importer.saveRowsToDB(0, 0);
            } else {
                saved_rows = importer.saveRowsToDB(start, count);
            }
        } catch (Exception ex){
            System.err.println(ex);
            model.addAttribute("message", "ошибка при сохранении");
            return "admin/plans_load_result";
        }
        model.addAttribute("message", "успешное сохранение, сохранено " + saved_rows + " строк");
        return "admin/plans_load_result";
    }
    
    @RequestMapping(value = "/admin/plansLoad", method = RequestMethod.POST)
    public String postPlansLoad(Model model, MultipartFile file, Boolean overwrite) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser app_user = appUserService.getByMailWithChair(user.getUsername());
        Chair ch = app_user.getReadingChair();
        
        this.savePlansToDB(model, file, overwrite, null, null, ch.getName());
        return "admin/plans_load_result";
    }

    @RequestMapping(value = "/config/plansLoad", method = RequestMethod.POST)
    public String postConfigPlansLoad(Model model, MultipartFile file, Boolean overwrite, Integer start, Integer count) {       
        this.savePlansToDB(model, file, overwrite, start, count, null);
        return "admin/plans_load_result";
    }

    public AppUserDao getAppUserDao() {
        return appUserDao;
    }

    public void setAppUserDao(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
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

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public LessonPlanDao getLessonDao() {
        return lessonDao;
    }

    public void setLessonDao(LessonPlanDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public ChairDao getChairDao() {
        return chairDao;
    }

    public void setChairDao(ChairDao chairDao) {
        this.chairDao = chairDao;
    }

    public DisciplineDao getDisciplineDao() {
        return disciplineDao;
    }

    public void setDisciplineDao(DisciplineDao disciplineDao) {
        this.disciplineDao = disciplineDao;
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

    public HibernateTransactionManager getTxManager() {
        return txManager;
    }

    public void setTxManager(HibernateTransactionManager txManager) {
        this.txManager = txManager;
    }
}
