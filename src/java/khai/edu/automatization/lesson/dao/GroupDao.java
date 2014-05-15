/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import khai.edu.automatization.lesson.model.Group;

/**
 *
 * @author Alex
 */
public interface GroupDao extends DaoInterface<Group> {
    public Group getByName(String name);
}
