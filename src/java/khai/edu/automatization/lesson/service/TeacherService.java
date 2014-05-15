/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service;

import java.util.List;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Teacher;

/**
 *
 * @author Alex
 */
public interface TeacherService {
    public List<Teacher> getAllWithChair();
    public Teacher getWithSolutionAndLessonPlans(int id);
    public Teacher getWithChair(int id);
    
    public Teacher get(String fname, String mname, String lname);
    public List<Teacher> get(Teacher t, Chair ch);
    public Teacher get(Integer id);
    public List<Teacher> getAll();
    public void saveOrUpdate(Teacher obj);
    public void remove(Teacher obj);
    public List<Teacher> getByChair(Chair ch);
}
