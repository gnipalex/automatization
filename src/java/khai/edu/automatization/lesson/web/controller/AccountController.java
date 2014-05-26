/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.controller;

import java.util.List;
import javax.validation.Valid;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
//import org.hibernate.validator.Valid;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.security.RegisterForm;
import khai.edu.automatization.lesson.service.AppUserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AppUserDao usersDao;
    @Autowired
    private RoleDao rolesDao;
    @Autowired
    private LazyInitializer lazyInit;
    @Autowired
    private ChairDao chairDao;
    @Autowired
    private AppUserService userService;

    //redirect:/url
    //tiles библиотека для сборки страниц
    
    @RequestMapping("/conf_login")
    public String confLogin(){
        return "account/conf_login";
    }
    
    @RequestMapping("/login")
    public String login(){
        return "account/login";
    }
    
    @RequestMapping("/login_failed")
    public ModelAndView login_failed(){
        ModelAndView mav = new ModelAndView("account/login");
        mav.addObject("error", "Ошибка при авторизации");
        return mav;
    }

    @RequestMapping("/register")
    public String register(Model model){
        List<Chair> chairs = chairDao.getAll();
        model.addAttribute("chairs", chairs);
        if (!model.containsAttribute("registerForm")){
            model.addAttribute("registerForm", new RegisterForm());
        }
        return "account/register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register_post(Model model, 
        @ModelAttribute(value = "registerForm") @Valid RegisterForm registerForm,
        BindingResult result) {
        if (usersDao.getByMail(registerForm.getEmail()) != null){
            result.addError(new FieldError("registerForm", "email", "Пользователь с указанной почтой уже существует"));
        }
        if (! registerForm.getPassword().equals(registerForm.getPassword1())){
            result.addError(new FieldError("registerForm", "password", "Введенные пароли не совпадают"));
        }
        Chair chair = chairDao.getByName(registerForm.getChair());
        if (chair == null){
            result.addError(new FieldError("registerForm", "chair", "Выбранная кафедра не существует в базе"));
        }
        if (result.hasErrors()){
            return register(model);
        }

        userService.createUser(registerForm);
        return "account/login";
    }
    

    public void setLazyInit(LazyInitializer lazyInit) {
        this.lazyInit = lazyInit;
    }

    public LazyInitializer getLazyInit() {
        return lazyInit;
    }
    
    public AppUserDao getUsersDao() {
        return usersDao;
    }

    public void setUsersDao(AppUserDao usersDao) {
        this.usersDao = usersDao;
    }

    public RoleDao getRolesDao() {
        return rolesDao;
    }

    public void setRolesDao(RoleDao rolesDao) {
        this.rolesDao = rolesDao;
    }

    public ChairDao getChairDao() {
        return chairDao;
    }

    public void setChairDao(ChairDao chairDao) {
        this.chairDao = chairDao;
    }

    public void setUserService(AppUserService userService) {
        this.userService = userService;
    }
    
    public AppUserService getUserService() {
        return userService;
    }
}
