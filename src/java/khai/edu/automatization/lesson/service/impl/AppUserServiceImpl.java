/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service.impl;

import java.util.List;
import khai.edu.automatization.lesson.dao.AppUserDao;
import khai.edu.automatization.lesson.dao.ChairDao;
import khai.edu.automatization.lesson.dao.RoleDao;
import khai.edu.automatization.lesson.model.AppUser;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Role;
import khai.edu.automatization.lesson.security.RegisterForm;
import khai.edu.automatization.lesson.service.AppUserService;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private AppUserDao appUserDao;
    private RoleDao roleDao;
    private ChairDao chairDao;
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public void createUser(RegisterForm registerForm) {
        Chair chair = chairDao.getByName(registerForm.getChair());

        AppUser newuser = new AppUser();
        newuser.setReadingChair(chair);
        newuser.setEmail(registerForm.getEmail());
        newuser.setExpired(false);
        newuser.setIsActive(false);
        newuser.setLastName(registerForm.getLastname());
        newuser.setName(registerForm.getName());
        newuser.setPassword(encoder.encodePassword(registerForm.getPassword(), null));
        Role role_user = roleDao.getByName("ROLE_USER");
        if (role_user == null){
            role_user = new Role();
            role_user.setName("ROLE_USER");
        }
        newuser.getRoles().add(role_user);
        appUserDao.saveOrUpdate(newuser);
    }
    
    @Override
    public void activateUser(AppUser user, boolean isActive) {
        if (user == null) return;
        user.setIsActive(isActive);
        appUserDao.saveOrUpdate(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppUser> getUsersWithChairs() {
        List<AppUser> list = appUserDao.getAll();
        for (AppUser u : list){
            Hibernate.initialize(u.getReadingChair());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser getByMailWithChair(String email) {
        AppUser user = appUserDao.getByMail(email);
        if (user == null) return null;
        Hibernate.initialize(user.getReadingChair());
        return user;
    }
    
    public AppUserDao getAppUserDao() {
        return appUserDao;
    }

    public void setAppUserDao(AppUserDao appUserDao) {
        this.appUserDao = appUserDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public ChairDao getChairDao() {
        return chairDao;
    }

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public void setChairDao(ChairDao chairDao) {
        this.chairDao = chairDao;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }
    
    @Override
    public AppUser getByMail(String login) {
        return this.appUserDao.getByMail(login);
    }
    @Override
    public AppUser get(Integer id) {
        return this.appUserDao.get(id);
    }
    @Override
    public List<AppUser> getAll() {
        return this.appUserDao.getAll();
    }
    @Override
    public void saveOrUpdate(AppUser obj) {
        this.appUserDao.saveOrUpdate(obj);
    }
    @Override
    public void remove(AppUser obj) {
        this.appUserDao.remove(obj);
    }
}
