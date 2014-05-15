/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import khai.edu.automatization.lesson.model.Role;

/**
 *
 * @author Alex
 */
public interface RoleDao extends DaoInterface<Role>{
    public Role getByName(String name);
}
