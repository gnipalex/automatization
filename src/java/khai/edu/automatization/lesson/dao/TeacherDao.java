/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import java.util.List;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Teacher;

/**
 *
 * @author Alex
 */
public interface TeacherDao extends DaoInterface<Teacher> {
    public Teacher get(String fname,String mname, String lname);
    public List<Teacher> get(Teacher t, Chair ch);
    public List<Teacher> getByChair(Chair ch);
}
