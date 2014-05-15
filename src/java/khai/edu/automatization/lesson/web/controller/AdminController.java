/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.web.controller;

import java.util.List;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.dao.LazyInitializer;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Alex
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AppUserDao appUserDao;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private LazyInitializer lazyInit;
    
    @RequestMapping("")
    public String listUsers(Model model){
//        List<AppUser> users = appUserDao.getAll();
//        for(AppUser u : users){
//            lazyInit.lazyInit(u, u.getReadingChair());
//        }
        List<AppUser> users = appUserService.getUsersWithChairs();
        model.addAttribute("users", users);
        return "admin/users_list";
    }
    @RequestMapping(value = "/activate")
    public String activate(Model model, Integer id, Boolean activate){
        if (id == null || activate == null){
            model.addAttribute("error", "неверный запрос");
            return "error";
        }
        AppUser user = appUserDao.get(id);
        if (user == null){
            model.addAttribute("error", "пользователь " + id + "не существует");
            return "error";
        }
        user.setIsActive(activate);
        appUserDao.saveOrUpdate(user);
        return "redirect:/admin";
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
}
