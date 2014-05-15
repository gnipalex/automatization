/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package khai.edu.automatization.lesson.service;

import java.util.List;
import khai.edu.automatization.lesson.model.SolutionPlan;

/**
 *
 * @author Alex
 */
public interface SolutionPlanService {
    public SolutionPlan get(Integer id);
    public List<SolutionPlan> getAll();
    public void saveOrUpdate(SolutionPlan obj);
    public void remove(SolutionPlan obj);
    
    public SolutionPlan getWithAll(Integer id);
    public boolean createSolution(Integer lp_id, Integer user_id, Integer teacher_id, Integer room_id);
    public boolean editSolution(Integer solution_id, Integer user_id, Integer teacher_id, Integer room_id);
}
