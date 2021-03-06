/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service;

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
public interface LessonPlanService {
    public List<LessonPlan> getByFilterAllFields(Chair r_chair, Chair pr_chair, Speciality spec, Group group, Discipline disc, Semester sem);
    public LessonPlan getWithAll(int id);
    public LessonPlan getByIdAndReadingChair(int id, Chair ch);

    public boolean contains(LessonPlan lp);
    public LessonPlan get(Integer id);
    public List<LessonPlan> getAll();
    public void saveOrUpdate(LessonPlan obj);
    public void remove(LessonPlan obj);
}
