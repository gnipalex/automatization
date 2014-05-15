/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service;

import java.util.List;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.security.RegisterForm;


/**
 *
 * @author Alex
 */
public interface AppUserService {
    public void createUser(RegisterForm registerForm);
    public void activateUser(AppUser user, boolean isActive);
    public List<AppUser> getUsersWithChairs();
    public AppUser getByMailWithChair(String email);
    
    public AppUser getByMail(String login);
    public AppUser get(Integer id);
    public List<AppUser> getAll();
    public void saveOrUpdate(AppUser obj);
    public void remove(AppUser obj);
}
