/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import khai.edu.automatization.lesson.model.Semester;

/**
 *
 * @author Alex
 */
public interface SemesterDao extends DaoInterface<Semester> {
    public Semester getByName(String name);
}
