/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import khai.edu.automatization.lesson.model.AppUser;

/**
 *
 * @author Alex
 */
public interface AppUserDao extends DaoInterface<AppUser>{    
    public AppUser getByMail(String login);
}
