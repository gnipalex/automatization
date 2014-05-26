/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.dao;

import java.util.List;
import khai.edu.automatization.lesson.model.Chair;
import khai.edu.automatization.lesson.model.Discipline;
import khai.edu.automatization.lesson.model.Group;
import khai.edu.automatization.lesson.model.LessonPlan;
import khai.edu.automatization.lesson.model.Semester;
import khai.edu.automatization.lesson.model.Speciality;

/**
 *
 * @author Alex
 */
public interface LessonPlanDao extends DaoInterface<LessonPlan>{
    public boolean contains(LessonPlan lp);
    
    public List<LessonPlan> getLessonByFilter(Chair r_chair, Chair pr_chair, Speciality spec, Group group, Discipline disc, Semester sem);
    public LessonPlan getByIdAndReadingChair(int id, Chair ch);
}
